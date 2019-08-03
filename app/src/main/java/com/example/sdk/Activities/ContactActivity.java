package com.example.sdk.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.example.sdk.Module.CallModule;
import com.example.sdk.R;
import com.genband.mobile.NotificationStates;
import com.genband.mobile.OnCompletionListener;
import com.genband.mobile.RegistrationApplicationListener;
import com.genband.mobile.RegistrationService;
import com.genband.mobile.RegistrationStates;
import com.genband.mobile.ServiceProvider;
import com.genband.mobile.api.utilities.MobileError;

import java.util.ArrayList;

public class ContactActivity extends AppCompatActivity {

    EditText txtToCall;
    String toCall;
    ListView listView;

    static ArrayList<String> listItems;
    static ArrayAdapter<String> adapter;
    static ArrayList<String> recentCalls = new ArrayList<>();

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {
        }
        setContentView(R.layout.activity_contact);

        context = this;

        txtToCall = (EditText) findViewById(R.id.txttoCall);
        listView = (ListView) findViewById(R.id.listView);

        CallModule cm = CallModule.getInstance();
        cm.setContext(getApplicationContext());

        listItems = new ArrayList<String>();
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listItems);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                callMethod(recentCalls.get(i));
                System.out.println("---------------ARRAY---------------");
                System.out.println(recentCalls);
            }
        });
    }

    public static ArrayList<String> getListItem() {
        return listItems;
    }

    public static ArrayAdapter<String> getAdapter() {
        return adapter;
    }

    public static ArrayList<String> getRecentCalls() {
        return recentCalls;
    }

    public void call(View view) {
        toCall = txtToCall.getText().toString().trim();
        callMethod(toCall);
    }

    public void callMethod(String toCall) {
        System.out.println("---------------Call Button Clicked---------------");

        ContactActivity.getRecentCalls().add(toCall);

        Intent i = new Intent(this, CallScreenActivity.class);

        if (isValid(toCall) == true) {
            i.putExtra("CALL", toCall);
            startActivity(i);
        } else {
            toCall = toCall + "@spidr.com";
            i.putExtra("CALL", toCall);
            startActivity(i);
            //Toast.makeText(this, "Not a valid mail address", Toast.LENGTH_LONG).show();
        }
    }

    static boolean isValid(String email) {
        String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        return email.matches(regex);
    }

    @Override
    public void onBackPressed() {
        logoutMethod();
        super.onBackPressed();
    }

    public void logoutMethod(){
        System.out.println("---------------UN REGISTER TO SERVER START---------------");
        RegistrationApplicationListener registrationListener = new RegistrationApplicationListener() {
            @Override
            public void registrationStateChanged(RegistrationStates registrationStates) {
                // Handle registration state changes
            }

            @Override
            public void notificationStateChanged(NotificationStates notificationStates) {
                // Handle notification state changes
            }

            @Override
            public void onInternalError(MobileError mobileError) {

            }
        };
        final RegistrationService registrationService = ServiceProvider.getInstance(getApplicationContext()).getRegistrationService();
        registrationService.unregisterFromServer(new OnCompletionListener() {
            @Override
            public void onSuccess() {
                //Handle unregistration success
                System.out.println("---------------UNREGISTER SUCCESS---------------");
                Intent i = new Intent(context, LoginActivity.class);
                context.startActivity(i);
                finish();
            }

            @Override
            public void onFail(MobileError mobileError) {
                //Handle unregistration error
                System.out.println("---------------UNREGISTER FAIL---------------");
            }
        });
    }
}
