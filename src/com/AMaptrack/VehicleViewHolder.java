package com.AMaptrack;

import android.widget.CheckedTextView;

public class VehicleViewHolder {

	public CheckedTextView  m_chkCarlicense = null;
	public String			m_strDEUID="";	
	public boolean			m_bSelected = false;
	
	public  void 	setDEUID( String strDEUID ){		
		m_strDEUID = strDEUID;
	}
	public  String	getDEUID( ){
		
		return m_strDEUID;
	}
	public  void 	setSelected( boolean   bFlag ){
		m_chkCarlicense.setChecked(bFlag);
		m_bSelected = bFlag;
	}
	public void    setSelectedEx(boolean bFlag ){
		m_bSelected = bFlag;
	}
	public boolean  getSelected( ){
		return m_bSelected;
	}
}
