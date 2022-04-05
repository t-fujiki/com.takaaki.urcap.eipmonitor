package com.takaaki.urcap.eipmonitor.impl;

import com.takaaki.urcap.eipmonitor.impl.xmlrpc.EthernetIPMonitorXmlRpcClient;
import com.ur.urcap.api.contribution.InstallationNodeContribution;
import com.ur.urcap.api.contribution.InstallationNodeService;
import com.ur.urcap.api.domain.URCapAPI;

import java.io.InputStream;

import com.ur.urcap.api.domain.data.DataModel;

public class EthernetIPMonitorInstallationNodeService implements InstallationNodeService {
	private EthernetIPMonitorXmlRpcClient ethernetIPMonitorXmlRpcClient;
	public EthernetIPMonitorInstallationNodeContribution contribution = null;

	public EthernetIPMonitorInstallationNodeService(EthernetIPMonitorXmlRpcClient ethernetIPMonitorXmlRpcClient) {
		this.ethernetIPMonitorXmlRpcClient = ethernetIPMonitorXmlRpcClient;
	}

	@Override
	public InstallationNodeContribution createInstallationNode(URCapAPI api, DataModel model) {

		contribution = new EthernetIPMonitorInstallationNodeContribution(api, model,
				this.ethernetIPMonitorXmlRpcClient);

		return contribution;
	}

	@Override
	public String getTitle() {
		return "Ethernet/IP I/O Monitor";
	}

	@Override
	public InputStream getHTML() {

		return this.getClass().getResourceAsStream("/com/takaaki/urcap/eipmonitor/impl/installation_en.html");

	}
}
