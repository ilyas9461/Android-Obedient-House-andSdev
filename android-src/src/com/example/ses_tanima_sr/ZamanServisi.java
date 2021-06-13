package com.example.ses_tanima_sr;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import android.os.SystemClock;
import android.widget.Toast;

public class ZamanServisi extends Service
{
	Timer zamanlayici;
	Handler yardimci;
	ZamanKomutPref zmnIslem;
	cBluetooth bl;
	final Handler mHandler= new Handler(Looper.getMainLooper());
	
	
	final static long ZAMAN = 5000;
	int prefSon;
	int index;
	
	@Override
	public IBinder onBind(Intent intent)
	{
		return null;
	}
	
	@Override
	public void onCreate()
	{
		super.onCreate();
		
		zamanlayici = new Timer();
		yardimci = new Handler(Looper.getMainLooper());
		//mHandler=new Handler();
		zmnIslem=new ZamanKomutPref(getApplicationContext());
		bl=new cBluetooth(this,yardimci);
		index=0;
		
		zamanlayici.scheduleAtFixedRate(new TimerTask()
		{
			@Override
			public void run()
			{
				islemYap();
			}
		}, 0, ZAMAN);
	}
	private void islemYap()
	{
		long zaman = System.currentTimeMillis();
		
		SimpleDateFormat bilgi = new SimpleDateFormat("dd MMMM yyyy, EEEE / HH:mm");
		
		final String sonuc = bilgi.format(new Date(zaman));
		
		yardimci.post(new Runnable()
		{
			@Override
			public void run()
			{
				String islem="";
				prefSon=Integer.parseInt(zmnIslem.PrefOku(zmnIslem.KEY_PREF_INDEX))-1;
				if(prefSon-index>-1){
					islem=zmnIslem.PrefOku("z"+String.valueOf(prefSon-index));
					//yardimci.obtainMessage(MainActivity.ON_ZAMAN_ISLEM,islem.length(), -1, islem);
				}
				//yardimci.sendEmptyMessage(MainActivity.ON_ZAMAN_ISLEM);
				Toast.makeText(ZamanServisi.this, sonuc+"\n"+String.valueOf(index)+"-"+islem,
						Toast.LENGTH_SHORT).show();
				bl.sendData("*"+"001"+"#\r");
				//bl.sendData("*"+kmt_kodu+"#\r");
				index++;
				if(index>prefSon){
					index=0;
					bl.sendData("*"+"002"+"#\r");
				}
			}
		});
	}
	@Override
	public void onDestroy()
	{
		zamanlayici.cancel();
		super.onDestroy();
	}
}