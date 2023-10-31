package com.github.pernilsalat.newbarrelplugin.actions

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.PlatformDataKeys
import com.intellij.openapi.vfs.VirtualFile

abstract class MultipleFilesSelected : AnAction() {
    override fun update(event: AnActionEvent) {
        val selectedFiles: Array<VirtualFile> = event.getData(PlatformDataKeys.VIRTUAL_FILE_ARRAY) ?: emptyArray()
        super.update(event)

        var canShow = false
        if (selectedFiles.isNotEmpty()) {
            val firstFile = selectedFiles[0]
            val firstFileType = firstFile.fileType.defaultExtension
            val firstFileParentName = firstFile.parent.name

            canShow = selectedFiles.all {
                !it.isDirectory &&
                    it.fileType.defaultExtension == firstFileType &&
                    it.parent.name == firstFileParentName
            }
        }

        event.presentation.setEnabledAndVisible(canShow)
    }
}
