package exporter.action

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import exporter.NotificationManager
import exporter.generator.strings.engines.excel.StringGenerator

class StringsGenerateAction : AnAction() {

    override fun actionPerformed(e: AnActionEvent) {
        generateStrings(e, false)
    }

    private fun generateStrings(e: AnActionEvent, useNewDock: Boolean) {
        e.project?.let {
            NotificationManager.showInfoNotification(
                id = "exporter.strings",
                title = "String Exporter",
                message = "String export started",
                project = it
            )
        }
        try {
            if (useNewDock) {
                StringGenerator().generate(e)
            } else {
                StringGenerator().generateOld(e)
            }
            e.project?.let {
                NotificationManager.showInfoNotification(
                    id = "exporter.strings",
                    title = "String Exporter",
                    message = "String export successfully finished",
                    project = it
                )
            }
        } catch (exception: Exception) {
            exception.printStackTrace()
            e.project?.let {
                NotificationManager.showInfoNotification(
                    id = "exporter.strings",
                    title = "String Exporter",
                    message = "String export failed with exception: ${exception.message}",
                    project = it
                )
            }
        }
    }
}