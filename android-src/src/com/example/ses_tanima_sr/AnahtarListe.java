package com.example.ses_tanima_sr;

public class AnahtarListe {
	//Döndürülecek liste bilgisini tutan sabitler
	public final int SRDUR=0;
	public final int BLBAGLAN=1;
	public final int SALON=10;
	public final int MUTFAK=11;
	public final int OTURMA=12;
	public final int COCUK=13;
	public final int YATAK=14;
	public final int LAMBA=15;
	public final int YAK=16;
	public final int SONDUR=17;
	public final int PERDE=18;
	public final int LAMBALAR=19;
	public final int KOMBI=30;
	public final int TV=50;
	public final int KANALLAR=51;
	public final int M_SET=70;
	public final int ARTTIR=80;
	public final int AZALT=81;
	public final int BAHCE=90;
	public final int ALARM=110;
	public final int ODA=130;
	
	//Döndürülecek anahtar listeyi tutan sabitler	
	public final  String SrDur="dur,bitir,son,sonlandýr,iptal,kes,yapma";
	public final  String BlBaglan="baðlan,baðlantý,bul,kur,oluþtur";	
	public final  String Odalar="salon,sabah,mutfak,mutfað,oturma,yatak,çocuk,antre,hol,sal";
	public final  String Salon="salon,sal,büyük oda,yemek odasý,misafir odasý,sabah";
	public final  String OturmaOdasi="oturma oda,oturma,günlük oda";
	public final  String YatakOdasi="yatak odasý,yatak,uyku odasý,dinlenme odasý";
	public final  String Mutfak="mutfak,mutfað,yemek piþirilen";
	public final  String Antre="antre,hol,ara";
	public final  String CocukOdasi="çocuk odasý,küçük oda,oyun odasý,oyun";
	public final  String Lamba="lamba,ýþýk,ýþýðý,elektrik,elektriði,anahtar,ceyran,ceryan,cereyan,"+
					     "þavk,kuruþka";
	public final  String LambaYak="yak,aç,düðmeye bas,aydýnlat,10,çak,çalýþ,düðmesini çevir,baþlat," +
						"düðmesine bas,koy";
	public final  String LambaSondur="söndür,sondur,kapat,düðmeye bas,karart,kapa,off,of,dur,sonlan,bitir";
	public final  String Lambalar="lambalarý,ýþýklarý,elektrikleri,hepsini,tüm,tümünü,tamamýný";
	public final  String Tv="tv,tivi,televizyon,lcd,lc d,el cd,lecede,elsidi";
	public final  String Kanallar="trt1,trt bir,terete bir,trt2,trt iki,ntv,entv,neteve,netv,ne tv,entivi," +
					"atv,ateve,ativi,stv,seteve,se tv,s tv,kanal 26,kanal yirmi altý,star,star tv,sýtar tv" +
					"kanal d,kanal de,kanalde";	
	public final  String trt1="trt1,trt bir,terete bir";
	public final  String trt2="trt2,trt iki,terete iki";
	public final  String ntv="ntv,entv,neteve,netv,ne tv,entivi";
	public final  String atv="atv,ateve,ativi";
	public final  String kanalD="kanal d,kanal de,kanalde";
	public final  String stv="stv,seteve,se tv,s tv";
	public final  String kanal26="kanal 26,kanal yirmi altý";
	public final  String star="star,star tv,sýtar tv";
	public final  String Mset="müzik set,set,teyb,teyip,teyib,cd çalar,çalar,sidi çalar,mp3";
	public final  String Arttir="art,çoðalt,ver,aç,iler";
	public final  String Azalt="azalt,kes,kýs,geri";
	//Sabit olarak kaydedilen isteden veya bellekten anahtar kelimeleri getirir.
	public String getir(int liste){
		String anahtar="";
		switch (liste) {
			case SRDUR:anahtar=SrDur;break;
			case BLBAGLAN:anahtar=BlBaglan;break;
			case SALON:anahtar=Salon;break;
			case MUTFAK:anahtar=Mutfak;break;
			case LAMBA:anahtar=Lamba;break;
			case YAK:anahtar=LambaYak;break;
			case SONDUR:anahtar=LambaSondur;break;
			case LAMBALAR:anahtar=Lambalar;break;
			case ODA:anahtar=Odalar;break;
			case TV:anahtar=Tv;break;
			case M_SET:anahtar=Mset;break;
			case KANALLAR:anahtar=Kanallar;break;
			case ARTTIR:anahtar=Arttir;break;
			case AZALT:anahtar=Azalt;break;
			//........//
		}
		return anahtar;			
	}
}//class son
