package com.takaaki.urcap.eipmonitor.impl;

import com.takaaki.urcap.eipmonitor.impl.xmlrpc.EthernetIPMonitorXmlRpcClient;
import com.ur.urcap.api.contribution.toolbar.ToolbarContext;
import com.ur.urcap.api.contribution.toolbar.swing.SwingToolbarContribution;
import javax.swing.JPanel;

class EIPToolbarContribution implements SwingToolbarContribution {
    private final ToolbarContext context;

    private EthernetIPMonitorXmlRpcClient ethernetIPMonitorXmlRpcClient;

    EIPToolbarContribution(ToolbarContext context, EthernetIPMonitorXmlRpcClient ethernetIPMonitorXmlRpcClient) {
        this.context = context;
        this.ethernetIPMonitorXmlRpcClient = ethernetIPMonitorXmlRpcClient;
    }

    public void openView() {
        if (this.ethernetIPMonitorXmlRpcClient != null)
            this.ethernetIPMonitorXmlRpcClient.showMonitor(true);
    }

    public void closeView() {
    }

    public void buildUI(JPanel jPanel) {
    }
}
