package exporter.generator.module.templates

import exporter.generator.module.Config

class PresenterTemplate(val config: Config) : BaseTemplate(config) {
    override fun getTemplate(): String {
        return "package ${config.getRootScreenImport()}.presentation.presenter\n" +
                "\n" +
                "import moxy.InjectViewState\n" +
                "import com.synesis.gem.core.api.errorshandling.ErrorHandler\n" +
                "import com.synesis.gem.core.common.rx.SchedulerProvider\n" +
                "import com.synesis.gem.core.ui.base.BasePresenter\n" +
                "import ${config.getInteractorImport()}\n" +
                "\n" +
                "@InjectViewState\n" +
                "class ${config.getPresenterName()}(\n" +
                "        errorHandler: ErrorHandler,\n" +
                "        private val interactor: ${config.getInteractorName()},\n" +
                "        private val schedulerProvider: SchedulerProvider) : BasePresenter<${config.getMVPViewName()}>(errorHandler) {\n" +
                "}"
    }
}