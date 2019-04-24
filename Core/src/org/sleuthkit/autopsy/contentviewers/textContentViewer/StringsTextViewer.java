/*
 * Autopsy Forensic Browser
 *
 * Copyright 2011-2019 Basis Technology Corp.
 * Contact: carrier <at> sleuthkit <dot> org
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.sleuthkit.autopsy.contentviewers.textContentViewer;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.logging.Level;
import org.openide.util.NbBundle;
import org.sleuthkit.autopsy.coreutils.Logger;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import org.openide.nodes.Node;
import org.openide.util.lookup.ServiceProvider;
import org.sleuthkit.autopsy.corecomponentinterfaces.TextViewer;
import org.sleuthkit.autopsy.corecomponents.DataContentViewerUtility;
import org.sleuthkit.autopsy.coreutils.StringExtract;
import org.sleuthkit.autopsy.coreutils.StringExtract.StringExtractResult;
import org.sleuthkit.autopsy.coreutils.StringExtract.StringExtractUnicodeTable.SCRIPT;
import org.sleuthkit.autopsy.datamodel.StringContent;
import org.sleuthkit.datamodel.Content;
import org.sleuthkit.datamodel.TskCoreException;

/**
 * Viewer displays strings extracted from contents.
 */
@ServiceProvider(service = TextViewer.class, position = 1)
@SuppressWarnings("PMD.SingularField") // UI widgets cause lots of false positives
public class StringsTextViewer extends javax.swing.JPanel implements TextViewer {

    private static long currentOffset = 0;
    private static final long PAGE_LENGTH = 16384;
    private final byte[] data = new byte[(int) PAGE_LENGTH];
    private static int currentPage = 1;
    private Content dataSource;
    //string extract utility
    private final StringExtract stringExtract = new StringExtract();
    private static final Logger logger = Logger.getLogger(StringsTextViewer.class.getName());

    /**
     * Creates new form StringsTextViewer
     */
    public StringsTextViewer() {
        initComponents();
        customizeComponents();
        this.resetComponent();
        logger.log(Level.INFO, "Created StringView instance: {0}", this); //NON-NLS
    }

    private void customizeComponents() {
        outputViewPane.setComponentPopupMenu(rightClickMenu);
        ActionListener actList = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JMenuItem jmi = (JMenuItem) e.getSource();
                if (jmi.equals(copyMenuItem)) {
                    outputViewPane.copy();
                } else if (jmi.equals(selectAllMenuItem)) {
                    outputViewPane.selectAll();
                }
            }
        };
        copyMenuItem.addActionListener(actList);
        selectAllMenuItem.addActionListener(actList);

        List<SCRIPT> supportedScripts = StringExtract.getSupportedScripts();
        for (SCRIPT s : supportedScripts) {
            languageCombo.addItem(s);
        }

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        rightClickMenu = new javax.swing.JPopupMenu();
        copyMenuItem = new javax.swing.JMenuItem();
        selectAllMenuItem = new javax.swing.JMenuItem();
        jScrollPane1 = new javax.swing.JScrollPane();
        outputViewPane = new javax.swing.JTextPane();
        jScrollPane2 = new javax.swing.JScrollPane();
        jPanel2 = new javax.swing.JPanel();
        totalPageLabel = new javax.swing.JLabel();
        ofLabel = new javax.swing.JLabel();
        currentPageLabel = new javax.swing.JLabel();
        pageLabel = new javax.swing.JLabel();
        nextPageButton = new javax.swing.JButton();
        pageLabel2 = new javax.swing.JLabel();
        prevPageButton = new javax.swing.JButton();
        goToPageLabel = new javax.swing.JLabel();
        goToPageTextField = new javax.swing.JTextField();
        languageCombo = new javax.swing.JComboBox<>();
        languageLabel = new javax.swing.JLabel();

        copyMenuItem.setText(org.openide.util.NbBundle.getMessage(StringsTextViewer.class, "StringsTextViewer.copyMenuItem.text")); // NOI18N
        rightClickMenu.add(copyMenuItem);

        selectAllMenuItem.setText(org.openide.util.NbBundle.getMessage(StringsTextViewer.class, "StringsTextViewer.selectAllMenuItem.text")); // NOI18N
        rightClickMenu.add(selectAllMenuItem);

        setMinimumSize(new java.awt.Dimension(5, 5));
        setPreferredSize(new java.awt.Dimension(100, 144));

        jScrollPane1.setPreferredSize(new java.awt.Dimension(640, 402));

        outputViewPane.setEditable(false);
        outputViewPane.setFont(new java.awt.Font("Courier New", 0, 11)); // NOI18N
        outputViewPane.setPreferredSize(new java.awt.Dimension(638, 400));
        jScrollPane1.setViewportView(outputViewPane);

        jScrollPane2.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane2.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        totalPageLabel.setText(org.openide.util.NbBundle.getMessage(StringsTextViewer.class, "StringsTextViewer.totalPageLabel.text_1")); // NOI18N

        ofLabel.setText(org.openide.util.NbBundle.getMessage(StringsTextViewer.class, "StringsTextViewer.ofLabel.text_1")); // NOI18N

        currentPageLabel.setText(org.openide.util.NbBundle.getMessage(StringsTextViewer.class, "StringsTextViewer.currentPageLabel.text_1")); // NOI18N
        currentPageLabel.setMaximumSize(new java.awt.Dimension(18, 14));
        currentPageLabel.setPreferredSize(new java.awt.Dimension(18, 14));

        pageLabel.setText(org.openide.util.NbBundle.getMessage(StringsTextViewer.class, "StringsTextViewer.pageLabel.text_1")); // NOI18N
        pageLabel.setMaximumSize(new java.awt.Dimension(33, 14));
        pageLabel.setMinimumSize(new java.awt.Dimension(33, 14));

        nextPageButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/sleuthkit/autopsy/corecomponents/btn_step_forward.png"))); // NOI18N
        nextPageButton.setText(org.openide.util.NbBundle.getMessage(StringsTextViewer.class, "StringsTextViewer.nextPageButton.text")); // NOI18N
        nextPageButton.setBorderPainted(false);
        nextPageButton.setContentAreaFilled(false);
        nextPageButton.setDisabledIcon(new javax.swing.ImageIcon(getClass().getResource("/org/sleuthkit/autopsy/corecomponents/btn_step_forward_disabled.png"))); // NOI18N
        nextPageButton.setMargin(new java.awt.Insets(2, 0, 2, 0));
        nextPageButton.setPreferredSize(new java.awt.Dimension(55, 23));
        nextPageButton.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/org/sleuthkit/autopsy/corecomponents/btn_step_forward_hover.png"))); // NOI18N
        nextPageButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nextPageButtonActionPerformed(evt);
            }
        });

        pageLabel2.setText(org.openide.util.NbBundle.getMessage(StringsTextViewer.class, "StringsTextViewer.pageLabel2.text")); // NOI18N
        pageLabel2.setMaximumSize(new java.awt.Dimension(29, 14));
        pageLabel2.setMinimumSize(new java.awt.Dimension(29, 14));

        prevPageButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/sleuthkit/autopsy/corecomponents/btn_step_back.png"))); // NOI18N
        prevPageButton.setText(org.openide.util.NbBundle.getMessage(StringsTextViewer.class, "StringsTextViewer.prevPageButton.text")); // NOI18N
        prevPageButton.setBorderPainted(false);
        prevPageButton.setContentAreaFilled(false);
        prevPageButton.setDisabledIcon(new javax.swing.ImageIcon(getClass().getResource("/org/sleuthkit/autopsy/corecomponents/btn_step_back_disabled.png"))); // NOI18N
        prevPageButton.setMargin(new java.awt.Insets(2, 0, 2, 0));
        prevPageButton.setPreferredSize(new java.awt.Dimension(55, 23));
        prevPageButton.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/org/sleuthkit/autopsy/corecomponents/btn_step_back_hover.png"))); // NOI18N
        prevPageButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                prevPageButtonActionPerformed(evt);
            }
        });

        goToPageLabel.setText(org.openide.util.NbBundle.getMessage(StringsTextViewer.class, "StringsTextViewer.goToPageLabel.text")); // NOI18N

        goToPageTextField.setText(org.openide.util.NbBundle.getMessage(StringsTextViewer.class, "StringsTextViewer.goToPageTextField.text")); // NOI18N
        goToPageTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                goToPageTextFieldActionPerformed(evt);
            }
        });

        languageCombo.setToolTipText(org.openide.util.NbBundle.getMessage(StringsTextViewer.class, "StringsTextViewer.languageCombo.toolTipText")); // NOI18N
        languageCombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                languageComboActionPerformed(evt);
            }
        });

        languageLabel.setText(org.openide.util.NbBundle.getMessage(StringsTextViewer.class, "StringsTextViewer.languageLabel.text")); // NOI18N
        languageLabel.setToolTipText(org.openide.util.NbBundle.getMessage(StringsTextViewer.class, "StringsTextViewer.languageLabel.toolTipText")); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pageLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(currentPageLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(ofLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(totalPageLabel)
                .addGap(50, 50, 50)
                .addComponent(pageLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(prevPageButton, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(nextPageButton, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(goToPageLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(goToPageTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 357, Short.MAX_VALUE)
                .addComponent(languageLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(languageCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(pageLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(currentPageLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(ofLabel)
                .addComponent(totalPageLabel))
            .addComponent(pageLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(nextPageButton, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(prevPageButton, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(goToPageLabel)
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(goToPageTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(languageCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(languageLabel))
        );

        jScrollPane2.setViewportView(jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 966, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void languageComboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_languageComboActionPerformed

        if (dataSource != null) {
            setDataView(dataSource, currentOffset);
        }
    }//GEN-LAST:event_languageComboActionPerformed

    private void goToPageTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_goToPageTextFieldActionPerformed
        String pageNumberStr = goToPageTextField.getText();
        int pageNumber;
        int maxPage = Math.round((dataSource.getSize() - 1) / PAGE_LENGTH) + 1;
        try {
            pageNumber = Integer.parseInt(pageNumberStr);
        } catch (NumberFormatException ex) {
            pageNumber = maxPage + 1;
        }
        if (pageNumber > maxPage || pageNumber < 1) {
            JOptionPane.showMessageDialog(this,
                NbBundle.getMessage(this.getClass(),
                    "StringsTextViewer.goToPageTextField.msgDlg",
                    maxPage),
                NbBundle.getMessage(this.getClass(),
                    "StringsTextViewer.goToPageTextField.err"),
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        currentOffset = (pageNumber - 1) * PAGE_LENGTH;
        currentPage = pageNumber;
        currentPageLabel.setText(Integer.toString(currentPage));
        setDataView(dataSource, currentOffset);
    }//GEN-LAST:event_goToPageTextFieldActionPerformed

    private void prevPageButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_prevPageButtonActionPerformed
        //@@@ this is part of the code dealing with the data viewer. could be copied/removed to implement the scrollbar
        currentOffset -= PAGE_LENGTH;
        currentPage -= 1;
        currentPageLabel.setText(Integer.toString(currentPage));
        setDataView(dataSource, currentOffset);
    }//GEN-LAST:event_prevPageButtonActionPerformed

    private void nextPageButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nextPageButtonActionPerformed
        //@@@ this is part of the code dealing with the data viewer. could be copied/removed to implement the scrollbar
        currentOffset += PAGE_LENGTH;
        currentPage += 1;
        currentPageLabel.setText(Integer.toString(currentPage));
        setDataView(dataSource, currentOffset);
    }//GEN-LAST:event_nextPageButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem copyMenuItem;
    private javax.swing.JLabel currentPageLabel;
    private javax.swing.JLabel goToPageLabel;
    private javax.swing.JTextField goToPageTextField;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JComboBox<SCRIPT> languageCombo;
    private javax.swing.JLabel languageLabel;
    private javax.swing.JButton nextPageButton;
    private javax.swing.JLabel ofLabel;
    private javax.swing.JTextPane outputViewPane;
    private javax.swing.JLabel pageLabel;
    private javax.swing.JLabel pageLabel2;
    private javax.swing.JButton prevPageButton;
    private javax.swing.JPopupMenu rightClickMenu;
    private javax.swing.JMenuItem selectAllMenuItem;
    private javax.swing.JLabel totalPageLabel;
    // End of variables declaration//GEN-END:variables

    /**
     * Sets the DataView (The tabbed panel)
     *
     * @param dataSource the content that want to be shown
     * @param offset     the starting offset
     */
    private void setDataView(Content dataSource, long offset) {
        if (dataSource == null) {
            return;
        }

        // change the cursor to "waiting cursor" for this operation
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

        this.dataSource = dataSource;

        int bytesRead = 0;
        // set the data on the bottom and show it
   
        if (dataSource.getSize() > 0) {
            try {
                bytesRead = dataSource.read(data, offset, PAGE_LENGTH); // read the data
            } catch (TskCoreException ex) {
                logger.log(Level.WARNING, "Error while trying to show the String content.", ex); //NON-NLS
            }
        }
        String text;
        if (bytesRead > 0) {
            //text = DataConversion.getString(data, bytesRead, 4);
            final SCRIPT selScript = (SCRIPT) languageCombo.getSelectedItem();
            stringExtract.setEnabledScript(selScript);
            StringExtractResult res = stringExtract.extract(data, bytesRead, 0);
            text = res.getText();
            if (text.trim().isEmpty()) {
                text = NbBundle.getMessage(this.getClass(),
                        "StringsTextViewer.setDataView.errorNoText", currentOffset,
                        currentOffset + PAGE_LENGTH);
            }
        } else {
            text = NbBundle.getMessage(this.getClass(), "StringsTextViewer.setDataView.errorText", currentOffset,
                    currentOffset + PAGE_LENGTH);
        }

        // disable or enable the next button
        if (offset + PAGE_LENGTH < dataSource.getSize()) {
            nextPageButton.setEnabled(true);
        } else {
            nextPageButton.setEnabled(false);
        }

        if (offset == 0) {
            prevPageButton.setEnabled(false);
            currentPage = 1; // reset the page number
        } else {
            prevPageButton.setEnabled(true);
        }

        int totalPage = Math.round((dataSource.getSize() - 1) / PAGE_LENGTH) + 1;
        totalPageLabel.setText(Integer.toString(totalPage));
        currentPageLabel.setText(Integer.toString(currentPage));
        outputViewPane.setText(text); // set the output view
        setComponentsVisibility(true); // shows the components that not needed
        outputViewPane.moveCaretPosition(0);

        this.setCursor(null);
    }

    private void setDataView(StringContent dataSource) {
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        try {
            this.dataSource = null;

            // set the data on the bottom and show it
            String text = dataSource.getString();

            nextPageButton.setEnabled(false);

            prevPageButton.setEnabled(false);
            currentPage = 1;

            int totalPage = 1;
            totalPageLabel.setText(Integer.toString(totalPage));
            currentPageLabel.setText(Integer.toString(currentPage));
            outputViewPane.setText(text); // set the output view
            setComponentsVisibility(true); // shows the components that not needed
            outputViewPane.moveCaretPosition(0);
        } finally {
            this.setCursor(null);
        }
    }

    /**
     * To set the visibility of specific components in this class.
     *
     * @param isVisible whether to show or hide the specific components
     */
    private void setComponentsVisibility(boolean isVisible) {
        currentPageLabel.setVisible(isVisible);
        totalPageLabel.setVisible(isVisible);
        ofLabel.setVisible(isVisible);
        prevPageButton.setVisible(isVisible);
        nextPageButton.setVisible(isVisible);
        pageLabel.setVisible(isVisible);
        pageLabel2.setVisible(isVisible);
        goToPageTextField.setVisible(isVisible);
        goToPageLabel.setVisible(isVisible);
        languageCombo.setVisible(isVisible);
        languageLabel.setVisible(isVisible);
    }

    @Override
    public void setNode(Node selectedNode) {
        if ((selectedNode == null) || (!isSupported(selectedNode))) {
            resetComponent();
            return;
        }

        Content content = DataContentViewerUtility.getDefaultContent(selectedNode);
        if (content != null) {
            this.setDataView(content, 0);
            return;
        } else {
            StringContent scontent = selectedNode.getLookup().lookup(StringContent.class);
            if (scontent != null) {
                this.setDataView(scontent);
                return;
            }
        }
        resetComponent();
    }

    @Override
    public String getTitle() {
        return NbBundle.getMessage(this.getClass(), "StringsTextViewer.title");
    }

    @Override
    public String getToolTip() {
        return NbBundle.getMessage(this.getClass(), "StringsTextViewer.toolTip");
    }

    @Override
    public TextViewer createInstance() {
        return new StringsTextViewer();
    }

    @Override
    public void resetComponent() {
        // clear / reset the fields
        currentPage = 1;
        currentOffset = 0;
        this.dataSource = null;
        currentPageLabel.setText("");
        totalPageLabel.setText("");
        prevPageButton.setEnabled(false);
        nextPageButton.setEnabled(false);
        outputViewPane.setText(""); // reset the output view
        setComponentsVisibility(false); // hides the components that not needed
    }

    @Override
    public boolean isSupported(Node node) {
        if (node == null) {
            return false;
        }
        Content content = DataContentViewerUtility.getDefaultContent(node);
        return (content != null && content.getSize() > 0);
    }

    @Override
    public int isPreferred(Node node) {
        return 1;
    }

    @Override
    public Component getComponent() {
        return this;
    }
}
