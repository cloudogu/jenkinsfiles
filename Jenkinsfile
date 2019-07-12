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

        stage('Build') {
            mvn 'clean install -DskipTests'
            archiveArtifacts '**/target/*.*ar'
        }

        parallel(
                unitTest: {
                    stage('Unit Test') {
                        mvn 'test'
                    }
                },
                integrationTest: {
                    stage('Integration Test') {
                        if (isTimeTriggeredBuild()) {
                            mvn 'verify -DskipUnitTests -Parq-wildfly-swarm '
                        }
                    }
                }
        )

        stage('Statical Code Analysis') {
            analyzeWithSonarQubeAndWaitForQualityGoal()
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

void analyzeWithSonarQubeAndWaitForQualityGoal() {
    withSonarQubeEnv('sonarcloud.io-cloudogu') {
        mvn "$SONAR_MAVEN_GOAL -Dsonar.host.url=$SONAR_HOST_URL -Dsonar.login=$SONAR_AUTH_TOKEN " +
                // Here, we could define e.g. sonar.organization, needed for sonarcloud.io
                "$SONAR_EXTRA_PROPS " +
                // Addionally needed when using the branch plugin (e.g. on sonarcloud.io)
                "-Dsonar.branch.name=$BRANCH_NAME -Dsonar.branch.target=master"
    }
    timeout(time: 2, unit: 'MINUTES') {
        def qg = waitForQualityGate()
        if (qg.status != 'OK') {
            unstable("Pipeline unstable due to quality gate failure: ${qg.status}")
        }
    }
}
