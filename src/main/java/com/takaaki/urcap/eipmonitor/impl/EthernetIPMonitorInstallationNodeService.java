package com.takaaki.urcap.eipmonitor.impl;

import com.ur.urcap.api.contribution.InstallationNodeContribution;
import com.ur.urcap.api.contribution.InstallationNodeService;
import com.ur.urcap.api.domain.URCapAPI;

import java.io.InputStream;

import com.ur.urcap.api.domain.data.DataModel;

public class EthernetIPMonitorInstallationNodeService implements InstallationNodeService {

	public EthernetIPMonitorInstallationNodeContribution contribution = null;

	public EthernetIPMonitorInstallationNodeService() {

	}

	@Override
	public InstallationNodeContribution createInstallationNode(URCapAPI api, DataModel model) {

		contribution = new EthernetIPMonitorInstallationNodeContribution(api, model);

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
