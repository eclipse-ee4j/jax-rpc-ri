/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0, which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * This Source Code may also be made available under the following Secondary
 * Licenses when the conditions for such availability set forth in the
 * Eclipse Public License v. 2.0 are satisfied: GNU General Public License,
 * version 2 with the GNU Classpath Exception, which is available at
 * https://www.gnu.org/software/classpath/license.html.
 *
 * SPDX-License-Identifier: EPL-2.0 OR GPL-2.0 WITH Classpath-exception-2.0
 */

/*
 * LicenseForm.java
 *
 * Created on 2003/11/06, 21:17
 */
package com.sun.xml.rpc.installer;

import java.awt.Adjustable;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;


/**
 * License screen.
 * 
 * @author
 *     Kohsuke Kawaguchi (kohsuke.kawaguchi@sun.com)
 */
public abstract class LicenseForm extends JFrame {
    
    public LicenseForm( Reader text ) throws IOException {
        final JScrollPane scrollPane = new JScrollPane();
        JTextArea licenseTextArea = new JTextArea();
        JPanel buttonPanel = new JPanel();
        final JButton acceptButton = new JButton();
        final JButton cancelButton = new JButton();
        
        setTitle("License Agreement");
        
        {// load the license text
            BufferedReader reader = new BufferedReader(text);
            String line;
            StringBuffer buf = new StringBuffer();
            while((line=reader.readLine())!=null) {
                buf.append(line);
                buf.append('\n');
            }
            licenseTextArea.setText(buf.toString());
            licenseTextArea.setLineWrap(true);
        }
        
        
        getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent evt) {
                exitForm();
            }
        });

        getContentPane().add(scrollPane);
        scrollPane.setViewportView(licenseTextArea);
        
        licenseTextArea.setEditable(false);
        
        getContentPane().add(buttonPanel);
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        
        buttonPanel.add(acceptButton);
        acceptButton.setText("Accept");
        acceptButton.setEnabled(false);

        buttonPanel.add(cancelButton);
        cancelButton.setText("Decline");
        
        acceptButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                install();
                exitForm();
             }
        });
        
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                exitForm();
            }
        });
        
        pack();
        
        // don't enable the yes button until the scroll bar has been dragged
        // to the bottom or the window was enlarged enough to make the scroll
        // bar disappear
        JScrollBar scrollBar = scrollPane.getVerticalScrollBar();
        scrollBar.addAdjustmentListener( new AdjustmentListener() {
            Adjustable a;
            public void adjustmentValueChanged(AdjustmentEvent e) {
                a = e.getAdjustable();
                if( a.getValue() + a.getVisibleAmount() >= a.getMaximum() ) 
                    acceptButton.setEnabled(true);
            }
        });
        
        java.awt.Dimension screenSize = 
            java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setSize(new java.awt.Dimension(550, 450));
        setLocation((screenSize.width-550)/2,(screenSize.height-450)/2);
    }
    
    /**
     * Does the actual installation.
     */
    protected abstract void install();
    
    /** Exit the Application */
    private void exitForm() {
        System.exit(0);
    }
}
