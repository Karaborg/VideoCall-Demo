package com.example.sdk.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sdk.Module.CallModule;
import com.example.sdk.R;
import com.genband.mobile.NotificationStates;
import com.genband.mobile.OnCompletionListener;
import com.genband.mobile.RegistrationApplicationListener;
import com.genband.mobile.RegistrationService;
import com.genband.mobile.RegistrationStates;
import com.genband.mobile.ServiceProvider;
import com.genband.mobile.api.services.call.CallServiceInterface;
import com.genband.mobile.api.utilities.Configuration;
import com.genband.mobile.api.utilities.Constants;
import com.genband.mobile.api.utilities.MobileError;
import com.genband.mobile.api.utilities.exception.MobileException;

public class LoginActivity extends AppCompatActivity {

    EditText user_name, user_password, user_ip;
    String username, userpassword, userip;

    Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Log in to App:");
        setContentView(R.layout.activity_login);

        user_name = (EditText) findViewById(R.id.txtUsername);
        user_password = (EditText) findViewById(R.id.txtPassword);
        user_ip = (EditText) findViewById(R.id.txtIp);

        activity = this;

    }

    public void login(View view) {
        username = user_name.getText().toString().trim();
        userpassword = user_password.getText().toString().trim();
        userip = user_ip.getText().toString().trim();

        user_name.setText("");

        if (isValid(username) == true) {
            configExample(username, userpassword, userip);
        } else {
            Toast.makeText(activity, "Unvalid Input/s", Toast.LENGTH_SHORT).show();
        }
    }

    public void register() {
        System.out.println("---------------REGISTER START---------------");
        Intent i = new Intent(this, ContactActivity.class);
        RegistrationApplicationListener registrationListener = new RegistrationApplicationListener() {
            @Override
            public void registrationStateChanged(RegistrationStates state) {
                // Handle registration state changes
            }

            @Override
            public void notificationStateChanged(NotificationStates state) {
                // Handle notification state changes
            }

            @Override
            public void onInternalError(MobileError mobileError) {
                // Handle internal errors
            }
        };
        ServiceProvider serviceProvider = ServiceProvider.getInstance(getApplicationContext());
        final RegistrationService registrationService = serviceProvider.getRegistrationService();
        //Get registration notifications
        registrationService.setRegistrationApplicationListener(registrationListener);
        //Service types used in registration
        Constants.SubscribeServices[] subscribeServices = {Constants.SubscribeServices.Call};
        registrationService.registerToServer(subscribeServices, 3600, new OnCompletionListener() {
            @Override
            public void onSuccess() {
                ServiceProvider serviceProvider = ServiceProvider.getInstance(getApplicationContext());
                CallServiceInterface callService = serviceProvider.getCallService();
                try {
                    callService.setCallApplication(CallModule.getInstance());
                    System.out.println("---------------REGISTER SUCCESS---------------");
                    startActivity(i);
                    // Login input u sıfırlasana burda
                } catch (MobileException exception) {
                }
                //Handle registration success
                //Developer can get expiration time, which is gathered from registration response
                int expirationTime = registrationService.getExpirationTime();
            }

            @Override
            public void onFail(MobileError error) {
                //Handle registration error
                System.out.println("---------------REGISTER FAIL---------------");
                Toast.makeText(activity, "Register Fail", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void configExample(String name, String password, String ip) {
        System.out.println("---------------CONFIG START---------------");
        Configuration configuration = Configuration.getInstance();
        configuration.setUsername(name);
        configuration.setPassword(password);
        configuration.setRestServerIp(ip);
        configuration.setRestServerPort(8580);
        configuration.setRequestHttpProtocol(true);

        configuration.setWebSocketServerIp(ip);
        configuration.setWebSocketServerPort(8581);
        configuration.setSecuredWSProtocol(false);
        System.out.println("---------------CONFIG END---------------");

        register();
    }

    static boolean isValid(String email) {
        String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        return email.matches(regex);
    }
}
