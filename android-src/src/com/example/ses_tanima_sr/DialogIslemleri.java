package com.example.ses_tanima_sr;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class DialogIslemleri {
	 Context bu;
	 AlertDialog.Builder alertDialog;
	 public DialogIslemleri(Context context,AlertDialog.Builder dialog){
			bu=context;
			alertDialog=dialog;
	 }
	 //
	 
	 //
	 public void ProgHakkinda(){
		 LinearLayout linearLayout = new LinearLayout(bu);
		// final EditText et_giris = new EditText(bu);
	     //et_giris.setText("Silinecek Kayýt NO:");
		 //alertDialog.setView(linearLayout);
		 alertDialog.setTitle("Program Hakkýnda");
		 alertDialog.setMessage("TÜBÝTAK 45. ORTAÖÐRETÝM ÖÐRENCÝLERÝ ARAÞTIRMA PROJELERÝ YARIÞMASI\n\n" +
								" Proje Adý : Android Söz Dinleyen Ev \n" +
		 						"          	       (AndSDeV)\n\n" +
		 						" Okulu		: Eskiþehir Türk Telekom Teknik Endüstri Meslek Lisesi\n\n" +
		 						"Öðrenciler :\n" +
		 						"		Hacer DEMÝR		Halil KÜPRAY\n\n" +
		 						"Danýþman :\n" +
		 						"		Ýlyas YAÐCIOÐLU\n" +
		 						"		Elektronik Öðretmeni\n\n" +
		 						"*** Katký saðlayan herkese teþekkür ederiz.");
   	

	     alertDialog.setPositiveButton("Tamam", new DialogInterface.OnClickListener() {			
				@Override
				public void onClick(DialogInterface dialog, int which) {
					//Toast.makeText(getApplicationContext(), "Kayitlar Listelendi...", Toast.LENGTH_SHORT).show();
				}
			});
	     alertDialog.show();
		 
	 }
}
/*
  alertDialog.setMessage("				TÜBÝTAK 45. ORTAÖÐRETÝM\n " +
								"				 ÖÐRENCÝLERÝ ARAÞTIRMA\n " +
								"			 	   PROJELERÝ YARIÞMASI\n\n " +
				 				"					  Android Söz Dinleyen Ev \n" +
		 						"          					  (AndSDeV)\n\n" +
		 						"			  Eskiþehir Türk Telekom Teknik\n" +
		 						"				     Endüstri Meslek Lisesi\n\n" +
		 						"Öðrenciler :\n" +
		 						"		Hacer DEMÝR		Halil KÜPRAY\n\n" +
		 						"Danýþman :\n" +
		 						"		Ýlyas YAÐCIOÐLU\n" +
		 						"		Elektronik Öðretmeni\n\n" +
		 						"*** Katký saðlayan herkese teþekkür ederiz.");
		 						*/
