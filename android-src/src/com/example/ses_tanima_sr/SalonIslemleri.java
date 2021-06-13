package com.example.ses_tanima_sr;

import java.util.Calendar;

import android.content.IntentSender.SendIntentException;

public class SalonIslemleri {

	private SrStrIslem islem=new SrStrIslem();
	AnahtarListe anahtar=new AnahtarListe();
	
	public String SalonIslem(String sonuc){
		String komut="";
		
		if(islem.Uygula(sonuc, islem.LAMBA)||islem.listeVarMI(sonuc,anahtar.Salon))//||islem.Uygula(sonuc, islem.YAK)||				
		{			
			if(islem.Uygula(sonuc, islem.YAK))komut="001";
			if(islem.Uygula(sonuc, islem.SONDUR))komut="002";
			
			//Salon Lambasýný ... saniye/dakika/saat sonra aç
			if(islem.Uygula(sonuc,islem.ZAMAN_YAK)){			
				if(sonuc.contains("saniye")){
					komut="zaman,"+islem.zamanAyarla(Integer.parseInt(islem.StrSayiBul(sonuc)),0)+",001";
					return komut;
				}
				if(sonuc.contains("dakika")||sonuc.contains("dakka")){
					 komut="zaman,"+islem.zamanAyarla(Integer.parseInt(islem.StrSayiBul(sonuc)),1)+",001";
					 return komut;
				}
				if(sonuc.contains("saat")){
					komut="zaman,"+islem.zamanAyarla(Integer.parseInt(islem.StrSayiBul(sonuc)),2)+",001";
					return komut;
				}
			}//Zaman yak....
			if(islem.Uygula(sonuc,islem.ZAMAN_SONDUR)){
				if(sonuc.contains("saniye")){
					komut="zaman,"+islem.zamanAyarla(Integer.parseInt(islem.StrSayiBul(sonuc)),0)+",002";
					return komut;
				}
				if(sonuc.contains("dakika")||sonuc.contains("dakka")){
					komut="zaman,"+islem.zamanAyarla(Integer.parseInt(islem.StrSayiBul(sonuc)),1)+",002";
					return komut;
				}
				if(sonuc.contains("saat")){
					komut="zaman"+islem.zamanAyarla(Integer.parseInt(islem.StrSayiBul(sonuc)),2)+",002";
						return komut;
				}
			}//zaman sondur
			if(sonuc.contains("perde")){
				if(sonuc.contains("aç"))komut="090";
				if(sonuc.contains("kapat"))komut="091";
			}
		}
		return komut;
	}//SalonIslem
	
	 public boolean VarMI(String sonuc, String liste){
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
	
}//class


//////////

/*//Salon Lambasýný ... saniye/dakika/saat sonra kapat
//if(islem.Uygula(sonuc,islem.ZAMAN_SONDUR))
//if((islem.listeVarMI(sonuc, listeZaman))&&sonuc.contains("sön"))//(islem.listeVarMI(sonuc,LambaSondur)))
	if((VarMI(sonuc,listeZaman))&&(VarMI(sonuc,LambaSondur)))
{
	if(sonuc.contains("saniye")){
		komut="zaman,"+islem.zamanAyarla(Integer.parseInt(islem.StrSayiBul(sonuc)),0)+",002";
		return komut;
	}
	if(sonuc.contains("dakika")||sonuc.contains("dakka")){
		komut="zaman,"+islem.zamanAyarla(Integer.parseInt(islem.StrSayiBul(sonuc)),1)+",002";
		return komut;
	}
	if(sonuc.contains("saat")){
		try {
			komut="zaman"+islem.zamanAyarla(Integer.parseInt(islem.StrSayiBul(sonuc)),2)+",002";
			return komut;
		} catch (Exception e) {
		
		}				
	}
}

//Salon lambasýný aç .... saniye/dakika/saat sonra söndür
if(islem.Uygula(sonuc, islem.ZAMAN_YAK_SONDUR))
{	/*					
	if(sonuc.contains("saniye")){
		komut="zaman,"+islem.zamanAyarla(Integer.parseInt(islem.StrSayiBul(sonuc)),0)+",001,002";
	}
	if(sonuc.contains("dakika")||sonuc.contains("dakka")){
		komut="zaman,"+islem.zamanAyarla(Integer.parseInt(islem.StrSayiBul(sonuc)),1)+",001,002";
	}
	if(sonuc.contains("saat")){
		komut="zaman,"+islem.zamanAyarla(Integer.parseInt(islem.StrSayiBul(sonuc)),2)+",001,002";
	}
	*/
	
