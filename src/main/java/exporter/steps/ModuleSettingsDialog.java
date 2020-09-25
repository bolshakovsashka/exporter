package exporter.steps;

import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.ProjectManager;
import exporter.NotificationManager;
import exporter.generator.module.ModuleGenerator;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class ModuleSettingsDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonCreate;
    private JButton buttonCancel;
    private JTextField moduleName;
    private JTextField packageName;
    private JTextField classesPrefix;
    private JTextField screenName;
    private String path;

    public ModuleSettingsDialog(String path, AnActionEvent anActionEvent) {
        this.path = path;
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonCreate);
        buttonCreate.addActionListener(e -> create(e, anActionEvent));
        buttonCancel.addActionListener(e -> dispose());
    }

    private void create(ActionEvent e, AnActionEvent anActionEvent) {
        if (!moduleName.getText().isEmpty() && !packageName.getText().isEmpty() && !classesPrefix.getText().isEmpty()) {
            new ModuleGenerator(
                    path,
                    moduleName.getText(),
                    packageName.getText(),
                    classesPrefix.getText(),
                    screenName.getText())
                    .generate();

            ActionManager am = ActionManager.getInstance();
            AnAction sync = am.getAction("Android.SyncProject");
            sync.actionPerformed(anActionEvent);

            NotificationManager.Companion.showInfoNotification(
                    "exporter.module",
                    "New module created",
                    "Project sync started",
                    ProjectManager.getInstance().getOpenProjects()[0]
            );
            dispose();
        }
    }

    public static void main(String[] args) {
        ModuleSettingsDialog dialog = new ModuleSettingsDialog("", null);
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
