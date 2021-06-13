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
	     //et_giris.setText("Silinecek Kay�t NO:");
		 //alertDialog.setView(linearLayout);
		 alertDialog.setTitle("Program Hakk�nda");
		 alertDialog.setMessage("T�B�TAK 45. ORTA��RET�M ��RENC�LER� ARA�TIRMA PROJELER� YARI�MASI\n\n" +
								" Proje Ad� : Android S�z Dinleyen Ev \n" +
		 						"          	       (AndSDeV)\n\n" +
		 						" Okulu		: Eski�ehir T�rk Telekom Teknik End�stri Meslek Lisesi\n\n" +
		 						"��renciler :\n" +
		 						"		Hacer DEM�R		Halil K�PRAY\n\n" +
		 						"Dan��man :\n" +
		 						"		�lyas YA�CIO�LU\n" +
		 						"		Elektronik ��retmeni\n\n" +
		 						"*** Katk� sa�layan herkese te�ekk�r ederiz.");
   	

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
  alertDialog.setMessage("				T�B�TAK 45. ORTA��RET�M\n " +
								"				 ��RENC�LER� ARA�TIRMA\n " +
								"			 	   PROJELER� YARI�MASI\n\n " +
				 				"					  Android S�z Dinleyen Ev \n" +
		 						"          					  (AndSDeV)\n\n" +
		 						"			  Eski�ehir T�rk Telekom Teknik\n" +
		 						"				     End�stri Meslek Lisesi\n\n" +
		 						"��renciler :\n" +
		 						"		Hacer DEM�R		Halil K�PRAY\n\n" +
		 						"Dan��man :\n" +
		 						"		�lyas YA�CIO�LU\n" +
		 						"		Elektronik ��retmeni\n\n" +
		 						"*** Katk� sa�layan herkese te�ekk�r ederiz.");
		 						*/
