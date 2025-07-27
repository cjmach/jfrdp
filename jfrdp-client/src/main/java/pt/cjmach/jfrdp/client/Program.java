/*
 * Licensed to the Apache Software Foundation (ASF) under one or more 
 * contributor license agreements.  See the NOTICE file distributed with this 
 * work for additional information regarding copyright ownership. The ASF 
 * licenses this file to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance with the License.  
 * You may obtain a copy of the License at
 * 
 *   https://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT 
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the 
 * License for the specific language governing permissions and limitations
 * under the License.  
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
import pt.cjmach.jfrdp.lib.RdpException;
import pt.cjmach.jfrdp.lib.RdpSettings;
import pt.cjmach.jfrdp.lib.SettingsKeys;
import pt.cjmach.jfrdp.ui.RdpDisplay;

/**
 *
 * @author cmachado
 */
public class Program {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            final JFrame frame = new JFrame("RDP Client");
            JMenuBar menuBar = new JMenuBar();
            final RdpDisplay displayArea = new RdpDisplay();

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
                        try {
                            displayArea.connect();
                        } catch (RdpException ex) {
                            JOptionPane.showMessageDialog(frame, ex.getMessage(),
                                    "Error!", JOptionPane.ERROR_MESSAGE);
                        }
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
