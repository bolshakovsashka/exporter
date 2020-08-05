package exporter.action

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import exporter.NotificationManager
import exporter.generator.strings.engines.locize.LocizeGenerator

class LocizeGenerateAction : AnAction() {

    override fun actionPerformed(actionEvent: AnActionEvent) {
        showStartBalloon(actionEvent)
        try {
            LocizeGenerator().generate(actionEvent)
            showEndBalloon(actionEvent)
        } catch (e: Throwable) {
            e.printStackTrace()
            showErrorBalloon(actionEvent, e)
        }
    }

    private fun showStartBalloon(anActionEvent: AnActionEvent) {
        anActionEvent.project?.let {
            NotificationManager.showInfoNotification(
                id = DISPLAY_ID,
                title = "String Exporter",
                message = "String export from Locize.io started",
                project = it
            )
        }
    }

    private fun showEndBalloon(anActionEvent: AnActionEvent) {
        anActionEvent.project?.let {
            NotificationManager.showInfoNotification(
                id = DISPLAY_ID,
                title = "String Exporter",
                message = "String export from Locize.io finished",
                project = it
            )
        }
    }

    private fun showErrorBalloon(anActionEvent: AnActionEvent, throwable: Throwable) {
        anActionEvent.project?.let {
            NotificationManager.showErrorNotification(
                id = DISPLAY_ID,
                title = "String Exporter",
                message = "String export from Locize.io failed with exception: " + throwable.message,
                project = it
            )
        }
    }

    companion object {
        private const val DISPLAY_ID = "exoprter.locize"
    }
}