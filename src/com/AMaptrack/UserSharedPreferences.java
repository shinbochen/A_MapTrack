/*
 * UserSharedPreferences基本配置信息的存储
*/
package com.AMaptrack;

import android.content.Context;
import android.content.SharedPreferences;


public class UserSharedPreferences {	

	SharedPreferences sp;
	SharedPreferences.Editor editor;
	Context context;

	public UserSharedPreferences(Context c,String name){

		context =c;
		sp=context.getSharedPreferences(name, 0);
		editor=sp.edit();
	}

	public void setValue(String key,String value){
		
		editor =sp.edit();
		editor.putString(key, value);
		editor.commit();
	}
	public void setValue(String key,int value){
		
		editor =sp.edit();
		editor.putInt(key,  value);
		editor.commit();
	}
	public void setValue(String key, boolean value ){
		
		editor =sp.edit();
		editor.putBoolean(key, value);
		editor.commit();
	}
	public String getValue(String key, String  strDefault){
			
		return sp.getString(key, strDefault);
	}
	
	public int getValue(String key, int  nDefault){
		
		return sp.getInt(key, nDefault);
	}
	public boolean getValue(String key, boolean  bDefault){
		
		return  sp.getBoolean(key, bDefault);
	}
}
