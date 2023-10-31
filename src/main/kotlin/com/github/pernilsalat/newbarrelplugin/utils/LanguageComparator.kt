package com.github.pernilsalat.newbarrelplugin.utils

import com.intellij.lang.Language

abstract class LanguageComparator {
    abstract fun compare(src: Language, dest: Language): Boolean
}