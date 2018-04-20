// Object instanceof String
package com.Data;


import java.util.ArrayList;
import java.util.List;


import com.Protocol.CLProtocolData;
import com.Protocol.CarInfo;
import com.Protocol.DESProtocolData;
import com.Protocol.Data;
import com.Protocol.UserInfo;

//*****************************************
//  ��������
public class ServerCommand {
	
	public  int				 m_nCmdType=-1;
	public  int				 m_nPara = 0;
	public  int				 m_nPara2 = 0;
	public  int				 m_nPara3 = 0;
	public  static  int		 m_nSequence = 0;
	public  String			 m_strPara="";
	public  String			 m_strPara2="";
	public  UserInfo		 m_oUserInfo = null;
	public  CarInfo			 m_oCarInfo = null;
	public  CLProtocolData   m_oProlData = null;
	public  DESProtocolData  m_oDESProlData = null;
	public  List<String>	 m_oStrArray = null;
	
	//*****************************************
	//
	public ServerCommand(){

		m_nCmdType = -1;
	}	
	public  int	getCmdType(){
		return m_nCmdType;
	}
	//************************************************************
	//  ��½ָ��
	public  void  setLoginCmd( String  strUser,  String  strPsd ){
		
		m_strPara = strUser;
		m_strPara2 = strPsd;
		m_nCmdType = CLCmdCode.TECMD_LOGIN;
	}
	//************************************************************
	//  ��ѯ���λ����Ϣ
	public  void  findTrackLast( String  strUser, List<String> lstDEUID ){		
		
		String  		strDEUID;
		
		if( m_oStrArray == null ){
			m_oStrArray = new ArrayList<String>();
		}
		m_strPara = strUser;		
		for( int nCnt = 0; nCnt < lstDEUID.size(); nCnt++) {			
			strDEUID = lstDEUID.get(nCnt);
			m_oStrArray.add(strDEUID);
		}		
		m_nCmdType = CLCmdCode.TECMD_TRACKLAST;
	}
	//************************************************************
	//
	public void  findRamData( String  strUser, List<String> lstDEUID ){
		
		String  		strDEUID;
		
		if( m_oStrArray == null ){
			m_oStrArray = new ArrayList<String>();
		}
		m_strPara = strUser;		
		for( int nCnt = 0; nCnt < lstDEUID.size(); nCnt++) {			
			strDEUID = lstDEUID.get(nCnt);
			m_oStrArray.add(strDEUID);
		}		
		m_nCmdType = CLCmdCode.TECMD_QUERY_RAMDATA;
	}
	//************************************************************
	//  ����һ������
	public  void  setGeneralCmd( CarInfo oCarInfo, int  nCmdType ){
		
		m_oCarInfo = oCarInfo;
		m_nCmdType = nCmdType;
	}
	//***********************************************************
	// 
	public  void  setGeneralCmd( CarInfo oCarInfo, int  nCmdType, int nPara){
		
		m_oCarInfo = oCarInfo;
		m_nCmdType = nCmdType;
		m_nPara    = nPara;
	}
	//***********************************************************
	// 
	public  void  setGeneralCmd( CarInfo oCarInfo, int  nCmdType, String strSrc){
		
		m_oCarInfo  = oCarInfo;
		m_nCmdType  = nCmdType;
		m_strPara   = strSrc;
	}
	//***********************************************************
	//  ע���û�
	public  void  RegisterUserCmd( UserInfo obj ){
		
		m_nCmdType  = CLCmdCode.TECMD_REGISTER_USER;
		m_oUserInfo = obj;
	}
	//**********************************************************
	//
	public void  ModifyUserCmd( UserInfo obj ){
		
		m_nCmdType  = CLCmdCode.TECMD_MODIFY_USER;
		m_oUserInfo = obj;
	}
	//***********************************************************
	//  ���ӳ���
	public  void  AddVehicleCmd( String  strUser, CarInfo oCarInfo ){
		
		m_nCmdType  = CLCmdCode.TECMD_ADD_VEHICLE;
		m_oCarInfo  = oCarInfo;
		m_strPara   = strUser;
	}
	//***********************************************************
	//  �޸ĳ���
	public  void  ModifyVehicleCmd(String  strUser, CarInfo oCarInfo ){
		
		m_nCmdType  = CLCmdCode.TECMD_MODIFY_VEHICLE;
		m_oCarInfo  = oCarInfo;
		m_strPara   = strUser;
	}
	//***********************************************************
	//  ɾ������
	public  void  DelVehicleCmd(String  strUser, CarInfo oCarInfo ){
		
		m_nCmdType  = CLCmdCode.TECMD_DEL_VEHICLE;
		m_strPara   = strUser;
		if( m_oStrArray == null ){
			m_oStrArray = new ArrayList<String>();
		}
		m_oStrArray.clear();
		m_oStrArray.add( oCarInfo.GetDEUID() );
	}
	//***********************************************************
	//  ɾ������
	public  void  DelVehicleCmd(String  strUser, List<String> inData ){
		
		m_nCmdType  = CLCmdCode.TECMD_DEL_VEHICLE;
		m_strPara   = strUser;
		if( m_oStrArray == null ){
			m_oStrArray = new ArrayList<String>();
		}
		m_oStrArray.clear();
		for( int nCnt = 0; nCnt < inData.size(); nCnt++ ){
			m_oStrArray.add( inData.get(nCnt) );
		}		
	}
	//***********************************************************
	//
	public  void setQueryAlarmCondition( String  strUser, 
										 String  strDEUID,
										 int	 nStartTime,
										 int	 nEndTime,
										 int	 nAlarmCondition ){
	
		m_nCmdType  = CLCmdCode.TECMD_QUERY_ALARM;
		m_strPara   = strUser;
		if( m_oStrArray == null ){
			m_oStrArray = new ArrayList<String>();
		}
		m_oStrArray.clear();
		m_oStrArray.add( strDEUID );
		m_nPara     = nStartTime;
		m_nPara2    = nEndTime;
		m_nPara3    = nAlarmCondition;
	}
	//***********************************************************
	//  ��ѯ��ʷ�ط�����
	public  void  setQueryPlayGPSData( String  strUser, 
									   String  strDEUID,
									   int	   nStartTime,
									   int	   nEndTime,
									   int	   nCondition){
		
		m_nCmdType  = CLCmdCode.TECMD_QUERY_PLAYDATA;
		m_strPara   = strUser;
		if( m_oStrArray == null ){
			m_oStrArray = new ArrayList<String>();
		}
		m_oStrArray.clear();
		m_oStrArray.add( strDEUID );
		m_nPara     = nStartTime;
		m_nPara2    = nEndTime;
		m_nPara3    = nCondition;
	}

	//***********************************************************
	//
	public  String	getDEUID(){
		return  m_oCarInfo.GetDEUID();
	}
	//***********************************************************
	//
	public  String  getDESIM(){
		
		return m_oCarInfo.GetDESIM();
	}
	//***********************************************************
	//
	public  Data  getSendSMSCmdData(){
		
		
		if( m_oDESProlData == null ){
			m_oDESProlData = new DESProtocolData();
		}		
		switch( m_nCmdType ){
		case CLCmdCode.TECMD_TRACKONCE:		//��λ
			m_oDESProlData.ComposeCtrlTENormal( (byte)(m_nSequence++&0xFF), m_oCarInfo.GetDEUID(), m_oCarInfo.GetTEType(), (byte)0x00);
			break;
		case CLCmdCode.TECMD_STOP_ENGINE:	//����
			m_oDESProlData.ComposeCtrlTEOilWay((byte)(m_nSequence++&0xFF), m_oCarInfo.GetDEUID(), m_oCarInfo.GetTEType(), (byte)0x01 );
			break;
		case CLCmdCode.TECMD_RESUME_ENGINE:	//�ָ���·
			m_oDESProlData.ComposeCtrlTEOilWay((byte)(m_nSequence++&0xFF), m_oCarInfo.GetDEUID(), m_oCarInfo.GetTEType(), (byte)0x00 );
			break;
		case CLCmdCode.TECMD_CAR_LISTEN:	//����	
			m_oDESProlData.ComposeCtrlTEListen((byte)(m_nSequence++&0xFF), m_oCarInfo.GetDEUID(), m_oCarInfo.GetTEType(), m_strPara );
			break;
		case CLCmdCode.TECMD_CAR_TALK:
			m_oDESProlData.ComposeCtrlTETalk( (byte)(m_nSequence++&0xFF), m_oCarInfo.GetDEUID(), m_oCarInfo.GetTEType(), m_strPara );
			break;
		case CLCmdCode.TECMD_LOCK:			//����
			m_oDESProlData.ComposeCtrlTENormal((byte)(m_nSequence++&0xFF), m_oCarInfo.GetDEUID(), m_oCarInfo.GetTEType(),(byte)0x01 );
			break;
		case CLCmdCode.TECMD_UNLOCK:		//����
			m_oDESProlData.ComposeCtrlTENormal((byte)(m_nSequence++&0xFF), m_oCarInfo.GetDEUID(), m_oCarInfo.GetTEType(),(byte)0x00 );
			break;
		}
		return m_oDESProlData.GetTLSendData();
	}
	//***********************************************************
	//  ��ȡ��������
	public  Data  getSendCmdData( ){
		
		if( m_oProlData == null ){
			m_oProlData = new CLProtocolData();
		}		
		switch( m_nCmdType ){
		case CLCmdCode.TECMD_TRACKONCE:		//��λ
			m_oProlData.ComposeCtrlTENormal(m_nSequence++, m_oCarInfo.GetDEUID(), m_oCarInfo.GetTEType(), (byte)0x00 );
			break;
		case CLCmdCode.TECMD_STOP_ENGINE:	//����
			m_oProlData.ComposeCtrlTEOilWay(m_nSequence++, m_oCarInfo.GetDEUID(), m_oCarInfo.GetTEType(), (byte)0x01 );
			break;
		case CLCmdCode.TECMD_RESUME_ENGINE:	//�ָ���·
			m_oProlData.ComposeCtrlTEOilWay(m_nSequence++, m_oCarInfo.GetDEUID(), m_oCarInfo.GetTEType(), (byte)0x00 );
			break;
		case CLCmdCode.TECMD_CAR_LISTEN:	//����	
			m_oProlData.ComposeCtrlTEListen(m_nSequence++, m_oCarInfo.GetDEUID(), m_oCarInfo.GetTEType(), m_strPara );
			break;
		case CLCmdCode.TECMD_CAR_TALK:
			m_oProlData.ComposeCtrlTETalk(m_nSequence++, m_oCarInfo.GetDEUID(), m_oCarInfo.GetTEType(), m_strPara );
			break;
		case CLCmdCode.TECMD_LOCK:			//����
			m_oProlData.ComposeCtrlTEDoor(m_nSequence++, m_oCarInfo.GetDEUID(), m_oCarInfo.GetTEType(), (byte)0x01 );
			break;
		case CLCmdCode.TECMD_UNLOCK:		//����
			m_oProlData.ComposeCtrlTEDoor(m_nSequence++, m_oCarInfo.GetDEUID(), m_oCarInfo.GetTEType(), (byte)0x00 );
			break;
		case CLCmdCode.TECMD_TRACKLAST:		//���λ��
			m_oProlData.ComposeQueryGpsDataCmd(m_nSequence++, m_strPara, 0, 0, (byte)255, m_oStrArray );
			break;	
		case CLCmdCode.TECMD_QUERY_PLAYDATA:
			m_oProlData.ComposeQueryGpsDataCmd(m_nSequence++, m_strPara, m_nPara, m_nPara2, (byte)(m_nPara3&0xFF), m_oStrArray );
			break;
		case CLCmdCode.TECMD_QUERY_RAMDATA: //��ѯ�ڴ�����
			m_oProlData.ComposeQueryRamData(m_nSequence++, m_strPara, m_oStrArray);
			break;
		case CLCmdCode.TECMD_LOGIN:
			m_oProlData.ComposeLoginCmd(m_nSequence++, m_strPara, m_strPara2);
			break;
		case CLCmdCode.TECMD_REGISTER_USER:
			m_oProlData.ComposeRegisterUserCmd( m_nSequence, 
												m_oUserInfo.getUserName(), 
												m_oUserInfo.getUserPsd(),
												m_oUserInfo.getFName(),
												m_oUserInfo.getLName(),
												m_oUserInfo.getCoName(),
												m_oUserInfo.getTelNum(),
												m_oUserInfo.getAddr(),
												m_oUserInfo.getEmail(),
												m_oUserInfo.getRemark(),
												m_oUserInfo.getkey() );
			break;
		case CLCmdCode.TECMD_MODIFY_USER:
			m_oProlData.ComposeModifyUserCmd(   m_nSequence,  
												m_oUserInfo.getUserName(), 
												m_oUserInfo.getUserPsd(),
												m_oUserInfo.getFName(),
												m_oUserInfo.getLName(),
												m_oUserInfo.getCoName(),
												m_oUserInfo.getTelNum(),
												m_oUserInfo.getAddr(),
												m_oUserInfo.getEmail(),
												m_oUserInfo.getRemark() );
			break;
		case CLCmdCode.TECMD_ADD_VEHICLE:
			m_oProlData.ComposeAddCarCmd( m_nSequence, 
										  m_strPara, 
										  m_oCarInfo.GetDEUID(), 
										  m_oCarInfo.GetDESIM(), 
										  m_oCarInfo.GetCarLicense(), 
										  m_oCarInfo.GetTEType(), 
										  m_oCarInfo.GetFName(), 
										  m_oCarInfo.GetLName(), 
										  m_oCarInfo.GetOwnerTel(), 
										  m_oCarInfo.GetOwnerAddr(), 
										  m_oCarInfo.GetRemark() );
			break;
		case CLCmdCode.TECMD_MODIFY_VEHICLE:
			m_oProlData.ComposeModifyCarCmd( m_nSequence, 
											  m_strPara, 
											  m_oCarInfo.GetDEUID(), 
											  m_oCarInfo.GetDESIM(), 
											  m_oCarInfo.GetCarLicense(), 
											  m_oCarInfo.GetTEType(), 
											  m_oCarInfo.GetFName(), 
											  m_oCarInfo.GetLName(), 
											  m_oCarInfo.GetOwnerTel(), 
											  m_oCarInfo.GetOwnerAddr(), 
											  m_oCarInfo.GetRemark() );
			break;
		case CLCmdCode.TECMD_DEL_VEHICLE:
			m_oProlData.ComposeDeleteCarCmd(m_nSequence, m_strPara, m_oStrArray );
			break;
		case CLCmdCode.TECMD_QUERY_ALARM:
			m_oProlData.ComposeQueryAlarmDataCmd( m_nSequence, 
												m_strPara, 
												m_nPara, 
												m_nPara2, 
												(byte)(m_nPara3&0xFF), 
												m_oStrArray );
			break;
		}
		return m_oProlData.GetTLSendData();
	}
}