package com.takaaki.urcap.eipmonitor.impl;

import com.takaaki.urcap.eipmonitor.impl.xmlrpc.EthernetIPMonitorXmlRpcClient;
import com.ur.urcap.api.contribution.InstallationNodeService;
import com.ur.urcap.api.contribution.toolbar.swing.SwingToolbarService;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class Activator implements BundleActivator {

	private final int DEFAULT_PORT = 61101;

	@Override
	public void start(BundleContext context) throws Exception {

		EthernetIPMonitorXmlRpcClient ethernetIPMonitorXmlRpcClient = new EthernetIPMonitorXmlRpcClient(null,
				DEFAULT_PORT);
		EIPToolbarService eipToolbarService = new EIPToolbarService(ethernetIPMonitorXmlRpcClient);

		EthernetIPMonitorInstallationNodeService ethernetIPMonitorInstallationNodeService = new EthernetIPMonitorInstallationNodeService(
				ethernetIPMonitorXmlRpcClient);
		context.registerService(SwingToolbarService.class, eipToolbarService, null);
		context.registerService(InstallationNodeService.class, ethernetIPMonitorInstallationNodeService, null);

	}

	@Override
	public void stop(BundleContext bundleContext) throws Exception {

	}
}
