package exporter.action

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import exporter.NotificationManager
import exporter.generator.provide.ClassProvider

class ProvideClassAction : AnAction() {

    override fun actionPerformed(e: AnActionEvent) {
        provideClass(e)
    }

    private fun provideClass(actionEvent: AnActionEvent) {
        try {
            ClassProvider().provide(actionEvent)
            actionEvent.project?.let {
                NotificationManager.showInfoNotification(
                    id = "exporter.provide",
                    title = "Provide finished",
                    message = "Provide function copied to clipboard",
                    project = it
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()
            actionEvent.project?.let {
                NotificationManager.showErrorNotification(
                    id = "exporter.provide",
                    title = "Provide failed",
                    message = "Provide faield with exception: " + e.message,
                    project = it
                )
            }
        }
    }
}