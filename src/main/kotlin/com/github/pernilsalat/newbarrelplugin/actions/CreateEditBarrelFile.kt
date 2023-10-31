package com.github.pernilsalat.newbarrelplugin.actions

import com.github.pernilsalat.newbarrelplugin.config.Supported
import com.github.pernilsalat.newbarrelplugin.notification.Notifier
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.PlatformDataKeys
import com.intellij.openapi.application.WriteAction
import com.intellij.openapi.fileEditor.FileEditorManager
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiDirectory
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.intellij.psi.PsiFileFactory

class CreateEditBarrelFile : MultipleFilesSelected() {
    override fun actionPerformed(event: AnActionEvent) {
        val project: Project = event.project!!
        val fileEditorManager: FileEditorManager = FileEditorManager.getInstance(project)
        val fileFactory: PsiFileFactory = PsiFileFactory.getInstance(project)
        val psiElements: Array<PsiElement> = event.getData(PlatformDataKeys.PSI_ELEMENT_ARRAY)!!
        var psiFiles: List<PsiFile> = psiElements.map { it.containingFile }
        val first: PsiFile = psiFiles[0]
        val parent: PsiDirectory = first.parent!!
        val fileExtension = first.fileType.defaultExtension
        val language = Supported.extensions[fileExtension]
        if (language == null) {
            val msg = "Supported extensions: ${Supported.extensions.keys}. Found: $fileExtension"
            Notifier.notifyError(project, msg)

            return
        }
        psiFiles = psiFiles.filter { it.name != language.indexFullName }

        WriteAction.run<Throwable> {
            val indexFile: PsiFile? = parent.findFile(language.indexFullName)
            val content: String = psiFiles.joinToString(separator = "\n") {
                val name = it.name.substringBeforeLast(".")
                language.exportLineBuilder(name)
            }

            if (indexFile == null) {
                val newIndexFile: PsiFile = fileFactory.createFileFromText(
                    language.indexFullName,
                    language.fileType,
                    content,
                )
                parent.add(newIndexFile)
                fileEditorManager.openFile(newIndexFile.virtualFile, true)

                val msg = "Entrypoint \"${language.indexFullName}\" successfully created"
                Notifier.notifySuccess(project, msg)
            } else {
                var newIndexFile: PsiFile = fileFactory.createFileFromText(
                    language.indexFullName,
                    language.fileType,
                    "${indexFile.text}\n$content",
                )
                indexFile.delete()
                newIndexFile = parent.add(newIndexFile).containingFile

                fileEditorManager.openFile(newIndexFile.virtualFile, true, true)

                val msg = "Entrypoint \"${language.indexFullName}\" lines successfully created"
                Notifier.notifySuccess(project, msg)
            }
        }
    }
}
