package com.takaaki.urcap.eipmonitor.impl;

import com.takaaki.urcap.eipmonitor.impl.iomonitor.IOMonitorFrame;
import com.takaaki.urcap.eipmonitor.impl.xmlrpc.EthernetIOMonitorXmlRpcServer;
import com.takaaki.urcap.eipmonitor.impl.xmlrpc.EthernetIPMonitorXmlRpcClient;
import com.ur.urcap.api.contribution.InstallationNodeContribution;
import com.ur.urcap.api.domain.URCapAPI;
import com.ur.urcap.api.domain.data.DataModel;
import com.ur.urcap.api.domain.script.ScriptWriter;
import com.ur.urcap.api.ui.annotation.Input;
import com.ur.urcap.api.ui.component.InputEvent;
import com.ur.urcap.api.ui.component.InputTextField;
import com.ur.urcap.api.ui.component.*;
import com.ur.urcap.api.ui.component.InputEvent.EventType;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import static com.ur.urcap.api.ui.component.InputEvent.EventType.ON_PRESSED;

public class EthernetIPMonitorInstallationNodeContribution implements InstallationNodeContribution {
	private EthernetIPMonitorXmlRpcClient ethernetIPMonitorXmlRpcClient;
	public URCapAPI api;
	public DataModel model;

	public IOMonitorFrame ioMonitorFrame;

	private final int DEFAULT_PORT = 61101;

	@Input(id = "btnLoadIOMonitor")
	private InputButton btnLoadIOMonitor;

	@Input(id = "inputPort")
	private InputTextField inputPort;

	@Input(id = "inputPort")
	private void onChange_inputPort(InputEvent event) {
		if (event.getEventType() == EventType.ON_CHANGE) {

			if (!inputPort.getText().isEmpty())
				model.set("inputPort", Integer.valueOf(inputPort.getText()));
			else
				model.remove("inputPort");

		}
	}

	@Input(id = "btnLoadIOMonitor")
	private void onClick_btnLoadIOMonitor(InputEvent event) {
		if (event.getEventType() == ON_PRESSED)
			ioMonitorFrame.setVisible(true);

	}

	public String[] readScriptFile(String filename) {
		try {

			BufferedReader br = new BufferedReader(
					new InputStreamReader(this.getClass().getResourceAsStream(filename)));

			ArrayList<String> list = new ArrayList<String>();

			String addstr;
			while ((addstr = br.readLine()) != null) {
				list.add(addstr);
			}

			br.close();
			String[] res = list.toArray(new String[0]);
			return res;

		} catch (IOException e) {
			return null;
		}

	}

	public EthernetIPMonitorInstallationNodeContribution(URCapAPI api, DataModel model,
			EthernetIPMonitorXmlRpcClient ethernetIPMonitorXmlRpcClient) {
		this.api = api;
		this.model = model;
		this.ethernetIPMonitorXmlRpcClient = ethernetIPMonitorXmlRpcClient;

		if (ioMonitorFrame != null) {
			ioMonitorFrame.dispose();
			ioMonitorFrame = null;
		}

		ioMonitorFrame = new IOMonitorFrame(this);

		int port = model.get("inputPort", DEFAULT_PORT);

		try {
			EthernetIOMonitorXmlRpcServer xmlRpcServer = new EthernetIOMonitorXmlRpcServer(this, port);
			xmlRpcServer.start();
		} catch (Exception e) {

		}

	}

	@Override
	public void openView() {

		inputPort.setText(String.valueOf(model.get("inputPort", DEFAULT_PORT)));

	}

	@Override
	public void closeView() {

	}

	public boolean isDefined() {
		return false;
	}

	@Override
	public void generateScript(ScriptWriter writer) {

		String[] scripts = readScriptFile("/com/takaaki/urcap/eipmonitor/impl/eipmonitor.script");

		for (String str : scripts) {
			str = str.replace("{port}", String.valueOf(model.get("inputPort", DEFAULT_PORT)));
			writer.appendLine(str);
		}

	}

}
