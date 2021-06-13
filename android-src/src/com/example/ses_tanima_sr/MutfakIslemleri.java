package com.example.ses_tanima_sr;

public class MutfakIslemleri {
	private SrStrIslem islem=new SrStrIslem();
	AnahtarListe anahtar=new AnahtarListe();
	
	public String MufakIslem(String sonuc){
		String komut="";
		//lamba iþlemleri
		if(islem.Uygula(sonuc,islem.LAMBA)||islem.listeVarMI(sonuc,anahtar.Mutfak)){
			
			if(islem.Uygula(sonuc,islem.YAK))komut="009";
			if(islem.Uygula(sonuc,islem.SONDUR))komut="016";
			
			if(islem.Uygula(sonuc,islem.ZAMAN_YAK)){			
				if(sonuc.contains("saniye")){
					komut="zaman,"+islem.zamanAyarla(Integer.parseInt(islem.StrSayiBul(sonuc)),0)+",009";
					return komut;
				}
				if(sonuc.contains("dakika")||sonuc.contains("dakka")){
					 komut="zaman,"+islem.zamanAyarla(Integer.parseInt(islem.StrSayiBul(sonuc)),1)+",009";
					 return komut;
				}
				if(sonuc.contains("saat")){
					komut="zaman,"+islem.zamanAyarla(Integer.parseInt(islem.StrSayiBul(sonuc)),2)+",009";
					return komut;
				}
			}//Zaman yak....
			if(islem.Uygula(sonuc,islem.ZAMAN_SONDUR)){
				if(sonuc.contains("saniye")){
					komut="zaman,"+islem.zamanAyarla(Integer.parseInt(islem.StrSayiBul(sonuc)),0)+",016";
					return komut;
				}
				if(sonuc.contains("dakika")||sonuc.contains("dakka")){
					komut="zaman,"+islem.zamanAyarla(Integer.parseInt(islem.StrSayiBul(sonuc)),1)+",016";
					return komut;
				}
				if(sonuc.contains("saat")){
					komut="zaman"+islem.zamanAyarla(Integer.parseInt(islem.StrSayiBul(sonuc)),2)+",016";
						return komut;
				}
			}//zaman sondur
		}
		//Kombi iþlemleri
		if(sonuc.contains("kombi")){
				if(islem.listeVarMI(sonuc,anahtar.getir(anahtar.YAK)))
				 	komut=("068");
				if(islem.listeVarMI(sonuc,anahtar.getir(anahtar.SONDUR)))
				    komut=("069");
				if(sonuc.contains("sýcak")){
					if(sonuc.contains("art"))komut=("060");
					if(sonuc.contains("az"))komut=("061");
				}	
				if(islem.Uygula(sonuc,islem.ZAMAN_YAK)){			
					if(sonuc.contains("saniye")){
						komut="zaman,"+islem.zamanAyarla(Integer.parseInt(islem.StrSayiBul(sonuc)),0)+",068";
						return komut;
					}
					if(sonuc.contains("dakika")||sonuc.contains("dakka")){
						 komut="zaman,"+islem.zamanAyarla(Integer.parseInt(islem.StrSayiBul(sonuc)),1)+",068";
						 return komut;
					}
					if(sonuc.contains("saat")){
						komut="zaman,"+islem.zamanAyarla(Integer.parseInt(islem.StrSayiBul(sonuc)),2)+",068";
						return komut;
					}
				}//Zaman yak....
				if(islem.Uygula(sonuc,islem.ZAMAN_SONDUR)){
					if(sonuc.contains("saniye")){
						komut="zaman,"+islem.zamanAyarla(Integer.parseInt(islem.StrSayiBul(sonuc)),0)+",069";
						return komut;
					}
					if(sonuc.contains("dakika")||sonuc.contains("dakka")){
						komut="zaman,"+islem.zamanAyarla(Integer.parseInt(islem.StrSayiBul(sonuc)),1)+",069";
						return komut;
					}
					if(sonuc.contains("saat")){
						komut="zaman"+islem.zamanAyarla(Integer.parseInt(islem.StrSayiBul(sonuc)),2)+",069";
							return komut;
					}
				}//zaman sondur
		}
		//Çamaþýr makinesi iþlemleri
		if(sonuc.contains("çamaþýr")){
			if(islem.listeVarMI(sonuc,anahtar.getir(anahtar.YAK)))
				komut=("062");
		 	if(islem.listeVarMI(sonuc,anahtar.getir(anahtar.SONDUR)))
		 		komut=("063");
		    if(islem.Uygula(sonuc,islem.ZAMAN_YAK)){			
				if(sonuc.contains("saniye")){
					komut="zaman,"+islem.zamanAyarla(Integer.parseInt(islem.StrSayiBul(sonuc)),0)+",062";
					return komut;
				}
				if(sonuc.contains("dakika")||sonuc.contains("dakka")){
					 komut="zaman,"+islem.zamanAyarla(Integer.parseInt(islem.StrSayiBul(sonuc)),1)+",062";
					 return komut;
				}
				if(sonuc.contains("saat")){
					komut="zaman,"+islem.zamanAyarla(Integer.parseInt(islem.StrSayiBul(sonuc)),2)+",062";
					return komut;
				}
			}//Zaman yak....
		}
		//ocak iþlemleri
		if(sonuc.contains("ocað")||sonuc.contains("ocak")){
			if(islem.listeVarMI(sonuc,anahtar.getir(anahtar.YAK)))
				return komut=("064");
		 	if(islem.listeVarMI(sonuc,anahtar.getir(anahtar.SONDUR)))
		 		return komut=("065");
		    if(islem.Uygula(sonuc,islem.ZAMAN_YAK)){			
				if(sonuc.contains("saniye")){
					komut="zaman,"+islem.zamanAyarla(Integer.parseInt(islem.StrSayiBul(sonuc)),0)+",064";
					return komut;
				}
				if(sonuc.contains("dakika")||sonuc.contains("dakka")){
					 komut="zaman,"+islem.zamanAyarla(Integer.parseInt(islem.StrSayiBul(sonuc)),1)+",064";
					 return komut;
				}
				if(sonuc.contains("saat")){
					komut="zaman,"+islem.zamanAyarla(Integer.parseInt(islem.StrSayiBul(sonuc)),2)+",064";
					return komut;
				}
			}//Zaman yak....
			if(islem.Uygula(sonuc,islem.ZAMAN_SONDUR)){
				if(sonuc.contains("saniye")){
					komut="zaman,"+islem.zamanAyarla(Integer.parseInt(islem.StrSayiBul(sonuc)),0)+",065";
					return komut;
				}
				if(sonuc.contains("dakika")||sonuc.contains("dakka")){
					komut="zaman,"+islem.zamanAyarla(Integer.parseInt(islem.StrSayiBul(sonuc)),1)+",065";
					return komut;
				}
				if(sonuc.contains("saat")){
					komut="zaman"+islem.zamanAyarla(Integer.parseInt(islem.StrSayiBul(sonuc)),2)+",065";
						return komut;
				}
			}//zaman sondur
		}
		//Fýrýn iþlemleri
		if(sonuc.contains("fýrýn")){
			if(islem.listeVarMI(sonuc,anahtar.getir(anahtar.YAK)))
				komut=("066");
		 	if(islem.listeVarMI(sonuc,anahtar.getir(anahtar.SONDUR)))
		 		komut=("067");
		 	if(islem.Uygula(sonuc,islem.ZAMAN_YAK)){			
				if(sonuc.contains("saniye")){
					komut="zaman,"+islem.zamanAyarla(Integer.parseInt(islem.StrSayiBul(sonuc)),0)+",066";
					return komut;
				}
				if(sonuc.contains("dakika")||sonuc.contains("dakka")){
					 komut="zaman,"+islem.zamanAyarla(Integer.parseInt(islem.StrSayiBul(sonuc)),1)+",066";
					 return komut;
				}
				if(sonuc.contains("saat")){
					komut="zaman,"+islem.zamanAyarla(Integer.parseInt(islem.StrSayiBul(sonuc)),2)+",066";
					return komut;
				}
			}//Zaman yak....
			if(islem.Uygula(sonuc,islem.ZAMAN_SONDUR)){
				if(sonuc.contains("saniye")){
					komut="zaman,"+islem.zamanAyarla(Integer.parseInt(islem.StrSayiBul(sonuc)),0)+",067";
					return komut;
				}
				if(sonuc.contains("dakika")||sonuc.contains("dakka")){
					komut="zaman,"+islem.zamanAyarla(Integer.parseInt(islem.StrSayiBul(sonuc)),1)+",067";
					return komut;
				}
				if(sonuc.contains("saat")){
					komut="zaman"+islem.zamanAyarla(Integer.parseInt(islem.StrSayiBul(sonuc)),2)+",067";
						return komut;
				}
			}//zaman sondur
		}
		
		return komut;
	}//MufakIslem
}//class
