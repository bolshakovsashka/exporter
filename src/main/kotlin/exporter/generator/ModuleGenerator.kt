package exporter.generator

import exporter.generator.templates.*
import java.io.File
import java.io.FileWriter
import java.nio.file.Files
import java.util.regex.Pattern

class ModuleGenerator(
    private val path: String,
    private val moduleName: String,
    private val packageName: String,
    private val classesPrefix: String
) {
    val rootFolder = File(path, moduleName).apply {
        if (!exists()) {
            mkdirs()
        }
    }

    val srcFolder = File(rootFolder, "src").apply {
        if (!exists()) {
            mkdirs()
        }
    }

    val mainFolder = File(srcFolder, "main").apply {
        if (!exists()) {
            mkdirs()
        }
    }

    val javaFolder = File(mainFolder, "java").apply {
        if (!exists()) {
            mkdirs()
        }
    }

    val resFolder = File(mainFolder, "res").apply {
        if (!exists()) {
            mkdirs()
        }
    }

    val packageFolder = File(javaFolder, "com/synesis/gem/$packageName").apply {
        if (!exists()) {
            mkdirs()
        }
    }

    val interactorFolder = File(packageFolder, "business/interactor").apply {
        if (!exists()) {
            mkdirs()
        }
    }

    val useCaseFolder = File(packageFolder, "business/usecase").apply {
        if (!exists()) {
            mkdirs()
        }
    }

    val systemFolder = File(packageFolder, "business/system").apply {
        if (!exists()) {
            mkdirs()
        }
    }

    val componentsFolder = File(packageFolder, "di/components").apply {
        if (!exists()) {
            mkdirs()
        }
    }

    val modulesFolder = File(packageFolder, "di/modules").apply {
        if (!exists()) {
            mkdirs()
        }
    }

    val modelsFolder = File(packageFolder, "models").apply {
        if (!exists()) {
            mkdirs()
        }
    }

    val presenterFolder = File(packageFolder, "presentation/presenter").apply {
        if (!exists()) {
            mkdirs()
        }
    }

    val viewFolder = File(packageFolder, "presentation/view").apply {
        if (!exists()) {
            mkdirs()
        }
    }

    val customViewsFolder = File(packageFolder, "views").apply {
        if (!exists()) {
            mkdirs()
        }
    }

    val drawablesFolder = File(resFolder, "drawable").apply {
        if (!exists()) {
            mkdirs()
        }
    }

    val layoutFolder = File(resFolder, "layout").apply {
        if (!exists()) {
            mkdirs()
        }
    }

    val valuesFolder = File(resFolder, "values").apply {
        if (!exists()) {
            mkdirs()
        }
    }

    fun generate() {
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
        includeIntoSettingsGradle()
        includeIntoGemInjectorApp()
    }

    private fun includeIntoGemInjectorApp() {
        val gemInjectorAppFile = File(path, "GemInjectorApp.kt")
        var text = StringBuilder(String(Files.readAllBytes(gemInjectorAppFile.toPath()))).toString()
        val regex = "\\)\\nclass"
        val pattern = Pattern.compile(regex, Pattern.MULTILINE)
        val matcher = pattern.matcher(text)
        text = matcher.replaceFirst(",\n        \"com.synesis.gem.$packageName.moxybase\")\nclass")

        val out = FileWriter(gemInjectorAppFile)
        out.write(text)
        out.close()
    }

    private fun includeIntoSettingsGradle() {
        val settingsGradle = File(path, "settings.gradle")
        val text = StringBuilder(String(Files.readAllBytes(settingsGradle.toPath())))
            .appendln()
            .appendln("include \':$moduleName\'")
            .toString()

        val out = FileWriter(settingsGradle)
        out.write(text)
        out.close()
    }

    private fun generateFragment() {
        val out = FileWriter(File(viewFolder, "${classesPrefix.capitalize()}Fragment.kt"))
        out.write(FragmentTemplate().getTemplate(packageName, classesPrefix))
        out.close()
    }

    private fun generateViewController() {
        val out = FileWriter(File(viewFolder, "${classesPrefix.capitalize()}ViewController.kt"))
        out.write(ViewControllerTemplate().getTemplate(packageName, classesPrefix))
        out.close()
    }

    private fun generatePresenter() {
        val out = FileWriter(File(presenterFolder, "${classesPrefix.capitalize()}Presenter.kt"))
        out.write(PresenterTemplate().getTemplate(packageName, classesPrefix))
        out.close()
    }

    private fun generateMvpView() {
        val out = FileWriter(File(presenterFolder, "${classesPrefix.capitalize()}View.kt"))
        out.write(MvpViewTemplate().getTemplate(packageName, classesPrefix))
        out.close()
    }

    private fun generateModule() {
        val out = FileWriter(File(modulesFolder, "${classesPrefix.capitalize()}Module.kt"))
        out.write(ModuleTemplate().getTemplate(packageName, classesPrefix))
        out.close()
    }

    private fun generateComponent() {
        val out = FileWriter(File(componentsFolder, "${classesPrefix.capitalize()}Component.kt"))
        out.write(ComponentTemplate().getTemplate(packageName, classesPrefix))
        out.close()
    }

    private fun generateInteractor() {
        val out = FileWriter(File(interactorFolder, "${classesPrefix.capitalize()}Interactor.kt"))
        out.write(InteractorTemplate().getTemplate(packageName, classesPrefix))
        out.close()
    }

    private fun generateUseCase() {
        val out = FileWriter(File(useCaseFolder, "${classesPrefix.capitalize()}UseCase.kt"))
        out.write(UseCaseTemplate().getTemplate(packageName, classesPrefix))
        out.close()
    }

    private fun generateManifest() {
        val out = FileWriter(File(mainFolder, "AndroidManifest.xml"))
        out.write(ManifestTemplate().getTemplate(packageName))
        out.close()
    }

    private fun generateConsumerRules() {
        val out = FileWriter(File(rootFolder, "consumer-rules.pro"))
        out.write("")
        out.close()
    }

    private fun generateGradleFile() {
        val out = FileWriter(File(rootFolder, "build.gradle"))
        out.write(GradleTemplate().getTemplate(packageName))
        out.close()
    }
}