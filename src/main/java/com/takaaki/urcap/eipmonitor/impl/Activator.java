package com.takaaki.urcap.eipmonitor.impl;

import com.takaaki.urcap.eipmonitor.impl.xmlrpc.EthernetIPMonitorXmlRpcClient;
import com.ur.urcap.api.contribution.InstallationNodeService;
import com.ur.urcap.api.contribution.toolbar.swing.SwingToolbarService;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class Activator implements BundleActivator {

	@Override
	public void start(BundleContext context) throws Exception {
		EthernetIPMonitorXmlRpcClient ethernetIPMonitorXmlRpcClient = new EthernetIPMonitorXmlRpcClient(null, 60005);
		EIPToolbarService eipToolbarService = new EIPToolbarService(ethernetIPMonitorXmlRpcClient);

		EthernetIPMonitorInstallationNodeService statusMonitorInstallationNodeService = new EthernetIPMonitorInstallationNodeService(
				ethernetIPMonitorXmlRpcClient);
		context.registerService(SwingToolbarService.class, eipToolbarService, null);
		context.registerService(InstallationNodeService.class, statusMonitorInstallationNodeService, null);

	}

	@Override
	public void stop(BundleContext bundleContext) throws Exception {

	}
}
