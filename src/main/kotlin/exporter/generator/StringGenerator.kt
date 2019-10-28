package exporter.generator

import com.google.api.client.auth.oauth2.Credential
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver
import com.google.api.client.util.store.FileDataStoreFactory
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.services.sheets.v4.SheetsScopes
import com.google.api.client.json.jackson2.JacksonFactory
import java.util.*
import com.google.api.services.sheets.v4.Sheets
import com.intellij.openapi.actionSystem.AnActionEvent
import java.io.*
import java.io.FileOutputStream
import java.io.OutputStreamWriter
import java.io.BufferedWriter


class StringGenerator {
    private val APPLICATION_NAME = "Google Sheets API Java Quickstart"
    private val JSON_FACTORY = JacksonFactory.getDefaultInstance()
    private val TOKENS_DIRECTORY_PATH = "tokens"

    /**
     * Global instance of the scopes required by this quickstart.
     * If modifying these scopes, delete your previously saved tokens/ folder.
     */
    private val SCOPES = Collections.singletonList(SheetsScopes.SPREADSHEETS_READONLY)
    private val CREDENTIALS_FILE_PATH = "/credentials.json"

    /**
     * Creates an authorized Credential object.
     * @param HTTP_TRANSPORT The network HTTP Transport.
     * @return An authorized Credential object.
     * @throws IOException If the credentials.json file cannot be found.
     */
    @Throws(IOException::class)
    private fun getCredentials(HTTP_TRANSPORT: NetHttpTransport): Credential {
        // Load client secrets.
        val `in` = StringGenerator::class.java.getResourceAsStream(CREDENTIALS_FILE_PATH)
            ?: throw FileNotFoundException("Resource not found: $CREDENTIALS_FILE_PATH")
        val clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, InputStreamReader(`in`))

        // Build flow and trigger user authorization request.
        val flow = GoogleAuthorizationCodeFlow.Builder(
            HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES
        )
            .setDataStoreFactory(FileDataStoreFactory(java.io.File(TOKENS_DIRECTORY_PATH)))
            .setAccessType("offline")
            .build()
        val receiver = LocalServerReceiver.Builder().setPort(8888).build()
        return AuthorizationCodeInstalledApp(flow, receiver).authorize("user")
    }

    fun generate(e: AnActionEvent): String {
        val HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport()
        val spreadsheetId = "154-T6SHC9Cjs-va3DUDL2D56pmbsyjhBtevsAmpPeQA";
        val range = "A1:R";
        val service = Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
            .setApplicationName(APPLICATION_NAME)
            .build()

        val response = service.spreadsheets().values()
            .get(spreadsheetId, range)
            .execute()

        val values = response.getValues()

        val languages = values.first().filter { it.toString().isNotEmpty() }
        val strings = languages.mapIndexed { index, languagePostfix ->
            Strings(
                languagePostfix.toString(),
                values.takeLast(values.size - 1).mapNotNull { rows ->
                    try {
                        if (rows[0].toString().isBlank()) {
                            return@mapNotNull null
                        }
                        Translation(
                            rows[0].toString(),
                            fixParams(rows[index + 1].toString())
                        )
                    } catch (e: Exception) {
                        e.printStackTrace()
                        println(rows)
                        null
                    }
                })
        }
        generateFiles(e.project!!.basePath!!, strings)
        return values.toTypedArray().contentToString()
    }

    private fun fixParams(str: String): String {
        var res = str
        for (i in 0 until 10) {
            res = res.replace("{$i}", "%" + (i + 1) + "\$s")
        }
        res = res.replace("&", "&amp;")
        res = res.replace("""('|\\')""".toRegex(), """\\'""")
        return res
    }

    private fun generateFiles(path: String, strings: List<Strings>) {
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
            it.values.forEach {
                stringBuilder.appendln("    <string name=\"${it.key}\">${it.value}</string>")
            }
            stringBuilder.appendln("</resources>\n")

            val out = BufferedWriter(OutputStreamWriter(FileOutputStream(stringsFile.path), "UTF-8"))
            out.write(stringBuilder.toString())
            out.close()
        }
    }


    private data class Strings(val translation: String, val values: List<Translation>)
    private data class Translation(val key: String, val value: String)
}