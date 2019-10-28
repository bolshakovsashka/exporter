package exporter.generator.module.templates

import exporter.generator.module.Config

class ComponentTemplate(val config: Config) : BaseTemplate(config) {
    override fun getTemplate(): String {
        return "package ${config.getRootScreenImport()}.di.components\n" +
                "\n" +
                "import com.synesis.gem.core.di.ApplicationProvider\n" +
                "import ${config.getDIModuleImport()}\n" +
                "import ${config.getFragmentImport()}\n" +
                "import dagger.Component\n" +
                "\n" +
                "@Component(\n" +
                "        dependencies = [ApplicationProvider::class],\n" +
                "        modules = [${config.getDIModuleName()}::class])\n" +
                "interface ${config.getDIComponentName()} {\n" +
                "\n" +
                "    fun inject(fragment: ${config.getFragmentName()})\n" +
                "\n" +
                "    companion object {\n" +
                "        fun init(applicationProvider: ApplicationProvider): ${config.getDIComponentName()} {\n" +
                "            return Dagger${config.getDIComponentName()}.builder()\n" +
                "                    .applicationProvider(applicationProvider)\n" +
                "                    .build()\n" +
                "        }\n" +
                "    }\n" +
                "}"
    }
}