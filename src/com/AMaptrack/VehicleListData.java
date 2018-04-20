package com.AMaptrack;

import com.Protocol.CarInfo;
import com.Protocol.GPSData;

public class VehicleListData {
	
	public    boolean		m_bSelected = false;
	public 	  CarInfo		m_oCarInfo = null;
	public 	  GPSData		m_oGPSData = null;
	
	//********************************************************
	//
	public  VehicleListData( CarInfo  oCarInfo, GPSData  oGPSData ){
		
		m_oCarInfo = oCarInfo;
		m_oGPSData = oGPSData;
	}
	public void setCarInfo( CarInfo  oCarInfo ){
		m_oCarInfo = oCarInfo;
	}
	public CarInfo getCarInfo(){
		return m_oCarInfo;
	}
	public  void  setGPSData( GPSData  oGPSData ){
		    		
		m_oGPSData = oGPSData;
	}
	public GPSData  getGPSData(){
		return m_oGPSData;
	}
	public void  setSelected( boolean  bSelected ){
		m_bSelected = bSelected;
	}
	public boolean getSelected(){
		return m_bSelected;
	}
}
