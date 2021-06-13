package com.example.ses_tanima_sr;

import java.util.Calendar;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothHeadset;
import android.bluetooth.BluetoothProfile;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends Activity {
	
	//*** Global tan�mlamalar b�l�m� ***//
	private boolean sr_basla_dur=true; 		// Ses tan�man�n ba�lat�l�p ba�lat�lmad���n� tutar.
	private TextView sonuc_text;			// Ses tan�ma sonu�lar�n� g�steren TextView
	private ImageButton konus_btn;			// Ses tan�mlama i�lemini ba�lata buton
	private ProgressBar pbar;				// Ses tan�mlama an�nda sonu�lar�n geldi�ini 
											// g�steren yuvarlak d�nen daire.
	private static final String TAG = "Sdev"; // LogCat i�in etiket String de�i�keni 
										    
	public static final int ON_RESULT = 20;	  //Ses tan�ma sonu�lar�n�n Handler ile al�nmas�n� 
	public static final int ON_RMS = 21;	  //sa�layan Sabit integer de�i�kenler.
	public static final int ON_ERROR = 22;
	public static final int ON_PARTIAL_RESULT = 23;
	public static final String ON_RESULT_str= "Ses tan�ma Sonuclar�";
	public static final int ON_ZAMAN_ISLEM = 24;	
	
	private SpeechRecognizer sr;			// Ses tan�mlay�c�
	private Listener dinleyici = null;		// Listener s�n�f� "dinleyici" isminde bo� olu�turuluyor.
	private KomutIslemleri komut=null;
	private cBluetooth bl=null;
	private Tts tts=null;
	ZamanKomutPref zamanIslem=null;
	
	protected BluetoothHeadset mBluetoothHeadset; //Bluetooth kulakl�k i�in gerekli tan�mlamalar.
	protected BluetoothDevice mConnectedHeadset;  //Kulakl���n bal� olup olmad���n� kontrol etmek i�in	
	protected BluetoothAdapter mBluetoothAdapter;
	
	AudioManager audioManager =null;
	MediaPlayer mPlayer = null;
	
	private int prefIndex=0;
	private String bl_adres;
	private Boolean kulaklikBagli=false;
	private String islem_kodu;
	private float sesRms;
	private Handler cizimHandler;
	
	public int index=0;
	public Boolean bl_bagli=false;
	public Boolean SesliUyari=false;
	public Boolean BeepSesi=false;
	
	AlertDialog.Builder alertDialog;
	DialogIslemleri DialogMesaj=null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main); 	//Grafik aray�z belirleniyor.
		setTitle("AndSDev");
		
		konus_btn=(ImageButton)findViewById(R.id.imageButton1);
	    sonuc_text=(TextView)findViewById(R.id.textView_al2);
	    pbar=(ProgressBar)findViewById(R.id.progressBar1);
	  
	    zamanIslem=new ZamanKomutPref(this);
	    komut=new KomutIslemleri(this);
	    sr = SpeechRecognizer.createSpeechRecognizer(this);
	    dinleyici=new Listener(this,srHandler);
	    sr.setRecognitionListener(dinleyici);
	    
	    audioManager=(AudioManager) this.getSystemService(Context.AUDIO_SERVICE); 
	    mPlayer=new MediaPlayer();
	    
	   //bl_adres=(String)"20:13:06:21:11:43";//ilk Bluetooth mac adresi
	    bl_adres=(String)"20:13:06:21:13:87";
		bl=new cBluetooth(this, srHandler);   //cBluetooth class'� buradaki srHandler ile olu�turuluyor...
		bl.checkBTState();	
		tts=new Tts(this,mPlayer);
		
		alertDialog = new AlertDialog.Builder(this); 
		DialogMesaj=new DialogIslemleri(this, alertDialog);

	}//create
	//: Bluetooth kulakl�k telefona ba�l� m� de�il mi kontrol eder. E�er ba�l� ise 
	//:  kulakl���n hoparl�r ve mikrofonunun kullan�lmas�n� sa�lar. De�ilse telefonunkiler kullan�l�r.
	private void kulaklikKontrol(){
		mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
   	 	mBluetoothAdapter.getProfileProxy(this,mHeadsetProfileListener,BluetoothProfile.HEADSET);
   	 	if (mBluetoothAdapter != null){ //Bluetooth ba�lant�s� var  		 
	   	       if(kulaklikBagli){		//ve kulakl� ba�l� ise
	   	    	    audioManager.setMode(AudioManager.MODE_IN_COMMUNICATION);
		   	        audioManager.startBluetoothSco();
		   	        audioManager.setBluetoothScoOn(true); 
		   	        audioManager.setStreamSolo(AudioManager.STREAM_VOICE_CALL, false);//beep sesi �al
		   	        mPlayer.setAudioStreamType(AudioManager.MODE_IN_COMMUNICATION);
		   	        Toast.makeText(getApplicationContext(),"Kulaklik aktif",Toast.LENGTH_SHORT).show();
		   	        
	   	       }else{
	   	    	   audioManager.setMode(AudioManager.MODE_NORMAL);
	   	    	   audioManager.stopBluetoothSco();
	   	    	   audioManager.setBluetoothScoOn(false); 
	   	    	   mPlayer.setAudioStreamType(AudioManager.MODE_NORMAL);
	   	    	   if(BeepSesi)
	   	    		   audioManager.setStreamSolo(AudioManager.STREAM_VOICE_CALL, true);//beep sesi �alma
	   	    	   else
	   	    		   audioManager.setStreamSolo(AudioManager.STREAM_VOICE_CALL, false);//beep sesi �al
	   	       }   	       
   	 	}
   	 kulaklikBagli=false;
	}//kulakl�k kontrol
	
	//:Ses tan�ma i�leminin ba�lat�lmas�n�,kulaklik kontrol� ve ses tan�ma i�lemi ile meydana 
	// gelmesi gereken i�lemlerin yap�lmas�n� sa�lar.
    public void sr_baslat(){ 		
    	 sr.cancel();
         sr_basla_dur=true;
    	 pbar.setVisibility(pbar.VISIBLE);
    	 
    	 Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH); 
    	 //intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.ACTION_VOICE_SEARCH_HANDS_FREE);   	 
         intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
         intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, this.getPackageName());
		 intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS,10); 
		 
         try {
        	 sr.startListening(intent);  //Ses tan�ma i�in dinleme i�lemi ba�lat�l�r.
        	 //audioManager.setStreamMute(AudioManager.STREAM_SYSTEM,true);//beep sesini keser
        	 //audioManager.setStreamMute(AudioManager.VIBRATE_TYPE_NOTIFICATION, true);
        	 //audioManager.setStreamSolo(AudioManager.STREAM_VOICE_CALL, true);//beep sesini keser
        	 audioManager.setStreamSolo(AudioManager.STREAM_VOICE_CALL, false);//beep sesi �al
        	 kulaklikKontrol(); 
        	 
         } catch (Exception e) { } 
         
     }
    //:ImageButton'a bas�l�nca �al���r.
	 public void onClick(View v) {
		 konus_btn.setImageResource(R.drawable.sr);
		 cizimHandler = new Handler();
		 cizimHandler.post(cizimUpdate);
		 sonuc_text.setText("::->");
		 sr_baslat();		 
		// startService(new Intent(this, ZamanServisi.class));
	 }
	 //: servis durdurma i�lemi yap�l�r.
	 public void ZmnServisDurdur(){
		 stopService(new Intent(this, ZamanServisi.class));
	 }
	 //: ses tan�ma sonu�lar� ve bluetooth ile alakal� handler mesajlar� yakalan�r.
	 private final Handler srHandler=new Handler(){ 
		 @Override
	     public void handleMessage(Message msg) { 
			 String sonuc = new String();
		 switch (msg.what) {
		 case ON_ERROR:	// Ses tan�ma i�lminde meydana gelen hatalarla ilgili mesajlar�n
			 			// al�nd��� b�l�m.
			 Log.d("SR","HATA");
			 if(msg.obj.toString().contains("ERROR_SERVER")){
				 Toast.makeText(getApplicationContext(),msg.obj.toString()+
						 "\n A� Ba�lants� yokk.",Toast.LENGTH_SHORT).show();
				 sr.cancel();
				 konus_btn.setImageResource(R.drawable.sdev_resim);
				 sonuc_text.setText("\n Hata: A� ba�lant�s� YOK !!! Kontrol ediniz...");
				 pbar.setVisibility(pbar.INVISIBLE);
			 }
			 else{
				 Toast.makeText(getApplicationContext(),msg.obj.toString()+
						 "\nSes tan�ma tekrar ba�lat�ld�.",Toast.LENGTH_SHORT).show();
				 if(msg.obj.toString().contains("ERROR_NETWORK"))//A� hatas� yar� sesi 
				 {
					 audioManager.setStreamSolo(AudioManager.STREAM_VOICE_CALL, false);//sesi a�ar
					 tts.SesliMsg("503");
				 }
					 
				 pbar.setVisibility(pbar.INVISIBLE);
				 if(sr_basla_dur==true){ //Her yeni sonu� veya hatadan sonra ses tan�ma tekrar ba�lat�l�r.
						sr.cancel();
						sr_baslat();
						SPref_islem();
				 }				 
			 }
			 
		 break;
			case ON_RESULT://ses tan�ma sonu�lar�n�n al�nd��� b�l�m
				sonuc=msg.obj.toString();
				sonuc_text.setText("");
				sonuc_text.setText(sonuc);
				islem_kodu=komut.islem(sonuc);
				if(islem_kodu=="dur"){ //ses tan�may� durdurma i�lemi.
					sr.cancel();pbar.setVisibility(pbar.INVISIBLE);
				    sr_basla_dur=false;
				    konus_btn.setImageResource(R.drawable.sdev_resim);sonuc_text.setText("");
				    audioManager.setStreamSolo(AudioManager.STREAM_VOICE_CALL, false);//beep sesi �al
				    tts.SesliMsg("501");
				    Toast.makeText(getApplicationContext(), "Ses tan�ma i�lemi durduruldu...",Toast.LENGTH_SHORT).show();
				    ZmnServisDurdur();
				}else
					if(islem_kodu=="ba�lan"){ //Bluetooth mod�le ba�lanma i�lemi.
						if(!bl_bagli)bl.BT_Connect(bl_adres);
							 Toast.makeText(getApplicationContext(), 
									"Bluetooth zaten ba�l�...",Toast.LENGTH_SHORT).show();						
					}else
						if(islem_kodu=="hakk�nda"){
							DialogMesaj.ProgHakkinda();
						}
					else
						//: Zamanl� i�lemler.
						if(islem_kodu.contains("zaman")){ 
							String z_kod="";
							if(!islem_kodu.contains("hata")){//zaman kodunda hata yoksa i�lemi kaydet
									z_kod=islem_kodu.replace("zaman,","");
									try {
										prefIndex=Integer.parseInt(zamanIslem.PrefOku(zamanIslem.KEY_PREF_INDEX));
									} catch (NumberFormatException e) {
										
									}
									
									zamanIslem.PrefKaydet(zamanIslem.KEY_PREF_ONEK+String.valueOf(prefIndex++),z_kod);
									zamanIslem.PrefKaydet(zamanIslem.KEY_PREF_INDEX,String.valueOf(prefIndex));									
							}
							//Toast.makeText(getApplicationContext(), 
							//		"Komut="+komut.islem(sonuc)+"\n"+"prefIndex="+String.valueOf(prefIndex)+"\n"
							//				,Toast.LENGTH_SHORT).show();
							sonuc_text.setText(sonuc_text.getText()+"\n"+islem_kodu+
									"\n"+"prefIndex="+String.valueOf(prefIndex)+"\n"+
									zamanIslem.PrefOku("z"+String.valueOf(prefIndex-1)));
							
						}
						else	
							if( islem_kodu.length()>3){ //ikili i�lev i�lemleri.
								IkiliIslev islev=new IkiliIslev();
								String[]k=komut.islem(sonuc).split(",");
								//Log.d("Main Aactivitiy","K0:"+k[0]);
								komutGonder(k[0]);
								islev.bekle(1000);
								komutGonder(k[1]);
							}
							else {
								komutGonder(islem_kodu); //Ev i�in kontrol komudu g�nder.
								if(!bl_bagli&&!SesliUyari&&(islem_kodu!="")){
									audioManager.setStreamSolo(AudioManager.STREAM_VOICE_CALL, false);
									tts.SesliMsg(islem_kodu);
								}
							}
				
				if(islem_kodu==""){ //e�er i�lem yoksa komut kodu olu�turalamam��t�r.
									//komut yok uyar�s� g�nder.
					if(!bl_bagli){
						audioManager.setStreamSolo(AudioManager.STREAM_VOICE_CALL, false);//sesi a�ar
						tts.SesliMsg("502");
					}
					/*try {
						audioManager.setStreamSolo(AudioManager.STREAM_VOICE_CALL, false);
						cizimHandler.removeCallbacks(cizimUpdate);
		           	    Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
		           	    Ringtone r = RingtoneManager.getRingtone(getApplicationContext(),notification);
		           	    //r.setStreamType(audioManager.MODE_IN_COMMUNICATION);
		           	    r.play();while(r.isPlaying());
		           	 } catch (Exception e) {}*/
						
					cizimHandler.post(cizimUpdate);
				}
				
				if(sr_basla_dur==true){ //Her yeni sonu� veya hatadan sonra ses tan�ma tekrar ba�lat�l�r.
					//Log.d(TAG,"SR Baslatma islemi");
					sr.cancel();sr_baslat();
					SPref_islem();
				}
				if(islem_kodu!="")
					Toast.makeText(getApplicationContext(), "Komut="+islem_kodu,Toast.LENGTH_SHORT).show();
				else
					Toast.makeText(getApplicationContext(), "Komut: !!! Ev Taraf�ndan Desteklenmiyor !!!",Toast.LENGTH_SHORT).show();
				break;
			case ON_PARTIAL_RESULT:
				Toast.makeText(getApplicationContext(),msg.obj.toString(),Toast.LENGTH_SHORT).show();
			break;
			case ON_RMS: // ses sinyalinin de�erinin al�nd��� k�s�m.
				sesRms=Float.parseFloat(msg.obj.toString());
			
				break;
			    ////
			case ON_ZAMAN_ISLEM:
				Toast.makeText(getApplicationContext(),"Zaman �slem:"+msg.obj.toString(),
						Toast.LENGTH_SHORT).show();
				break;
				//////
			case cBluetooth.BL_NOT_AVAILABLE:
	               	Log.d(cBluetooth.TAG, "Bluetooth mevcut de�il. Bitir");
	            	Toast.makeText(getBaseContext(), "Bluetooth mevcut de�il", Toast.LENGTH_SHORT).show();
	                finish();
	                break;	
			case cBluetooth.BL_INCORRECT_ADDRESS:
            	Log.d(cBluetooth.TAG, "MAC adres yanl��");
            	Toast.makeText(getBaseContext(), "Bluetooth adresi yanl��", Toast.LENGTH_SHORT).show();
                break;
			 case cBluetooth.BL_REQUEST_ENABLE:   
	            	Log.d(cBluetooth.TAG, "Bluetooth a��lmas� gerekli");
	            	BluetoothAdapter.getDefaultAdapter();
	            	Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
	                startActivityForResult(enableBtIntent, 1);
	                break;
			 case cBluetooth.BL_SOCKET_FAILED:
	            	Toast.makeText(getBaseContext(), "Socket ba�ar�s�z! �leti�im hatas�.", Toast.LENGTH_SHORT).show();
	                finish();
	                break;
			 case cBluetooth.STATE_CONNECTED:
	            	Toast.makeText(getBaseContext(), "Bluetooth mod�le ba�lant� sa�land�", Toast.LENGTH_SHORT).show();
	                bl_bagli=true;
	            	break;
			 case cBluetooth.RECIEVE_MESSAGE: //Bluetooth mod�lden gelen verilerin al�nd��� k�s�m.										
              	   	String msj_oku= msg.obj.toString();
		           	Log.d("SR",msj_oku); 
		           	msj_oku.replace("cavap","");
		           	sonuc_text.setText(sonuc_text.getText().toString()+"\n"+msj_oku+"\n");
		           	
		           	if(!msj_oku.contains("OK")){// donan�m taraf�ndan komut al�nmam��sa komut
		           								// yok sesli uyar�s�n� olu�tur.
		           		/*try {
		           			audioManager.setStreamSolo(AudioManager.STREAM_VOICE_CALL, false);
		           			cizimHandler.removeCallbacks(cizimUpdate);
		           	        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
		           	        Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
		           	        r.setStreamType(audioManager.MODE_IN_COMMUNICATION);
		           	        r.play();
		           	    } catch (Exception e) {}*/
		           		if(bl_bagli){
			           		audioManager.setStreamSolo(AudioManager.STREAM_VOICE_CALL, false);//sesi a�ar
			           		tts.SesliMsg("502");
		           		}
						cizimHandler.post(cizimUpdate);
		           	}else
		           		if(msj_oku.contains("OK"))//e�er donan�m taraf�ndan komut al�nm��sa
		           								  // al�nan komutun sonucu ile alakal� sesli uyar� verilir.
		           		{
		           			audioManager.setStreamSolo(AudioManager.STREAM_VOICE_CALL, false);
		           			tts.SesliMsg(islem_kodu);
		           		}
		           		
         	break;
			}
		 }
	 };//srHandler sonu
	 
	 //: Mikrofonun arka k�sm�nda sesin �iddetini g�steren dairelerin 
	 // belli bir s�rede �izilmesini sa�layan k�s�m.
	 private Runnable cizimUpdate = new Runnable() {
		   public void run() {
		       daireCiz(sesRms);
		       cizimHandler.postDelayed(this,13);
		       //cizimHandler.removeCallbacks(mUpdate);
		       }
	};
	//: Ses �iddetine g�re Dairelerin �izdirildi�i k�s�m
	public  void daireCiz(float r){
		konus_btn.setBackgroundDrawable(null);
	    Bitmap bmp = Bitmap.createBitmap(500, 500,Bitmap.Config.ARGB_8888);
	    Canvas c = new Canvas(bmp);
	    Paint p = new Paint();
	    p.setAntiAlias(true);
		p.setColor(Color.LTGRAY);
		p.setStyle(Paint.Style.FILL); 
		p.setStrokeWidth(3f);
	    c.drawCircle(230, 215, r, p);
	    konus_btn.setBackgroundDrawable(new BitmapDrawable(bmp));
	    sesRms=0;
	}
	//: Bluetooth donan�ma komut kodu g�nderildi�i k�s�m.
	public void komutGonder(String kmt_kodu){
		  if(kmt_kodu!=null){
			  try {
				  kmt_kodu.trim();
				  bl.sendData("*"+kmt_kodu+"#\r");
			} catch (NumberFormatException e) {
				// TODO: handle exception
			}
			  
		  }
	 }
	 @Override
	    protected void onResume() {
	    	super.onResume();
	    	//bl.BT_Connect(bl_adres);
	    }
	 @Override
	    protected void onPause() {
	    	super.onPause();
	    	bl.BT_onPause();
	    }   
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode==RESULT_OK){
			//sonuc_text.setText("intent tAMAAM");	
		}
	}
	//: Preference i�lemleri
    private void  SPref_islem(){
		String[] kod=null;
		int saniye=0,dakika=0,saat=0,yer=-1;
		Calendar takvim = Calendar.getInstance();
		saniye=takvim.get(Calendar.SECOND);
		dakika=takvim.get(Calendar.MINUTE);
		saat=takvim.get(Calendar.HOUR_OF_DAY);
		String islem_zamani=String.valueOf(saat)+":"+String.valueOf(dakika);
		try {
			prefIndex=Integer.parseInt(zamanIslem.PrefOku(zamanIslem.KEY_PREF_INDEX));
		} catch (NumberFormatException e) {
			
		}
		
		for (int index = 0; index < prefIndex; index++) {
			try {
				if(zamanIslem.KEY_PREF_ONEK+String.valueOf(index)!=null)
					kod=zamanIslem.PrefOku(zamanIslem.KEY_PREF_ONEK+String.valueOf(index)).split(",");
			} catch (Exception e) {
					if(index <= prefIndex)index++;
			}
			try {
				if((kod[0]!=null)&&(kod[1]!=null)){
					prefIndex=Integer.parseInt(zamanIslem.PrefOku(zamanIslem.KEY_PREF_INDEX));
					if(kod[0].contains(islem_zamani)){
						if(bl_bagli)komutGonder(kod[1]);						
						zamanIslem.PrefSil(zamanIslem.KEY_PREF_ONEK+String.valueOf(index));
						prefIndex=Integer.parseInt(zamanIslem.PrefOku(zamanIslem.KEY_PREF_INDEX));
						for (int i = 0; i < prefIndex; i++) {//Kay�tlar yeniden d�zenleniyor...
							yer++;
							if(zamanIslem.PrefOku(zamanIslem.KEY_PREF_ONEK+String.valueOf(i))!=null){
								String s=zamanIslem.PrefOku(zamanIslem.KEY_PREF_ONEK+String.valueOf(i));
								zamanIslem.PrefKaydet(zamanIslem.KEY_PREF_ONEK+String.valueOf(yer),s);
							}else yer--;
								
						}	
						zamanIslem.PrefKaydet(zamanIslem.KEY_PREF_INDEX,String.valueOf(prefIndex-1));
						Toast.makeText(this, kod[0]+"-->"+kod[1]+" !!! Silindi...",Toast.LENGTH_LONG).show();
						
						tts.SesliMsg(kod[1]);
						break;
					}
				}
			} catch (Exception e) {
					
			}
			
		}//for
	}
    private void prefKayitgoster(){
    	String kayitlar="";
    	prefIndex=Integer.parseInt(zamanIslem.PrefOku(zamanIslem.KEY_PREF_INDEX));
		for (int index = 0; index < prefIndex; index++) {
			try {
				if(zamanIslem.KEY_PREF_ONEK+String.valueOf(index)!=null)
					kayitlar+=String.valueOf(index)+"-"+
							zamanIslem.PrefOku(zamanIslem.KEY_PREF_ONEK+String.valueOf(index))+"\n";
			} catch (Exception e) {
					
			}
			
		}//for
    	AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);    	 
    	LinearLayout linearLayout = new LinearLayout(this);
	    final EditText et_giris = new EditText(this);
	    et_giris.setText("Silinecek Kay�t NO:");
        et_giris.setInputType(InputType.TYPE_CLASS_NUMBER);
        linearLayout.addView(et_giris);
        et_giris.setOnClickListener(new OnClickListener() {		
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				et_giris.setText("");
			}
		});
        alertDialog.setView(linearLayout);
        alertDialog.setTitle("Zamanl� ��lev Kay�tlar�");
        alertDialog.setMessage(kayitlar);
        alertDialog.setPositiveButton("Hay�r", new DialogInterface.OnClickListener() {			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Toast.makeText(getApplicationContext(), "Kayitlar Listelendi...", Toast.LENGTH_SHORT).show();
			}
		});
        alertDialog.setNegativeButton("Evet", new DialogInterface.OnClickListener() {			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				int yer=-1;
				try {
					zamanIslem.PrefSil(zamanIslem.KEY_PREF_ONEK+et_giris.getText().toString());
					prefIndex=Integer.parseInt(zamanIslem.PrefOku(zamanIslem.KEY_PREF_INDEX));
					for (int i = 0; i < prefIndex; i++) {//Kay�tlar yeniden d�zenleniyor...
						yer++;
						if(zamanIslem.PrefOku(zamanIslem.KEY_PREF_ONEK+String.valueOf(i))!=null){
							String s=zamanIslem.PrefOku(zamanIslem.KEY_PREF_ONEK+String.valueOf(i));
							zamanIslem.PrefKaydet(zamanIslem.KEY_PREF_ONEK+String.valueOf(yer),s);
						}else yer--;
							
					}
					zamanIslem.PrefKaydet(zamanIslem.KEY_PREF_INDEX,String.valueOf(prefIndex-1));
					Toast.makeText(getApplicationContext(), "Kayit Silindi...", Toast.LENGTH_SHORT).show();
				} catch (Exception e) {
					// TODO: handle exception
				}
				
			}
		});
        //alertDialog.setNeutralButton("�ptal", null);
        alertDialog.show();   	
    }//
    public void TextKomutGirisi(){
    	AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);    	 
    	LinearLayout linearLayout = new LinearLayout(this);
        final EditText et_giris = new EditText(this);
        et_giris.setText("Komut ???");
        //et_giris.setInputType(InputType.TYPE_CLASS_NUMBER);
        linearLayout.addView(et_giris);
        et_giris.setOnClickListener(new OnClickListener() {		
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				et_giris.setText("");
			}
		});
        alertDialog.setView(linearLayout);
        alertDialog.setTitle("Text Komut Giri�i");
        //alertDialog.setMessage(kayitlar);
        alertDialog.setPositiveButton("Tamam", new DialogInterface.OnClickListener() {			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				islem_kodu=komut.islem(et_giris.getText().toString());
				if(bl_bagli)komutGonder(islem_kodu); //Ev i�in kontrol komudu g�nder.
				if(!bl_bagli&&!SesliUyari&&(islem_kodu!=""))tts.SesliMsg(islem_kodu);
				Toast.makeText(getApplicationContext(), "Kod= "+islem_kodu, Toast.LENGTH_SHORT).show();
			}
		});
        alertDialog.setNegativeButton("�ptal",null);
        alertDialog.show();
    }
  //: Menu i�lemleri
  	@Override
  	public boolean onCreateOptionsMenu(Menu menu) {
  		// Inflate the menu; this adds items to the action bar if it is present.
  		getMenuInflater().inflate(R.menu.main, menu);
  		return true;
  	}
  	@Override
  	public boolean onPrepareOptionsMenu(Menu menu){
  		boolean result = super.onPrepareOptionsMenu(menu);
  		if(SesliUyari)
  			menu.findItem(R.id.SesliUyari).setChecked(false);
  			else
  				menu.findItem(R.id.SesliUyari).setChecked(true);
  		if(BeepSesi)
  			menu.findItem(R.id.beepSesi).setChecked(false);
  			else
  				menu.findItem(R.id.beepSesi).setChecked(true);
  		return result;
  	}
      @Override
    public boolean onOptionsItemSelected(MenuItem item) {
       switch (item.getItemId()) {
  	    //case R.id.cikis:
  	    	// finish();
  	    // break;
       	case R.id.bl_deneme:
       		//sonuc_text.setText("Adresi verilen Blutooth cihaz aran�yor");
       		Intent bl_dnm=new Intent(this,BluetoothDeneme.class);
       		startActivity(bl_dnm);
       		break;
       	case R.id.bl_connect:
       		if(!bl_bagli)bl.BT_Connect(bl_adres);
       			else Toast.makeText(getApplicationContext(), 
  						"Bluetooth zaten ba�l�...",Toast.LENGTH_SHORT).show();
       		break;
       	case R.id.k_sil:
       		zamanIslem.PrefHepsiniSil();
       		index=0;
       		Toast.makeText(this,"Zamanl� i�lev kayitlar� silindi...",Toast.LENGTH_SHORT).show();
       		break;
       	case R.id.k_listele:
       		prefKayitgoster();
       		break;
       	case R.id.SesliUyari:
       		if(SesliUyari)SesliUyari=false;
       		else SesliUyari=true;
       		break;
       	case R.id.beepSesi:
       		if(BeepSesi)BeepSesi=false;
       		else BeepSesi=true;
       		break;       		
       	case R.id.textKomut:
       		sr.cancel();
       		konus_btn.setImageResource(R.drawable.sdev_resim);
			pbar.setVisibility(pbar.INVISIBLE);
       		TextKomutGirisi();
       		break;
       	case R.id.hakkinda:
       		DialogMesaj.ProgHakkinda();
       	
       	break;
       }
       return super.onOptionsItemSelected(item);
      }
    //: Kulakl�k ba�lant� durumunun kontrol edilmesini sa�lar.
    private BluetoothProfile.ServiceListener mHeadsetProfileListener = new BluetoothProfile.ServiceListener()
    {
        @Override
        public void onServiceDisconnected(int profile)
        {
        	if (profile == BluetoothProfile.HEADSET) {
                mBluetoothHeadset.stopVoiceRecognition(mConnectedHeadset);
                Log.d("Head set","disconnect");
                mBluetoothHeadset = null; 
                kulaklikBagli=false;
            }
        }

        @Override
        public void onServiceConnected(int profile, BluetoothProfile proxy)
        {
            mBluetoothHeadset = (BluetoothHeadset) proxy;
            List<BluetoothDevice> devices = mBluetoothHeadset.getConnectedDevices();
            if (devices.size() > 0)
            {
                mConnectedHeadset = devices.get(0);
                mBluetoothHeadset.startVoiceRecognition(mConnectedHeadset);
                Log.d("Headset",devices.get(0).getName().toString());
                kulaklikBagli=true;
            }
        }
    };

}
