package exporter.generator.module

import java.io.File

class Config(
    val path: String,
    val moduleName: String,
    val packageName: String,
    val classesPrefix: String,
    val screenName: String
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

    val screenFolder = if (screenName.isNotBlank()) {
        File(packageFolder, screenName).apply {
            if (!exists()) {
                mkdirs()
            }
        }
    } else {
        packageFolder
    }

    val interactorFolder = File(screenFolder, "business/interactor").apply {
        if (!exists()) {
            mkdirs()
        }
    }

    val useCaseFolder = File(screenFolder, "business/usecase").apply {
        if (!exists()) {
            mkdirs()
        }
    }

    val systemFolder = File(screenFolder, "business/system").apply {
        if (!exists()) {
            mkdirs()
        }
    }

    val componentsFolder = File(screenFolder, "di/components").apply {
        if (!exists()) {
            mkdirs()
        }
    }

    val modulesFolder = File(screenFolder, "di/modules").apply {
        if (!exists()) {
            mkdirs()
        }
    }

    val modelsFolder = File(screenFolder, "models").apply {
        if (!exists()) {
            mkdirs()
        }
    }

    val presenterFolder = File(screenFolder, "presentation/presenter").apply {
        if (!exists()) {
            mkdirs()
        }
    }

    val viewFolder = File(screenFolder, "presentation/view").apply {
        if (!exists()) {
            mkdirs()
        }
    }

    val customViewsFolder = File(screenFolder, "views").apply {
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

    fun getInteractorName() = "${classesPrefix.capitalize()}Interactor"

    fun getUseCaseName() = "${classesPrefix.capitalize()}UseCase"

    fun getDIComponentName() = "${classesPrefix.capitalize()}Component"

    fun getDIModuleName() = "${classesPrefix.capitalize()}Module"

    fun getPresenterName() = "${classesPrefix.capitalize()}Presenter"

    fun getMVPViewName() = "${classesPrefix.capitalize()}View"

    fun getFragmentName() = "${classesPrefix.capitalize()}Fragment"

    fun getViewControllerName() = "${classesPrefix.capitalize()}ViewController"

    fun getRootScreenImport() = if (screenName.isBlank()) {
        "com.synesis.gem.$packageName"
    } else {
        "com.synesis.gem.$packageName.${screenName}"
    }

    fun getDIComponentImport(): String {
        return "${getRootScreenImport()}.di.components.${getDIComponentName()}"
    }

    fun getPresenterImport(): String {
        return "${getRootScreenImport()}.presentation.presenter.${getPresenterName()}"
    }

    fun getMVPViewImport(): String {
        return "${getRootScreenImport()}.presentation.presenter.${getMVPViewName()}"
    }

    fun getDIModuleImport(): String {
        return "${getRootScreenImport()}.di.modules.${getDIModuleName()}"
    }

    fun getFragmentImport(): String {
        return "${getRootScreenImport()}.presentation.view.${getFragmentName()}"
    }

    fun getUseCaseImport(): String {
        return "${getRootScreenImport()}.business.usecase.${getUseCaseName()}"
    }

    fun getInteractorImport(): String {
        return "${getRootScreenImport()}.business.interactor.${getInteractorName()}"
    }

    fun getLayoutName(): String {
        return "fragment_" + classesPrefix.split("(?<=.)(?=\\p{Lu})".toRegex()).joinToString(separator = "_")
            .toLowerCase()
    }
}