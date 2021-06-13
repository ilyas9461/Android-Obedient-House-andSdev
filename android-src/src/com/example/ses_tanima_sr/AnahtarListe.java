package com.example.ses_tanima_sr;

public class AnahtarListe {
	//D�nd�r�lecek liste bilgisini tutan sabitler
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
	
	//D�nd�r�lecek anahtar listeyi tutan sabitler	
	public final  String SrDur="dur,bitir,son,sonland�r,iptal,kes,yapma";
	public final  String BlBaglan="ba�lan,ba�lant�,bul,kur,olu�tur";	
	public final  String Odalar="salon,sabah,mutfak,mutfa�,oturma,yatak,�ocuk,antre,hol,sal";
	public final  String Salon="salon,sal,b�y�k oda,yemek odas�,misafir odas�,sabah";
	public final  String OturmaOdasi="oturma oda,oturma,g�nl�k oda";
	public final  String YatakOdasi="yatak odas�,yatak,uyku odas�,dinlenme odas�";
	public final  String Mutfak="mutfak,mutfa�,yemek pi�irilen";
	public final  String Antre="antre,hol,ara";
	public final  String CocukOdasi="�ocuk odas�,k���k oda,oyun odas�,oyun";
	public final  String Lamba="lamba,���k,�����,elektrik,elektri�i,anahtar,ceyran,ceryan,cereyan,"+
					     "�avk,kuru�ka";
	public final  String LambaYak="yak,a�,d��meye bas,ayd�nlat,10,�ak,�al��,d��mesini �evir,ba�lat," +
						"d��mesine bas,koy";
	public final  String LambaSondur="s�nd�r,sondur,kapat,d��meye bas,karart,kapa,off,of,dur,sonlan,bitir";
	public final  String Lambalar="lambalar�,���klar�,elektrikleri,hepsini,t�m,t�m�n�,tamam�n�";
	public final  String Tv="tv,tivi,televizyon,lcd,lc d,el cd,lecede,elsidi";
	public final  String Kanallar="trt1,trt bir,terete bir,trt2,trt iki,ntv,entv,neteve,netv,ne tv,entivi," +
					"atv,ateve,ativi,stv,seteve,se tv,s tv,kanal 26,kanal yirmi alt�,star,star tv,s�tar tv" +
					"kanal d,kanal de,kanalde";	
	public final  String trt1="trt1,trt bir,terete bir";
	public final  String trt2="trt2,trt iki,terete iki";
	public final  String ntv="ntv,entv,neteve,netv,ne tv,entivi";
	public final  String atv="atv,ateve,ativi";
	public final  String kanalD="kanal d,kanal de,kanalde";
	public final  String stv="stv,seteve,se tv,s tv";
	public final  String kanal26="kanal 26,kanal yirmi alt�";
	public final  String star="star,star tv,s�tar tv";
	public final  String Mset="m�zik set,set,teyb,teyip,teyib,cd �alar,�alar,sidi �alar,mp3";
	public final  String Arttir="art,�o�alt,ver,a�,iler";
	public final  String Azalt="azalt,kes,k�s,geri";
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
