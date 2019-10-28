package exporter.generator.module.templates

import exporter.generator.module.Config

class ViewControllerTemplate(val config: Config) : BaseTemplate(config) {

    override fun getTemplate(): String {
        return "package ${config.getRootScreenImport()}.presentation.view\n" +
                "\n" +
                "import android.view.View\n" +
                "import com.synesis.gem.core.ui.screens.base.view.AbstractViewController\n" +
                "\n" +
                "class ${config.getViewControllerName()}(root: View) : AbstractViewController(root) {\n" +
                "}"
    }
}