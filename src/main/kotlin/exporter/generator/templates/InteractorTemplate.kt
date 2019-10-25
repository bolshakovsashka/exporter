package exporter.generator.templates

class InteractorTemplate {
    fun getTemplate(packageName: String, classesPrefix: String): String {
        return "package com.synesis.gem.$packageName.business.interactor\n" +
                "\n" +
                "import com.synesis.gem.$packageName.business.usecase.${classesPrefix.capitalize()}UseCase\n" +
                "\n" +
                "class ${classesPrefix.capitalize()}Interactor(private val useCase: ${classesPrefix.capitalize()}UseCase) {\n" +
                "}"
    }
}