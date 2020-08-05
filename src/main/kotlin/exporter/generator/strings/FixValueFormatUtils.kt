package exporter.generator.strings

class FixValueFormatUtils {

    fun fixParams(str: String): String {
        var res = str
        for (i in 0 until 10) {
            res = res.replace("{$i}", "%" + (i + 1) + "\$s")
        }
        res = res.replace("&", "&amp;")
        res = res.replace("""('|\\')""".toRegex(), """\\'""")
        return res
    }
}