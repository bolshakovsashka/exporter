package exporter.generator.templates

class PresenterTemplate {
    fun getTemplate(packageName: String, classesPrefix: String): String {
        return "package com.synesis.gem.$packageName.presentation.presenter\n" +
                "\n" +
                "import com.arellomobile.mvp.InjectViewState\n" +
                "import com.synesis.gem.core.api.errorshandling.ErrorHandler\n" +
                "import com.synesis.gem.core.common.rx.SchedulerProvider\n" +
                "import com.synesis.gem.core.ui.base.BasePresenter\n" +
                "import com.synesis.gem.$packageName.business.interactor.${classesPrefix.capitalize()}Interactor\n" +
                "\n" +
                "@InjectViewState\n" +
                "class ${classesPrefix.capitalize()}Presenter(\n" +
                "        private val errorHandler: ErrorHandler,\n" +
                "        private val interactor: ${classesPrefix.capitalize()}Interactor,\n" +
                "        private val schedulerProvider: SchedulerProvider) : BasePresenter<${classesPrefix.capitalize()}View>(errorHandler) {\n" +
                "}"
    }
}