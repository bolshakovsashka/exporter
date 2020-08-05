package exporter

import com.intellij.notification.NotificationDisplayType
import com.intellij.notification.NotificationGroup
import com.intellij.notification.NotificationType
import com.intellij.openapi.project.Project
import exporter.action.LocizeGenerateAction

class NotificationManager {
    companion object {
        fun showInfoNotification(
            id: String,
            title: String,
            message: String,
            project: Project
        ) {
            NotificationGroup(id, NotificationDisplayType.BALLOON, true).apply {
                createNotification(
                    title,
                    message,
                    NotificationType.INFORMATION,
                    null
                )
                    .notify(project)
            }
        }

        fun showErrorNotification(
            id: String,
            title: String,
            message: String,
            project: Project
        ) {
            NotificationGroup(id, NotificationDisplayType.BALLOON, true).apply {
                createNotification(
                    title,
                    message,
                    NotificationType.ERROR,
                    null
                )
                    .notify(project)
            }
        }
    }
}