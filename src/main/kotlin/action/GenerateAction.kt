package action

import com.intellij.notification.NotificationDisplayType
import com.intellij.notification.NotificationGroup
import com.intellij.notification.NotificationType
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import generator.Generator

class GenerateAction : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        val noti = NotificationGroup("extractor", NotificationDisplayType.BALLOON, true)
        noti.createNotification(
            "String Exporter",
            "String export started",
            NotificationType.INFORMATION,
            null
        ).notify(e.project)
        try {
            Generator().generate(e)
        } catch (exception: Exception) {
            val noti2 = NotificationGroup("extractor", NotificationDisplayType.BALLOON, true)
            noti2.createNotification(
                "String Exporter",
                "String export failed with exception: ${exception.message}",
                NotificationType.INFORMATION,
                null
            ).notify(e.project)
        }

        val noti2 = NotificationGroup("extractor", NotificationDisplayType.BALLOON, true)
        noti2.createNotification(
            "String Exporter",
            "String export finished",
            NotificationType.INFORMATION,
            null
        ).notify(e.project)
    }
}