package com.Data;

public class CmdTimingCheck {

	public  final  int  MAX_NUMBER = 3;
	// ��ѯ���������λ��, �����ѯһ��,  3�β�ѯʧ��.
	// ȡ����ѯ
	public 	 int		m_nCnt = 0;	 		
	public   String		m_strDEUID;			//�ն�ѯ��

	//*************************************************
	//
	CmdTimingCheck(){
		
		m_nCnt = 0;
		m_strDEUID = "";
	}
	//*************************************************
	//
	public boolean  isQueryDEUIDData(){
		
		if( m_nCnt++ > MAX_NUMBER ){			
			return  false;
		}
		else{
			return true;
		}
	}
	//*************************************************
	//
	public void  setDEUID( String  strDEUID ){
		m_strDEUID = strDEUID;
	}
	//*************************************************
	//
	public String  getDEUID(){
		
		return  m_strDEUID;
	}
}
