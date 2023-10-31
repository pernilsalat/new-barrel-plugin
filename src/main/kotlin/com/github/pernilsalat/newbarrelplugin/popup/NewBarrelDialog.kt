package com.github.pernilsalat.newbarrelplugin.popup

import com.github.pernilsalat.newbarrelplugin.config.*
import com.intellij.openapi.components.service
import com.intellij.openapi.ui.DialogWrapper
import com.intellij.openapi.ui.ValidationInfo
import com.intellij.ui.dsl.builder.bind
import com.intellij.ui.dsl.builder.bindText
import com.intellij.ui.dsl.builder.panel
import javax.swing.JComponent
class NewBarrelDialog : DialogWrapper(true) {
    val formPersisted: FormPersisted = service<FormPersisted>()

    init {
        super.setTitle("Create new Barrel");
        super.init();
    }

    override fun createCenterPanel(): JComponent {
        return panel {
            buttonsGroup("Select Language:") {
                for (language in Supported.List) {
                    row {
                        icon(language.icon)
                        radioButton(language.name, language)
                    }
                }
            }.bind(formPersisted.state::language)
            row("Name") {
                textField()
                    .focused()
                    .bindText(formPersisted.state::name)
                    .validation {
                        if (it.text.isEmpty()) {
                            ValidationInfo("Name cannot be empty", it)
                        } else {
                            null
                        }
                    }
            }
        }
    }

    // data class Form(var language: ALanguage, var name: String);
}