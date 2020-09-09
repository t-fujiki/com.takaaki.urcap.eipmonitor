package com.takaaki.urcap.eipmonitor.impl.realtime;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * RealTime通信用クライアントオブジェクト
 * 
 */
public class RealTimeClient {

    private String MESSAGE_HEADER = "Client -> port30003:";
    private String ipAddress;

    /**
     * コンストラクタ
     */
    public RealTimeClient(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    /**
     * ブール型レジスタにビットを設定
     * 
     * @param addr レジスタのアドレス
     * @param bit  設定するビット
     */
    public void setBitToRegister(int addr, boolean bit) {
        String command = "write_output_boolean_register(" + String.valueOf(addr) + ","
                + String.valueOf(bit ? "True" : "False") + ")";
        sendCommand(command);
    }

    /**
     * RealTime通信サーバへコマンド送信
     * 
     * @param command コマンド
     */
    public void sendCommand(String command) {
        try {

            Socket socket = new Socket(ipAddress, 30003);
            socket.setSoTimeout(1000);

            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);

            if (socket.isConnected()) {

                System.out.println(MESSAGE_HEADER + "Send \"" + command + "\"");
                writer.println(command);
                writer.flush();

            }

            socket.close();
            reader.close();
            writer.close();

        } catch (Exception e) {
            System.out.println(MESSAGE_HEADER + e.getMessage());
        }
    }

}