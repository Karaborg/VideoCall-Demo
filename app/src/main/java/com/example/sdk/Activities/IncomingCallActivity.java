package com.example.sdk.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.sdk.Module.CallModule;
import com.example.sdk.R;
import com.genband.mobile.api.services.call.IncomingCallInterface;

public class IncomingCallActivity extends AppCompatActivity {

    TextView caller;

    static Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {
        }
        setContentView(R.layout.activity_incoming_call);

        activity = this;

        caller = (TextView) findViewById(R.id.txtCallerName);
        caller.setText(CallModule.getInstance().call.getCallerAddress());
    }

    public static Activity getActivity(){
        return activity;
    }

    public void acceptCall(View view) {
        Intent i = new Intent(this, CallScreenActivity.class);
        i.putExtra("COMING", "coming");
        startActivity(i);
        finish();
    }

    public void rejectCall(View view) {
        System.out.println("------------Call Rejected------------");
        IncomingCallInterface incomingCall = (IncomingCallInterface) CallModule.getInstance().call;
        incomingCall.rejectCall();
        finish();
    }

    @Override
    public void onBackPressed() {
        System.out.println("------------Call Rejected with Back Button------------");
        IncomingCallInterface incomingCall = (IncomingCallInterface) CallModule.getInstance().call;
        incomingCall.rejectCall();
        finish();
        super.onBackPressed();
    }
}
