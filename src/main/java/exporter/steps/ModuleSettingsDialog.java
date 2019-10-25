package exporter.steps;

import com.intellij.notification.NotificationDisplayType;
import com.intellij.notification.NotificationGroup;
import com.intellij.notification.NotificationType;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.openapi.wm.WindowManager;
import exporter.generator.ModuleGenerator;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ModuleSettingsDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonCreate;
    private JButton buttonCancel;
    private JTextField moduleName;
    private JTextField packageName;
    private JTextField classesPrefix;
    private String path;

    public ModuleSettingsDialog(String path) {
        this.path = path;
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonCreate);

        buttonCreate.addActionListener(e -> create(e));
        buttonCancel.addActionListener(e -> dispose());
    }

    private void create(ActionEvent e) {
        if (!moduleName.getText().isEmpty() && !packageName.getText().isEmpty()  && !classesPrefix.getText().isEmpty() ) {
            new ModuleGenerator(path, moduleName.getText(), packageName.getText(), classesPrefix.getText()).generate();

            NotificationGroup noti2 = new NotificationGroup("extractor", NotificationDisplayType.BALLOON, true);
            noti2.createNotification(
                    "New module created",
                    "Please sync gradle to see it",
                    NotificationType.INFORMATION,
                    null
            ).notify(ProjectManager.getInstance().getOpenProjects()[0]);
            dispose();
        }
    }

    public static void main(String[] args) {
        ModuleSettingsDialog dialog = new ModuleSettingsDialog("");
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
