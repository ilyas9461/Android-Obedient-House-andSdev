package com.example.ses_tanima_sr;

import java.util.Calendar;

import android.R.string;
import android.os.CountDownTimer;
import android.util.Log;

public class IkiliIslev{
	//Salon lambas�n� yak mutfak lambas�n� sondur
	AnahtarListe anahtar=new AnahtarListe();
	
	public String ikiliIslev(String sonuc){
		boolean dur=false;
		String liste=anahtar.getir(anahtar.ODA);
		String komut="",liste2="";
		String[] yer=liste.split(",");
		
		for (int i = 0; i <yer.length; i++) {
			for (int j = 0; j < yer.length; j++) 
			{
				if(sonuc.contains(yer[i])&& sonuc.contains(yer[j]))
				{
					if(sonuc.indexOf(yer[i])!=sonuc.indexOf(yer[j])){											
						liste=anahtar.getir(anahtar.YAK);	
						liste2=anahtar.getir(anahtar.SONDUR);
						if(listeVarMI(sonuc,liste)&&listeVarMI(sonuc,liste2)){
							Log.d("ikili islev","i�lev="+String.valueOf(islevYeri(sonuc,liste, liste2)));
							dur=true;
							if(sonuc.indexOf(yer[i])<sonuc.indexOf(yer[j])){
								Log.d("ikili islev","i�lev="+yer[i]+yer[j]);
								//komut="yer1,yer2,islev"
								//islev 1: a�,kapat   islev 2:kapat,a�
								komut=yer[i]+","+yer[j]+","+ 
								String.valueOf(islevYeri(sonuc,liste, liste2));
								return islevYerKomut(komut);
							}else{
								Log.d("ikili islev","i�lev="+yer[j]+yer[i]);
								komut=yer[j]+","+yer[i]+","+
									   String.valueOf(islevYeri(sonuc,liste, liste2));
								return islevYerKomut(komut);
							}																						    
						}//if(listeVarMI...
						dur=true;
						break;
					}//if(sonuc.indexOf(yer[i])....			
				}//if(sonuc.contains...
			}//for j
			if(dur)break;
		}//for i
		return null;
	}//ikiliIslev
	//
	public String islevYerKomut(String islev_yer){
		String[] islev_yer_ayir=islev_yer.split(",");
		String komut="";
		//islev_yer="salon,mutfak,1" format�nda olur...
		if(listeVarMI(islev_yer_ayir[0], anahtar.getir(anahtar.SALON))){
			switch (Integer.parseInt(islev_yer_ayir[2])) {
			case 1://yak
				if(islev_yer_ayir[1].contains("mutfa�")||islev_yer_ayir[1].contains("mutfak"))
					komut="001,016";//salon a� mutfa�� kapat
				if(islev_yer_ayir[1].contains("oturma"))
					komut="001,006";//salon a� oturma odas� kapat
				if(islev_yer_ayir[1].contains("yatak"))
					komut="001,018";//salon a� yatak odas� kapat
				if(islev_yer_ayir[1].contains("�ocuk"))
					komut="001,008";//salon a� �ocuk odas� kapat
				if(islev_yer_ayir[1].contains("antre"))
					komut="001,024";//salon a� antre kapat
				break;
			case 2://s�nd�r
				if(islev_yer_ayir[1].contains("mutfa�")||islev_yer_ayir[1].contains("mutfak"))
					komut="002,009";//salon kapat mutfa�� a�
				if(islev_yer_ayir[1].contains("oturma"))
					komut="002,005";//salon kapat oturma odas� a�
				if(islev_yer_ayir[1].contains("yatak"))
					komut="002,017";//salon kapat yatak odas� a�
				if(islev_yer_ayir[1].contains("�ocuk"))
					komut="002,007";//salon kapat �ocuk odas� a�
				if(islev_yer_ayir[1].contains("antre"))
					komut="002,023";//salon kapat antre a�
				break;
			}//Switch
			//..........//
		}//anahtar.SALON)))...
		if(listeVarMI(islev_yer_ayir[0], anahtar.getir(anahtar.MUTFAK))){
			switch (Integer.parseInt(islev_yer_ayir[2])) {
			case 1://yak
				if(listeVarMI(islev_yer_ayir[1], anahtar.getir(anahtar.SALON)))
					komut="009,002";//.... a� ....kapat
				if(islev_yer_ayir[1].contains("oturma"))
					komut="009,006";
				if(islev_yer_ayir[1].contains("yatak"))
					komut="009,018";
				if(islev_yer_ayir[1].contains("�ocuk"))
					komut="009,008";
				if(islev_yer_ayir[1].contains("antre"))
					komut="009,024";
				break;
			case 2://Sondur
				if(listeVarMI(islev_yer_ayir[1], anahtar.getir(anahtar.SALON)))
					komut="016,001";//... kapat .... a�
				if(islev_yer_ayir[1].contains("oturma"))
					komut="016,005";
				if(islev_yer_ayir[1].contains("yatak"))
					komut="016,017";
				if(islev_yer_ayir[1].contains("�ocuk"))
					komut="016,007";
				if(islev_yer_ayir[1].contains("antre"))
					komut="016,023";
				break;
			}//Switch
		}//anahtar.MUTFAK
		if(listeVarMI(islev_yer_ayir[0],anahtar.OturmaOdasi)){
			switch (Integer.parseInt(islev_yer_ayir[2])) {
			case 1://yak
				if(listeVarMI(islev_yer_ayir[1], anahtar.getir(anahtar.SALON)))
					komut="005,002";//.... a� ....kapat
				if(islev_yer_ayir[1].contains("mutfa�")||islev_yer_ayir[1].contains("mutfak"))
					komut="005,016";
				if(islev_yer_ayir[1].contains("yatak"))
					komut="005,018";
				if(islev_yer_ayir[1].contains("�ocuk"))
					komut="005,008";
				if(islev_yer_ayir[1].contains("antre"))
					komut="005,024";
				break;
			case 2://Sondur
				if(listeVarMI(islev_yer_ayir[1], anahtar.getir(anahtar.SALON)))
					komut="006,001";//... kapat .... a�
				if(islev_yer_ayir[1].contains("mutfa�")||islev_yer_ayir[1].contains("mutfak"))
					komut="006,009";//salon kapat mutfa�� a�
				if(islev_yer_ayir[1].contains("yatak"))
					komut="006,017";
				if(islev_yer_ayir[1].contains("�ocuk"))
					komut="006,007";
				if(islev_yer_ayir[1].contains("antre"))
					komut="006,023";
				break;
			}//Switch
		}//oturma odas�
		if(listeVarMI(islev_yer_ayir[0],anahtar.Antre)){
			switch (Integer.parseInt(islev_yer_ayir[2])) {
			case 1://yak
				if(listeVarMI(islev_yer_ayir[1], anahtar.getir(anahtar.SALON)))
					komut="023,002";//.... a� ....kapat
				if(islev_yer_ayir[1].contains("mutfa�")||islev_yer_ayir[1].contains("mutfak"))
					komut="023,016";
				if(islev_yer_ayir[1].contains("yatak"))
					komut="023,018";
				if(islev_yer_ayir[1].contains("�ocuk"))
					komut="023,008";
				if(islev_yer_ayir[1].contains("oturma"))
					komut="023,006";
				break;
			case 2://Sondur
				if(listeVarMI(islev_yer_ayir[1], anahtar.getir(anahtar.SALON)))
					komut="024,001";//... kapat .... a�
				if(islev_yer_ayir[1].contains("mutfa�")||islev_yer_ayir[1].contains("mutfak"))
					komut="024,009";//salon kapat mutfa�� a�
				if(islev_yer_ayir[1].contains("yatak"))
					komut="024,017";
				if(islev_yer_ayir[1].contains("�ocuk"))
					komut="024,007";
				if(islev_yer_ayir[1].contains("oturma"))
					komut="024,005";
				break;
			}//Switch
		}//antre
		if(listeVarMI(islev_yer_ayir[0],anahtar.CocukOdasi)){
			switch (Integer.parseInt(islev_yer_ayir[2])) {
			case 1://yak
				if(listeVarMI(islev_yer_ayir[1], anahtar.getir(anahtar.SALON)))
					komut="007,002";//.... a� ....kapat
				if(islev_yer_ayir[1].contains("mutfa�")||islev_yer_ayir[1].contains("mutfak"))
					komut="007,016";
				if(islev_yer_ayir[1].contains("yatak"))
					komut="007,018";
				if(islev_yer_ayir[1].contains("antre")||islev_yer_ayir[1].contains("hol"))
					komut="007,024";
				if(islev_yer_ayir[1].contains("oturma"))
					komut="007,006";
				break;
			case 2://Sondur
				if(listeVarMI(islev_yer_ayir[1], anahtar.getir(anahtar.SALON)))
					komut="008,001";//... kapat .... a�
				if(islev_yer_ayir[1].contains("mutfa�")||islev_yer_ayir[1].contains("mutfak"))
					komut="008,009";//salon kapat mutfa�� a�
				if(islev_yer_ayir[1].contains("yatak"))
					komut="008,017";
				if(islev_yer_ayir[1].contains("antre")||islev_yer_ayir[1].contains("hol"))
					komut="008,023";
				if(islev_yer_ayir[1].contains("oturma"))
					komut="008,005";
				break;
			}//Switch
		}//Cocuk odas�
		if(listeVarMI(islev_yer_ayir[0],anahtar.YatakOdasi)){
			switch (Integer.parseInt(islev_yer_ayir[2])) {
			case 1://yak
				if(listeVarMI(islev_yer_ayir[1], anahtar.getir(anahtar.SALON)))
					komut="017,002";//.... a� ....kapat
				if(islev_yer_ayir[1].contains("mutfa�")||islev_yer_ayir[1].contains("mutfak"))
					komut="017,016";
				if(islev_yer_ayir[1].contains("oturma"))
					komut="017,006";
				if(islev_yer_ayir[1].contains("�ocuk"))
					komut="017,008";
				if(islev_yer_ayir[1].contains("antre"))
					komut="017,024";
				break;
			case 2://Sondur
				if(listeVarMI(islev_yer_ayir[1], anahtar.getir(anahtar.SALON)))
					komut="018,001";//... kapat .... a�
				if(islev_yer_ayir[1].contains("mutfa�")||islev_yer_ayir[1].contains("mutfak"))
					komut="018,009";//salon kapat mutfa�� a�
				if(islev_yer_ayir[1].contains("oturma"))
					komut="018,005";
				if(islev_yer_ayir[1].contains("�ocuk"))
					komut="018,007";
				if(islev_yer_ayir[1].contains("antre"))
					komut="018,023";
				break;
			}//Switch
		}//yatak odas�
		return komut;
	}//islevYerKomut
	//liste de verilenlerin herhangi birinin sonuc'ta olup olmad���n� bulur.
	public boolean listeVarMI(String sonuc, String liste){
		
		String[] ayir=liste.split(",");
		
		for (int i = 0; i < ayir.length; i++) {
			if(sonuc.contains(ayir[i]))return true;
		}
		return false;
	}
	//liste1 ve liste2 de var olan ifadelerden bir tanesi "sonuc" i�ersinde varsa
	//liste1 ve liste2 (i�lev listeleri) deki ifadelerin �nceli�ini bulur.
	public int islevYeri(String sonuc,String liste1,String liste2){		
		String[] sonuclar=sonuc.split("\n");
		for(int k=0;k<sonuclar.length;k++){		//B�t�n sonu�larda ARA
			if(listeVarMI(sonuclar[k],liste1)&&listeVarMI(sonuclar[k],liste2)){
				//herhangi bir sonu�ta var ise
				String[] l1s=liste1.split(",");
				String[] l2s=liste2.split(",");
				for (int i = 0; i < l1s.length; i++) {
					for (int j = 0; j < l2s.length; j++) {
						if(sonuclar[k].contains(l1s[i])&&sonuclar[k].contains(l2s[j]))//iki i�lev bulunduysa
						{
							if(sonuclar[k].indexOf(l1s[i]) < sonuclar[k].indexOf(l2s[j])){
								//Log.d("i�lev yeri","i�lev"+sonuclar[k]+">>>"+sonuclar[k].indexOf(l1s[i]));
								return 1;
							}
							if(sonuclar[k].indexOf(l1s[i]) > sonuclar[k].indexOf(l2s[j])){
								//Log.d("i�lev yeri","i�lev"+sonuclar[k]+">>>"+sonuclar[k].indexOf(l2s[j]));
								return 2;
							}
						}
					}//for j
				}//for i	
			}//if(listeVarMI
		}//for k
		return -1;
	}//islevYeri
	public void bekle(final long ms){
		try {
			Thread.sleep(ms);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

