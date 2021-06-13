package com.example.ses_tanima_sr;

import java.util.Calendar;

import android.R.integer;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.Toast;

public class SrStrIslem {
	//Uygulanacak iþlemlerle ilgili sabitler
	public final int LAMBA=1; 
	public final int YAK=2; 
	public final int SONDUR=3;
	public final int YAK_SONDUR=4;
	public final int SONDUR_YAK=5;
	public final int ZAMAN_YAK=6;
	public final int ZAMAN_SONDUR=7;
	public final int ZAMAN_YAK_SONDUR=8;
	public final int TV_MUZIK_SET=9;
	
	AnahtarListe anahtar=new AnahtarListe();
	
	public boolean Uygula(String sonuc, int islem){
		String liste="";
		String listeZaman="saat,dakika,dakka,saniye";
		String listeBos="";
		switch (islem) {
			case LAMBA:
				liste=anahtar.getir(anahtar.LAMBA);
				//liste=anahtar.Salon;
				return listeVarMI(sonuc, liste);
			case YAK:
				liste=anahtar.getir(anahtar.YAK);
				return listeVarMI(sonuc, liste)&&(!listeVarMI(sonuc, listeZaman));
			case SONDUR:
				liste=anahtar.getir(anahtar.SONDUR);
				return listeVarMI(sonuc, liste)&&(!listeVarMI(sonuc, listeZaman));
			case ZAMAN_YAK:
				if((listeVarMI(sonuc,anahtar.getir(anahtar.YAK)))&&(listeVarMI(sonuc, listeZaman)))
					//if(!listeVarMI(sonuc, listeBos))
						return true;
			break;
				//return (listeVarMI(sonuc, liste)&& listeVarMI(sonuc, listeZaman))&&(!listeVarMI(sonuc, listeBos));
			case ZAMAN_SONDUR:
				if((listeVarMI(sonuc,anahtar.getir(anahtar.SONDUR)))&& (listeVarMI(sonuc, listeZaman)))
					//if(!listeVarMI(sonuc, listeBos))
						return true;
			break;
				//return (listeVarMI(sonuc, liste)&& listeVarMI(sonuc, listeZaman))&&(!listeVarMI(sonuc, listeBos));
			case ZAMAN_YAK_SONDUR:		
				liste=anahtar.getir(anahtar.YAK);
				listeBos=anahtar.getir(anahtar.SONDUR);
				if(listeVarMI(sonuc,liste)&& listeVarMI(sonuc,listeBos)){
					String[] s1=liste.split(",");
					String[] s2=listeBos.split(",");
					for (int i = 0; i < s1.length; i++) {
						for (int j = 0; j < s2.length; j++) {
							if(sonuc.indexOf(s1[i])<sonuc.indexOf(s1[j]))return true;
						}	
					}	
				}
			break;
			case TV_MUZIK_SET:
				return (listeVarMI(sonuc, anahtar.getir(anahtar.M_SET))||
						listeVarMI(sonuc, anahtar.getir(anahtar.TV))||
						listeVarMI(sonuc, anahtar.getir(anahtar.KANALLAR)));
		}		
		return false;
	}//Uygula
	//
	public boolean listeVarMI(String sonuc, String liste){
		String[] ayir=liste.split(",");
		boolean var=false;
		for (int i = 0; i < ayir.length; i++) {
			if(sonuc.contains(ayir[i].toString())){
				var=true;
				break;
			}
		}
		return var;
	}//listeVarMI
	//
	public String StrSayiBul(String s){
		String sayi ="";
		String sonuclar[]=s.split("\n");

		for (int i = 0; i < sonuclar[0].length(); i++) {			
			if(Character.isDigit(s.charAt(i)))sayi+=String.valueOf(s.charAt(i));
		}
		if(sayi.length()==1)sayi=sayi.substring(0,1);//bir basamaklý sayý
		else
			if(sayi.length()==2)sayi=sayi.substring(0,2);//iki basamaklý sayý
			else
				if(sayi.length()==3)sayi=sayi.substring(0,3);//üç basamaklý sayý
				else
					if(sonuclar[0].contains("bir"))sayi="1";
					else sayi="-1";
		return sayi;
		
	}//StrSayiBul
	
	public String zamanAyarla(int deger, int zmn){
		int saniye=0,dakika=0,saat=0;
		Calendar takvim = Calendar.getInstance();
		saniye=takvim.get(Calendar.SECOND);
		dakika=takvim.get(Calendar.MINUTE);
		saat=takvim.get(Calendar.HOUR_OF_DAY);
		if(deger==-1)return "hata";
		switch (zmn) {
		case 0://Saniye
			if((saniye+deger)>59){
				dakika++;
				if(dakika>59){saat++;dakika=dakika-60;}
				saniye=(saniye+deger)-60;
				return String.valueOf(saat)+":"+String.valueOf(dakika)+":"+String.valueOf(saniye);
			}else return String.valueOf(saat)+":"+String.valueOf(dakika)+":"+String.valueOf(saniye+deger);
		case 1://Dakika
			if((dakika+deger)>59){
				dakika=(dakika+deger)-60;saat++;
				if(saat>23)saat=saat-24;
				return String.valueOf(saat)+":"+String.valueOf(dakika)+":"+String.valueOf(saniye);
			}else return String.valueOf(saat)+":"+String.valueOf(dakika+deger)+":"+String.valueOf(saniye);
		case 2://Saat
			if((saat+deger)>23){
				saat=(saat+deger)-24;
				return String.valueOf(saat)+":"+String.valueOf(dakika)+":"+String.valueOf(saniye);
			}else return String.valueOf(saat+deger)+":"+String.valueOf(dakika)+":"+String.valueOf(saniye);
		default:
			 return "hata";
		}	
	}//zamanAyarla
}//Class
///
/*
public boolean listeVarMI(String sonuc, String liste){
	String[] ayir=liste.split(",");
	for (int i = 0; i < ayir.length; i++) {
		if(sonuc.contains(ayir[i]))return true;
	}
	return false;
}//listeVarMI
*/

