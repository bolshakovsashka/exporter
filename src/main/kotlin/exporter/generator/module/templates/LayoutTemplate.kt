package exporter.generator.module.templates

import exporter.generator.module.Config

class LayoutTemplate(config: Config) : BaseTemplate(config) {
    override fun getTemplate(): String {
        return "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "<androidx.constraintlayout.widget.ConstraintLayout xmlns:android=\"http://schemas.android.com/apk/res/android\"\n" +
                "    android:layout_width=\"match_parent\"\n" +
                "    android:layout_height=\"match_parent\">\n" +
                "\n" +
                "</androidx.constraintlayout.widget.ConstraintLayout>"
    }

}