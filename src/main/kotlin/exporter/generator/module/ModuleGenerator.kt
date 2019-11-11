package exporter.generator.module

import exporter.generator.module.templates.*
import org.apache.batik.bridge.TextUtilities
import org.apache.commons.lang.text.StrBuilder
import java.io.BufferedWriter
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStreamWriter
import java.nio.file.Files
import java.util.regex.Pattern

class ModuleGenerator(
    path: String,
    moduleName: String,
    packageName: String,
    classesPrefix: String,
    screenName: String,
    addToGemInjectorAppCheckBox: Boolean
) {

    private val config = Config(path, moduleName, packageName, classesPrefix, screenName, addToGemInjectorAppCheckBox)

    fun generate() {
        try {
            generateGradleFile()
            generateConsumerRules()
            generateManifest()
            generateUseCase()
            generateInteractor()
            generateComponent()
            generateModule()
            generateMvpView()
            generatePresenter()
            generateViewController()
            generateFragment()
            generateLayout()
            includeIntoSettingsGradle()
            includeIntoGemInjectorApp()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun generateLayout() {
        writeTextToFile(
            File(config.layoutFolder, "${config.getLayoutName()}.xml"),
            LayoutTemplate(config).getTemplate()
        )
    }

    private fun includeIntoGemInjectorApp() {
        if (config.addToGemInjectorAppCheckBox) {
            val gemInjectorAppFile =
                File(config.path, "injector/src/main/java/com/synesis/gem/injector/di/GemInjectorApp.kt")
            var text = StringBuilder(String(Files.readAllBytes(gemInjectorAppFile.toPath()))).toString()
            val regex = "\\)\\nclass"
            val pattern = Pattern.compile(regex, Pattern.MULTILINE)
            val matcher = pattern.matcher(text)
            text = matcher.replaceFirst(",\n        \"com.synesis.gem.${config.packageName}.moxybase\")\nclass")

            writeTextToFile(gemInjectorAppFile, text)
        }
    }

    private fun includeIntoSettingsGradle() {
        val settingsGradle = File(config.path, "settings.gradle")
        val text = StringBuilder(String(Files.readAllBytes(settingsGradle.toPath())))
            .appendln()
            .appendln("include \':${config.moduleName}\'")
            .toString()

        writeTextToFile(settingsGradle, text)
    }

    private fun generateFragment() {
        writeTextToFile(
            File(config.viewFolder, "${config.getFragmentName()}.kt"),
            FragmentTemplate(config).getTemplate()
        )
    }

    private fun generateViewController() {
        writeTextToFile(
            File(config.viewFolder, "${config.getViewControllerName()}.kt"),
            ViewControllerTemplate(config).getTemplate()
        )
    }

    private fun generatePresenter() {
        writeTextToFile(
            File(config.presenterFolder, "${config.getPresenterName()}.kt"),
            PresenterTemplate(config).getTemplate()
        )
    }

    private fun generateMvpView() {
        writeTextToFile(
            File(config.presenterFolder, "${config.getMVPViewName()}.kt"),
            MvpViewTemplate(config).getTemplate()
        )
    }

    private fun generateModule() {
        writeTextToFile(
            File(config.modulesFolder, "${config.getDIModuleName()}.kt"),
            ModuleTemplate(config).getTemplate()
        )
    }

    private fun generateComponent() {
        writeTextToFile(
            File(config.componentsFolder, "${config.getDIComponentName()}.kt"),
            ComponentTemplate(config).getTemplate()
        )
    }

    private fun generateInteractor() {
        writeTextToFile(
            File(config.interactorFolder, "${config.getInteractorName()}.kt"),
            InteractorTemplate(config).getTemplate()
        )
    }

    private fun generateUseCase() {
        writeTextToFile(
            File(config.useCaseFolder, "${config.getUseCaseName()}.kt"),
            UseCaseTemplate(config).getTemplate()
        )
    }

    private fun generateManifest() {
        writeTextToFile(File(config.mainFolder, "AndroidManifest.xml"), ManifestTemplate(config).getTemplate())
    }

    private fun generateConsumerRules() {
        writeTextToFile(File(config.rootFolder, "consumer-rules.pro"), "")
    }

    private fun generateGradleFile() {
        writeTextToFile(File(config.rootFolder, "build.gradle"), GradleTemplate(config).getTemplate())
    }

    private fun writeTextToFile(file: File, text: String) {
        try {
            val out = BufferedWriter(OutputStreamWriter(FileOutputStream(file.path), "UTF-8"))
            out.write(text)
            out.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}