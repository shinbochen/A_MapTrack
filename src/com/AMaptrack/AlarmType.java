package com.AMaptrack;


public class AlarmType {

	private  String   	m_strAlarmName = "";
	private  int		 m_nAlarnType = 0;
	
	public AlarmType(){
		m_strAlarmName = "";
		m_nAlarnType = 0;
	}
	public AlarmType( String  strAlarmName, int  nAlarmType){
		
		m_strAlarmName = strAlarmName;
		m_nAlarnType = nAlarmType;
	}
	public void  setAlarmType( int  nAlarmType ){
		m_nAlarnType = nAlarmType;
	}
	public  int  getAlarmType(){
		return  m_nAlarnType;
	}
	public void   setAlarmName( String strAlarmName ){
		m_strAlarmName = strAlarmName;
	}
	public  String getAlarmName(){
		return  m_strAlarmName;
	}	
}
