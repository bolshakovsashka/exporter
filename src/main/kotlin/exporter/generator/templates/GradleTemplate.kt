package exporter.generator.templates

class GradleTemplate {
    fun getTemplate(name: String): String {
        return "apply plugin: 'com.android.library'\n" +
                "apply plugin: 'kotlin-android'\n" +
                "apply plugin: 'kotlin-kapt'\n" +
                "apply plugin: 'kotlin-android-extensions'\n" +
                "\n" +
                "android {\n" +
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
                "    flavorDimensions \"default\"\n" +
                "\n" +
                "    productFlavors {\n" +
                "        stage1 {\n" +
                "            dimension \"default\"\n" +
                "        }\n" +
                "\n" +
                "        stage2 {\n" +
                "            dimension \"default\"\n" +
                "        }\n" +
                "\n" +
                "        preprod {\n" +
                "            dimension \"default\"\n" +
                "        }\n" +
                "\n" +
                "        prod {\n" +
                "            dimension \"default\"\n" +
                "        }\n" +
                "    }\n" +
                "    compileOptions {\n" +
                "        sourceCompatibility = JavaVersion.VERSION_1_8\n" +
                "        targetCompatibility = JavaVersion.VERSION_1_8\n" +
                "    }\n" +
                "}\n" +
                "\n" +
                "kapt {\n" +
                "    correctErrorTypes = true\n" +
                "    mapDiagnosticLocations = true\n" +
                "\n" +
                "    arguments {\n" +
                "        arg(\"moxyReflectorPackage\", \"com.synesis.gem.$name.moxybase\")\n" +
                "    }\n" +
                "}\n" +
                "\n" +
                "dependencies {\n" +
                "    def projectExt = rootProject.ext\n" +
                "\n" +
                "    implementation project(':core')\n" +
                "\n" +
                "    kapt \"com.arello-mobile:moxy-compiler:\$projectExt.moxy\"\n" +
                "    kapt \"com.google.dagger:dagger-compiler:\$projectExt.dagger\"\n" +
                "}\n"
    }
}