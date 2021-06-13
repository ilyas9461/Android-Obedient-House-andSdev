package com.example.ses_tanima_sr;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.widget.Button;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.content.pm.ActivityInfo;

public class BluetoothDeneme extends Activity{
	
	private Button btn_gonder,btn_led1,btn_led2,btn_led3;
	private EditText text_gon;
	private TextView veri_al;
	private cBluetooth bl=null;
	private String bl_adres;
	private String s_okunan;
	private String str;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bl_deneme);
		

		btn_gonder=(Button)findViewById(R.id.btn_gonder);
		btn_led1=(Button)findViewById(R.id.button1);
		btn_led2=(Button)findViewById(R.id.button2);
		btn_led3=(Button)findViewById(R.id.button3);
		text_gon=(EditText)findViewById(R.id.editText_gon);
		veri_al=(TextView)findViewById(R.id.textView_al);
		veri_al.setMovementMethod(new ScrollingMovementMethod());//scroll kaydýrma için.
		
		bl_adres=(String)"20:13:06:21:11:43";//Bluetooth mac adresi
		bl=new cBluetooth(this, mHandler);//cBluetooth class'ý buradaki mHandler ile oluþturuluyor...
		bl.checkBTState();
		
		btn_led1.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				bl.sendData(String.valueOf("001\r"));	
				str="";
			}
		});
		btn_led2.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				bl.sendData(String.valueOf("15\r"));				
			}
		});
		btn_led3.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				bl.sendData(String.valueOf("132\r"));				
			}
		});
		btn_gonder.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.d("SR", "Buton veri gçnder");
				bl.sendData(text_gon.getText().toString()+"\r");
				
			}
		});
	}//onCreate(
	

	private final Handler mHandler =  new Handler() {
        public void handleMessage(android.os.Message msg) {
        	switch (msg.what) {
            case cBluetooth.BL_NOT_AVAILABLE:
               	Log.d(cBluetooth.TAG, "Bluetooth is not available. Exit");
            	Toast.makeText(getBaseContext(), "Bluetooth is not available", Toast.LENGTH_SHORT).show();
                finish();
                break;
            case cBluetooth.BL_INCORRECT_ADDRESS:
            	Log.d(cBluetooth.TAG, "Incorrect MAC address");
            	Toast.makeText(getBaseContext(), "Incorrect Bluetooth address", Toast.LENGTH_SHORT).show();
                break;
            case cBluetooth.BL_REQUEST_ENABLE:   
            	Log.d(cBluetooth.TAG, "Request Bluetooth Enable");
            	BluetoothAdapter.getDefaultAdapter();
            	Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, 1);
                break;
            case cBluetooth.BL_SOCKET_FAILED:
            	Toast.makeText(getBaseContext(), "Socket failed", Toast.LENGTH_SHORT).show();
                finish();
                break;
            case cBluetooth.RECIEVE_MESSAGE:												
                 	//byte[] readBuf = (byte[]) msg.obj;        	
            	String readMessage = msg.obj.toString();//new String(readBuf, 0, msg.arg1);
            	if(readMessage.contains("132"))
            		Toast.makeText(getBaseContext(), "3. LED iþlemi", Toast.LENGTH_SHORT).show();
            	readMessage.trim();
            	//readMessage=readMessage.replace("_","");
            	Log.d("SR",readMessage);
            	//veri_al.setText(veri_al.getText().toString().replace("\n","")+"\n");
            	veri_al.setText(veri_al.getText()+readMessage+"\n");
            	
            	break;    
            }
        };
    };
    
    @Override
    protected void onResume() {
    	super.onResume();
    	bl.BT_Connect(bl_adres);
    }

    @Override
    protected void onPause() {
    	super.onPause();
    	bl.BT_onPause();
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	//loadPref();
    }

}
