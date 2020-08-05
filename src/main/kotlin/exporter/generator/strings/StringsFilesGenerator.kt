package exporter.generator.strings

import exporter.generator.strings.models.Strings
import java.io.BufferedWriter
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStreamWriter

class StringsFilesGenerator {

    fun generateFiles(path: String, strings: List<Strings>) {
        val root = File(path, "core/src/main/res")
//        if (root.exists()) {
//            FileUtils.deleteDirectory(root)
//        }
        root.mkdirs()
        strings.forEach {
            val postfix = when (it.translation) {
                "-en" -> ""
                "-id" -> "-in"
                else -> it.translation
            }
            val languageFolder = File(root, "values$postfix")
            if (!languageFolder.exists()) {
                languageFolder.mkdir()
            }
            val stringsFile = File(languageFolder, "strings.xml")
            if (stringsFile.exists()) {
                stringsFile.delete()
            }
            val stringBuilder = StringBuilder()
            stringBuilder
                .appendln("<?xml version=\"1.0\" encoding=\"UTF-8\"?>")
                .appendln("<resources>")
            it.values
                .filter { it.value.isNotEmpty() && it.key.isNotEmpty() }
                .forEach {
                    stringBuilder.appendln("    <string name=\"${it.key}\">${it.value}</string>")
                }
            stringBuilder.appendln("</resources>\n")

            val out = BufferedWriter(OutputStreamWriter(FileOutputStream(stringsFile.path), "UTF-8"))
            out.write(stringBuilder.toString())
            out.close()
        }
    }
}