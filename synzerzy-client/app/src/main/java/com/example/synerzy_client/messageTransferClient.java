package com.example.synerzy_client;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.TextView;


class messageSendClient extends AsyncTask<Void, Void,Void> {
    String ipAddress ;
    int portNum;
    String messageToSend = "duplicate message";
    Activity activity;
    messageSendClient(String ipAddress , int portNum,String messaage,Activity acc){
        this.ipAddress = ipAddress ;
        this.portNum = portNum;
        messageToSend = messaage;
        activity = acc;
    }
    @Override
    protected Void doInBackground(Void... voids) {
        byteClass.sendMessageClient(ipAddress,portNum,messageToSend);
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
//        messageReceiveClient mrc = new messageReceiveClient(ipAddress , portNum+1,activity);
//        mrc.execute();
    }

}

class messageReceiveClient extends AsyncTask<Void, Void,Void> {
    String ipAddress ;
    int portNum;
    String messageToReceive = "duplicate message";
    Activity activity;

    messageReceiveClient(String ipAddress , int portNum,Activity acc){
        this.ipAddress = ipAddress ;
        this.portNum = portNum;
        activity = acc;
    }
    @Override
    protected Void doInBackground(Void... voids) {
        messageToReceive = byteClass.getMessageClient(ipAddress,portNum);
        System.out.println("syn: messfor"+messageToReceive);
        return null;
    }
    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        TextView textView = activity.findViewById(R.id.messageView);
        textView.setText(messageToReceive);
    }
}

class fileReceiveClient extends AsyncTask<Void, Void,Void> {
    String ipAddress ;
    int portNum;
    String fileName = " ";
    Activity activity;
    static byteClass globalObj;
    static int actualLength;
    fileReceiveClient(String ipAddress , int portNum,String fileName,Activity acc){
        this.ipAddress = ipAddress ;
        this.portNum = portNum;
        fileName = fileName;
        activity = acc;
    }
    @Override
    protected Void doInBackground(Void... voids) {

        globalObj = byteClass.getFile(ipAddress,portNum,fileName);
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        TextView tc = activity.findViewById(R.id.messageView);
        tc.setText("the file is received");
    }

}

