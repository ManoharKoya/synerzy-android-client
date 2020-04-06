package com.example.synerzy_client;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;


class clientParams extends AsyncTask<Void, Integer, Void>{

    String ipAddress;
    int portNum;
    String fileId;
    Activity activity;
    TextView messageView;
    static String responseToReceive;
    static byteClass byteObjReceived;
    clientParams( Activity ac,  String ip , int port ,String fid){
        this.activity = ac;
        this.ipAddress = ip;
        this.portNum = port;
        this.fileId = fid;
        responseToReceive = "ffgg";
        messageView = activity.findViewById(R.id.messageView);
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        messageView.setText("running " + values[0]+ "%");
    }

    @Override
    protected Void doInBackground(Void... voids) {
        byteClass.sendMessageClient(ipAddress,portNum,fileId);
        publishProgress(10);
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            System.out.println("syn: eroor while sleeping ");
        }
        publishProgress(20);
        responseToReceive = byteClass.getMessageClient(ipAddress,portNum+1);
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            System.out.println("syn: eroor while sleeping ");
        }
        publishProgress(40);
        byteObjReceived = byteClass.getFile(ipAddress,Integer.parseInt(responseToReceive),fileId);
        publishProgress(80);
        saveFileFromByte();
        publishProgress(100);
        return null;
    }

    public void saveFileFromByte(){
       File myExternalFile = new File(activity.getExternalFilesDir("f"),fileId);
        try {
            FileOutputStream fos = new FileOutputStream(myExternalFile);
            fos.write(byteObjReceived.byteArray,0,byteObjReceived.actualLength);
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        messageView.setText("song is saved");
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        Toast.makeText(activity,"single button worked",Toast.LENGTH_SHORT);
//        messageView.setText("process is completed and file is saved " + byteObjReceived.actualLength);
    }
}


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

