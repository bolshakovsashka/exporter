package exporter.generator.module.templates

import exporter.generator.module.Config

class InteractorTemplate(val config: Config) : BaseTemplate(config) {
    override fun getTemplate(): String {
        return "package ${config.getRootScreenImport()}.business.interactor\n" +
                "\n" +
                "import ${config.getUseCaseImport()}\n" +
                "\n" +
                "class ${config.getInteractorName()}(private val useCase: ${config.getUseCaseName()}) {\n" +
                "}"
    }
}