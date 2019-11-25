package exporter.generator.module.templates

import exporter.generator.module.Config

class MvpViewTemplate(val config: Config) : BaseTemplate(config) {

    override fun getTemplate(): String {
        return "package ${config.getRootScreenImport()}.presentation.presenter\n" +
                "\n" +
                "import com.synesis.gem.core.ui.base.BaseView\n" +
                "\n" +
                "interface ${config.getMVPViewName()} : BaseView"
    }
}