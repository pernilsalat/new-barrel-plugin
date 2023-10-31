package com.github.pernilsalat.newbarrelplugin.config

import com.intellij.lang.ecmascript6.JSXHarmonyFileType
import com.intellij.openapi.fileTypes.LanguageFileType
import javax.swing.Icon

object JSX : ALanguage() {
    override val fileType: LanguageFileType
        get() = JSXHarmonyFileType.INSTANCE
    override val icon: Icon
        get() = JSXHarmonyFileType.INSTANCE.icon
}
