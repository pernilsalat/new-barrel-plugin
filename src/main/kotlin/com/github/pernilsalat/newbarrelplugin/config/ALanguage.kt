package com.github.pernilsalat.newbarrelplugin.config

import com.intellij.lang.Language
import com.intellij.openapi.fileTypes.LanguageFileType
import javax.swing.Icon

abstract class ALanguage() {
    val name: String by lazy {
        this::class.simpleName!!
    }

    protected open val indexName = "index"

    val fileType: LanguageFileType by lazy {
        val language = Language.findLanguageByID(name)!!
        language.associatedFileType!!
    }

    val defaultLanguageExtension by lazy {
        fileType.defaultExtension
    }

    open val indexExtension: String by lazy {
        fileType.defaultExtension
    }

    val indexFullName: String by lazy {
        "$indexName.$indexExtension"
    }

    abstract val icon: Icon;

    open fun exportLineBuilder(fileName: String): String {
        return "export * from './$fileName';"
    }
}