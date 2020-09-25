package exporter.generator.module.templates

import exporter.generator.module.Config

class GradleTemplate(val config: Config) : BaseTemplate(config) {
    override fun getTemplate(): String {
        return "apply plugin: 'com.android.library'\n" +
                "apply plugin: 'kotlin-android'\n" +
                "apply plugin: 'kotlin-kapt'\n" +
                "apply plugin: 'kotlin-android-extensions'\n" +
                "apply from: \"\${rootProject.projectDir}/flavors.gradle\"\n" +
                "\n" +
                "android {\n" +
                "    with flavorConfig\n" +
                "\n" +
                "    def globalConfig = rootProject.ext\n" +
                "\n" +
                "    compileSdkVersion globalConfig.compileSdkVersion\n" +
                "\n" +
                "    defaultConfig {\n" +
                "        minSdkVersion globalConfig.minSdkVersion\n" +
                "        targetSdkVersion globalConfig.targetSdkVersion\n" +
                "        vectorDrawables.useSupportLibrary = true\n" +
                "    }\n" +
                "\n" +
                "    compileOptions {\n" +
                "        sourceCompatibility = JavaVersion.VERSION_1_8\n" +
                "        targetCompatibility = JavaVersion.VERSION_1_8\n" +
                "    }\n" +
                "\n" +
                "    kotlinOptions {\n" +
                "        jvmTarget = \"1.8\"\n" +
                "    }\n" +
                "}\n" +
                "\n" +
                "kapt {\n" +
                "    correctErrorTypes = true\n" +
                "    mapDiagnosticLocations = true\n" +
                "}\n" +
                "\n" +
                "dependencies {\n" +
                "    def projectExt = rootProject.ext\n" +
                "\n" +
                "    implementation project(':core')\n" +
                "\n" +
                "    kapt \"com.github.moxy-community:moxy-compiler:\$projectExt.moxy\"\n" +
                "    kapt \"com.google.dagger:dagger-compiler:\$projectExt.dagger\"\n" +
                "}"
    }
}