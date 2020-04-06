package com.example.synerzy_client;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;

public class MainActivity extends AppCompatActivity {

    File myExternalFile;
    EditText ipEditText;
    TextView messageView;
    String fileName = "songReceived.mp3";
    EditText fileNameEditText;
    Button sendInfoButton , receiveResponseButton , receiveFileButton , saveFileButton , singleButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        saveFileButton  = findViewById(R.id.saveButton);
//        receiveFileButton = findViewById(R.id.receiveFileButton);
//        sendInfoButton = findViewById(R.id.sendInfoButton);
//        receiveResponseButton = findViewById(R.id.receiveResponseButton);
        singleButton = findViewById(R.id.singleButton);

        messageView = findViewById(R.id.messageView);
        ipEditText = findViewById(R.id.ipEditText);
        fileNameEditText = findViewById(R.id.fileNameEditText);


//        sendInfoButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                sendText(v);
//            }
//        });
//         receiveResponseButton.setOnClickListener(new View.OnClickListener() {
//             @Override
//             public void onClick(View v) {
//                 receiveText(v);
//             }
//         });
//         receiveFileButton.setOnClickListener(new View.OnClickListener() {
//             @Override
//             public void onClick(View v) {
//                 recieveFile(v);
//             }
//         });
//         saveFileButton.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v) {
//                saveFile(v);
//            }
//        });
         singleButton.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                clientOp();
             }
         });

    }
    public void clientOp(){
        clientParams cp = new clientParams(this,ipEditText.getText().toString(),9996 ,fileNameEditText.getText().toString());
        cp.execute();
    }


    public void saveFile(View v){
            myExternalFile = new File(getExternalFilesDir("f"),fileNameEditText.getText().toString());
        try {
            FileOutputStream fos = new FileOutputStream(myExternalFile);
            fos.write(fileReceiveClient.globalObj.byteArray,0,fileReceiveClient.globalObj.actualLength);
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        messageView.setText("song is saved");
    }


    private static boolean isExternalStorageReadOnly() {
        String extStorageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(extStorageState)) {
            return true;
        }
        return false;
    }

    private static boolean isExternalStorageAvailable() {
        String extStorageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(extStorageState)) {
            return true;
        }
        return false;
    }
    public void sendText(View v){
        fileName = fileNameEditText.getText().toString();
        Toast.makeText(this , "the file info is sent",Toast.LENGTH_LONG).show();
        messageView.setText("file info is sent.. waiting for response");
        messageSendClient msc = new messageSendClient(ipEditText.getText().toString(),9996,fileName,this);
        msc.execute();
    }
    public void receiveText(View v){
        Toast.makeText(this , "waiting for response",Toast.LENGTH_LONG).show();
        messageView.setText("waiting for response from server");
        messageReceiveClient mrc = new messageReceiveClient(ipEditText.getText().toString(),9997,this);
        mrc.execute();
    }
    public void recieveFile(View v){
        int portnum = 9999;
        messageView.setText("waiting to receive file");
        fileReceiveClient frc = new fileReceiveClient(ipEditText.getText().toString(),portnum,fileName,this);
        frc.execute();
    }
}
