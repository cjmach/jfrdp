/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package pt.cjmach.jfrdp.client;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import pt.cjmach.jfrdp.lib.RdpSettings;
import pt.cjmach.jfrdp.lib.SettingsKeys;
import pt.cjmach.jfrdp.ui.RdpDisplay;

/**
 *
 * @author mach
 */
public class Program {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            final JFrame frame = new JFrame("RDP Client");
            JMenuBar menuBar = new JMenuBar();
            RdpDisplay displayArea = new RdpDisplay();

            frame.setLayout(new BorderLayout());
            frame.add(displayArea, BorderLayout.CENTER);

            JMenu fileMenu = new JMenu("File");
            fileMenu.add(new AbstractAction("Connect...") {
                @Override
                public void actionPerformed(ActionEvent ae) {
                    ConnectDialog connectDialog = new ConnectDialog();
                    int result = connectDialog.show(frame);
                    if (result == JOptionPane.OK_OPTION) {
                        RdpSettings settings = displayArea.getSettings();
                        settings.setString(SettingsKeys.String.SERVER_HOSTNAME, connectDialog.getServer());
                        settings.setString(SettingsKeys.String.DOMAIN, connectDialog.getDomain());
                        settings.setString(SettingsKeys.String.USERNAME, connectDialog.getUser());
                        settings.setString(SettingsKeys.String.PASSWORD, connectDialog.getPassword());
                        displayArea.connect();
                    }
                }
            });
            fileMenu.add(new AbstractAction("Exit") {
                @Override
                public void actionPerformed(ActionEvent ae) {
                    displayArea.disconnect();
                    frame.dispose();
                }
            });

            menuBar.add(fileMenu);
            
            frame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    displayArea.disconnect();
                }
            });

            frame.setJMenuBar(menuBar);
            frame.setSize(800, 600);
            frame.setLocationRelativeTo(null);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
}
