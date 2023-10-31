package com.github.pernilsalat.newbarrelplugin.config

import com.intellij.lang.javascript.TypeScriptJSXFileType
import com.intellij.openapi.fileTypes.LanguageFileType
import javax.swing.Icon

object TSX : ALanguage() {
    override val indexExtension: String
        get() = "ts"
    override val fileType: LanguageFileType
        get() = TypeScriptJSXFileType.INSTANCE
    override val icon: Icon
        get() = TypeScriptJSXFileType.INSTANCE.icon
}
