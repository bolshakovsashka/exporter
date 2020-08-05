package exporter.generator.strings.engines.excel

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
import exporter.generator.strings.FixValueFormatUtils
import exporter.generator.strings.StringsFilesGenerator
import exporter.generator.strings.models.Strings
import exporter.generator.strings.models.Translation
import java.io.*
import java.io.FileOutputStream
import java.io.OutputStreamWriter
import java.io.BufferedWriter


class StringGenerator {

    private val fixValueFormatUtils = FixValueFormatUtils()
    private val stringsFilesGenerator = StringsFilesGenerator()

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

    fun generateOld(e: AnActionEvent): String {
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
                            fixValueFormatUtils.fixParams(rows[index + 1].toString())
                        )
                    } catch (e: Exception) {
                        e.printStackTrace()
                        println(rows)
                        null
                    }
                })
        }
        stringsFilesGenerator. generateFiles(e.project!!.basePath!!, strings)
        return values.toTypedArray().contentToString()
    }

    fun generate(e: AnActionEvent): String {
        val HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport()
        val spreadsheetId = "12iDxfLlg_wVYQLwLj_NI33DJNjzImEO-uU3NSsCZGCg";
        val range = "A1:U";
        val service = Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
            .setApplicationName(APPLICATION_NAME)
            .build()

        val response = service.spreadsheets().values()
            .get(spreadsheetId, range)
            .execute()

        val values = response.getValues()

        var languages = values.first().filter { (it as String).isNotBlank() }
        languages = languages.takeLast(languages.size - 4)

        val strings = languages.mapIndexedNotNull { index, language ->
            try {
                Strings(
                    translation = "-" + language as String,
                    values = values.takeLast(values.size - 1).mapNotNull { row ->
                        try {
                            val key = row[0].toString()
                            if (key.isNotBlank()) {
                                val value = fixValueFormatUtils.fixParams(row[index + 4].toString())
                                Translation(
                                    key = key,
                                    value = value
                                )
                            } else {
                                null
                            }
                        } catch (e: Exception) {
                            e.printStackTrace()
                            null
                        }
                    }
                )
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
        stringsFilesGenerator.generateFiles(e.project!!.basePath!!, strings)
        return values.toTypedArray().contentToString()
    }

    companion object {
        private const val APPLICATION_NAME = "GEM4ME String Generator"
    }
}