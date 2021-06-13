package com.example.ses_tanima_sr;

import java.text.Format;
import java.util.ArrayList;

import android.content.Context;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.speech.RecognitionListener;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.view.ViewDebug.IntToString;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class Listener implements RecognitionListener {

	 private final Handler srHandler;
	 private static final String TAG = "Ses Tanima";
	 
	 public Listener(Context context, Handler handler){
		srHandler=handler; 
	 }
	 public void onReadyForSpeech(Bundle params)
     {
              Log.d(TAG, "onReadyForSpeech");
     }
     public void onBeginningOfSpeech()
     {
              Log.d(TAG, "onBeginningOfSpeech");
              
     }
     public void onRmsChanged(float rmsdB)
     {
    	 	double ses=10*Math.pow(10,((float)rmsdB/(float)10));//dB rms deðerini 0-100 arasýna sýkýþtýrýr...
    	 	ses=ses*2.0;
           //Log.d(TAG, "onRmsChanged= "+(int) ses);
            //Log.d(TAG, "onRmsChanged= "+(int)rmsdB);
    	 	srHandler.obtainMessage(MainActivity.ON_RMS,String.valueOf(ses).length(),
       			 -1,String.valueOf(ses)).sendToTarget(); 
              
     }
     public void onBufferReceived(byte[] buffer)
     {
              Log.d(TAG, "onBufferReceived");
     }
     public void onEndOfSpeech()
     {
              Log.d(TAG, "onEndofSpeech");
              
     }
     public void onError(int error)
     {
    	 Log.d(TAG, hata_kodu_gonder(error));
    	 srHandler.obtainMessage(MainActivity.ON_ERROR,hata_kodu_gonder(error).length(),
    			 -1,hata_kodu_gonder(error)).sendToTarget();   	 
     }
	public void onResults(Bundle results)                   
     {
     	      String str = new String();
     	      String sk=new String();
              //Log.d(TAG, "onResults " + results);
              ArrayList<String> data = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
              //float[] skor=results.getFloatArray(SpeechRecognizer.CONFIDENCE_SCORES);
              for (int i = 0; i < data.size(); i++){

                        str+="-->"+data.get(i)+"\n";
              }
              Log.d(TAG,str);
              srHandler.obtainMessage(MainActivity.ON_RESULT,str.length(),
         			 -1,str).sendToTarget();  
     }
     public void onPartialResults(Bundle partialResults)
     {   	
         Log.d(TAG, "onPartialResults");
     }
     public void onEvent(int eventType, Bundle params)
     {
              Log.d(TAG, "onEvent " + eventType);
     }
     ////
     private String hata_kodu_gonder(int error){
    	 
        String hata="";
     	switch (error) {
 		case SpeechRecognizer.ERROR_AUDIO:
 			hata="hata:ERROR_AUDIO kodu="+String.valueOf(error);
 			break;
 		case SpeechRecognizer.ERROR_CLIENT:
 			hata="hata:ERROR_CLIENT kodu="+String.valueOf(error);
 			break;
 		case SpeechRecognizer.ERROR_NETWORK:
 			hata="hata:ERROR_NETWORK kodu="+String.valueOf(error);
 			break;
 		case SpeechRecognizer.ERROR_NETWORK_TIMEOUT:
 			hata="hata:ERROR_NETWORK_TIMEOUT kodu="+String.valueOf(error);
 			break;	
 		case SpeechRecognizer.ERROR_NO_MATCH:
 			hata="hata:ERROR_NO_MATCH kodu="+String.valueOf(error);
 			break;	
 		case SpeechRecognizer.ERROR_RECOGNIZER_BUSY:
 			hata="hata:ERROR_RECOGNIZER_BUSY kodu="+String.valueOf(error);
 			break;
 		case SpeechRecognizer.ERROR_SERVER:
 			hata="hata:ERROR_SERVER kodu="+String.valueOf(error);
 			break;
 		case SpeechRecognizer.ERROR_SPEECH_TIMEOUT:
 			hata="hata:ERROR_SPEECH_TIMEOUT kodu="+String.valueOf(error);
 			break;
 		case SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS:
 			hata="hata:EERROR_INSUFFICIENT_PERMISSIONS kodu="+String.valueOf(error);
 			break;
 			
 		}
     	return hata;
     }

}
