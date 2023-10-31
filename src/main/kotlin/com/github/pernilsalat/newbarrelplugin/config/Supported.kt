package com.github.pernilsalat.newbarrelplugin.config

object Supported {
    val extensions = mapOf<String, ALanguage>(
        "js" to JavaScript,
        "jsx" to JavaScript,
        "ts" to TypeScript,
        "tsx" to TypeScript,
        "vue" to Vue,
        SCSS.indexExtension to SCSS,
    );

    val List = listOf<ALanguage>(JavaScript, TypeScript, SCSS, Vue)
}