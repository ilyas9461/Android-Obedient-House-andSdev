package com.example.ses_tanima_sr;
//package android.media;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
//import android.media.SubtitleController;
//import android.media.SubtitleTrack.RenderingWidget;
//import android.media.WebVttRenderer;
public class Tts {
	private Context bu;
	private MediaPlayer mPlayer=new MediaPlayer();
	
	Tts(Context context, MediaPlayer mPl){
		bu=context;
		mPlayer=mPl;
	}
	public void SesliMsg(String tts_kod){
		mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
		 int kod=-1;
		 	if(tts_kod!=null){
			 	try {
			 		kod=Integer.parseInt(tts_kod.trim());
				} catch (NumberFormatException e) {
					// TODO: handle exception
				}
		 	}else return;
				 
		 		 
		 switch (kod) {
			case 500://Ses tanýma basla
				mPlayer = MediaPlayer.create(bu,R.raw.ses_basla_f);
				mPlayer.start();
				break;
			case 501://Ses tanýma dur
				mPlayer = MediaPlayer.create(bu,R.raw.ses_durdur_f);
				mPlayer.start();
				break;
			case 502://Hatalý komut mesajý
				mPlayer = MediaPlayer.create(bu,R.raw.chord);
				mPlayer.start();
				break;
			case 503://network hatasoý
				mPlayer = MediaPlayer.create(bu,R.raw.utopiaas);
				mPlayer.start();
				break;
			case 0://
				mPlayer = MediaPlayer.create(bu,R.raw.lambalar_off);
				mPlayer.start();		
				break;
			case 1://
				mPlayer = MediaPlayer.create(bu,R.raw.salon);
				mPlayer.start();		
				break;
			case 2://
				mPlayer = MediaPlayer.create(bu,R.raw.salon_off);
				mPlayer.start();		
				break;
			case 5://
				mPlayer = MediaPlayer.create(bu,R.raw.oturma);
				mPlayer.start();		
				break;
			case 6://
				mPlayer = MediaPlayer.create(bu,R.raw.oturma_off);
				mPlayer.start();		
				break;
			case 7://
				mPlayer = MediaPlayer.create(bu,R.raw.cocuk);
				mPlayer.start();		
				break;	
			case 8://
				mPlayer = MediaPlayer.create(bu,R.raw.cocuk_off);
				mPlayer.start();		
				break;
			case 9://
				mPlayer = MediaPlayer.create(bu,R.raw.mutfak);
				mPlayer.start();		
				break;	
			case 16://
				mPlayer = MediaPlayer.create(bu,R.raw.mutfak_off);
				mPlayer.start();		
				break;
			case 68://
				mPlayer = MediaPlayer.create(bu,R.raw.kombi);
				mPlayer.start();		
				break;
			case 69://
				mPlayer = MediaPlayer.create(bu,R.raw.kombi_off);
				mPlayer.start();		
				break;
			case 60://
				mPlayer = MediaPlayer.create(bu,R.raw.kombi_sic);
				mPlayer.start();		
				break;
			case 61://
				mPlayer = MediaPlayer.create(bu,R.raw.kombi_sic_az);
				mPlayer.start();		
				break;
			case 62://
				mPlayer = MediaPlayer.create(bu,R.raw.cam_mak);
				mPlayer.start();		
				break;
			case 63://
				mPlayer = MediaPlayer.create(bu,R.raw.cam_mak_off);
				mPlayer.start();		
				break;
			case 66://
				mPlayer = MediaPlayer.create(bu,R.raw.firin);
				mPlayer.start();		
				break;
			case 67://
				mPlayer = MediaPlayer.create(bu,R.raw.firin_off);
				mPlayer.start();		
				break;
			default:
				mPlayer = MediaPlayer.create(bu,R.raw.komut_ok_f);
				mPlayer.start();
				break;
		}
    	while(mPlayer.isPlaying());	
	}

}
