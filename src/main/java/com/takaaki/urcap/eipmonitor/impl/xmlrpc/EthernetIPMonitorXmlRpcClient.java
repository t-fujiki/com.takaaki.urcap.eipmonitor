package com.takaaki.urcap.eipmonitor.impl.xmlrpc;

import com.takaaki.urcap.eipmonitor.impl.EthernetIPMonitorInstallationNodeContribution;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfig;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;

public class EthernetIPMonitorXmlRpcClient extends XmlRpcClient {
    private XmlRpcClientConfigImpl config;

    private EthernetIPMonitorInstallationNodeContribution contribution;

    public EthernetIPMonitorXmlRpcClient(EthernetIPMonitorInstallationNodeContribution contribution, int port) {
        this.contribution = contribution;
        this.config = new XmlRpcClientConfigImpl();
        this.config.setConnectionTimeout(1000);
        try {
            this.config.setServerURL(new URL("http://127.0.0.1:" + String.valueOf(port) + "/RPC2"));
            setConfig((XmlRpcClientConfig) this.config);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public void showMonitor(boolean b) {
        ArrayList<Object> args1 = new ArrayList();
        try {
            execute("eip_monitor.show", args1);
        } catch (XmlRpcException e) {
            e.printStackTrace();
        }
    }
}
