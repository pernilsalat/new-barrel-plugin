package com.github.pernilsalat.newbarrelplugin.config

import com.intellij.lang.Language
import com.intellij.openapi.fileTypes.LanguageFileType
import javax.swing.Icon

abstract class ALanguage() {
    open val name: String by lazy {
        this::class.simpleName!!
    }

    open val isJSandTS = false

    protected open val indexName = "index"

    open val fileType: LanguageFileType by lazy {
        val language = Language.findLanguageByID(name)!!
        language.associatedFileType!!
    }

    open val defaultLanguageExtension by lazy {
        fileType.defaultExtension
    }

    open val indexExtension: String by lazy {
        "js"
    }

    val indexFullName: String by lazy {
        "$indexName.$indexExtension"
    }

    abstract val icon: Icon

    open fun exportLineBuilder(fileName: String): String {
        return "export * from './$fileName';"
    }
}
