#!groovy

@Library('github.com/triologygmbh/jenkinsfile@f38945a') _

// Query outside of node, in order to get pending script approvals
//boolean isTimeTriggered = isTimeTriggeredBuild()

node('docker') { // Require a build executor with docker (label)

    catchError {

        properties([
                pipelineTriggers(createPipelineTriggers()),
                disableConcurrentBuilds(),
                buildDiscarder(logRotator(numToKeepStr: '10'))
        ])

        stage('Checkout') {
            checkout scm
        }

        String versionName = createVersion()

        stage('Build') {
            mvn "clean install -DskipTests -Parq-wildfly-swarm -Drevision=${versionName}"
            archiveArtifacts '**/target/*.*ar'
        }

        parallel(
                unitTest: {
                    stage('Unit Test') {
                        mvn "test -Drevision=${versionName}"
                    }
                },
                integrationTest: {
                    stage('Integration Test') {
                        if (isNightly()) {
                            mvn "verify -DskipUnitTests -Parq-wildfly-swarm -Drevision=${versionName}"
                        }
                    }
                }
        )

        stage('Statical Code Analysis') {
            analyzeWithSonarQubeAndWaitForQualityGoal()
        }

        stage('Deploy') {
            if (currentBuild.currentResult == 'SUCCESS') {

                // Comment in and out some things so this deploys branch 11-x for this demo.
                // In real world projects its good practice to deploy only develop and master branches
                if (env.BRANCH_NAME == "master") {
                    //deployToKubernetes(versionName, 'kubeconfig-prod', 'jenkinsfile.cloudogu.com')
                } else { //if (env.BRANCH_NAME == 'develop') {
                    deployToKubernetes(versionName, 'kubeconfig-staging', '35.202.189.144')
                }
            }
        }
    }

    // Archive Unit and integration test results, if any
    junit allowEmptyResults: true,
            testResults: '**/target/surefire-reports/TEST-*.xml, **/target/failsafe-reports/*.xml'

    mailIfStatusChanged env.EMAIL_RECIPIENTS
}


def createPipelineTriggers() {
    if (env.BRANCH_NAME == 'master') {
        // Run a nightly only for master
        return [cron('H H(0-3) * * 1-5')]
    }
    return []
}

String createVersion() {
    // E.g. "201708140933"
    String versionName = "${new Date().format('yyyyMMddHHmm')}"

    if (env.BRANCH_NAME != "master") {
        versionName += '-SNAPSHOT'
    }
    echo "Building version $versionName on branch ${env.BRANCH_NAME}"
    currentBuild.description = versionName
    return versionName
}

void deployToKubernetes(String versionName, String credentialsId, String hostname) {

    String dockerRegistry = 'us.gcr.io/ces-demo-instances'
    String imageName = "$dockerRegistry/kitchensink:${versionName}"

    docker.withRegistry("https://$dockerRegistry", 'docker-us.gcr.io/ces-demo-instances') {
        docker.build(imageName, '.').push()
    }

    withCredentials([file(credentialsId: credentialsId, variable: 'kubeconfig')]) {

        withEnv(["IMAGE_NAME=$imageName"]) {

            kubernetesDeploy(
                    credentialsType: 'KubeConfig',
                    kubeConfig: [path: kubeconfig],
                    configs: 'k8s/deployment.yaml',
                    enableConfigSubstitution: true
            )
        }
    }

    timeout(time: 3, unit: 'MINUTES') {
        waitUntil {
            sleep(time: 10, unit: 'SECONDS')
            isVersionDeployed(versionName, "http://$hostname/rest/version")
        }
    }
}

boolean isVersionDeployed(String expectedVersion, String versionEndpoint) {
    def deployedVersion = sh(returnStdout: true, script: "curl -s $versionEndpoint").trim()
    echo "Deployed version returned by $versionEndpoint: $deployedVersion. Waiting for $expectedVersion."
    return expectedVersion == deployedVersion
}

/**
 * Note that this requires the following script approvals by your jenkins administrator
 * (via https://JENKINS-URL/scriptApproval/):
 * <br/>
 * {@code method hudson.model.Run getCauses}
 * {@code method org.jenkinsci.plugins.workflow.support.steps.build.RunWrapper getRawBuild}.
 * <br/><br/>
 * Note that that the pending script approvals only appear if this method is called <b>outside a {@code node}</b>
 * within the pipeline!
 *
 * @return {@code true} if the build was time triggered, otherwise {@code false}
 */
boolean isTimeTriggeredBuild() {
    for (Object currentBuildCause : currentBuild.rawBuild.getCauses()) {
        return currentBuildCause.class.getName().contains('TimerTriggerCause')
    }
    return false
}

void analyzeWithSonarQubeAndWaitForQualityGoal() {
    withSonarQubeEnv('sonarcloud.io') {
        mvn "$SONAR_MAVEN_GOAL -Dsonar.host.url=$SONAR_HOST_URL -Dsonar.login=$SONAR_AUTH_TOKEN " +
                // Here, we could define e.g. sonar.organization, needed for sonarcloud.io
                "$SONAR_EXTRA_PROPS " +
                // Addionally needed when using the branch plugin (e.g. on sonarcloud.io)
                "-Dsonar.branch.name=$BRANCH_NAME -Dsonar.branch.target=master"
    }
    timeout(time: 10, unit: 'MINUTES') { // Normally, this takes only some ms. sonarcloud.io might take minutes, though :-(
        def qg = waitForQualityGate()
        if (qg.status != 'OK') {
            echo "Pipeline unstable due to quality gate failure: ${qg.status}"
            currentBuild.result = 'UNSTABLE'
        }
    }
}
