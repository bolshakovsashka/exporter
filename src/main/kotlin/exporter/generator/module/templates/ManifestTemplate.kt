package exporter.generator.module.templates

import exporter.generator.module.Config

class ManifestTemplate(val config: Config) : BaseTemplate(config) {

    override fun getTemplate(): String {
        return "<manifest package=\"com.synesis.gem.${config.packageName}\" />\n"
    }
}