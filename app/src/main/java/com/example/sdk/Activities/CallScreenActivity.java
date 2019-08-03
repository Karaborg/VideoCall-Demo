package com.example.sdk.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.sdk.Module.CallModule;
import com.example.sdk.R;
import com.genband.mobile.ServiceProvider;
import com.genband.mobile.api.services.call.CallServiceInterface;
import com.genband.mobile.api.services.call.IncomingCallInterface;
import com.genband.mobile.api.services.call.OutgoingCallCreateInterface;
import com.genband.mobile.api.services.call.OutgoingCallInterface;
import com.genband.mobile.api.utilities.MobileError;
import com.genband.mobile.api.utilities.exception.MobileException;
import com.genband.mobile.core.webrtc.view.VideoView;

public class CallScreenActivity extends AppCompatActivity {

    ImageButton btnMute;
    ImageButton btnHold;
    ImageButton btnStop;

    Context context;
    VideoView lvv, rvv;

    int i = 0, k = 0, j = 0, l = 0;

    static Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {
        }
        setContentView(R.layout.activity_call_screen);

        activity = this;

        lvv = (VideoView) findViewById(R.id.localVideoView);
        rvv = (VideoView) findViewById(R.id.remoteVideoView);

        btnMute = (ImageButton) findViewById(R.id.ibtnMute);
        btnHold = (ImageButton) findViewById(R.id.ibtnHold);
        btnStop = (ImageButton) findViewById(R.id.ibtnStop);


        Intent intent = getIntent();
        String toCall = intent.getStringExtra("CALL");
        String coming = intent.getStringExtra("COMING");

        if (toCall != null) {
            callActivity(toCall);
        }
        if (coming != null) {
            acceptCall();
        }
    }

    public static Activity getInstance() {
        return activity;
    }

    public void callActivity(String toCall) {
        System.out.println("---------------Call Activity Started---------------");

        CallModule callModule = CallModule.getInstance();

        ServiceProvider serviceProvider = ServiceProvider.getInstance(context);
        CallServiceInterface callService = serviceProvider.getCallService();
        //prepare outgoing call parameters
        String terminatorAdress = toCall;
        //initialize related video UI views for local and remote video display
        VideoView localVideoView = lvv;
        VideoView remoteVideoView = rvv;

        callService.createOutgoingCall(terminatorAdress, callModule, new OutgoingCallCreateInterface() {
            @Override
            public void callCreated(OutgoingCallInterface outgoingCallInterface) {
                outgoingCallInterface.setLocalVideoView(localVideoView);
                outgoingCallInterface.setRemoteVideoView(remoteVideoView);
                //To create an audio and video call:
                outgoingCallInterface.establishCall(true);
                //OR To create audio only call with two m lines which can be answered with video
                //directly, use:
                //outgoingCallInterface.establishCall(false);
                //OR To create an audio only call with only one m line, use:
                //outgoingCallInterface.establishAudioCall();

                callModule.call = outgoingCallInterface;
                System.out.println("---------------CALL CREATED---------------");
                ContactActivity.getListItem().add("Outgoing Call: " + toCall.trim());
                ContactActivity.getAdapter().notifyDataSetChanged();
            }

            @Override
            public void callCreationFailed(MobileError mobileError) {
                Toast.makeText(context, "Invalid Input", Toast.LENGTH_SHORT).show();
                System.out.println("---------------CALL FAILED---------------");
            }
        });
    }

    public void acceptCall() {
        System.out.println("------------Call Accepted------------");

        IncomingCallInterface incomingCall = (IncomingCallInterface) CallModule.getInstance().call;
        //  ACCEPT CALL
        //initialize related video UI views for local and remote video display
        VideoView localVideoView = lvv;
        VideoView remoteVideoView = rvv;
        //assign UI elements
        incomingCall.setLocalVideoView(localVideoView);
        incomingCall.setRemoteVideoView(remoteVideoView);

        //If you want to check if the call has a video m line:
        if (incomingCall.canReceiveVideo()) {
            //If call has video m line and you want to answer with video:
            incomingCall.acceptCall(true);
            //OR if you want to answer with audio only:
            //incomingCall.acceptCall(false);
        } else {
            //If call has only one m line, the call will be answered with audio only
            incomingCall.acceptCall(false);
        }
    }

    public void endCall(View view) {
        System.out.println("---------------Call Ended---------------");
        if (CallModule.getInstance().call != null) {
            try {
                CallModule.getInstance().call.endCall();
                finish();
            } catch (MobileException e) {
                e.printStackTrace();
            }
        }
    }

    public void muteCall(View view) {
        if (i == 0) {
            System.out.println("---------------Mute Call---------------");
            CallModule.getInstance().call.mute();
            btnMute.setImageResource(R.drawable.ic_unmute);
            btnHold.setEnabled(false);
            btnStop.setEnabled(false);
            i++;
        } else if (i != 0) {
            System.out.println("---------------Un Mute Call---------------");
            CallModule.getInstance().call.unMute();
            btnMute.setImageResource(R.drawable.ic_mute);
            btnHold.setEnabled(true);
            btnStop.setEnabled(true);
            i = 0;
        }
    }

    public void holdCall(View view) {
        if (k == 0) {
            System.out.println("---------------Hold Call---------------");
            CallModule.getInstance().call.holdCall();
            btnHold.setImageResource(R.drawable.ic_unhold);
            btnMute.setEnabled(false);
            btnStop.setEnabled(false);
            k++;
        } else if (k != 0) {
            System.out.println("---------------Un Hold Call---------------");
            CallModule.getInstance().call.unHoldCall();
            btnHold.setImageResource(R.drawable.ic_hold);
            btnMute.setEnabled(true);
            btnStop.setEnabled(true);
            k = 0;
        }
    }

    public void stopVideo(View view) {
        if (j == 0) {
            System.out.println("---------------Stop Video---------------");
            CallModule.getInstance().call.videoStop();
            btnStop.setImageResource(R.drawable.ic_start);
            btnMute.setEnabled(false);
            btnHold.setEnabled(false);
            j++;
        } else if (j != 0) {
            System.out.println("---------------Continue Video---------------");
            CallModule.getInstance().call.videoStart();
            btnStop.setImageResource(R.drawable.ic_stop);
            btnMute.setEnabled(true);
            btnHold.setEnabled(true);
            j = 0;
        }
    }

    public void changeCamera(View view){
        if (l == 0) {
            System.out.println("---------------Change---------------");
            lvv = (VideoView) findViewById(R.id.remoteVideoView);
            rvv = (VideoView) findViewById(R.id.localVideoView);
            l++;
        } else if (l != 0) {
            System.out.println("---------------Change---------------");
            lvv = (VideoView) findViewById(R.id.localVideoView);
            rvv = (VideoView) findViewById(R.id.remoteVideoView);
            l = 0;
        }
    }

    @Override
    public void onBackPressed() {
        System.out.println("---------------Call Ended---------------");
        if (CallModule.getInstance().call != null) {
            try {
                CallModule.getInstance().call.endCall();
            } catch (MobileException e) {
                e.printStackTrace();
            }
        }
        finish();
        super.onBackPressed();
    }
}
