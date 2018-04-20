package com.Protocol;

import java.util.ArrayList;
import java.util.List;

public class PlayHistoryData {

	public  int					m_nPos = 0;
	public  List<GPSData>		m_lstGPSData = null;
	  		
	//*****************************************************
	//
	public PlayHistoryData(){
		
		m_nPos = 0;
		m_lstGPSData = new ArrayList<GPSData>();
		m_lstGPSData.clear();
	}
	//******************************************************
	//
	public void freeMemory(){
		
		m_nPos = 0;
		m_lstGPSData.clear();
	}
	//******************************************************
	//
	public  void  AddPlayGPSData( GPSData  oGPSData  ){
		
		//Log.e("GPSTime", oGPSData.getTime() );
		m_lstGPSData.add(oGPSData);
	}
	//******************************************************
	//
	public  int  getSize(){
		
		return m_lstGPSData.size();
	}
	//*****************************************************
	//
	public  GPSData  Next(){
		
		GPSData    oGPSData = null;
		
		if( m_lstGPSData.size() > m_nPos ){
		
			oGPSData = m_lstGPSData.get( m_nPos );
			m_nPos += 1;
		}
		return  oGPSData;
	}
	//*****************************************************
	//
	public  void  setPlayPos( int  nPos ){
		m_nPos = nPos;
	}
}
