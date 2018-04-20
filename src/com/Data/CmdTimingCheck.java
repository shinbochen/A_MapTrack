package com.Data;

public class CmdTimingCheck {

	public  final  int  MAX_NUMBER = 3;
	// 查询服务器最后位置, 五秒查询一次,  3次查询失败.
	// 取消查询
	public 	 int		m_nCnt = 0;	 		
	public   String		m_strDEUID;			//终端询号

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
