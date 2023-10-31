package com.github.pernilsalat.newbarrelplugin.config

import icons.SassIcons
import javax.swing.Icon

object SCSS : ALanguage() {
    override val indexName: String
        get() = "_index"

    override val icon: Icon
        get() = SassIcons.Sass

    override fun exportLineBuilder(fileName: String): String {
        return "@forward './$fileName';"
    }
}