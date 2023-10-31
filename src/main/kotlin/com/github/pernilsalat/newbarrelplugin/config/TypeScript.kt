package com.github.pernilsalat.newbarrelplugin.config

import icons.JavaScriptPsiIcons
import javax.swing.Icon

object TypeScript : ALanguage() {
    override val indexExtension: String
        get() = "ts"
    override val icon: Icon
        get() = JavaScriptPsiIcons.FileTypes.TypeScriptFile
}
