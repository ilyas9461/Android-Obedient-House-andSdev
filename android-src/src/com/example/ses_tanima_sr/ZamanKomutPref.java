package com.example.ses_tanima_sr;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.widget.Toast;
	
	
public class ZamanKomutPref{

	public final String KEY_PREF_INDEX="prefIndex";
	public final String KEY_PREF_ONEK="z";
	
	Context contex;
	SharedPreferences mSharedPrefs=null;
	SharedPreferences.Editor mPrefsEditor;
	
	ZamanKomutPref(Context bu){
		contex=bu;
		mSharedPrefs =contex.getSharedPreferences("ZamanIslev",contex.MODE_PRIVATE);
		
	}
	public void PrefKaydet(String ad,String deger){	
		mPrefsEditor = mSharedPrefs.edit();
		mPrefsEditor.putString(ad,deger);
		mPrefsEditor.commit();		
	}
	public String PrefOku(String ad){
		return mSharedPrefs.getString(ad,null);
	}
	public void PrefSil(String ad){
		mPrefsEditor = mSharedPrefs.edit();
		mPrefsEditor.remove(ad);
		mPrefsEditor.commit();
	}
	public void PrefHepsiniSil(){
		mPrefsEditor = mSharedPrefs.edit();
		mPrefsEditor.clear();
		PrefKaydet(KEY_PREF_INDEX,"0");
		//PrefKaydet(KEY_PREF_ONEK+"0","ilk,999");		
		mPrefsEditor.commit();
	}
	
}



/*SharedPreferences preferences = getSharedPreferences("Mypref", 0);
preferences.edit().remove("text").commit();*/

/*	String[] kod=zamanIslem.PrefOku(zamanIslem.KEY_PREF_ONEK+String.valueOf(index)).split(",");
if(kod[1]!=null){
	try {
		//prefIndex=Integer.parseInt(zamanIslem.PrefOku(zamanIslem.KEY_PREF_INDEX));
		if(bl_bagli)komutGonder(kod[1]);
		if(index>=prefIndex-1)index=-1;
		
		Toast.makeText(getApplicationContext(),"index="+String.valueOf(index)+"-->"+
				"\n "+String.valueOf(prefIndex)+
				"\n"+kod[0]+"->>"+kod[1],Toast.LENGTH_SHORT).show();
	} catch (NumberFormatException e) {
		
	}
}
index++;
*/
/*if(index==0)komutGonder(new String("001"));
if(index==1)komutGonder(new String("005"));
if(index==2)komutGonder(new String("007"));
if(index==3)komutGonder(new String("000"));
if(index>=3)index=-1;*/