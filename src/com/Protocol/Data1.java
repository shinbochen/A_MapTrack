package com.Protocol;


public class Data1 extends Data {
	
	protected byte	m_nDataType = 0x00;
	
	//*******************************************
	//
	Data1(){ 
	}
	//*******************************************
	//
	void	SetDataType( byte nDataType) {
		m_nDataType = nDataType;
	}
	//*******************************************
	//
	byte	GetDataType( ){
		return m_nDataType; 
	}	
}