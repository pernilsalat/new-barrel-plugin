package com.github.pernilsalat.newbarrelplugin.actions

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.PlatformDataKeys
import com.intellij.openapi.diagnostic.thisLogger
import com.intellij.openapi.vfs.VirtualFile

abstract class SingleSelection : AnAction() {
    override fun update(event: AnActionEvent) {
        val selectedFiles: Array<VirtualFile> = event.getData(PlatformDataKeys.VIRTUAL_FILE_ARRAY) ?: emptyArray();
        super.update(event)

        thisLogger().warn(selectedFiles.toString())
        event.presentation.setEnabledAndVisible(selectedFiles.size == 1)
    }
}