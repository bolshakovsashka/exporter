package exporter.generator.module.templates

import exporter.generator.module.Config

abstract class BaseTemplate(config: Config) {
    open abstract fun getTemplate(): String
}