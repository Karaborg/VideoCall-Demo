package com.example.sdk.Module;

import android.content.Context;
import android.content.Intent;

import com.example.sdk.Activities.CallScreenActivity;
import com.example.sdk.Activities.ContactActivity;
import com.example.sdk.Activities.IncomingCallActivity;
import com.genband.mobile.api.services.call.CallApplicationListener;
import com.genband.mobile.api.services.call.CallInterface;
import com.genband.mobile.api.services.call.IncomingCallInterface;
import com.genband.mobile.api.services.call.OutgoingCallInterface;
import com.genband.mobile.api.utilities.MobileError;
import com.genband.mobile.impl.services.call.CallState;
import com.genband.mobile.impl.services.call.MediaAttributes;

import java.util.Map;

public class CallModule implements CallApplicationListener {

    private static CallModule callInterface = null;
    private Context context;
    public CallInterface call;

    private CallModule() {
        super();
    }

    public static CallModule getInstance() {
        if (callInterface == null)
            callInterface = new CallModule();
        return callInterface;
    }

    @Override
    public void incomingCall(IncomingCallInterface incomingCallInterface) {
        System.out.println("--------------------INCOMING CALL--------------------");
        if (call != null) {
            incomingCallInterface.rejectCall();
        }
        call = incomingCallInterface;

        ContactActivity.getRecentCalls().add(call.getCallerAddress());
        ContactActivity.getListItem().add("Incoming Call: " + call.getCallerAddress().trim());
        ContactActivity.getAdapter().notifyDataSetChanged();

        context = getContext();
        Intent i = new Intent(context, IncomingCallActivity.class);
        context.startActivity(i);
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public void callStatusChanged(CallInterface callInterface, CallState callState) {
        System.out.println("---------------Call State Changed---------------");
        System.out.println("---------------" + callState.getType() + "---------------");
        if (callState.getType() == CallState.Type.ENDED) {
            try {
                if (CallScreenActivity.getInstance() != null)
                    CallScreenActivity.getInstance().finish();
                if (IncomingCallActivity.getActivity() != null)
                    IncomingCallActivity.getActivity().finish();
            } catch (Exception e) {
                e.printStackTrace();
            }call = null;
        }
    }

    @Override
    public void mediaAttributesChanged(CallInterface callInterface, MediaAttributes mediaAttributes) {

    }

    @Override
    public void callAdditionalInfoChanged(CallInterface callInterface, Map<String, String> map) {

    }

    @Override
    public void errorReceived(CallInterface callInterface, MobileError mobileError) {

    }

    @Override
    public void errorReceived(MobileError mobileError) {

    }

    @Override
    public void establishCallSucceeded(OutgoingCallInterface outgoingCallInterface) {

    }

    @Override
    public void establishCallFailed(OutgoingCallInterface outgoingCallInterface, MobileError mobileError) {

    }

    @Override
    public void acceptCallSucceed(IncomingCallInterface incomingCallInterface) {

    }

    @Override
    public void acceptCallFailed(IncomingCallInterface incomingCallInterface, MobileError mobileError) {

    }

    @Override
    public void rejectCallSuccedded(IncomingCallInterface incomingCallInterface) {
        System.out.println("---------------OHA---------------");
    }

    @Override
    public void rejectCallFailed(IncomingCallInterface incomingCallInterface, MobileError mobileError) {
        System.out.println("---------------YUH---------------");
    }

    @Override
    public void ignoreSucceed(IncomingCallInterface incomingCallInterface) {

    }

    @Override
    public void ignoreFailed(IncomingCallInterface incomingCallInterface, MobileError mobileError) {

    }

    @Override
    public void videoStopSucceed(CallInterface callInterface) {

    }

    @Override
    public void videoStopFailed(CallInterface callInterface, MobileError mobileError) {

    }

    @Override
    public void videoStartSucceed(CallInterface callInterface) {

    }

    @Override
    public void videoStartFailed(CallInterface callInterface, MobileError mobileError) {

    }

    @Override
    public void muteCallSucceed(CallInterface callInterface) {

    }

    @Override
    public void muteCallFailed(CallInterface callInterface, MobileError mobileError) {

    }

    @Override
    public void unMuteCallSucceed(CallInterface callInterface) {

    }

    @Override
    public void unMuteCallFailed(CallInterface callInterface, MobileError mobileError) {

    }

    @Override
    public void holdCallSucceed(CallInterface callInterface) {

    }

    @Override
    public void holdCallFailed(CallInterface callInterface, MobileError mobileError) {

    }

    @Override
    public void transferCallSucceed(CallInterface callInterface) {

    }

    @Override
    public void transferCallFailed(CallInterface callInterface, MobileError mobileError) {

    }

    @Override
    public void unHoldCallSucceed(CallInterface callInterface) {

    }

    @Override
    public void unHoldCallFailed(CallInterface callInterface, MobileError mobileError) {

    }

    @Override
    public void sendCustomParametersSuccess(CallInterface callInterface) {

    }

    @Override
    public void sendCustomParametersFail(CallInterface callInterface, MobileError mobileError) {

    }

    @Override
    public void joinSucceeded(CallInterface callInterface) {

    }

    @Override
    public void joinFailed(CallInterface callInterface, MobileError mobileError) {

    }

    @Override
    public void endCallSucceeded(CallInterface callInterface) {
        System.out.println("---------------Call Ended Successfully---------------");
        call = null;
        CallScreenActivity.getInstance().finish();
    }

    @Override
    public void endCallFailed(CallInterface callInterface, MobileError mobileError) {
        System.out.println("---------------Call Could Not End Successfully---------------");
        call = null;
        CallScreenActivity.getInstance().finish();
    }

    @Override
    public void ringingFeedbackSucceeded(IncomingCallInterface incomingCallInterface) {

    }

    @Override
    public void ringingFeedbackFailed(IncomingCallInterface incomingCallInterface, MobileError mobileError) {

    }

    @Override
    public void notifyCallProgressChange(CallInterface callInterface) {

    }
}
