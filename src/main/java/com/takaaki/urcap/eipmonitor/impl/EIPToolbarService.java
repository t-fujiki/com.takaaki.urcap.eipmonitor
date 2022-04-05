package com.takaaki.urcap.eipmonitor.impl;

import com.takaaki.urcap.eipmonitor.impl.xmlrpc.EthernetIPMonitorXmlRpcClient;
import com.ur.urcap.api.contribution.toolbar.ToolbarConfiguration;
import com.ur.urcap.api.contribution.toolbar.ToolbarContext;
import com.ur.urcap.api.contribution.toolbar.swing.SwingToolbarContribution;
import com.ur.urcap.api.contribution.toolbar.swing.SwingToolbarService;
import javax.swing.Icon;
import javax.swing.ImageIcon;

public class EIPToolbarService implements SwingToolbarService {
    private EthernetIPMonitorXmlRpcClient ethernetIPMonitorXmlRpcClient;

    public EIPToolbarService(EthernetIPMonitorXmlRpcClient ethernetIPMonitorXmlRpcClient) {
        this.ethernetIPMonitorXmlRpcClient = ethernetIPMonitorXmlRpcClient;
    }

    public Icon getIcon() {
        return new ImageIcon(getClass().getResource("/icons/eip_logo.png"));
    }

    public void configureContribution(ToolbarConfiguration configuration) {
        configuration.setToolbarHeight(200);
    }

    public SwingToolbarContribution createToolbar(ToolbarContext context) {
        return new EIPToolbarContribution(context, this.ethernetIPMonitorXmlRpcClient);
    }
}
