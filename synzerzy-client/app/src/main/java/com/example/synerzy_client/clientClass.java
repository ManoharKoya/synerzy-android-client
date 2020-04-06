package com.example.synerzy_client;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Environment;
import android.widget.TextView;

import java.io.*;
import java.net.*;
import java.util.Arrays;




class byteClass{
    byte[] byteArray;
    int actualLength;
    byteClass(byte ByteArray[],int ActualLength){
        byteArray = ByteArray;
        actualLength = ActualLength;
    }
    public static void sendMessageClient(String ipAddress ,int socketPort,String message){
        byteClass messageByte = byteClass.getByteFromString(message);
        try{
            Socket sock = new Socket(ipAddress , socketPort);
            byteClass.sendByte(sock, messageByte);
            System.out.println("the message is sent");
            sock.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public static String getMessageClient( String ipAddress , int socket_port){
        try{
            byteClass receivedByte = byteClass.getByteClass(ipAddress,socket_port);
            return getStringFrombyteClass(receivedByte);
        }catch(Exception e){
            e.printStackTrace();
        }
        return "error";
    }
    public static byteClass getByteFromString(String str){
        byte[] byteString = str.getBytes();
        return new byteClass(byteString,byteString.length);
    }
    public static String getStringFrombyteClass(byteClass stringByte){
        byte[] actualByte = Arrays.copyOfRange(stringByte.byteArray, 0, stringByte.actualLength);
        String str = new String(actualByte);
        return str;
    }

    public static byteClass joinByteClass(byteClass byte1,byteClass byte2){
        byte[] joinedArray = new byte[byte1.actualLength + byte2.actualLength];
        int last = 0;
        for(int i=0;i<byte1.actualLength;i++){
            joinedArray[last++] = byte1.byteArray[i];
        }
        for(int i=0;i<byte2.actualLength;i++){
            joinedArray[last++] = byte2.byteArray[i];
        }
        byteClass joinedByte = new byteClass(joinedArray,byte1.actualLength+byte2.actualLength);
        return joinedByte;
    }
    public static byteClass getByteClass(String ipAddress ,int portnum){
        byte[] receivedByteArray = new byte[1];
        int bytesRead;
        int current = 0;
        try{
            FileOutputStream fos = null;
            BufferedOutputStream bos = null;
            System.out.println("Connecting...");
            int FILE_SIZE = 6033596;
            receivedByteArray = new byte [FILE_SIZE];
            Socket socket = new Socket(ipAddress,portnum);
            InputStream is = socket.getInputStream();
            bytesRead = is.read(receivedByteArray,0,receivedByteArray.length);
            current = bytesRead;
            do {
                bytesRead =
                        is.read(receivedByteArray, current, (receivedByteArray.length-current));
                if(bytesRead >= 0) current += bytesRead;
            } while(bytesRead > -1);
            byteClass receivedByte = new byteClass(receivedByteArray,current);
            socket.close();
            return receivedByte;
        }
        catch(Exception e){
            e.printStackTrace();
        }
        byteClass receivedByte = new byteClass(receivedByteArray,current);
        return receivedByte;
    }
    public static void sendByte(Socket socket , byteClass byteToSend){
        OutputStream os = null;
        try{
            os = socket.getOutputStream();
            // System.out.println("Sending " + FILE_TO_SEND + "(" + mybytearray.length + " bytes)");
            os.write(byteToSend.byteArray,0,byteToSend.byteArray.length);
            os.flush();
            System.out.println("Done.");
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public static void createFile(byteClass fileByte,String fileName){
        String filename = "SampleFile.txt";
        String filepath = "MyFileStorage";
//           File myExternalFile = new File(getExternalFilesDir(filepath), filename);

    }
    public static byteClass getFile(String ipAddress , int portnum,String fileName){
        byteClass byteReceived  =new byteClass(new byte[10] , 10);
        try{
            byteReceived = getByteClass(ipAddress, portnum);
            System.out.println("syn: file received");
        }catch(Exception e){
            e.printStackTrace();
        }
        return byteReceived;
    }

//    public static boolean isExternalStorageReadOnly(){
//        String extStorageState = Environment.getExternalStorageState();
//        if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(extStorageState)) {
//            return true;
//        }
//        return false;
//    }

//    public static boolean isExternalStorageAvailable() {
//        String extStorageState = Environment.getExternalStorageState();
//        if (Environment.MEDIA_MOUNTED.equals(extStorageState)) {
//            return true;
//        }
//        return false;
//    }

}
