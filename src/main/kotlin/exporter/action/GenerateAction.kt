package exporter.action

import com.intellij.notification.NotificationDisplayType
import com.intellij.notification.NotificationGroup
import com.intellij.notification.NotificationType
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import exporter.generator.strings.StringGenerator
import exporter.steps.ModuleSettingsDialog

class GenerateAction : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        when (e.presentation.text) {
            "Generate New Module" -> {
                generateNewModule(e)
            }
            "Generate Strings" -> {
                generateStrings(e)
            }
        }
    }

    private fun generateNewModule(e: AnActionEvent) {
        val dialog = ModuleSettingsDialog(e.project!!.basePath!!)
        dialog.pack()
        dialog.setLocationRelativeTo(null);  // *** this will center your app ***
        dialog.isVisible = true
    }

    private fun generateStrings(e: AnActionEvent) {
        val noti = NotificationGroup("extractor", NotificationDisplayType.BALLOON, true)
        noti.createNotification(
            "String Exporter",
            "String export started",
            NotificationType.INFORMATION,
            null
        ).notify(e.project)
        try {
            StringGenerator().generate(e)
            val noti2 = NotificationGroup("extractor", NotificationDisplayType.BALLOON, true)
            noti2.createNotification(
                "String Exporter",
                "String export finished",
                NotificationType.INFORMATION,
                null
            ).notify(e.project)
        } catch (exception: Exception) {
            val noti2 = NotificationGroup("extractor", NotificationDisplayType.BALLOON, true)
            noti2.createNotification(
                "String Exporter",
                "String export failed with exception: ${exception.message}",
                NotificationType.INFORMATION,
                null
            ).notify(e.project)
        }
    }
}