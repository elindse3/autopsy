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
package org.sleuthkit.autopsy.contentviewers.textContentViewer;

import java.awt.Component;
import org.openide.nodes.Node;
import org.openide.util.NbBundle.Messages;
import org.openide.util.lookup.ServiceProvider;
import org.sleuthkit.autopsy.corecomponentinterfaces.DataContentViewer;
import org.sleuthkit.autopsy.coreutils.Logger;

@ServiceProvider(service = DataContentViewer.class, position = 2)
public class TextContentViewer implements DataContentViewer {

    private static final Logger logger = Logger.getLogger(TextContentViewer.class.getName());
    private final TextContentViewerPanel panel;
    private volatile Node currentNode = null;

    public TextContentViewer() {
        this(true);
    }

    private TextContentViewer(boolean isMain) {
        panel = new TextContentViewerPanel(isMain);
    }

    @Override
    public void setNode(Node selectedNode) {
        currentNode = selectedNode;
        panel.setNode(currentNode);

    }

    @Messages({"TextContentViewer.title=Text"})
    @Override
    public String getTitle() {
        return Bundle.TextContentViewer_title();
    }

    @Messages({"TextContentViewer.tooltip=Displays text associated with the selected item"})
    @Override
    public String getToolTip() {
        return Bundle.TextContentViewer_tooltip();
    }

    @Override
    public DataContentViewer createInstance() {
        return new TextContentViewer(false);
    }

    @Override
    public Component getComponent() {
        return panel;
    }

    @Override
    public void resetComponent() {
        currentNode = null;
    }

    @Override
    public boolean isSupported(Node node) {
        //if any of the subvewiers are supported then this is supported
        if (node == null) {
            return false;
        }
        return panel.isSupported(node);
    }

    @Override
    public int isPreferred(Node node) {
        //return max of supported TextViewers isPreferred methods
        return panel.isPreffered(node);
    }

}
