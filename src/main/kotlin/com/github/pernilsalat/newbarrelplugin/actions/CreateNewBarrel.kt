package com.github.pernilsalat.newbarrelplugin.actions

import com.github.pernilsalat.newbarrelplugin.config.ALanguage
import com.github.pernilsalat.newbarrelplugin.notification.Notifier
import com.github.pernilsalat.newbarrelplugin.popup.NewBarrelDialog
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.PlatformDataKeys
import com.intellij.openapi.application.WriteAction
import com.intellij.openapi.fileEditor.FileEditorManager
import com.intellij.openapi.project.Project
import com.intellij.openapi.roots.ProjectFileIndex
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.PsiFileFactory
import com.intellij.psi.PsiManager

class CreateNewBarrel : SingleSelection() {
    override fun actionPerformed(event: AnActionEvent) {
        val project: Project = event.project!!

        ProjectFileIndex.getInstance(project).iterateContent {
            true
        }

        val psiManager: PsiManager = PsiManager.getInstance(project)
        val fileFactory: PsiFileFactory = PsiFileFactory.getInstance(project)
        val fileEditorManager: FileEditorManager = FileEditorManager.getInstance(project)

        var virtualFile: VirtualFile = event.getData(PlatformDataKeys.VIRTUAL_FILE)!!
        if (!virtualFile.isDirectory) {
            virtualFile = virtualFile.parent
        }
        val psiDirectory = psiManager.findDirectory(virtualFile)!!

        val dialog = NewBarrelDialog()
        if (dialog.showAndGet()) {
            val (language: ALanguage, name: String) = dialog.formPersisted.state

            if (psiDirectory.findSubdirectory(name) != null) {
                val msg = "Folder \"$name\" already exists at: \"${virtualFile.path}\""
                Notifier.notifyError(project, msg)

                return
            }

            WriteAction.run<Throwable> {
                val subDirectory = psiDirectory.createSubdirectory(name)
                val fileName = "$name.${language.defaultLanguageExtension}"
                val newFile = subDirectory.createFile(fileName)

                val indexFile = fileFactory.createFileFromText(
                    language.indexFullName,
                    language.fileType,
                    language.exportLineBuilder(name),
                )
                subDirectory.add(indexFile)
                fileEditorManager.openFile(newFile.virtualFile, true)

                val msg = "Barrel \"$name\" successfully created"
                Notifier.notifySuccess(project, msg)
            }
        }
    }
}
