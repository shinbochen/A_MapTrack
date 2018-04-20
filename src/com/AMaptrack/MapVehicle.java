package com.AMaptrack;

public class MapVehicle {

	public  String    m_strDEUID = "";
	public  boolean   m_bShow = false;
	public  boolean	  m_bCenterShow = false;
	
	
	public MapVehicle( String  strDEUID,  boolean bShow, boolean  bCenterShow ){
		
		m_strDEUID = strDEUID;
		m_bShow = bShow;
		m_bCenterShow = bCenterShow;
	}
	public String  getDEUID(){
		return  m_strDEUID;
	}
	public void   setShow( boolean bShow ){
		m_bShow = bShow;
	}
	public boolean getShow(){
		return m_bShow;
	}
	public boolean isShow(){
		return m_bShow;
	}
	public  void  setCenterShow( boolean  bCenterShow ){
		
		m_bCenterShow = bCenterShow;
	}
	public  boolean  isCenterShow(){
		return  m_bCenterShow;
	}
}
