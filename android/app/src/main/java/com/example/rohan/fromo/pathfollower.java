package com.example.rohan.fromo;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.hardware.Camera;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.DrawableRes;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.UUID;
import java.util.logging.Logger;


public class pathfollower extends Activity implements View.OnClickListener {
    private Camera mCamera = null;
    private CameraView mCameraView = null;
    Handler chandler;




    private static final String TAG = "bluetooth2";

    TextView txtArduino;
    Handler h;

    final int RECIEVE_MESSAGE = 1;        // Status  for Handler
    private BluetoothAdapter btAdapter = null;
    private BluetoothSocket btSocket = null;
    private StringBuilder sb = new StringBuilder();

    private ConnectedThread mConnectedThread;

    // SPP UUID service
    private static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    // MAC-address of Bluetooth module (you must edit this line)
    private static String address = "00:21:13:01:45:CA";

    ImageView img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pathfollower);

        img=(ImageView)findViewById(R.id.img);
        //chandler=new Handler();
        //chandler.postDelayed(runnable, 500);

        //Logger l = Logger.getInstance(this); //create the logger instance

        //Logger.Log("Starting Timótheo...");
        try {
            //Logger.Log("\tCreating the camera...");
            mCamera = Camera.open();//you can use open(int) to use different cameras
        } catch (Exception e) {
           // Logger.LogError("\tFailed to get camera: " + e.getMessage());
        }

       // Logger.Log("\tDone!\n\tCreating the camera preview...");
        if (mCamera != null) {
            mCameraView = new CameraView(this, mCamera);//create a SurfaceView to show camera data
            FrameLayout camera_view = (FrameLayout) findViewById(R.id.camera_view);
            camera_view.addView(mCameraView);//add the SurfaceView to the layout
        }
        //Logger.Log("\tDone!");
        //btn to close the application
        ImageButton imgClose = (ImageButton) findViewById(R.id.imgClose);
        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.exit(0);
            }
        });
       // Logger.Log("Timótheo started successfully!");

        findViewById(R.id.btnCalibrate).setOnClickListener(this);
        findViewById(R.id.btnFlash).setOnClickListener(this);
        findViewById(R.id.btnConnect).setOnClickListener(this);
        findViewById(R.id.btnStop).setOnClickListener(this);
        findViewById(R.id.btnStart).setOnClickListener(this);

        h = new Handler() {
            public void handleMessage(android.os.Message msg) {
                switch (msg.what) {
                    case RECIEVE_MESSAGE:                                                   // if receive massage
                        byte[] readBuf = (byte[]) msg.obj;
                        String strIncom = new String(readBuf, 0, msg.arg1);                 // create string from bytes array
                        sb.append(strIncom);                                                // append string
                        int endOfLineIndex = sb.indexOf("\r\n");                            // determine the end-of-line
                        if (endOfLineIndex > 0) {                                            // if end-of-line,
                            String sbprint = sb.substring(0, endOfLineIndex);               // extract string
                            sb.delete(0, sb.length());                                      // and clear
                            Log.d(TAG,"Data from Arduino: " + sbprint);
//                            Toast.makeText(MainActivity.this,"data =string",Toast.LENGTH_SHORT).show();

                        }
                        Log.d(TAG, "...String:" + sb.toString() + "Byte:" + msg.arg1 + "...");
                        //mConnectedThread.write("0");
                        break;
                }
            }
        };

        h.postDelayed(runnable,100);

        btAdapter = BluetoothAdapter.getDefaultAdapter();       // get Bluetooth adapter
        checkBTState();



    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnCalibrate:
                mCameraView.Calibrate();
                break;
            case R.id.btnFlash:
                mCameraView.switchFlash();
                break;
            case R.id.btnConnect:
                connect();
                break;
            case R.id.btnStop:
                break;
            case R.id.btnStart:
                break;
        }
    }

    private void connect() {
        String s=CameraView.string;
        int val=CameraView.val;
        if(s==null) {
            s = "?";
        }

        else if(val==10){
            s="A";
        }

        else if(val==11){
            s="B";
        }

        else if(val==12){
            s="C";
        }

        else if(val==13){
            s="D";
        }

        else if(val==14){
            s="E";
        }

        else if(val==15){
            s="F";
        }

        else if(val==16){
            s="G";
        }

        else if(val==17){
            s="H";
        }

        else if(s=="18"){
            s="I";
        }

        else if(val==19){
            s="J";
        }

        else if(val==20){
            s="K";
        }

        else if(val==21){
            s="L";
        }

        else if(val==22){
            s="M";
        }

        else if(val==23){
            s="N";
        }

        else if(val==24){
            s="O";
        }

        else if(val==25){
            s="P";
        }

        else if(val==26){
            s="Q";
        }

        else if(val==27){
            s="R";
        }

        else if(val==28){
            s="S";
        }

        else if(val==29){
            s="T";
        }

        else if(val==30){
            s="U";
        }

        else if(val==31){
            s="V";
        }
        if(val==1) {
            img.setImageResource(R.drawable.l4new);
            mConnectedThread.write("3");
        }

        if(val==2) {
            img.setImageResource(R.drawable.r4new);
            mConnectedThread.write("4");
        }

        if(val==3||val==0) {
            img.setImageResource(R.drawable.c1new);
            mConnectedThread.write("1");
        }
     //   mConnectedThread.write(s);
        //Logger.Log("A" + s);
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {

            connect();
            h.postDelayed(this,100);
        }
    };

    private BluetoothSocket createBluetoothSocket(BluetoothDevice device) throws IOException {
        if(Build.VERSION.SDK_INT >= 10){
            try {
                final Method m = device.getClass().getMethod("createInsecureRfcommSocketToServiceRecord", UUID.class);
                return (BluetoothSocket) m.invoke(device, MY_UUID);
            } catch (Exception e) {
                Log.e(TAG, "Could not create Insecure RFComm Connection",e);
            }
        }
        return  device.createRfcommSocketToServiceRecord(MY_UUID);
    }


    @Override
    public void onResume() {
        super.onResume();

        Log.d(TAG, "...onResume - try connect...");

        // Set up a pointer to the remote node using it's address.
        BluetoothDevice device = btAdapter.getRemoteDevice(address);

        // Two things are needed to make a connection:
        //   A MAC address, which we got above.
        //   A Service ID or UUID.  In this case we are using the
        //     UUID for SPP.

        try {
            btSocket = createBluetoothSocket(device);
        } catch (IOException e) {
            errorExit("Fatal Error", "In onResume() and socket create failed: " + e.getMessage() + ".");
        }

        // Discovery is resource intensive.  Make sure it isn't going on
        // when you attempt to connect and pass your message.
        btAdapter.cancelDiscovery();

        // Establish the connection.  This will block until it connects.
        Log.d(TAG, "...Connecting...");
        try {
            btSocket.connect();
            Log.d(TAG, "....Connection ok...");
        } catch (IOException e) {
            try {
                btSocket.close();
            } catch (IOException e2) {
                errorExit("Fatal Error", "In onResume() and unable to close socket during connection failure" + e2.getMessage() + ".");
            }
        }

        // Create a data stream so we can talk to server.
        Log.d(TAG, "...Create Socket...");

        mConnectedThread = new ConnectedThread(btSocket);
        mConnectedThread.start();
    }

    @Override
    public void onPause() {
        super.onPause();

        Log.d(TAG, "...In onPause()...");

        try     {
            btSocket.close();
        } catch (IOException e2) {
            errorExit("Fatal Error", "In onPause() and failed to close socket." + e2.getMessage() + ".");
        }
    }

    private void checkBTState() {
        // Check for Bluetooth support and then check to make sure it is turned on
        // Emulator doesn't support Bluetooth and will return null
        if(btAdapter==null) {
            errorExit("Fatal Error", "Bluetooth not support");
        } else {
            if (btAdapter.isEnabled()) {
                Log.d(TAG, "...Bluetooth ON...");
            } else {
                //Prompt user to turn on Bluetooth
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, 1);
            }
        }
    }

    private void errorExit(String title, String message){
        Toast.makeText(getBaseContext(), title + " - " + message, Toast.LENGTH_LONG).show();
        finish();
    }

    private class ConnectedThread extends Thread {
        private final InputStream mmInStream;
        private final OutputStream mmOutStream;

        public ConnectedThread(BluetoothSocket socket) {
            InputStream tmpIn = null;
            OutputStream tmpOut = null;

            // Get the input and output streams, using temp objects because
            // member streams are final
            try {
                tmpIn = socket.getInputStream();
                tmpOut = socket.getOutputStream();
            } catch (IOException e) { }

            mmInStream = tmpIn;
            mmOutStream = tmpOut;
        }



        public void run() {
            byte[] buffer = new byte[256];  // buffer store for the stream
            int bytes; // bytes returned from read()
            //connect();


            // Keep listening to the InputStream until an exception occurs
            while (true) {
                try {
                    // Read from the InputStream
                    bytes = mmInStream.read(buffer);        // Get number of bytes and message in "buffer"
                    h.obtainMessage(RECIEVE_MESSAGE, bytes, -1, buffer).sendToTarget();     // Send to message queue Handler
                } catch (IOException e) {
                    break;
                }
            }
        }

        /* Call this from the main activity to send data to the remote device */
        public void write(String message) {
           // Logger.Log("B"+message);
            Log.d(TAG, "...Data to send: " + message + "...");
            byte[] msgBuffer = message.getBytes();
            try {
                mmOutStream.write(msgBuffer);
            } catch (IOException e) {
                Log.d(TAG, "...Error data send: " + e.getMessage() + "...");
            }
        }
    }
}


