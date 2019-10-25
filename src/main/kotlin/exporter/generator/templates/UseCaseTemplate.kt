package exporter.generator.templates

class UseCaseTemplate() {
    fun getTemplate(packageName: String, classesPrefix: String): String {
        return "package com.synesis.gem.$packageName.business.usecase\n" +
                "\n" +
                "class ${classesPrefix.capitalize()}UseCase {\n" +
                "}"
    }
}