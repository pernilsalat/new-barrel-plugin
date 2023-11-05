package com.github.pernilsalat.newbarrelplugin.services

import com.github.pernilsalat.newbarrelplugin.config.ALanguage
import com.github.pernilsalat.newbarrelplugin.config.JavaScript
import com.github.pernilsalat.newbarrelplugin.config.Supported
import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.Service
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage
import com.intellij.util.xmlb.Converter
import com.intellij.util.xmlb.annotations.Attribute

@Service()
@State(name = "NewDialogForm", storages = [Storage("NewDialogForm.xml")])
class FormPersisted : PersistentStateComponent<Form> {
    private var form = Form()

    override fun getState(): Form {
        return form
    }

    override fun loadState(state: Form) {
        form = state
    }
}

class LanguageConverter : Converter<ALanguage>() {
    override fun toString(value: ALanguage): String {
        return value.indexExtension
    }

    override fun fromString(value: String): ALanguage? {
        return Supported.extensions[value]
    }
}

data class Form(
    @Attribute("language", converter = LanguageConverter::class)
    var language: ALanguage = JavaScript,

    @Transient
    var name: String = "",

    var isJSandTS: Boolean = false,
) {
    override fun toString(): String {
        return "{ language: ${language.id}, name: $name, isJSandTS: $isJSandTS }"
    }
}
