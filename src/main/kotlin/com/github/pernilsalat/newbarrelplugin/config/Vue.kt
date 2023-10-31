package com.github.pernilsalat.newbarrelplugin.config

import org.jetbrains.vuejs.CreateVueSingleFileComponentAction
import org.jetbrains.vuejs.VuejsIcons
import javax.swing.Icon
object Vue : ALanguage() {
    override val icon: Icon
        get() = VuejsIcons.Vue

    override val indexExtension: String
        get() = "js"
}