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

class CreateEditEntrypointFile : MultipleFilesSelected() {
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
            var content: String = psiFiles.joinToString(separator = "\n") {
                val name = it.name.substringBeforeLast(".")
                language.exportLineBuilder(name)
            }
            var msg = "Entrypoint \"${language.indexFullName}\" successfully created"

            if (indexFile != null) {
                content = "${indexFile.text}\n$content"
                msg = "Entrypoint \"${language.indexFullName}\" lines successfully added"
                indexFile.delete()
            }

            val newIndexFile: PsiFile = fileFactory.createFileFromText(
                language.indexFullName,
                language.fileType,
                content,
            )
            val addedIndex = parent.add(newIndexFile)
            fileEditorManager.openFile(addedIndex.containingFile.virtualFile, true)

            Notifier.notifySuccess(project, msg)
        }
    }
}
