package exporter.generator.module.templates

import exporter.generator.module.Config

class ModuleTemplate(val config: Config) : BaseTemplate(config) {

    override fun getTemplate(): String {
        return "package ${config.getRootScreenImport()}.di.modules\n" +
                "\n" +
                "import com.synesis.gem.core.api.errorshandling.ErrorHandler\n" +
                "import com.synesis.gem.core.common.rx.SchedulerProvider\n" +
                "import ${config.getInteractorImport()}\n" +
                "import ${config.getUseCaseImport()}\n" +
                "import ${config.getPresenterImport()}\n" +
                "import dagger.Module\n" +
                "import dagger.Provides\n" +
                "\n" +
                "@Module\n" +
                "class ${config.getDIModuleName()} {\n" +
                "\n" +
                "    @Provides\n" +
                "    fun providePresenter(\n" +
                "            errorHandler: ErrorHandler,\n" +
                "            interactor: ${config.getInteractorName()},\n" +
                "            schedulerProvider: SchedulerProvider\n" +
                "    ) = ${config.getPresenterName()}(\n" +
                "            errorHandler,\n" +
                "            interactor,\n" +
                "            schedulerProvider\n" +
                "    )\n" +
                "\n" +
                "    @Provides\n" +
                "    fun provide${config.getInteractorName()}(\n" +
                "            useCase: ${config.getUseCaseName()}\n" +
                "    ) = ${config.getInteractorName()}(\n" +
                "            useCase\n" +
                "    )\n" +
                "\n" +
                "    @Provides\n" +
                "    fun provide${config.getUseCaseName()}() = ${config.getUseCaseName()}()\n" +
                "}"
    }
}