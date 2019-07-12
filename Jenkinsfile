@Library('github.com/triologygmbh/jenkinsfile@6610cd92') _

node('docker') { // Require a build executor with docker (label)

    properties([
            pipelineTriggers(createPipelineTriggers()),
            disableConcurrentBuilds(),
            buildDiscarder(logRotator(numToKeepStr: '10'))
    ])

    docker.image('maven:3.5.0-jdk-8').inside {

        catchError {

            stage('Checkout') {
                checkout scm
            }

            stage('Build') {
                sh 'mvn clean install -DskipTests'
                archiveArtifacts '**/target/*.*ar'
            }

            parallel(
                    unitTest: {
                        stage('Unit Test') {
                            sh 'mvn test'
                        }
                    },
                    integrationTest: {
                        stage('Integration Test') {
                            if (isTimeTriggeredBuild()) {
                                sh 'mvn verify -DskipUnitTests -Parq-wildfly-swarm '
                            }
                        }
                    }
            )
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