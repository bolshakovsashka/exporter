package exporter.generator.provide

import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.editor.Document
import com.intellij.openapi.fileEditor.FileDocumentManager
import com.intellij.openapi.fileEditor.FileEditorManager
import com.intellij.openapi.vfs.VirtualFile
import java.awt.Toolkit
import java.awt.datatransfer.Clipboard
import java.awt.datatransfer.StringSelection


class ClassProvider {
    fun provide(anActionEvent: AnActionEvent) {
        anActionEvent.project?.let { project ->
            val currentDoc: Document = FileEditorManager.getInstance(project).selectedTextEditor!!.document
            val currentFile = FileDocumentManager.getInstance().getFile(currentDoc)
            currentFile?.let {

                val classText = currentDoc.text
                val startConstructor = classText.indexOfFirst { it == '(' }
                val endConstructor = classText.indexOfFirst { it == ')' }
                val isConstructorEmpty = startConstructor > endConstructor
                val nameWithoutExtension = currentFile.nameWithoutExtension
                if (isConstructorEmpty) {
                    copyToClipboard(
                        "\t@Provides\n" +
                                "\tfun provide$nameWithoutExtension() = $nameWithoutExtension()"
                    )
                } else {
                    val classes = mutableListOf<String>()
                    classText.substring(startConstructor, endConstructor).split(",").forEach {
                        it.split(":").lastOrNull()?.let {
                            classes.add(it.replace("\n", "").trim())
                        }
                    }
                    val stringBuilder = StringBuilder()
                    stringBuilder
                        .append("\t@Provides\n")
                        .append("\tfun provide$nameWithoutExtension(\n")
                    classes.forEachIndexed { index, s ->
                        val last = index == classes.size - 1
                        val postfix = if (last) {
                            "\n"
                        } else {
                            ",\n"
                        }
                        stringBuilder.append(s[0].toLowerCase() + s.substring(1) + ": " + s + postfix)
                    }
                    stringBuilder.append("\t) = $nameWithoutExtension(\n")
                    classes.forEachIndexed { index, s ->
                        val last = index == classes.size - 1
                        val postfix = if (last) {
                            "\n"
                        } else {
                            ",\n"
                        }
                        stringBuilder.append(s[0].toLowerCase() + s.substring(1) + postfix)
                    }
                    stringBuilder.append("\t)")
                    copyToClipboard(stringBuilder.toString())
                }
            }
        }
//        val psiFile = anActionEvent.getData(DataKeys.PSI_FILE) as PsiFile
//        val psiElement = anActionEvent.getData(DataKeys.PSI_ELEMENT) as? PsiElement
//        val nameWithoutExtension = virtualFile.nameWithoutExtension
//        val loadText: CharSequence = LoadTextUtil.loadText(virtualFile)
//        val psiClass = PsiElementFactory.SERVICE.getInstance(anActionEvent.project!!)
//            .createClassFromText(loadText.toString(), psiFile)
//        val createFileFromText: PsiFile? = PsiFileFactory.getInstance(anActionEvent.project).createFileFromText(loadText, psiFile)
//        FileTemplateManager.getInstance(anActionEvent.project!!).allCodeTemplates
    }

//    private fun findMethod(element: PsiElement): PsiMethod? {
//        val method = if (element is PsiMethod)
//            element
//        else
//            PsiTreeUtil.getParentOfType(element, PsiMethod::class.java)
//        return if (method != null && method.containingClass is PsiAnonymousClass) {
//            findMethod(method.parent)
//        } else method
//    }

    fun copyToClipboard(str: String) {
        val clipboard = getSystemClipboard()
        clipboard.setContents(StringSelection(str), null)
    }

    fun getSystemClipboard(): Clipboard {
        return Toolkit.getDefaultToolkit().systemClipboard
    }

    fun notifyUser() {

    }
}