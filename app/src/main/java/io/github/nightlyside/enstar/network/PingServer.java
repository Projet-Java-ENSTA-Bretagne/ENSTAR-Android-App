package io.github.nightlyside.enstar.network;

import android.util.Log;

public class PingServer {

    public static boolean ping(String host, int port) {
        TCPClient client = new TCPClient(host, port);
        client.connectToServer();
        String res = client.sendRequest("{'command': 'PING'}");
        Log.i(PingServer.class.getSimpleName(), res);

        return true;
    }

    public static boolean ping(String address) {
        String[] args = address.split(":");
        return ping(args[0], Integer.parseInt(args[1]));
    }
}
