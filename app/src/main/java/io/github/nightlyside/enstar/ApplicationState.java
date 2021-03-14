package io.github.nightlyside.enstar;

import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONObject;

public class ApplicationState {

    private Context applicationContext;
    private static ApplicationState instance;

    // variables to store
    public String serverAddress;
    public boolean isUserLoggedIn = false;
    public JSONObject userData;

    private ApplicationState(Context context) {
        this.applicationContext = context.getApplicationContext();
    }

    public static ApplicationState getInstance(Context context) {
        if(instance == null) {
            instance = new ApplicationState(context);
        }
        return instance;
    }
}