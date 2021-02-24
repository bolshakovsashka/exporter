package exporter.generator.strings.engines.locize

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.intellij.openapi.actionSystem.AnActionEvent
import exporter.generator.strings.FixValueFormatUtils
import exporter.generator.strings.StringsFilesGenerator
import exporter.generator.strings.models.Strings
import exporter.generator.strings.models.Translation
import exporter.generator.strings.models.TranslationInfo
//import org.json.JSONObject
import java.net.URL

class LocizeGenerator {

    private val fixValueFormatUtils = FixValueFormatUtils()
    private val stringsFilesGenerator = StringsFilesGenerator()

    fun generate(anActionEvent: AnActionEvent) {

        val translationToke = object : TypeToken<ArrayList<TranslationInfo>>() {}.type

        val json = URL(URL_ALL).readText()
        val fromJson: ArrayList<TranslationInfo> = Gson().fromJson(json, translationToke)

        val strings = fromJson.map {
            val language = it.key.split("/").get(2)
            val nameSpace = it.key.split("/").get(3).replace(("[^\\p{Alpha}]+").toRegex(), "_").toLowerCase()
            Gson().fromJson(URL(it.url).readText(), HashMap<String, String>()::class.java).let {
                Strings(
                    translation = "-$language",
                    nameSpace = nameSpace,
                    values = it.map {
                        Translation(
                            key = it.key,
                            value = fixValueFormatUtils.fixParams(it.value)
                        )
                    }
                )
            }
        }

        stringsFilesGenerator.generateFiles(anActionEvent.project!!.basePath!!, strings)
    }

    companion object {
        private const val PROJECT_ID = "171f13ef-6c33-495a-b9f2-f5a0fce6ef99"
        private const val URL_ALL = "https://api.locize.app/download/$PROJECT_ID"
        private const val URL_LANGUAGES = "https://api.locize.app/languages/$PROJECT_ID"
        private const val URL_STRINGS = "https://api.locize.app/$PROJECT_ID/latest/%1s/Strings"
    }
}