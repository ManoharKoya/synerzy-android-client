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
    EditText portEditText;
    TextView messageView;
    Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        saveButton  = findViewById(R.id.saveButton);
        messageView = findViewById(R.id.messageView);
        ipEditText = findViewById(R.id.ipEditText);
        portEditText = findViewById(R.id.portEditText);

        saveButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (!isExternalStorageAvailable() || isExternalStorageReadOnly()) {
//                    saveButton.setEnabled(false);
                }
                else {
                    myExternalFile = new File(getExternalFilesDir("f"),"sample.mp3");
                }
                try {
                    FileOutputStream fos = new FileOutputStream(myExternalFile);
                    fos.write(fileReceiveClient.globalObj.byteArray);
                    fos.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                messageView.setText("sample.txt is saved ");
            }
        });



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
        Toast.makeText(this , "the process is started",Toast.LENGTH_LONG).show();
        messageSendClient msc = new messageSendClient(ipEditText.getText().toString(),13222,"/Users/khalilshaik/Downloads/kev.mp3",this);
        msc.execute();
    }
    public void receiveText(View v){
        Toast.makeText(this , "the process of getting started",Toast.LENGTH_LONG).show();
        messageReceiveClient mrc = new messageReceiveClient(ipEditText.getText().toString(),13223,this);
        mrc.execute();

    }
    public void recieveFile(View v){

        int portnum = 13232;
        fileReceiveClient frc = new fileReceiveClient(ipEditText.getText().toString(),portnum,"syn.mp3",this);
        frc.execute();
    }


}
