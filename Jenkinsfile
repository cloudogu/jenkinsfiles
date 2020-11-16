#!groovy

@Library('github.com/triologygmbh/jenkinsfile@ad12c8a9') _

node('docker') { // Require a build executor with docker (label)

    catchError {

        properties([
                pipelineTriggers(createPipelineTriggers()),
                disableConcurrentBuilds(),
                buildDiscarder(logRotator(numToKeepStr: '10'))
        ])

        kubectlImage = 'lachlanevenson/k8s-kubectl:v1.19.3'
        helmImage = 'lachlanevenson/k8s-helm:v3.4.1'
        
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

                // Comment in and out some things so this deploys branch 12-x for this demo.
                // In real world projects its good practice to deploy only develop and main branches
                if (env.BRANCH_NAME == "main") {
                    //deployToKubernetes('production', versionName, 'kubeconfig-prod')
                } else { //if (env.BRANCH_NAME == 'develop') {
                    deployToKubernetes('staging', versionName, 'kubeconfig-oss-deployer')
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

void deployToKubernetes(String stage, String versionName, String credentialsId) {
    String imageRepo = "cloudogu/kitchensink"
    String imageName = "${imageRepo}:${versionName}"

    docker.withRegistry('', 'hub.docker.com-cesmarvin') {
        docker.build(imageName, '.').push()
    }

    String helmReleaseName = 'kitchensink'
    String chartFolder = "chart"
    String helmFlags = "--values=chart/values-${stage}.yaml --set image.repository=${imageRepo} --set image.tag=${versionName}"
    String helmResourceType = 'Deployment'
    String helmResourceName = ''

    docker.image(helmImage).inside("--entrypoint=''") {
        withCredentials([file(credentialsId: credentialsId, variable: 'KUBECONFIG')]) {
            sh "helm upgrade --install ${helmFlags} ${helmReleaseName} ${chartFolder}"
            helmResourceName = getHelmChartResourceName(helmResourceType, helmReleaseName, helmFlags, chartFolder)
        }
    }

    waitForRolloutToComplete(credentialsId, helmResourceType, helmResourceName)
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

String getHelmChartResourceName(String resource, String releaseName, String helmFlags, String chartLocation) {
    sh(returnStdout: true, script:
            "helm template ${releaseName} ${helmFlags} ${chartLocation} | " +
                    // Find deployment amongst all stuff rendered by helm
                    "awk '/${resource}/,/--/' | " +
                    // Find metadata and name
                    "awk '/metadata/,/  name:/' | " +
                    // grep only the "pure" name lines
                    "grep -e '^[ ]*name:' | " +
                    // keep only the first bellow metadata
                    "head -1 | " +
                    // keep only the value, drop "name:"
                    "sed 's@name:@@'")
            // Get rid of whitespaces
            .trim()
}

void waitForRolloutToComplete(String credentialsId, String resourceType, String resourceName, String timeout = '2m') {
    withKubectl(credentialsId) {
        sh "kubectl rollout status ${resourceType} ${resourceName} --timeout=${timeout}"
    }
}

void withKubectl(String credentialsId, Closure body) {

    // Namespace is set via KUBECONFIG!

    withCredentials([file(credentialsId: credentialsId, variable: 'KUBECONFIG')]) {
         docker.image(kubectlImage).inside("--entrypoint=''") {
            body()
        }
    }
}

String kubectlImage
String helmImage