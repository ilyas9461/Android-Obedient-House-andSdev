package com.example.ses_tanima_sr;

public class TvMuzikSetiIslemleri {
	
	private SrStrIslem islem=new SrStrIslem();
	AnahtarListe anahtar=new AnahtarListe();
	
	public String TvMzSetIslem(String sonuc){
		String komut="";
		
		if(islem.listeVarMI(sonuc,islem.anahtar.getir(anahtar.TV))){
			
			if(islem.Uygula(sonuc,islem.YAK)&&islem.listeVarMI(sonuc,islem.anahtar.getir(anahtar.TV)))
				komut="030";
			if(islem.Uygula(sonuc,islem.SONDUR)&&islem.listeVarMI(sonuc,islem.anahtar.getir(anahtar.TV)))
				komut="031";
			if(sonuc.contains("prog")||sonuc.contains("kanal")){
				if(islem.listeVarMI(sonuc, anahtar.getir(anahtar.ARTTIR)))komut="032";
				if(islem.listeVarMI(sonuc, anahtar.getir(anahtar.AZALT)))komut="033";
			}
			if(sonuc.contains("ses")||sonuc.contains("volüm")){
				if(islem.listeVarMI(sonuc, anahtar.getir(anahtar.ARTTIR)))komut="034";
				if(islem.listeVarMI(sonuc, anahtar.getir(anahtar.AZALT)))komut="035";
			}
		}
		if(islem.listeVarMI(sonuc,anahtar.trt1))komut="040";
		if(islem.listeVarMI(sonuc,anahtar.trt2))komut="041";
		if(islem.listeVarMI(sonuc,anahtar.ntv))komut="042";
		if(islem.listeVarMI(sonuc,anahtar.atv))komut="043";
		if(islem.listeVarMI(sonuc,anahtar.kanalD))komut="044";		
		if(islem.listeVarMI(sonuc,anahtar.star))komut="045";
		if(islem.listeVarMI(sonuc,anahtar.stv))komut="046";
		if(islem.listeVarMI(sonuc,anahtar.kanal26))komut="047";
		
		if(islem.listeVarMI(sonuc,islem.anahtar.getir(anahtar.M_SET))){
			if(sonuc.contains("ses")||sonuc.contains("volüm")){				
				if(islem.listeVarMI(sonuc, anahtar.getir(anahtar.ARTTIR)))komut="038";
				if(islem.listeVarMI(sonuc, anahtar.getir(anahtar.AZALT)))komut="039";
			}
			if(islem.Uygula(sonuc,islem.YAK)&&islem.listeVarMI(sonuc,islem.anahtar.getir(anahtar.M_SET)))
				komut="036";
			if(islem.Uygula(sonuc,islem.SONDUR)&&islem.listeVarMI(sonuc,islem.anahtar.getir(anahtar.M_SET)))
				komut="037";			
		}
		
		return komut;
	}

}
