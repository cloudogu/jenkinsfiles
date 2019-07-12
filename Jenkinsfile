#!groovy

@Library('github.com/triologygmbh/jenkinsfile@ad12c8a9') _

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
                        if (isTimeTriggeredBuild()) {
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
                    //deployToKubernetes(versionName, 'kubeconfig-prod', getServiceIp('kubeconfig-prod'))
                } else { //if (env.BRANCH_NAME == 'develop') {
                    deployToKubernetes(versionName, 'kubeconfig-oss-deployer', getServiceIp('kubeconfig-oss-deployer'))
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
    echo "Building version ${versionName} on branch ${env.BRANCH_NAME}"
    currentBuild.description = versionName
    return versionName
}

void deployToKubernetes(String versionName, String credentialsId, String hostname) {

    String imageName = "cloudogu/kitchensink:${versionName}"

    docker.withRegistry('', 'hub.docker.com-cesmarvin') {
        docker.build(imageName, '.').push()
    }

    withCredentials([file(credentialsId: credentialsId, variable: 'kubeconfig')]) {

        withEnv(["IMAGE_NAME=${imageName}"]) {

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
            isVersionDeployed(versionName, "http://${hostname}/rest/version")
        }
    }
}

boolean isVersionDeployed(String expectedVersion, String versionEndpoint) {
    // "|| true" is needed to avoid failing builds on connection refused (e.g. during first deployment)
    def deployedVersion = sh(returnStdout: true, script: "curl -s ${versionEndpoint} || true").trim()
    echo "Deployed version returned by ${versionEndpoint}: ${deployedVersion}. Waiting for ${expectedVersion}."
    return expectedVersion == deployedVersion
}

void analyzeWithSonarQubeAndWaitForQualityGoal() {
    withSonarQubeEnv('sonarcloud.io-cloudogu') {
        mvn "${SONAR_MAVEN_GOAL} -Dsonar.host.url=${SONAR_HOST_URL} -Dsonar.login=${SONAR_AUTH_TOKEN} " +
                // Here, we could define e.g. sonar.organization, needed for sonarcloud.io
                "${SONAR_EXTRA_PROPS} " +
                // Addionally needed when using the branch plugin (e.g. on sonarcloud.io)
                "-Dsonar.branch.name=${BRANCH_NAME} -Dsonar.branch.target=master"
    }
    timeout(time: 10, unit: 'MINUTES') { // Normally, this takes only some ms. sonarcloud.io might take minutes, though :-(
        def qg = waitForQualityGate()
        if (qg.status != 'OK') {
            unstable("Pipeline unstable due to quality gate failure: ${qg.status}")
        }
    }
}

String getServiceIp(String kubeconfigCredential) {

    withCredentials([file(credentialsId: kubeconfigCredential, variable: 'kubeconfig')]) {

        String serviceName = 'kitchensink' // See k8s/service.yaml

        // Using kubectl is so much easier than plain REST via curl (parsing info from kubeconfig is cumbersome!)
        return sh(returnStdout: true, script:
                "docker run -v ${kubeconfig}:/root/.kube/config lachlanevenson/k8s-kubectl:v1.9.5" +
                        " get svc ${serviceName}" +
                        ' |  awk \'{print $4}\'  | sed -n 2p'
        ).trim()
    }
}