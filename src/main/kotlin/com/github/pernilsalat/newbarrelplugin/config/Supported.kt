package com.github.pernilsalat.newbarrelplugin.config

object Supported {
    val extensions = mapOf<String, ALanguage>(
        "js" to JavaScript,
        "jsx" to JSX,
        "ts" to TypeScript,
        "tsx" to TSX,
        "vue" to Vue,
        "scss" to SCSS,
    )

    val List: Collection<ALanguage> = extensions.values
}
