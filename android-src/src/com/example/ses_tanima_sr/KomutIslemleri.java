package com.example.ses_tanima_sr;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.widget.Toast;

import com.example.ses_tanima_sr.*;

public class KomutIslemleri {
	
	private Context islemContext;
	
	SalonIslemleri salon=new SalonIslemleri();
	MutfakIslemleri mutfak=new MutfakIslemleri();
	TvMuzikSetiIslemleri TvMuzik=new TvMuzikSetiIslemleri();
	SrStrIslem islem=new SrStrIslem();
	IkiliIslev lambalar=new IkiliIslev();
	AnahtarListe anahtar=new AnahtarListe();
	
	//Tutucu fonksiyon (Constructor)
	KomutIslemleri(Context context){//(Context context,String sesli_komut){
		islemContext = context;
	}
	//Ses tan�ma sonuclar� i�ersinden var olan say� degerini bulan fonksiyon.

	public String islem(String sonuc){
		String komut="";
		//Telefona verilecek komutlarla ilgili i�lemler.
		if(sonuc.contains("neksus")){
			   sonuc.replace("neksus","");
			   if(islem.listeVarMI(sonuc, anahtar.getir(anahtar.BLBAGLAN)))komut="ba�lan";
			   if(islem.listeVarMI(sonuc,anahtar.getir(anahtar.SRDUR)))komut="dur";
			   if(sonuc.contains("hakk�nda")||sonuc.contains("kimiz")||sonuc.contains("prog"))
				   komut="hakk�nda";
		}
		//Lambalar�n hepsini s�nd�rme i�lemi
		if(islem.listeVarMI(sonuc,anahtar.getir(anahtar.LAMBALAR))){
			if(islem.listeVarMI(sonuc,anahtar.getir(anahtar.SONDUR)))
			    komut="000";
			if(islem.Uygula(sonuc,islem.ZAMAN_SONDUR)){
				if(sonuc.contains("saniye")){
					komut="zaman,"+islem.zamanAyarla(Integer.parseInt(islem.StrSayiBul(sonuc)),0)+",000";
					return komut;
				}
				if(sonuc.contains("dakika")||sonuc.contains("dakka")){
					komut="zaman,"+islem.zamanAyarla(Integer.parseInt(islem.StrSayiBul(sonuc)),1)+",000";
					return komut;
				}
				if(sonuc.contains("saat")){
					komut="zaman"+islem.zamanAyarla(Integer.parseInt(islem.StrSayiBul(sonuc)),2)+",000";
						return komut;
				}
			}//zaman sondur
		}
		//Salon ile ilgili i�lemler
		if((lambalar.ikiliIslev(sonuc)==null)&&	islem.listeVarMI(sonuc, anahtar.getir(anahtar.SALON)))	
		{		
			//sonuc=sonuc.replace("salon","");
			komut=salon.SalonIslem(sonuc);
		}
		//�kili i�lev i�lemleri
		if(lambalar.ikiliIslev(sonuc)!=null){
			komut=lambalar.ikiliIslev(sonuc);
		}
			
		if((lambalar.ikiliIslev(sonuc)==null)&& sonuc.contains("oturma oda")
				&&islem.listeVarMI(sonuc, anahtar.getir(anahtar.LAMBA))
				&&islem.listeVarMI(sonuc, anahtar.getir(anahtar.YAK))){
			 komut=("005");
		}
		if((lambalar.ikiliIslev(sonuc)==null)&& sonuc.contains("oturma oda")
				&&islem.listeVarMI(sonuc, anahtar.getir(anahtar.LAMBA))
				&&islem.listeVarMI(sonuc, anahtar.getir(anahtar.SONDUR))){
			 komut=("006");
		}
		if((lambalar.ikiliIslev(sonuc)==null)&& sonuc.contains("�ocuk oda")
				&&islem.listeVarMI(sonuc, anahtar.getir(anahtar.LAMBA))
				&&islem.listeVarMI(sonuc, anahtar.getir(anahtar.YAK))){
			 komut=("007");
		}
		if((lambalar.ikiliIslev(sonuc)==null)&& sonuc.contains("�ocuk oda")
				&&islem.listeVarMI(sonuc, anahtar.getir(anahtar.LAMBA))
				&&islem.listeVarMI(sonuc, anahtar.getir(anahtar.SONDUR))){
			 komut=("008");
		}
		//Mutfak i�lemleri
		if((lambalar.ikiliIslev(sonuc)==null)&& 
			(islem.listeVarMI(sonuc, anahtar.getir(anahtar.MUTFAK))||
					sonuc.contains("kombi")||sonuc.contains("�ama��r")||
					sonuc.contains("ocak")||sonuc.contains("oca�")||sonuc.contains("f�r�n")
					)){
			   komut=mutfak.MufakIslem(sonuc);
		}
	    //
		if((lambalar.ikiliIslev(sonuc)==null)&& sonuc.contains("yatak oda")
				&&islem.listeVarMI(sonuc, anahtar.getir(anahtar.LAMBA))
				&&islem.listeVarMI(sonuc, anahtar.getir(anahtar.YAK))){
			 komut=("017");
		}
		if((lambalar.ikiliIslev(sonuc)==null)&& sonuc.contains("yatak oda")
				&&islem.listeVarMI(sonuc, anahtar.getir(anahtar.LAMBA))
				&&islem.listeVarMI(sonuc, anahtar.getir(anahtar.SONDUR))){
			 komut=("018");
		}
		if((lambalar.ikiliIslev(sonuc)==null)&& (sonuc.contains("antre")||sonuc.contains("hol")||
				sonuc.contains("ara")||sonuc.contains("ar�")||sonuc.contains("antri")
				||sonuc.contains("andre")||sonuc.contains("anti")||sonuc.contains("a�r�"))
				&&islem.listeVarMI(sonuc, anahtar.getir(anahtar.LAMBA))
				&&islem.listeVarMI(sonuc, anahtar.getir(anahtar.YAK))){
			 komut=("023");
		}
		if((lambalar.ikiliIslev(sonuc)==null)&& (sonuc.contains("antre")||sonuc.contains("hol")||
				sonuc.contains("ara")||sonuc.contains("ar�")||sonuc.contains("antri")
				||sonuc.contains("andre")||sonuc.contains("anti")||sonuc.contains("a�r�"))
				&&islem.listeVarMI(sonuc, anahtar.getir(anahtar.LAMBA))
				&&islem.listeVarMI(sonuc, anahtar.getir(anahtar.SONDUR))){
			 komut=("024");
		}
		if(sonuc.contains("perde")||sonuc.contains("pencere")){
			if(islem.listeVarMI(sonuc,anahtar.getir(anahtar.YAK)))
				komut=("090");
			if(islem.listeVarMI(sonuc,anahtar.getir(anahtar.SONDUR)))
				komut=("091");
		}
		//TV M�zik seti i�lemleri
		if((lambalar.ikiliIslev(sonuc)==null)&&islem.Uygula(sonuc, islem.TV_MUZIK_SET))
		{
			komut=TvMuzik.TvMzSetIslem(sonuc);
		}
		
			
		return komut;
	} //String islem
	
}
