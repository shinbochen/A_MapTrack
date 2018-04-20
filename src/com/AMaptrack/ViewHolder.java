package com.AMaptrack;


import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

public class ViewHolder {
	
	public CheckBox 		m_CheckCar = null;
	public TextView			m_TextCar= null;
	public TextView			m_TextGPSDesc = null;
	public ImageView		m_ImageCar = null;
	
	public String			m_strDEUID="";	
	
	public  void 	setDEUID( String strDEUID ){		
		m_strDEUID = strDEUID;
	}
	public  String	getDEUID( ){    		
		return m_strDEUID;
	}
	public void    setSelected(boolean bFlag ){
		m_CheckCar.setChecked(bFlag);
	}
}
