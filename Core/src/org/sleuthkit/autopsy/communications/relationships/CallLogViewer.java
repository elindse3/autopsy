/*
 * Autopsy Forensic Browser
 *
 * Copyright 2019 Basis Technology Corp.
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
package org.sleuthkit.autopsy.communications.relationships;

import javax.swing.JPanel;
import javax.swing.table.TableColumnModel;
import org.netbeans.swing.etable.ETableColumn;
import org.netbeans.swing.etable.ETableColumnModel;
import org.netbeans.swing.outline.DefaultOutlineModel;
import org.netbeans.swing.outline.Outline;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.nodes.NodeAdapter;
import org.openide.nodes.NodeMemberEvent;
import org.openide.util.Lookup;
import org.openide.util.NbBundle.Messages;
import org.sleuthkit.autopsy.corecomponents.TableFilterNode;
import org.sleuthkit.autopsy.directorytree.DataResultFilterNode;
import static org.sleuthkit.datamodel.BlackboardAttribute.ATTRIBUTE_TYPE.TSK_PHONE_NUMBER_FROM;
import static org.sleuthkit.datamodel.BlackboardAttribute.ATTRIBUTE_TYPE.TSK_PHONE_NUMBER_TO;
import static org.sleuthkit.datamodel.BlackboardAttribute.ATTRIBUTE_TYPE.TSK_DATETIME_START;

/**
 *
 * 
 */
final class CallLogViewer extends javax.swing.JPanel implements RelationshipsViewer {
    
    private final CallLogsChildNodeFactory nodeFactory;
    
    @Messages({
        "CallLogViewer_title=Call Logs",
        "CallLogViewer_noCallLogs=<No call logs found for selected account>"
    })
    
    /**
     * Creates new form CallLogViewer
     */
    public CallLogViewer() {
        initComponents();
        
        nodeFactory = new CallLogsChildNodeFactory(null);
        outlineViewPanel.hideOutlineView(Bundle.CallLogViewer_noCallLogs());
        
        outlineViewPanel.getOutlineView().setPropertyColumns(
                TSK_PHONE_NUMBER_FROM.getLabel(), TSK_PHONE_NUMBER_FROM.getDisplayName(),
                TSK_PHONE_NUMBER_TO.getLabel(), TSK_PHONE_NUMBER_TO.getDisplayName(),
                TSK_DATETIME_START.getLabel(), TSK_DATETIME_START.getDisplayName()
        );
        
        Outline outline = outlineViewPanel.getOutlineView().getOutline();
        outline.setRootVisible(false);
        
        TableColumnModel columnModel = outline.getColumnModel();
        ETableColumn column = (ETableColumn) columnModel.getColumn(0);
        ((ETableColumnModel) columnModel).setColumnHidden(column, true);
        
        outlineViewPanel.getExplorerManager().setRootContext(
                new TableFilterNode(
                        new DataResultFilterNode(
                                new AbstractNode(Children.create(nodeFactory, true)), outlineViewPanel.getExplorerManager()), true));
        
         outlineViewPanel.getExplorerManager().getRootContext().addNodeListener(new NodeAdapter(){
            @Override
            public void childrenAdded(NodeMemberEvent nme) {
                updateOutlineViewPanel();
            }

            @Override
            public void childrenRemoved(NodeMemberEvent nme) {
                updateOutlineViewPanel();
            }       
        });
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        outlineViewPanel = new org.sleuthkit.autopsy.communications.relationships.OutlineViewPanel();

        setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(15, 15, 15, 15);
        add(outlineViewPanel, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

    @Override
    public String getDisplayName() {
        return Bundle.CallLogViewer_title();
    }

    @Override
    public JPanel getPanel() {
        return this;
    }

    @Override
    public void setSelectionInfo(SelectionInfo info) {
        nodeFactory.refresh(info);
    }

    @Override
    public Lookup getLookup() {
       return outlineViewPanel.getLookup();
    }
    
    private void updateOutlineViewPanel() {
        int nodeCount = outlineViewPanel.getExplorerManager().getRootContext().getChildren().getNodesCount();
        if(nodeCount == 0) {
            outlineViewPanel.hideOutlineView(Bundle.ContactsViewer_noContacts_message());
        } else {
            outlineViewPanel.showOutlineView();
        }
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private org.sleuthkit.autopsy.communications.relationships.OutlineViewPanel outlineViewPanel;
    // End of variables declaration//GEN-END:variables
}
