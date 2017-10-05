def call(String recipients) {
    // Also send "back to normal" emails. Mailer seems to check build result, but SUCCESS is not set during pipeline execution.
    if (currentBuild.currentResult == 'SUCCESS') {
        currentBuild.result = 'SUCCESS'
    }
    step([$class: 'Mailer', recipients: recipients])
}