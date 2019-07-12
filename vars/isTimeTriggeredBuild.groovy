def call() {
    for (Object currentBuildCause : currentBuild.getBuildCauses()) {
        if (currentBuildCause._class.contains('TimerTriggerCause')) {
            return true
        }
    }
    return false
}