package com.Protocol;


import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

import android.util.Log;


//**************************************************************
//
class DESCmdHead{
	public 	byte[]	 m_nDEUID = new byte[Structs.TEXT_DEUID_LEN];
	public  byte	 m_nDESType = 0;			// 终端类型
	public  byte	 m_nDESCmd = 0;				// 指令类型
	public  int		 m_nDESCmdLen = 0;			// 指令长度
}

//**************************************************************
//	登陆结构
class  Login{
	
	public   byte[]	m_nUser = null;
	public   byte[]	m_nPsd = null;
	
	Login(){
		m_nUser = new byte[Structs.TEXT_USER_LEN];
		m_nPsd = new byte[Structs.TEXT_PSD_LEN];
		Arrays.fill(m_nUser, (byte)0x00 );
		Arrays.fill(m_nPsd, (byte)0x00 );
	}
}

public class CLProtocolData extends ALProtocolData{

	protected 	List<Data1>		m_lstRecvData;

	public CLProtocolData(){
		m_lstRecvData = new ArrayList<Data1>();
		m_lstRecvData.clear();
	}
	
	public boolean	IsLoginCmd( ){ return (GetCMDType() == ControlCmd.CM_LOGIN) ? true:false;}
	public boolean	IsLoginOutCmd( ){ return (GetCMDType() == ControlCmd.CM_LOGOUT) ? true:false; }
	public boolean	IsLoginHandshakeCmd( ){ return (GetCMDType() == ControlCmd.CM_HANDSHAKE) ? true:false; }
	public boolean	IsQueryGPSDataCmd() { return (GetCMDType() == ControlCmd.CM_QUERY_GPSDATA) ? true:false; }
	public boolean	IsRegisterUserCmd() { return (GetCMDType() == ControlCmd.CM_REGISTER_USER) ? true:false; }
	public boolean	IsModifyUserCmd() { return (GetCMDType() == ControlCmd.CM_MODIFY_USER) ? true:false; }
	
	public boolean	IsAddCarCmd() { return (GetCMDType() == ControlCmd.CM_ADD_CAR) ? true:false; }
	public boolean	IsModifyCarCmd() { return (GetCMDType() == ControlCmd.CM_MODIFY_CAR) ? true:false; }
	public boolean	IsDelCarCmd() { return (GetCMDType() == ControlCmd.CM_DEL_CAR) ? true:false; }
	public boolean	IsQueryAlarmDataCmd() { return (GetCMDType() == ControlCmd.CM_QUERY_ALARMDATA) ? true:false; }
	
	public boolean	IsQueryRamDataCmd() { return (GetCMDType() == ControlCmd.CM_QUERY_RAMDATA) ? true:false; }
			
	//*****************************************************
	//
	protected boolean	GetData(byte  nDataType, List<Data>   outData ){
		
		boolean			  bResult = false;
		Data			  oData = null;
		Data1			  oData2= null;
				
		Iterator<Data1>	  it= m_lstRecvData.iterator();
		
		while( it.hasNext() ){			
			oData2 = it.next();
			if( oData2.GetDataType() == nDataType ){
				oData = new Data();
				oData.AddData( oData2.GetDataBuf(), oData2.GetDataLen() );				
				outData.add(oData );
				m_lstRecvData.remove( oData2 );
				it= m_lstRecvData.iterator();
				bResult = true;
			}
		}		
		return bResult;
	}
	//////////////////////////////////////////////////////////////////////////
	//
	public Data1  GetCLRecvData( ){
				
		byte						nCmdCode = 0;
		Data						oData = null;
		Data						oData2 = null;
		Data1						oResultObj = null;
		
		oData = GetALRecvData();
		if( oData == null ){
			return null;
		}		
		oData2 = new Data();
		oResultObj = new Data1();		
		nCmdCode = AppData.ParseData(oData2, oData.GetDataBuf(), oData.GetDataLen() );
		oResultObj.AddData( oData2.GetDataBuf(), oData2.GetDataLen() );
		oResultObj.SetDataType(nCmdCode);
		
		return oResultObj;
	}
	//////////////////////////////////////////////////////////////////////////
	//
	public boolean  GetCLRecvDataEx( ){
	
		byte				nDataType = 0;
		boolean				bResult = false;		
		Data1				oData = null;
		
		while( true ){
			
			Data		oData2 = null;
			oData2 = GetALRecvData();
			if( oData2 == null ){
				break;
			}
			Data		oTmpData = null;
			oTmpData = new Data();
			nDataType = AppData.ParseData(oTmpData, oData2.GetDataBuf(), oData2.GetDataLen() );
			bResult = true;
			if( nDataType > 0 ){
				oData = new Data1();
				oData.SetDataType( nDataType );
				oData.AddData( oTmpData.GetDataBuf(), oTmpData.GetDataLen() );
				m_lstRecvData.add(oData);
			}
		}
		return bResult;
	}
	//*****************************************************
	//
	public void      ComposeLoginCmd(int nSequence, String strUserName, String strPsd ){
	
		byte[]			nTmpBuf = null;
		int				nLen = 0;
		Login			oLogin;
		
		oLogin = new Login();
		SetSequence( (short)nSequence );
		
		nTmpBuf = strUserName.getBytes();
		nLen =  nTmpBuf.length;
		if( nLen >= Structs.TEXT_USER_LEN ){
			nLen = Structs.TEXT_USER_LEN-1;
		}
		System.arraycopy( nTmpBuf, 0, oLogin.m_nUser, 0, nLen); 
		nTmpBuf = strPsd.getBytes();
		nLen = nTmpBuf.length;
		if( nLen >= Structs.TEXT_PSD_LEN ){
			nLen = Structs.TEXT_PSD_LEN-1;
		}
		System.arraycopy(nTmpBuf, 0, oLogin.m_nPsd, 0, nLen );
		nTmpBuf = new  byte[Structs.TEXT_USER_LEN+Structs.TEXT_PSD_LEN];
		Arrays.fill(nTmpBuf, (byte)0x00 );
		System.arraycopy(oLogin.m_nUser, 0, nTmpBuf, 0, oLogin.m_nUser.length );
		System.arraycopy(oLogin.m_nPsd, 0, nTmpBuf, Structs.TEXT_USER_LEN, oLogin.m_nPsd.length );
		AddACLSendData( MacroDefinitions.TYPE_LOGIN, nTmpBuf, nTmpBuf.length );
		ComposeALData( ControlCmd.CM_LOGIN );
	}
	//*****************************************************
	//
	public void  AddACLSendData(byte nSubCmdCode, byte[]	lpBuf,  int  nLen ){
		
		Data		oData;

		oData = new Data();
		AppData.ComposeData(oData, nSubCmdCode, lpBuf, nLen  );
		AddAppSendData( oData );
	}
	//****************************************************************
	//
	public void		ComposeCheckUserCmd( int	nSequence,String	strUserName ){
		
		SetSequence( (short)nSequence );
		AddACLUserData( strUserName );
		ComposeALData( ControlCmd.CM_CHECK_USER );
	}
	//****************************************************************
	//
	public void		 AddACLUserData( String  strUserName ){
	
		int		 nDataLen = 0;
		byte[]	 nBuf = new  byte[Structs.TEXT_USER_LEN];
		
		Arrays.fill(nBuf, (byte)0);
		nDataLen = strUserName.getBytes().length;
		if( nDataLen >= Structs.TEXT_USER_LEN ){
			nDataLen = Structs.TEXT_USER_LEN-1;
		}
		System.arraycopy(strUserName.getBytes(), 0, nBuf, 0, nDataLen );
		
		AddACLSendData( MacroDefinitions.TYPE_USER_ONLY , nBuf, Structs.TEXT_USER_LEN );
	}
	//****************************************************************
	//  组织注册信息
	public  void		 ComposeRegisterUserCmd( int		nSequence,
												 String		vchr_user,
												 String		vchr_psd,
												 String		vchr_fname,
												 String		vchr_lname,
												 String		vchr_coname,
												 String		vchr_tel,
												 String		vchr_addr,
												 String		vchr_email,
												 String		vchr_remark,
												 String		vchr_strKey){
		
		// STUSERINFO 结构体大小为284
		int		nDataLen = 0;
		int		nTmpLen = 0;
		byte[]  nBuf = new byte[284]; 
		
		SetSequence( (short)nSequence );
		Arrays.fill(nBuf, (byte)0 );

		try {
			nTmpLen = vchr_user.getBytes("GBK").length;
			if( nTmpLen >= Structs.TEXT_USER_LEN ){
				nTmpLen = Structs.TEXT_USER_LEN-1;
			}
			System.arraycopy(vchr_user.getBytes("GBK"), 0, nBuf, 0, nTmpLen);
			nDataLen = Structs.TEXT_USER_LEN;		
	
			AddACLSendData(MacroDefinitions.TYPE_USER_ONLY, nBuf, Structs.TEXT_USER_LEN );
		
			nTmpLen = vchr_psd.getBytes("GBK").length;
			if( nTmpLen >= Structs.TEXT_PSD_LEN ){
				nTmpLen = Structs.TEXT_PSD_LEN-1;
			}
			System.arraycopy(vchr_psd.getBytes("GBK"), 0, nBuf, nDataLen, nTmpLen );
			nDataLen += Structs.TEXT_PSD_LEN;
			
			nDataLen += 2;	 //权限为空
			
			nTmpLen = vchr_fname.getBytes("GBK").length;
			if( nTmpLen >= Structs.TEXT_NAME_LEN ){
				nTmpLen = Structs.TEXT_NAME_LEN-1;
			}
			
			System.arraycopy( vchr_fname.getBytes("GBK"), 0, nBuf, nDataLen, nTmpLen );
			
			nDataLen += Structs.TEXT_NAME_LEN;
		
			nTmpLen = vchr_lname.getBytes("GBK").length;
			if( nTmpLen >= Structs.TEXT_NAME_LEN ){
				nTmpLen = Structs.TEXT_NAME_LEN-1;
			}
			System.arraycopy(vchr_lname.getBytes("GBK"), 0, nBuf, nDataLen, nTmpLen );
			nDataLen += Structs.TEXT_NAME_LEN;
			
			nTmpLen = vchr_coname.getBytes("GBK").length;
			if( nTmpLen >= Structs.TEXT_CONAME_LEN ){
				nTmpLen = Structs.TEXT_CONAME_LEN-1;
			}
			System.arraycopy(vchr_coname.getBytes("GBK"), 0, nBuf, nDataLen, nTmpLen );
			nDataLen += Structs.TEXT_CONAME_LEN;
			
			nTmpLen = vchr_tel.getBytes("GBK").length;
			if( nTmpLen >= Structs.TEXT_TEL_LEN ){
				nTmpLen = Structs.TEXT_TEL_LEN-1;
			}
			System.arraycopy(vchr_tel.getBytes("GBK"), 0, nBuf, nDataLen, nTmpLen );
			nDataLen += Structs.TEXT_TEL_LEN;
			
			nTmpLen = vchr_addr.getBytes("GBK").length;
			if( nTmpLen >= Structs.TEXT_ADDR_LEN ){
				nTmpLen = Structs.TEXT_ADDR_LEN-1;
			}
			System.arraycopy(vchr_addr.getBytes("GBK"), 0, nBuf, nDataLen, nTmpLen );
			nDataLen += Structs.TEXT_ADDR_LEN;
			
			nTmpLen = vchr_email.getBytes("GBK").length;
			if( nTmpLen >= Structs.TEXT_EMAIL_LEN ){
				nTmpLen = Structs.TEXT_EMAIL_LEN-1;
			}
			System.arraycopy(vchr_email.getBytes("GBK"), 0, nBuf, nDataLen, nTmpLen );
			nDataLen += Structs.TEXT_EMAIL_LEN;
			
			nTmpLen = vchr_remark.getBytes("GBK").length;
			if( nTmpLen >= Structs.TEXT_REMARK_LEN ){
				nTmpLen = Structs.TEXT_REMARK_LEN-1;
			}
			System.arraycopy(vchr_remark.getBytes("GBK"), 0, nBuf, nDataLen, nTmpLen );
			nDataLen += Structs.TEXT_REMARK_LEN;
			
			nTmpLen = vchr_strKey.getBytes("GBK").length;
			if( nTmpLen >= Structs.TEXT_TEL_LEN ){
				nTmpLen = Structs.TEXT_TEL_LEN-1;
			}
			System.arraycopy(vchr_strKey.getBytes("GBK"), 0, nBuf, nDataLen, nTmpLen );
			nDataLen += Structs.TEXT_TEL_LEN;

			AddACLSendData( MacroDefinitions.TYPE_USERINFO, nBuf, nDataLen );
			ComposeALData( ControlCmd.CM_REGISTER_USER );
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	//////////////////////////////////////////////////////////////////////////
	//
	public void	ComposeModifyUserCmd(   int	   		nSequence, 											  
										String 		vchr_user, 
										String 		vchr_psd, 
										String 		vchr_fname, 
										String 		vchr_lname, 
										String 		vchr_coname, 
										String 		vchr_tel, 
										String 		vchr_addr , 
										String 		vchr_email, 
										String 		vchr_remark){
			
		int			nDataLen = 0;
		int			nTmpLen = 0;
		byte[]		nBuf = new byte[284];;
		
		
		SetSequence( (short)nSequence );
		Arrays.fill(nBuf, (byte)0);		

		try {
			nTmpLen = vchr_user.getBytes("GBK").length;
			if( nTmpLen >= Structs.TEXT_USER_LEN ){
				nTmpLen = Structs.TEXT_USER_LEN-1;
			}
			System.arraycopy(vchr_user.getBytes("GBK"), 0, nBuf, 0, nTmpLen);
			AddACLSendData(MacroDefinitions.TYPE_USER_ONLY, nBuf, Structs.TEXT_USER_LEN );	
			nDataLen = Structs.TEXT_USER_LEN;
			
			nTmpLen = vchr_psd.getBytes("GBK").length;
			if( nTmpLen >= Structs.TEXT_PSD_LEN ){
				nTmpLen = Structs.TEXT_PSD_LEN-1;
			}
			System.arraycopy(vchr_psd.getBytes("GBK"), 0, nBuf, nDataLen, nTmpLen );
			nDataLen += Structs.TEXT_PSD_LEN;
			
			nDataLen += 2;	 //权限为空

			nTmpLen = vchr_fname.getBytes("GBK").length;
			if( nTmpLen >= Structs.TEXT_NAME_LEN ){
				nTmpLen = Structs.TEXT_NAME_LEN-1;
			}
			System.arraycopy(vchr_fname.getBytes("GBK"), 0, nBuf, nDataLen, nTmpLen );			
			nDataLen += Structs.TEXT_NAME_LEN;	

		
			nTmpLen = vchr_lname.getBytes("GBK").length;
			if( nTmpLen >= Structs.TEXT_NAME_LEN ){
				nTmpLen = Structs.TEXT_NAME_LEN-1;
			}
			System.arraycopy(vchr_lname.getBytes("GBK"), 0, nBuf, nDataLen, nTmpLen );
			nDataLen += Structs.TEXT_NAME_LEN;		
			
			nTmpLen = vchr_coname.getBytes("GBK").length;
			if( nTmpLen >= Structs.TEXT_CONAME_LEN ){
				nTmpLen = Structs.TEXT_CONAME_LEN-1;
			}
			System.arraycopy(vchr_coname.getBytes("GBK"), 0, nBuf, nDataLen, nTmpLen );
			nDataLen += Structs.TEXT_CONAME_LEN;	
			
			nTmpLen = vchr_tel.getBytes("GBK").length;
			if( nTmpLen >= Structs.TEXT_TEL_LEN ){
				nTmpLen = Structs.TEXT_TEL_LEN-1;
			}
			System.arraycopy(vchr_tel.getBytes("GBK"), 0, nBuf, nDataLen, nTmpLen );
			nDataLen += Structs.TEXT_TEL_LEN;		
			
			nTmpLen = vchr_addr.getBytes("GBK").length;
			if( nTmpLen >= Structs.TEXT_ADDR_LEN ){
				nTmpLen = Structs.TEXT_ADDR_LEN-1;
			}
			System.arraycopy(vchr_addr.getBytes("GBK"), 0, nBuf, nDataLen, nTmpLen );
			nDataLen += Structs.TEXT_ADDR_LEN;		
			
			nTmpLen = vchr_email.getBytes("GBK").length;
			if( nTmpLen >= Structs.TEXT_EMAIL_LEN ){
				nTmpLen = Structs.TEXT_EMAIL_LEN-1;
			}
			System.arraycopy(vchr_email.getBytes("GBK"), 0, nBuf, nDataLen, nTmpLen );
			nDataLen += Structs.TEXT_EMAIL_LEN;
			
			nTmpLen = vchr_remark.getBytes("GBK").length;
			if( nTmpLen >= Structs.TEXT_REMARK_LEN ){
				nTmpLen = Structs.TEXT_REMARK_LEN-1;
			}
			System.arraycopy(vchr_remark.getBytes("GBK"), 0, nBuf, nDataLen, nTmpLen );
			nDataLen += Structs.TEXT_REMARK_LEN;
			
			nDataLen += Structs.TEXT_TEL_LEN;
			
			AddACLSendData( MacroDefinitions.TYPE_USERINFO, nBuf, nDataLen );
			ComposeALData( ControlCmd.CM_MODIFY_USER );
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	//////////////////////////////////////////////////////////////////////////
	//
	public void	   ComposeAddCarCmd(int     nSequence, 
									String  strUserName, 
									String 	vchr_deuid, 
									String 	vchr_desim, 
									String 	vchr_license, 
									int	  	nDEType,
									String 	vchr_fname, 
									String 	vchr_lname, 
									String 	vchr_tel, 
									String 	vchr_addr, 
									String 	vchr_remark){
	
		int			nLen = 0;
		int			nDataLen = 0;
		byte[]		nBuf = new byte[204+1];
		
		SetSequence( (short)nSequence );		
		AddACLUserData( strUserName );
		Arrays.fill(nBuf, (byte)0);
		
		try {
			nLen = vchr_deuid.getBytes("GBK").length;
			if( nLen > Structs.TEXT_DEUID_LEN ){
				nLen = Structs.TEXT_DEUID_LEN - 1;
			}
			System.arraycopy(vchr_deuid.getBytes("GBK"), 0, nBuf, 0, nLen );
			nDataLen = Structs.TEXT_DEUID_LEN;
			
			nDataLen += 3;		//TCARINFO 结构对齐
			
			nBuf[nDataLen] =(byte)(nDEType & 0xFF);
			nBuf[nDataLen+1] =(byte)((nDEType >> 8) & 0xFF);
			nBuf[nDataLen+2] =(byte)((nDEType >> 16) & 0xFF);
			nBuf[nDataLen+3] =(byte)((nDEType >> 24) & 0xFF);
			nDataLen += 4;
			
			nLen = vchr_license.getBytes("GBK").length;
			if( nLen > Structs.TEXT_TEL_LEN ){
				nLen = Structs.TEXT_TEL_LEN-1;
			}
			System.arraycopy(vchr_license.getBytes("GBK"), 0, nBuf, nDataLen, nLen );
		
			nDataLen += Structs.TEXT_TEL_LEN;
			
			nLen = vchr_fname.getBytes("GBK").length;
			if( nLen > Structs.TEXT_NAME_LEN ){
				nLen = Structs.TEXT_NAME_LEN-1;
			}
			System.arraycopy(vchr_fname.getBytes("GBK"), 0, nBuf, nDataLen, nLen );
			nDataLen += Structs.TEXT_NAME_LEN;
			
			nLen = vchr_lname.getBytes("GBK").length;
			if( nLen > Structs.TEXT_NAME_LEN ){
				nLen = Structs.TEXT_NAME_LEN-1;
			}
			System.arraycopy(vchr_lname.getBytes("GBK"), 0, nBuf, nDataLen, nLen );
			nDataLen += Structs.TEXT_NAME_LEN;
			
			nLen = vchr_tel.getBytes("GBK").length;
			if( nLen > Structs.TEXT_TEL_LEN ){
				nLen = Structs.TEXT_TEL_LEN-1;
			}
			System.arraycopy(vchr_tel.getBytes("GBK"), 0, nBuf, nDataLen, nLen );
			nDataLen += Structs.TEXT_TEL_LEN;
			
			nLen = vchr_addr.getBytes("GBK").length;
			if( nLen > Structs.TEXT_ADDR_LEN ){
				nLen = Structs.TEXT_ADDR_LEN-1;
			}
			System.arraycopy(vchr_addr.getBytes("GBK"), 0, nBuf, nDataLen, nLen );
			nDataLen += Structs.TEXT_ADDR_LEN;	
			
			nLen = vchr_desim.getBytes("GBK").length;
			if( nLen > Structs.TEXT_TEL_LEN ){
				nLen = Structs.TEXT_TEL_LEN-1;
			}
			System.arraycopy(vchr_desim.getBytes("GBK"), 0, nBuf, nDataLen, nLen );
			nDataLen += Structs.TEXT_TEL_LEN;
			
			nLen = vchr_remark.getBytes("GBK").length;
			if( nLen > Structs.TEXT_REMARK_LEN ){
				nLen = Structs.TEXT_REMARK_LEN-1;
			}
			System.arraycopy(vchr_remark.getBytes("GBK"), 0, nBuf, nDataLen, nLen );
			nDataLen += Structs.TEXT_REMARK_LEN;
			
			AddACLSendData( MacroDefinitions.TYPE_CARINFO, nBuf, nDataLen );
			ComposeALData( ControlCmd.CM_ADD_CAR );
		} 
		catch (UnsupportedEncodingException e) {

		}
	}
	//////////////////////////////////////////////////////////////////////////
	//
	public void	   ComposeModifyCarCmd(int     nSequence, 
									String  strUserName, 
									String 	vchr_deuid, 
									String 	vchr_desim, 
									String 	vchr_license, 
									int	  	nDEType,
									String 	vchr_fname, 
									String 	vchr_lname, 
									String 	vchr_tel, 
									String 	vchr_addr, 
									String 	vchr_remark){
	
		int			nLen = 0;
		int			nDataLen = 0;
		byte[]		nBuf = new byte[204+1];
		
		SetSequence( (short)nSequence );		
		AddACLUserData( strUserName );
		Arrays.fill(nBuf, (byte)0);

		try {
			nLen = vchr_deuid.getBytes("GBK").length;
			if( nLen > Structs.TEXT_DEUID_LEN ){
				nLen = Structs.TEXT_DEUID_LEN - 1;
			}
			Log.e("DEUID", vchr_deuid );
			System.arraycopy(vchr_deuid.getBytes("GBK"), 0, nBuf, 0, nLen );
			nDataLen = Structs.TEXT_DEUID_LEN;		
			AddACLSendData( MacroDefinitions.TYPE_DEUID_ONLY, nBuf, nDataLen );		
			
			nDataLen += 3;		//TCARINFO 结构对齐
			
			nBuf[nDataLen] =(byte)(nDEType & 0xFF);
			nBuf[nDataLen+1] =(byte)((nDEType >> 8) & 0xFF);
			nBuf[nDataLen+2] =(byte)((nDEType >> 16) & 0xFF);
			nBuf[nDataLen+3] =(byte)((nDEType >> 24) & 0xFF);
			nDataLen += 4;
			
			nLen = vchr_license.getBytes("GBK").length;
			if( nLen > Structs.TEXT_TEL_LEN ){
				nLen = Structs.TEXT_TEL_LEN-1;
			}
			System.arraycopy(vchr_license.getBytes("GBK"), 0, nBuf, nDataLen, nLen );
		
			nDataLen += Structs.TEXT_TEL_LEN;
			
			nLen = vchr_fname.getBytes("GBK").length;
			if( nLen > Structs.TEXT_NAME_LEN ){
				nLen = Structs.TEXT_NAME_LEN-1;
			}
			System.arraycopy(vchr_fname.getBytes("GBK"), 0, nBuf, nDataLen, nLen );
			nDataLen += Structs.TEXT_NAME_LEN;
			
			nLen = vchr_lname.getBytes("GBK").length;
			if( nLen > Structs.TEXT_NAME_LEN ){
				nLen = Structs.TEXT_NAME_LEN-1;
			}
			System.arraycopy(vchr_lname.getBytes("GBK"), 0, nBuf, nDataLen, nLen );
			nDataLen += Structs.TEXT_NAME_LEN;
			
			nLen = vchr_tel.getBytes("GBK").length;
			if( nLen > Structs.TEXT_TEL_LEN ){
				nLen = Structs.TEXT_TEL_LEN-1;
			}
			System.arraycopy(vchr_tel.getBytes("GBK"), 0, nBuf, nDataLen, nLen );
			nDataLen += Structs.TEXT_TEL_LEN;
			
			nLen = vchr_addr.getBytes("GBK").length;
			if( nLen > Structs.TEXT_ADDR_LEN ){
				nLen = Structs.TEXT_ADDR_LEN-1;
			}
			System.arraycopy(vchr_addr.getBytes("GBK"), 0, nBuf, nDataLen, nLen );
			nDataLen += Structs.TEXT_ADDR_LEN;	
			
			nLen = vchr_desim.getBytes("GBK").length;
			if( nLen > Structs.TEXT_TEL_LEN ){
				nLen = Structs.TEXT_TEL_LEN-1;
			}
			System.arraycopy(vchr_desim.getBytes("GBK"), 0, nBuf, nDataLen, nLen );
			nDataLen += Structs.TEXT_TEL_LEN;
			
			nLen = vchr_remark.getBytes("GBK").length;
			if( nLen > Structs.TEXT_REMARK_LEN ){
				nLen = Structs.TEXT_REMARK_LEN-1;
			}
			System.arraycopy(vchr_remark.getBytes("GBK"), 0, nBuf, nDataLen, nLen );
			nDataLen += Structs.TEXT_REMARK_LEN;
			
			AddACLSendData( MacroDefinitions.TYPE_CARINFO, nBuf, nDataLen );
			ComposeALData( ControlCmd.CM_MODIFY_CAR );
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//////////////////////////////////////////////////////////////////////////
	//
	public void	  ComposeDeleteCarCmd(  int 			nSequence, 
										String 			strUserName, 
										List<String> 	inData ){
	
		SetSequence( (short)nSequence );
		AddACLUserData( strUserName );
		AddACLDEUIDData( inData );
		ComposeALData( ControlCmd.CM_DEL_CAR );
	}
	//////////////////////////////////////////////////////////////////////////
	//
	public void	  ComposeDeleteCarCmd(  int 			nSequence, 
										String 			strUserName, 
										String 			strDEUID ){
	
		List<String>	lst = new ArrayList<String>();
		
		lst.clear();
		lst.add(strDEUID);
		SetSequence( (short)nSequence );
		AddACLUserData( strUserName );
		AddACLDEUIDData( lst );
		ComposeALData( ControlCmd.CM_DEL_CAR );
	}
	//****************************************************************
	//  
	public boolean		GetUserInfoResult( List<UserInfo>		outData){
	
		boolean							bResult = false;
		UserInfo						oUserInfo;
		Data							oData = null;
		List<Data>						lstData;		
		Iterator<Data>					it = null;
		
		
		lstData = new ArrayList<Data>();
		if ( GetData( MacroDefinitions.TYPE_USERINFO,   lstData) == false){
			return bResult;
		}
		it = lstData.iterator();		
		if ( it.hasNext() ){
			
			oData = it.next();
			oUserInfo = new UserInfo();
			
			oUserInfo.ParseUserInfo( oData.GetDataBuf(), oData.GetDataLen() );
			
			outData.add( oUserInfo );
			bResult = true;
			lstData.remove( oData );
		}	
		return bResult;
	}
	//////////////////////////////////////////////////////////////////////////
	//  子用户
	public boolean	GetSubUserInfoResult( List<UserInfo>	outData){
	
		boolean							bResult = false;
		Data							oData = null;
		UserInfo						oUserInfo = null;
		List<Data>						lstData = null;
		
		
		lstData = new ArrayList<Data>();
		if ( GetData(MacroDefinitions.TYPE_SUBUSERINFO,   lstData) == false){
			
			Log.e("GetSubUserInfoResult", "1" );
			return bResult;
		}
		Iterator<Data> it= lstData.iterator();
		while ( it.hasNext() ){
		
			oData = it.next();
			oUserInfo = new UserInfo();			
			oUserInfo.ParseUserInfo( oData.GetDataBuf(), oData.GetDataLen() );			
			outData.add( oUserInfo );
			
			bResult = true;
			lstData.remove( oData );	
			it = lstData.iterator();
		}
		return bResult;
	}
	//////////////////////////////////////////////////////////////////////////
	//
	public boolean	GetGroupInfoResult( List<GroupInfo>     outData){
	
		boolean							bResult = false;
		GroupInfo						oGroupInfo = null;
		Data							oData = null;
		List<Data>						lstData = null;

		
		lstData = new ArrayList<Data>();
		if ( GetData(MacroDefinitions.TYPE_GROUPINFO,   lstData) == false){
			Log.e("GetGroupInfoResult", "1" );
			return bResult;
		}		
		Iterator<Data>		it = lstData.iterator();
		while( it.hasNext() ){
			
			oData = it.next();
			oGroupInfo = new GroupInfo();
			oGroupInfo.ParseGroupInfo( oData.GetDataBuf(), oData.GetDataLen() );
			outData.add(oGroupInfo);
			
			lstData.remove(oData);
			it = lstData.iterator();
			bResult = true;			
		}
		return bResult;
	}
	//////////////////////////////////////////////////////////////////////////
	//
	public boolean	 GetCarInfoResult( List<CarInfo>			outData){
	
		boolean							bResult = false;
		CarInfo							oCarInfo = null;
		Data							oData = null;
		List<Data>						lstData = null;

		
		lstData = new  ArrayList<Data>();
		if ( GetData(MacroDefinitions.TYPE_CARINFO,   lstData) == false){
			Log.e("GetCarInfoResult", "1" );
			return bResult;
		}
		Iterator<Data>  it = lstData.iterator();
		while( it.hasNext() ){
			
			oData = it.next();
			
			oCarInfo = new CarInfo();
			oCarInfo.ParseCarInfo(oData.GetDataBuf(), oData.GetDataLen() );			
			outData.add(oCarInfo);
			lstData.remove(oData);
			it = lstData.iterator();
			bResult = true;
		}
		return bResult;
	}
	//////////////////////////////////////////////////////////////////////////
	//
	public boolean	GetSubUserCarManageResult( List<SubUserManage>   outData){
	
		boolean							bResult = false;
		SubUserManage					oSubUserManage = null;
		Data							oData = null;
		List<Data>						lstData = null;
	
		lstData = new ArrayList<Data>();	
		if ( GetData( MacroDefinitions.TYPE_SUBUSERMANAGE,   lstData) == false){
			
			Log.e("GetSubUserCarManageResult", "1" );
			return bResult;
		}
		Iterator<Data>  it = lstData.iterator();
		while ( it.hasNext() ){
				
			oData = it.next();
			oSubUserManage = new SubUserManage();
			oSubUserManage.ParseSubUserManager(oData.GetDataBuf(), oData.GetDataLen() );		
	
			bResult = true;
			outData.add(oSubUserManage);
			
			lstData.remove(oData);
			it = lstData.iterator();
		}	
		return bResult;
	}
	//////////////////////////////////////////////////////////////////////////
	//
	public boolean	GetGroupManageResult(List<GroupManage>    outData){
	
		boolean							bResult = false;
		GroupManage						oGroupManage = null;
		Data							oData = null;
		List<Data>						lstData = null;
		
		lstData = new  ArrayList<Data>();
		if ( GetData( MacroDefinitions.TYPE_GROUPMANAGE,   lstData) == false){
			Log.e("GetGroupManageResult", "1" );
			return bResult;
		}
		Iterator<Data>	it = lstData.iterator();
		while ( it.hasNext() ){
		
			oData = it.next();
			
			oGroupManage = new GroupManage();
			oGroupManage.ParseGroupManage(oData.GetDataBuf(), oData.GetDataLen() );
			outData.add(oGroupManage);
			bResult = true;		
			
			lstData.remove(oData);
			it = lstData.iterator();			
		}
		return bResult;
	}
	//////////////////////////////////////////////////////////////////////////
	//获取设置参数信息
	public boolean	 GetDeviceSetupResult(List<DeviceSetup>		outData ){
		
		boolean							bResult = false;
		DeviceSetup						oDeviceSetup;
		Data							oData = null;
		List<Data>						lstData = null;

		lstData = new ArrayList<Data>();
		if ( GetData(MacroDefinitions.TYPE_TESETUP,   lstData) == false){
			Log.e("GetDeviceSetupResult", "1" );
			return bResult;
		}
		Iterator<Data>	it = lstData.iterator();
		while ( it.hasNext() ){
		
			oData = it.next();
			
			oDeviceSetup = new DeviceSetup();
			oDeviceSetup.ParseDeviceSetup(oData.GetDataBuf(), oData.GetDataLen() );
			outData.add(oDeviceSetup);
			
			bResult = true;
			lstData.remove(oData);
			it = lstData.iterator();
		}
		return  bResult;
	}
	//*************************************************************************
	//
	public boolean	GetGpsDataResult( List<GPSData>         outData){
	
		boolean							bResult = false;	
		Data							oData = null;
		GPSData							oGPSData = null;
		List<Data>						lstData = null;
		Iterator<Data>					it = null;
		
		
		lstData = new ArrayList<Data>();
		if ( GetData(MacroDefinitions.TYPE_GPSDATA,   lstData) == false){
			return bResult;
		}
		it = lstData.iterator();
		while ( it.hasNext() ){		
			
			oData = it.next();
			
			oGPSData = new GPSData();
			oGPSData.ParseGPSData( oData.GetDataBuf(), oData.GetDataLen() );
			outData.add(oGPSData);
			
			lstData.remove(oData);
			it = lstData.iterator();
			bResult = true;			
		}
		return bResult;
	}
	//////////////////////////////////////////////////////////////////////////
	//
	public boolean	GetAlarmDataResult( List<AlarmData> outData ){
	
		boolean							bResult = false;
		Data							oData = null;
		AlarmData						oAlarmData = null;
		List<Data>						lstData = null;
		Iterator<Data>					it = null;
		
		lstData = new ArrayList<Data>();
		lstData.clear();
		if ( GetData( MacroDefinitions.TYPE_ALARMDATA,   lstData) == false){
			return bResult;
		}
		it = lstData.iterator();
		while ( it.hasNext() ){
			
			oData = it.next();
			
			oAlarmData = new AlarmData();
			oAlarmData.ParseAlarmData( oData.GetDataBuf(), oData.GetDataLen() );			
			outData.add( oAlarmData );
			
			lstData.remove(oData);
			it = lstData.iterator();
			bResult = true;
		}
		return bResult;
	}
	//****************************************************************
	//
	public boolean	GetGasDataResult( List<GASData>		outData ){
	
		boolean							bResult = false;
		GASData							oGasData = null;
		Data							oData = null;
		List<Data>						lstData = null;
		Iterator<Data>					it = null;
		
		lstData = new ArrayList<Data>();
		if ( GetData(MacroDefinitions.TYPE_GASDATA,   lstData) == false){
			return bResult;
		}
		it = lstData.iterator();
		while ( it.hasNext() ){
		
			oData 	= it.next();
			
			oGasData = new GASData();
			oGasData.ParseUserInfo(oData.GetDataBuf(), oData.GetDataLen() );
			outData.add(oGasData);
			
			lstData.remove(oData);
			it = lstData.iterator();
			bResult = true;
		}
		return bResult;
	}
	//****************************************************************
	//
	public void	  ComposeQueryRamData(int nSequence, String strUserName,  List<String> inData){
	
		SetSequence( (short)nSequence );
		AddACLUserData( strUserName );			
		AddACLDEUIDData( inData );
		ComposeALData( ControlCmd.CM_QUERY_RAMDATA );
	}
	//****************************************************************
	//
	public  void  AddACLDEUIDData( List<String>  inData ){
		
		int					nDataLen = 0;		
		String				strDEUID;
		byte[]				nBuf = new byte[Structs.TEXT_DEUID_LEN];
		Iterator<String>	it = inData.iterator();		
		
		while( it.hasNext() ){
			
			strDEUID = it.next();
			Arrays.fill(nBuf, (byte)0 );
			nDataLen = strDEUID.getBytes().length;
			if( nDataLen >= Structs.TEXT_DEUID_LEN) {
				nDataLen = Structs.TEXT_DEUID_LEN-1;	
			}
			System.arraycopy(strDEUID.getBytes(), 0, nBuf, 0, nDataLen );
			AddACLSendData( MacroDefinitions.TYPE_DEUID_ONLY, nBuf, Structs.TEXT_DEUID_LEN );
		}
	}
	//****************************************************************
	//
	public void	  ComposeQueryGpsDataCmd( int 			nSequence, 
										  String 		strUserName, 
										  int  			nStratTime,
										  int  			nEndTime,
										  byte 			nCondition, 
										  List<String>  inData){
	
		SetSequence( (short)nSequence );
		AddACLUserData( strUserName );	
		AddACLQueryCondition( nStratTime, nEndTime, nCondition );		
		AddACLDEUIDData( inData );
		ComposeALData( ControlCmd.CM_QUERY_GPSDATA );
	}
	//////////////////////////////////////////////////////////////////////////
	//
	public void     ComposeQueryAlarmDataCmd( int 		    nSequence, 
											  String 	    strUserName,
											  int  			nStratTime,
											  int  			nEndTime,
											  byte 			nCondition, 
											  List<String>  inData ){
	
		SetSequence( (short)nSequence );
		AddACLUserData( strUserName );	
		AddACLQueryCondition( nStratTime, nEndTime, nCondition );	
		AddACLDEUIDData( inData );
		ComposeALData( ControlCmd.CM_QUERY_ALARMDATA );
	}

	//****************************************************************
	//
	public void  AddACLQueryCondition( int  nStratTime, int  nEndTime, byte nCondition ){
		
		byte[]		nBuf= new byte[12];
		
		nBuf[0] = nCondition;
		nBuf[4] = (byte)(nStratTime&0xFF);
		nBuf[5] = (byte)((nStratTime>>8)&0xFF);
		nBuf[6] = (byte)((nStratTime>>16)&0xFF);
		nBuf[7] = (byte)((nStratTime>>24)&0xFF);
		
		nBuf[8] = (byte)(nEndTime&0xFF);
		nBuf[9] = (byte)((nEndTime>>8)&0xFF);
		nBuf[10] = (byte)((nEndTime>>16)&0xFF);
		nBuf[11] = (byte)((nEndTime>>24)&0xFF);;
		
		AddACLSendData( MacroDefinitions.TYPE_QUERY_CONDITION, nBuf, 12 );
	}
	//////////////////////////////////////////////////////////////////////////
	//
	public void   ComposeCtrlTEData( byte[]			pCmdHead,
									 int			nCmdLen,
									 byte[]			pData, 
									 int			nLen, 
									 int			nSequence){
		
		byte[]			 nBuf = null;		
		
		SetSequence( (short)nSequence );
		nBuf = new  byte[nCmdLen+nLen];
		Arrays.fill(nBuf, (byte)0);
		System.arraycopy(pCmdHead, 0, nBuf, 0, nCmdLen);
		System.arraycopy(pData, 0, nBuf, nCmdLen, nLen);

		AddACLSendData( MacroDefinitions.TYPE_TECMD, nBuf, nCmdLen+nLen );	
		ComposeALData( ControlCmd.CM_CTRL_DE );
	}
	////////////////////////////////////////////////////////////////////////////////
	//1.	简单指令 CM_CTRL_TE_NORMAL
	//0: CM_CTRL_NORMAL_CALLONCE
	//1: CM_CTRL_NORMAL_RESET
	//2: CM_CTRL_NORMAL_RESTORE_FACTORY
	//3: CM_CTRL_NORMAL_TESETUP(读取终端设置)
	//4: CM_CTRL_NORMAL_FORTIFY(设防) 
	//5: CM_CTRL_NORMAL_ RESHUFFLE (设防)
	public void	ComposeCtrlTENormal( int		nSequence,
									 String     strDEUID,		
									 byte		nDEType,
									 byte		nDESubCmd  ){
	
		int				 nLen = 0;
		int				 nDataLen = 0;
		byte[]			 nCmdHeadBuf=null;
		byte[]			 nDataBuf = null;


		nCmdHeadBuf = new byte[16+1];
		Arrays.fill(nCmdHeadBuf, (byte)0);
		nLen = strDEUID.getBytes().length;
		if( nLen >= Structs.TEXT_DEUID_LEN) {
			nLen = Structs.TEXT_DEUID_LEN-1;	
		}
		System.arraycopy( strDEUID.getBytes(), 0, nCmdHeadBuf, 0, nLen );
		nDataLen = Structs.TEXT_DEUID_LEN;
		nCmdHeadBuf[nDataLen++] = nDEType;
		nCmdHeadBuf[nDataLen++] = CLToTE_Control.CM_CTRL_TE_NORMAL;
		nDataLen++;		//构造体对齐
		nCmdHeadBuf[nDataLen++] = 0x01;
		nCmdHeadBuf[nDataLen++] = 0x00;
		nCmdHeadBuf[nDataLen++] = 0x00;
		nCmdHeadBuf[nDataLen++] = 0x00;
		
		
		nDataBuf = new byte[1];
		nDataBuf[0] = nDESubCmd;
		ComposeCtrlTEData( nCmdHeadBuf, nDataLen, nDataBuf, 1, nSequence );
	}
	////////////////////////////////////////////////////////////////////////////////
	//2.	控制油路 CM_CTRL_TE_OILWAY
	public void   ComposeCtrlTEOilWay( int				nSequence,
									   String			strDEUID,
									   byte				nDEType,												
									   byte				nDESubCmd ){
		int				 nLen = 0;
		int				 nDataLen = 0;
		byte[]			 nCmdHeadBuf=null;
		byte[]			 nDataBuf = null;
		
		nCmdHeadBuf = new byte[16];
		Arrays.fill(nCmdHeadBuf, (byte)0);
		nLen = strDEUID.getBytes().length;
		if( nLen >= Structs.TEXT_DEUID_LEN) {
			nLen = Structs.TEXT_DEUID_LEN-1;	
		}
		System.arraycopy( strDEUID.getBytes(), 0, nCmdHeadBuf, 0, nLen );
		nDataLen = Structs.TEXT_DEUID_LEN;
		nCmdHeadBuf[nDataLen++] = nDEType;
		nCmdHeadBuf[nDataLen++] = CLToTE_Control.CM_CTRL_TE_OILWAY;		
		nDataLen++;		//构造体对齐
		nCmdHeadBuf[nDataLen++] = 0x01;
		nCmdHeadBuf[nDataLen++] = 0x00;
		nCmdHeadBuf[nDataLen++] = 0x00;
		nCmdHeadBuf[nDataLen++] = 0x00;
		
		
		nDataBuf = new byte[1];
		nDataBuf[0] = nDESubCmd;
		
		ComposeCtrlTEData( nCmdHeadBuf, nDataLen, nDataBuf, 1, nSequence );
	}
	////////////////////////////////////////////////////////////////////////////////
	//3.	控制边门 CM_CTRL_TE_DOOR
	public void	  ComposeCtrlTEDoor( int				nSequence,
									 String				strDEUID, 	
									 byte				nDEType,											  
									 byte		     	nDESubCmd ){
		
		int				 nLen = 0;
		int				 nDataLen = 0;
		byte[]			 nCmdHeadBuf=null;
		byte[]			 nDataBuf = null;
		
		nCmdHeadBuf = new byte[16];
		Arrays.fill(nCmdHeadBuf, (byte)0);
		nLen = strDEUID.getBytes().length;
		if( nLen >= Structs.TEXT_DEUID_LEN) {
			nLen = Structs.TEXT_DEUID_LEN-1;	
		}
		System.arraycopy( strDEUID.getBytes(), 0, nCmdHeadBuf, 0, nLen );
		nDataLen = Structs.TEXT_DEUID_LEN;
		nCmdHeadBuf[nDataLen++] = nDEType;
		nCmdHeadBuf[nDataLen++] = CLToTE_Control.CM_CTRL_TE_DOOR;
		nDataLen++;		//构造体对齐
		nCmdHeadBuf[nDataLen++] = 0x01;
		nCmdHeadBuf[nDataLen++] = 0x00;
		nCmdHeadBuf[nDataLen++] = 0x00;
		nCmdHeadBuf[nDataLen++] = 0x00;
		
		nDataBuf = new byte[1];
		nDataBuf[0] = nDESubCmd;
		
		ComposeCtrlTEData( nCmdHeadBuf, nDataLen, nDataBuf, 1, nSequence );
	}
	////////////////////////////////////////////////////////////////////////////////
	//7.	TE对车辆监听  CM_CTRL_TE_LISTEN  CM_CTRL_TE_TALK
	public	void    ComposeCtrlTEListen( int		 nSequence,
										 String		 strDEUID,
										 byte		 nDEType,											  
										 String		 strPhone){
	
		int				 nLen = 0;
		int				 nDataLen = 0;
		byte[]			 nCmdHeadBuf=null;
		byte[]			 nDataBuf = null;
		
		nCmdHeadBuf = new byte[16];
		Arrays.fill(nCmdHeadBuf, (byte)0);
		nLen = strDEUID.getBytes().length;
		if( nLen >= Structs.TEXT_DEUID_LEN) {
			nLen = Structs.TEXT_DEUID_LEN-1;	
		}
		System.arraycopy( strDEUID.getBytes(), 0, nCmdHeadBuf, 0, nLen );
		nDataLen = Structs.TEXT_DEUID_LEN;
		nCmdHeadBuf[nDataLen++] = nDEType;
		nCmdHeadBuf[nDataLen++] = CLToTE_Control.CM_CTRL_TE_LISTEN;
				
		nDataLen++;		//构造体对齐
		nLen = strPhone.getBytes().length;
		nCmdHeadBuf[nDataLen++] = (byte)(nLen & 0xFF);
		nCmdHeadBuf[nDataLen++] = 0x00;
		nCmdHeadBuf[nDataLen++] = 0x00;
		nCmdHeadBuf[nDataLen++] = 0x00;
		
		
		nDataBuf = new byte[nLen];
		Arrays.fill(nDataBuf, (byte)0);
		System.arraycopy(strPhone.getBytes(), 0, nDataBuf, 0, nLen);
		
		ComposeCtrlTEData( nCmdHeadBuf, nDataLen, nDataBuf, nLen, nSequence );
	}
	////////////////////////////////////////////////////////////////////////////////
	//7.	TE对车辆通话   CM_CTRL_TE_TALK	
	public	void    ComposeCtrlTETalk( int		 nSequence,
									   String	 strDEUID,
									   byte		 nDEType,											  
									   String	 strPhone){
		
		int				 nLen = 0;
		int				 nDataLen = 0;
		byte[]			 nCmdHeadBuf=null;
		byte[]			 nDataBuf = null;
		
		nCmdHeadBuf = new byte[16];
		Arrays.fill(nCmdHeadBuf, (byte)0);
		nLen = strDEUID.getBytes().length;
		if( nLen >= Structs.TEXT_DEUID_LEN) {
		nLen = Structs.TEXT_DEUID_LEN-1;	
		}
		System.arraycopy( strDEUID.getBytes(), 0, nCmdHeadBuf, 0, nLen );
		nDataLen = Structs.TEXT_DEUID_LEN;
		nCmdHeadBuf[nDataLen++] = nDEType;
		nCmdHeadBuf[nDataLen++] = CLToTE_Control.CM_CTRL_TE_TALK;
		
		nDataLen++;		//构造体对齐
		nLen = strPhone.getBytes().length;
		nCmdHeadBuf[nDataLen++] = (byte)(nLen & 0xFF);
		nCmdHeadBuf[nDataLen++] = 0x00;
		nCmdHeadBuf[nDataLen++] = 0x00;
		nCmdHeadBuf[nDataLen++] = 0x00;
		
		
		nDataBuf = new byte[nLen];
		Arrays.fill(nDataBuf, (byte)0);
		System.arraycopy(strPhone.getBytes(), 0, nDataBuf, 0, nLen);
		
		ComposeCtrlTEData( nCmdHeadBuf, nDataLen, nDataBuf, nLen, nSequence );
	}
}
