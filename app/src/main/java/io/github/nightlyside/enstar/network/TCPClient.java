package io.github.nightlyside.enstar.network;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.LogManager;

public class TCPClient {

    private final String TAG = TCPClient.class.getSimpleName();

    private int port;
    private String host;
    private Socket serverSocket;
    private PrintStream outStream;
    private BufferedReader inStream;

    public TCPClient(String host, int port) {
        this.port = port;
        this.host = host;
    }

    public boolean connectToServer() {
        boolean ok = false;
        try {
            Log.i(TAG, "Trying to connect to: " + host + ":" + port);
            serverSocket = new Socket();
            serverSocket.connect(new InetSocketAddress(host, port), 2000);

            outStream = new PrintStream(serverSocket.getOutputStream());
            inStream = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));
            Log.i(TAG, "Connexion successful");
            ok = true;
        } catch (UnknownHostException e) {
            Log.e(TAG, "Unknown host", e);
        } catch (ConnectException e) {
            Log.e(TAG,"Error during connexion", e);
        } catch (IOException e) {
            Log.e(TAG,"Error during data exchange", e);
        }

        return ok;
    }

    public void disconnectFromServer() {
        try {
            Log.i(TAG,"Client : " + serverSocket);
            outStream.close();
            inStream.close();
            serverSocket.close();
        } catch (Exception e) {
            Log.e(TAG,"Error during disconnect...");
        }
    }

    public String sendRequest(String request) {
        String serverResponse = null;
        // Trying to send the request
        try {
            Log.d(TAG,"Client request: " + request);
            outStream.println(request);
            outStream.flush();
            // Waiting for the main.java.server response
            serverResponse = inStream.readLine();
            Log.d(TAG,"Server response: " + serverResponse);
        } catch (UnknownHostException e) {
            Log.e(TAG,"Unknown host : " + e);
        } catch (IOException e) {
            Log.e(TAG,"IOException : " + e);
        }

        return serverResponse;
    }
}
