@Library('github.com/triologygmbh/jenkinsfile@4c739f9') _

// Query outside of node, in order to get pending script approvals
//boolean isTimeTriggered = isTimeTriggeredBuild()

node('docker') {

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
                        if (isNightly()) {
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