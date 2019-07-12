@Library('github.com/triologygmbh/jenkinsfile@6610cd92') _

node {

    properties([
            pipelineTriggers(createPipelineTriggers()),
            disableConcurrentBuilds(),
            buildDiscarder(logRotator(numToKeepStr: '10'))
    ])

    catchError {

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