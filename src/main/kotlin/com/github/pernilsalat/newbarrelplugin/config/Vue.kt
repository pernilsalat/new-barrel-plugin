package com.github.pernilsalat.newbarrelplugin.config

import org.jetbrains.vuejs.VuejsIcons
import javax.swing.Icon
object Vue : ALanguage() {
    override val isJSandTS: Boolean
        get() = true
    override val icon: Icon
        get() = VuejsIcons.Vue
}
