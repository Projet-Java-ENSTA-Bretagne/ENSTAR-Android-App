package io.github.nightlyside.enstar;

import android.app.ProgressDialog;
import android.graphics.LinearGradient;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import java.net.ConnectException;

import io.github.nightlyside.enstar.network.PingServer;
import io.github.nightlyside.enstar.network.RequestsHelper;

public class LoginActivity extends AppCompatActivity {

    private Button pingTestBtn;
    private Button signInBtn;

    private boolean canSignIn = false;

    private TextInputEditText serverPortInput;
    private TextInputEditText usernameInput;
    private TextInputEditText passwordInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        pingTestBtn = findViewById(R.id.pingTestBtn);
        signInBtn = findViewById(R.id.signInBtn);
        serverPortInput = findViewById(R.id.serverPortInput);
        usernameInput = findViewById(R.id.userNameInput);
        passwordInput = findViewById(R.id.passwordInput);

        pingTestBtn.setOnClickListener(this::pingServer);
        signInBtn.setOnClickListener(this::signIn);

        // Getting back data from sharedpreferences
        usernameInput.setText((String) SharedPreferencesHelper.get(this, getString(R.string.pref_login_username), ""));
        usernameInput.setText((String) SharedPreferencesHelper.get(this, getString(R.string.pref_server_address), "10.0.4.2:6666"));
    }

    public void pingServer(View v) {
        Snackbar.make(v, "Connecting to : " + serverPortInput.getText().toString(), Snackbar.LENGTH_SHORT).show();

        SharedPreferencesHelper.put(this, getString(R.string.pref_server_address), serverPortInput.getText().toString());

        Runnable runnable = () -> {
            try {
                PingServer.ping(serverPortInput.getText().toString());
            } catch (Exception e) {
                Log.e(LoginActivity.class.getSimpleName(), e.toString());
                Snackbar.make(v, "Cannot reach the server...", Snackbar.LENGTH_LONG).show();
                return;
            }

            runOnUiThread(() -> signInBtn.setEnabled(true));
            Snackbar.make(v, "Server reached!", Snackbar.LENGTH_LONG).show();
        };
        Thread thread = new Thread(runnable);
        thread.start();
    }

    public void signIn(View v) {
        ProgressDialog dialog = ProgressDialog.show(this, "Signing into the server",
                "Loading. Please wait...", true);

        SharedPreferencesHelper.put(this, getString(R.string.pref_login_username), usernameInput.getText().toString());
        SharedPreferencesHelper.put(this, getString(R.string.pref_server_address), serverPortInput.getText().toString());

        Runnable runnable = () -> {
            String userID;
            try {
                userID = RequestsHelper.forServer(serverPortInput.getText().toString()).
                        tryLogin(usernameInput.getText().toString(), passwordInput.getText().toString());
            } catch (Exception e) {
                Log.e(LoginActivity.class.getSimpleName(), e.toString());
                return;
            } finally {
                dialog.dismiss();
            }

            if (userID != null) {
                Snackbar.make(v, "Correct credentials!", Snackbar.LENGTH_LONG).show();
                // setup login vars
                ApplicationState.getInstance(this).isUserLoggedIn = true;
                ApplicationState.getInstance(this).userData = RequestsHelper.forServer(serverPortInput.getText().toString()).getUserFromID(userID);
                ApplicationState.getInstance(this).serverAddress = serverPortInput.getText().toString();
                // redirect
                this.finish();
            } else {
                Snackbar.make(v, "Wrong username/password combinaison...", Snackbar.LENGTH_LONG).show();
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
    }
}
