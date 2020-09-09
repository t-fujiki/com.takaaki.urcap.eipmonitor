package com.takaaki.urcap.eipmonitor.impl.xmlrpc;

import com.takaaki.urcap.eipmonitor.impl.EthernetIPMonitorInstallationNodeContribution;

public class EthernetIPMonitorHandler {

    private static EthernetIPMonitorInstallationNodeContribution _contribution;

    public static void initialize(EthernetIPMonitorInstallationNodeContribution contribution) {
        _contribution = contribution;
    }

    public boolean show() throws EthernetIPMonitorHandlerException {

        _contribution.ioMonitorFrame.setVisible(true);

        return true;
    }

}