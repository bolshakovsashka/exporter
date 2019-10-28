package exporter.generator.module.templates

import exporter.generator.module.Config

class UseCaseTemplate(val config: Config) : BaseTemplate(config) {

    override fun getTemplate(): String {
        return "package ${config.getRootScreenImport()}.business.usecase\n" +
                "\n" +
                "class ${config.getUseCaseName()} {\n" +
                "}"
    }
}