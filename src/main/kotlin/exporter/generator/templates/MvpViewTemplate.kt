package exporter.generator.templates

class MvpViewTemplate {
    fun getTemplate(packageName: String, classesPrefix: String): String {
        return "package com.synesis.gem.${packageName}.presentation.presenter\n" +
                "\n" +
                "import com.synesis.gem.core.ui.base.BaseView\n" +
                "\n" +
                "interface ${classesPrefix.capitalize()}View : BaseView"
    }
}