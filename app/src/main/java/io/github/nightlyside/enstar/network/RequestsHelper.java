package io.github.nightlyside.enstar.network;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class RequestsHelper {

    public enum StatusCode {
        OK,
        DENIED,
        NOT_FOUND,
        TOKEN_ERROR,
        SERVER_ERROR
    }

    private final String TAG = RequestsHelper.class.getSimpleName();

    private String host;
    private int port;

    public static RequestsHelper forServer(String host, int port) {
        RequestsHelper helper = new RequestsHelper();
        helper.host = host;
        helper.port = port;

        return helper;
    }

    public static RequestsHelper forServer(String address) {
        String[] args = address.split(":");
        return forServer(args[0], Integer.parseInt(args[1]));
    }

    public String tryLogin(String username, String password) {
        Log.i(TAG, "Login with : " + username + "/" + password + " combo.");

        // Getting the client
        TCPClient client = new TCPClient(host, port);
        client.connectToServer();

        // Hashing the password
        String hashedPassword;
        try {
            MessageDigest hashAlgo = MessageDigest.getInstance("SHA-512");
            hashedPassword = new String(hashAlgo.digest(password.getBytes()));
        } catch (NoSuchAlgorithmException e) {
            Log.e(TAG, e.toString());
            return null;
        }


        // Putting everything in the JSON object
        JSONObject cmd = new JSONObject();
        JSONObject args = new JSONObject();

        try {
            cmd.put("command", "login");
            args.put("username", username);
            args.put("password", hashedPassword);
            cmd.put("args", args);
        } catch (JSONException e) {
            Log.e(TAG, e.toString());
            return null;
        }

        String res = client.sendRequest(cmd.toString());
        JSONObject resJSON;
        String status;
        String message;
        String userID;
        try {
            resJSON = new JSONObject(res);
            status = resJSON.getString("status");
            message = resJSON.getJSONObject("data").getString("message");
            userID = resJSON.getJSONObject("data").getString("user_id");
            Log.i(TAG, resJSON.toString());
        } catch (JSONException e) {
            Log.e(TAG, e.toString());
            Log.i(TAG, res);
            return null;
        }

        if (status.equals(StatusCode.OK.toString())) {
            return userID;
        } else {
            Log.w(TAG, message);
            return null;
        }
    }

    public JSONObject getUserFromID(String userID) {
        Log.i(TAG, "Getting infos about: " + userID);

        // Getting the client
        TCPClient client = new TCPClient(host, port);
        client.connectToServer();

        JSONObject req = new JSONObject();
        JSONObject args = new JSONObject();
        try {
            req.put("command", "getUserByID");
            args.put("user_id", userID);
            req.put("args", args);
        } catch (JSONException e) {
            Log.e(TAG, e.toString());
            return null;
        }

        String res = client.sendRequest(req.toString());
        JSONObject resJSON;
        String status;
        String message;
        JSONObject user;
        try {
            resJSON = new JSONObject(res);
            status = resJSON.getString("status");
            message = resJSON.getJSONObject("data").getString("message");
        } catch (JSONException e) {
            Log.e(TAG, e.toString());
            return null;
        }

        if (status.equals(StatusCode.OK.toString())) {
            try {
                user = new JSONObject(resJSON.getJSONObject("data").getString("user"));
            } catch (JSONException e) {
                Log.e(TAG, e.toString());
                return null;
            }
            return user;
        } else {
            Log.w(TAG, message);
            return null;
        }
    }
}