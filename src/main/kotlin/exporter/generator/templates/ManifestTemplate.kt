package exporter.generator.templates

class ManifestTemplate {

    fun getTemplate(name: String): String {
        return "<manifest package=\"com.synesis.gem.$name\" />\n"
    }
}