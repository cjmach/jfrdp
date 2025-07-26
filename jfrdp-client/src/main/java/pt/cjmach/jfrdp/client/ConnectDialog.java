/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pt.cjmach.jfrdp.client;

import java.awt.Component;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

/**
 *
 * @author mach
 */
public class ConnectDialog {

    private final ConnectPanel panel;

    public ConnectDialog() {
        panel = new ConnectPanel();
    }

    public String getDomain() {
        return panel.getDomain();
    }

    public char[] getPassword() {
        return panel.getPassword();
    }

    public String getServer() {
        return panel.getServer();
    }

    public String getUser() {
        return panel.getUser();
    }

    public int show(Component parentComponent) {
        panel.reset();

        String title = "Connect to RDP Server";
        JOptionPane optionPane = new JOptionPane(panel, JOptionPane.PLAIN_MESSAGE, JOptionPane.OK_CANCEL_OPTION);

        optionPane.setInitialValue(null);
        optionPane.setComponentOrientation(((parentComponent == null)
                ? JOptionPane.getRootFrame() : parentComponent).getComponentOrientation());

        JDialog dialog = optionPane.createDialog(parentComponent, title);
        dialog.setFocusable(true);

        dialog.addFocusListener(new FocusAdapter() {
            private boolean gotFocus;

            @Override
            public void focusGained(FocusEvent e) {
                if (!gotFocus) {
                    panel.focusFirstTextField();
                    gotFocus = true;
                }
            }
        });

        dialog.setVisible(true);
        dialog.dispose();

        Object selectedValue = optionPane.getValue();

        if (selectedValue instanceof Integer) {
            return ((Integer) selectedValue);
        }
        return JOptionPane.CLOSED_OPTION;
    }
}
