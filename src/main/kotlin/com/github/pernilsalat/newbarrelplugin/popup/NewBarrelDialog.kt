package com.github.pernilsalat.newbarrelplugin.popup

import com.github.pernilsalat.newbarrelplugin.config.ALanguage
import com.github.pernilsalat.newbarrelplugin.config.Supported
import com.github.pernilsalat.newbarrelplugin.services.FormPersisted
import com.intellij.openapi.components.service
import com.intellij.openapi.ui.ComboBox
import com.intellij.openapi.ui.DialogWrapper
import com.intellij.openapi.ui.ValidationInfo
import com.intellij.ui.dsl.builder.Cell
import com.intellij.ui.dsl.builder.bindItem
import com.intellij.ui.dsl.builder.bindSelected
import com.intellij.ui.dsl.builder.bindText
import com.intellij.ui.dsl.builder.panel
import com.intellij.ui.dsl.builder.whenItemSelectedFromUi
import com.intellij.ui.layout.ComponentPredicate
import com.intellij.ui.layout.listCellRenderer
import javax.swing.JComponent

class NewBarrelDialog : DialogWrapper(true) {
    val formPersisted: FormPersisted = service<FormPersisted>()

    init {
        super.setTitle("Create new Barrel")
        super.init()
    }

    override fun createCenterPanel(): JComponent {
        val checkBoxEnablePredicate = { it: ALanguage -> it.isJSandTS }

        return panel {
            row("Language") {
                val langPicker: Cell<ComboBox<ALanguage>> = comboBox<ALanguage>(
                    items = Supported.List,
                    renderer = listCellRenderer<ALanguage> { value, _, _ ->
                        run {
                            text = value.id
                            icon = value.icon
                        }
                    },
                ).bindItem(formPersisted.state::language)
                checkBox("ts")
                    .visible(false)
                    .bindSelected(formPersisted.state::isJSandTS)
                    .enabledIf(ComboBoxPredicate<ALanguage>(langPicker, checkBoxEnablePredicate, checkBoxEnablePredicate(formPersisted.state.language)))
            }
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

    inner class ComboBoxPredicate<T>(
        private val comboBox: Cell<ComboBox<T>>,
        private val predicate: (T) -> Boolean,
        private val initialValue: Boolean,
    ) : ComponentPredicate() {

        override fun addListener(listener: (Boolean) -> Unit) {
            comboBox.whenItemSelectedFromUi {
                listener(predicate(it))
            }
        }

        override fun invoke(): Boolean {
            return initialValue
        }
    }
}
