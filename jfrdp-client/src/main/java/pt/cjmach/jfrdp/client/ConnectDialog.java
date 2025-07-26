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

import java.awt.Component;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

/**
 *
 * @author cmachado
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
