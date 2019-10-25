package exporter.generator.templates

class ComponentTemplate {

    fun getTemplate(packageName: String, classesPrefix: String): String {
        return "package com.synesis.gem.$packageName.di.components\n" +
                "\n" +
                "import com.synesis.gem.core.di.ApplicationProvider\n" +
                "import com.synesis.gem.$packageName.di.modules.${classesPrefix.capitalize()}Module\n" +
                "import com.synesis.gem.$packageName.presentation.view.${classesPrefix.capitalize()}Fragment\n" +
                "import dagger.Component\n" +
                "\n" +
                "@Component(\n" +
                "        dependencies = [ApplicationProvider::class],\n" +
                "        modules = [${classesPrefix.capitalize()}Module::class])\n" +
                "interface ${classesPrefix.capitalize()}Component {\n" +
                "\n" +
                "    fun inject(fragment: ${classesPrefix.capitalize()}Fragment)\n" +
                "\n" +
                "    companion object {\n" +
                "        fun init(applicationProvider: ApplicationProvider): ${classesPrefix.capitalize()}Component {\n" +
                "            return Dagger${classesPrefix.capitalize()}Component.builder()\n" +
                "                    .applicationProvider(applicationProvider)\n" +
                "                    .build()\n" +
                "        }\n" +
                "    }\n" +
                "}"
    }
}