package exporter.generator.strings.engines.locize

import com.google.gson.Gson
import com.intellij.openapi.actionSystem.AnActionEvent
import exporter.generator.strings.FixValueFormatUtils
import exporter.generator.strings.StringsFilesGenerator
import exporter.generator.strings.models.Strings
import exporter.generator.strings.models.Translation
//import org.json.JSONObject
import java.net.URL

class LocizeGenerator {

    private val fixValueFormatUtils = FixValueFormatUtils()
    private val stringsFilesGenerator = StringsFilesGenerator()

    fun generate(anActionEvent: AnActionEvent) {
        val json = URL(URL_LANGUAGES).readText()
        val map = HashMap<String, String>()
        val fromJson = Gson().fromJson(json, map::class.java)
        val languages = fromJson.keys
        val strings = languages.map { language ->
            val m = HashMap<String, String>()
            val fromJson1 = Gson().fromJson(URL(String.format(URL_STRINGS, language)).readText(), m::class.java)
            Strings(
                translation = "-$language",
                values = fromJson1.map {
                    Translation(
                        key = it.key,
                        value = fixValueFormatUtils.fixParams(it.value)
                    )
                }
            )
        }
        stringsFilesGenerator.generateFiles(anActionEvent.project!!.basePath!!, strings)
    }

    companion object {
        private const val PROJECT_ID = "171f13ef-6c33-495a-b9f2-f5a0fce6ef99"
        private const val URL_LANGUAGES = "https://api.locize.app/languages/$PROJECT_ID"
        private const val URL_STRINGS = "https://api.locize.app/$PROJECT_ID/latest/%1s/Strings"
    }
}