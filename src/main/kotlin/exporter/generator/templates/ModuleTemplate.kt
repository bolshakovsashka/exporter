package exporter.generator.templates

class ModuleTemplate {

    fun getTemplate(packageName: String, classesPrefix: String): String {
        return "package com.synesis.gem.$packageName.di.modules\n" +
                "\n" +
                "import com.synesis.gem.core.api.errorshandling.ErrorHandler\n" +
                "import com.synesis.gem.core.common.rx.SchedulerProvider\n" +
                "import com.synesis.gem.$packageName.business.interactor.${classesPrefix.capitalize()}Interactor\n" +
                "import com.synesis.gem.$packageName.business.usecase.${classesPrefix.capitalize()}UseCase\n" +
                "import com.synesis.gem.$packageName.presentation.presenter.${classesPrefix.capitalize()}Presenter\n" +
                "import dagger.Module\n" +
                "import dagger.Provides\n" +
                "\n" +
                "@Module\n" +
                "class ${classesPrefix.capitalize()}Module {\n" +
                "\n" +
                "    @Provides\n" +
                "    fun providePresenter(\n" +
                "            errorHandler: ErrorHandler,\n" +
                "            interactor: ${classesPrefix.capitalize()}Interactor,\n" +
                "            schedulerProvider: SchedulerProvider\n" +
                "    ) = ${classesPrefix.capitalize()}Presenter(\n" +
                "            errorHandler,\n" +
                "            interactor,\n" +
                "            schedulerProvider\n" +
                "    )\n" +
                "\n" +
                "    @Provides\n" +
                "    fun provide${classesPrefix.capitalize()}Interactor(\n" +
                "            useCase: ${classesPrefix.capitalize()}UseCase\n" +
                "    ) = ${classesPrefix.capitalize()}Interactor(\n" +
                "            useCase\n" +
                "    )\n" +
                "\n" +
                "    @Provides\n" +
                "    fun provide${classesPrefix.capitalize()}UseCase() = ${classesPrefix.capitalize()}UseCase()\n" +
                "}"
    }
}