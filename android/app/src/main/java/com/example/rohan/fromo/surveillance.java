package com.example.rohan.fromo;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.renderscript.ScriptGroup;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.UUID;

import static java.lang.Math.atan2;

public class surveillance extends Activity {

    String s1;


    // double yaw,totalpid,kp=1,ki=0,kd=0,sumyaw,lastyaw;
    // Sensor mSensor;
    // SensorManager mSensorManager;
    ImageButton forward,reverse,left,right,start,stop;
    Button tiltbackward,tiltforward;
    TextView tv;

    private static final String TAG = "bluetooth2";

    Handler h;

    final int RECIEVE_MESSAGE = 1;        // Status  for Handler
    private BluetoothAdapter btAdapter = null;
    private BluetoothSocket btSocket = null;
    private StringBuilder sb = new StringBuilder();

    private ConnectedThread mConnectedThread;

    double a[]; int num[],i=0;



    // SPP UUID service
    private static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    // MAC-address of Bluetooth module (you must edit this line)
    private static String address = "00:21:13:01:45:CA";

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_surveillance);

        // button LED ON

       /* mSensorManager=(SensorManager)getSystemService(Context.SENSOR_SERVICE);
        mSensor=mSensorManager.getDefaultSensor(Sensor.TYPE_GAME_ROTATION_VECTOR);
        mSensorManager.registerListener(this, mSensor,20000);*/
        a=new double[4];

        s1 = "";

        //led = (ToggleButton) findViewById(R.id.led);
        forward=(ImageButton)findViewById(R.id.forward);
        reverse=(ImageButton)findViewById(R.id.reverse);
        left=(ImageButton)findViewById(R.id.left);
        right=(ImageButton)findViewById(R.id.right);
        tiltbackward = (Button)findViewById(R.id.tiltbackward);
        tiltforward=(Button)findViewById(R.id.tiltforward);
        start=(ImageButton)findViewById(R.id.start);
        stop=(ImageButton)findViewById(R.id.stop);
        tv=(TextView)findViewById(R.id.tv);
        num= new int[100];


        // button LED OFF
        //txtArduino = (TextView) findViewById(R.id.textAurdino);      // for display the received data from the Arduino

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
                            //txtArduino.setText("Data from Arduino: " + sbprint);            // update TextView
                            //led.setEnabled(true);
                            forward.setEnabled(true);
                            reverse.setEnabled(true);
                            left.setEnabled(true);
                            right.setEnabled(true);
                            tiltbackward.setEnabled(true);
                            tiltforward.setEnabled(true);
                            start.setEnabled(true);
                            stop.setEnabled(true);
                        }
                        Log.d(TAG, "...String:"+ sb.toString() +  "Byte:" + msg.arg1 + "...");
                        break;
                }
            }
        };

        btAdapter = BluetoothAdapter.getDefaultAdapter();       // get Bluetooth adapter
        checkBTState();

        stop.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                s1=s1+"STOP\n";
                tv.setText(s1);

                //up.setEnabled(false);
                //mConnectedThread.write("1");
                num[i]=0; i++;
                //textView.setText(num);
               // Toast.makeText(getBaseContext(), "STOP", Toast.LENGTH_SHORT).show();
            }
        });

        tiltforward.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                s1=s1+"TILT FORWARD\n";
                tv.setText(s1);

                //up.setEnabled(false);
                //mConnectedThread.write("1");
                num[i]=5; i++;
                //textView.setText(num);
              //  Toast.makeText(getBaseContext(), "TILT FORWARD", Toast.LENGTH_SHORT).show();
            }
        });

        tiltbackward.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                s1=s1+"TILT BACKWARD\n";
                tv.setText(s1);

                //up.setEnabled(false);
                //mConnectedThread.write("1");
                num[i]=6; i++;
                //textView.setText(num);
               // Toast.makeText(getBaseContext(), "TILT BACKWARD", Toast.LENGTH_SHORT).show();
            }
        });


        forward.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {


                s1=s1+"FORWARD\n";
                tv.setText(s1);
                //up.setEnabled(false);
                //mConnectedThread.write("1");
                num[i]=1; i++;
                //textView.setText(num);
               // Toast.makeText(getBaseContext(), "MOVING FORWARD", Toast.LENGTH_SHORT).show();
            }
        });
        reverse.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {


                s1=s1+"REVERSE\n";
                tv.setText(s1);
                // down.setEnabled(false);
                //mConnectedThread.write("2");
                num[i]=2; i++;;
                //textView.setText(num);
               // Toast.makeText(getBaseContext(), "MOVING BACK", Toast.LENGTH_SHORT).show();
            }
        });

        left.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {

                s1=s1+"LEFT\n";
                tv.setText(s1);
                //   left.setEnabled(false);
                //mConnectedThread.write("3");
                num[i]=3; i++;;
                //textView.setText(num);
               // Toast.makeText(getBaseContext(), "MOVING LEFT", Toast.LENGTH_SHORT).show();
            }
        });

        right.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {

                s1=s1+"RIGHT\n";
                tv.setText(s1);
                //     up.setEnabled(false);
                //mConnectedThread.write("4");
                num[i]=4; i++;
                //textView.setText(num);
               // Toast.makeText(getBaseContext(), "MOVING RIGHT", Toast.LENGTH_SHORT).show();
            }
        });
        start.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {

                s1=s1+"START\n";
                tv.setText(s1);
                h.postDelayed(r1,2000);
               // Toast.makeText(getBaseContext(), "START", Toast.LENGTH_SHORT).show();
            }
        });
    }

    int j=0;
    Runnable r1=new Runnable() {
        @Override
        public void run() {
            //Toast.makeText(getBaseContext(), "FCB", Toast.LENGTH_SHORT).show();
               // Toast.makeText(getBaseContext(), "RMFC", Toast.LENGTH_SHORT).show();
               // Log.d("RMFC",num[j]+"");
                mConnectedThread.write(String.valueOf(num[j]));
            j=j+1;
            if((j!=11)&&((j%2)!=0)){
                h.postDelayed(this, 2000);
            }
            else if((j!=11)&&((j%2)==0)){
                h.postDelayed(this,700);
            }
        }
    };

    private BluetoothSocket createBluetoothSocket(BluetoothDevice device) throws IOException {
        if(Build.VERSION.SDK_INT >= 10){
            try {
                final Method  m = device.getClass().getMethod("createInsecureRfcommSocketToServiceRecord", UUID.class);
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
        mConnectedThread.write(0+"");
        h.removeCallbacks(r1);

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