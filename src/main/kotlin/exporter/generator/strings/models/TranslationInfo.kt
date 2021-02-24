package exporter.generator.strings.models

import com.google.gson.annotations.SerializedName

data class TranslationInfo(
    @SerializedName("url")
    val url: String,
    @SerializedName("key")
    val key: String
)