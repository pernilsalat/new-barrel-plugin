package com.github.pernilsalat.newbarrelplugin.notification

import com.intellij.notification.NotificationGroupManager
import com.intellij.notification.NotificationType
import com.intellij.openapi.project.Project

object Notifier {
    private const val NOTIFICATION_GROUP_ID = "Barrels notification"
    private val notificationManager = NotificationGroupManager
        .getInstance()
        .getNotificationGroup(NOTIFICATION_GROUP_ID)

    private fun notifyMessage(project: Project, content: String, type: NotificationType) {
        notificationManager
            .createNotification(content, type)
            .notify(project);
    }

    fun notifyError(project: Project, content: String) {
        notifyMessage(project, content, NotificationType.ERROR)
    }

    fun notifySuccess(project: Project, content: String) {
        notifyMessage(project, content, NotificationType.INFORMATION)
    }
}