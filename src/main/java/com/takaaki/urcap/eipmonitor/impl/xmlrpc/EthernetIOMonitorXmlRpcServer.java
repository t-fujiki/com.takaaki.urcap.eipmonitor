package com.takaaki.urcap.eipmonitor.impl.xmlrpc;

import org.apache.xmlrpc.server.*;
import org.apache.xmlrpc.webserver.WebServer;
import java.io.IOException;

import com.takaaki.urcap.eipmonitor.impl.EthernetIPMonitorInstallationNodeContribution;

public class EthernetIOMonitorXmlRpcServer extends XmlRpcServer {

    private WebServer webServer;

    public EthernetIOMonitorXmlRpcServer(EthernetIPMonitorInstallationNodeContribution contribution, int port)
            throws Exception {

        EthernetIPMonitorHandler.initialize(contribution);

        webServer = new WebServer(port);
        XmlRpcServer xmlRpcServer = webServer.getXmlRpcServer();

        PropertyHandlerMapping phm = new PropertyHandlerMapping();
        phm.addHandler("eip_monitor", EthernetIPMonitorHandler.class);
        xmlRpcServer.setHandlerMapping(phm);

        XmlRpcServerConfigImpl config = (XmlRpcServerConfigImpl) xmlRpcServer.getConfig();
        config.setEnabledForExtensions(true);
        config.setContentLengthOptional(false);
    }

    public void start() throws IOException {
        webServer.start();
    }

    public void stop() {
        webServer.shutdown();
    }
}