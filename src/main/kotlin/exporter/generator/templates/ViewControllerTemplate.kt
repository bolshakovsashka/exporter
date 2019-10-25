package exporter.generator.templates

class ViewControllerTemplate {

    fun getTemplate(packageName: String, classesPrefix: String): String {
        return "package com.synesis.gem.$packageName.presentation.view\n" +
                "\n" +
                "import android.view.View\n" +
                "import com.synesis.gem.core.ui.screens.base.view.AbstractViewController\n" +
                "\n" +
                "class ${classesPrefix.capitalize()}ViewController(root: View) : AbstractViewController(root) {\n" +
                "}"
    }
}