package exporter.action

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import exporter.steps.ModuleSettingsDialog

class ModuleGenerateAction : AnAction() {

    override fun actionPerformed(e: AnActionEvent) {
       generate(e)
    }

    private fun generate(anActionEvent: AnActionEvent) {
        val dialog = ModuleSettingsDialog(anActionEvent.project!!.basePath!!, anActionEvent)
        dialog.pack()
        dialog.setLocationRelativeTo(null);  // *** this will center your app ***
        dialog.isVisible = true
    }
}