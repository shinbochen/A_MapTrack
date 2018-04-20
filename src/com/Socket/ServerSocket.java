package com.Socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.BindException;
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.util.Log;

import com.Data.GlobalData;
import com.Data.ProgramType;
import com.Protocol.AlarmData;
import com.Protocol.CLProtocolData;
import com.Protocol.CarInfo;
import com.Protocol.ComposeData;
import com.Protocol.Data;
import com.Protocol.DeviceSetup;
import com.Protocol.GPSData;
import com.Protocol.GroupInfo;
import com.Protocol.GroupManage;
import com.Protocol.SubUserManage;
import com.Protocol.UserInfo;


//**************************************************
//	  ������ socket �����߳�
//
public class ServerSocket implements Runnable{

	public Socket 				oSocket = null;
	public InputStream 			in = null;
	public OutputStream			out = null;
	public boolean				bGPSData = false;
	public boolean			    bQueryAlarm = false;
	public boolean				bPlayCmd = false;
	public static boolean		m_bOperationExit = true;
	

	//*********************************************
	//
	public void closeSocket(){			
		
		try {
			in.close();
			in = null;
			out.close();
			out = null;
			oSocket.close();
			oSocket = null;
		} 
		catch (IOException e){
			
			e.printStackTrace();
			Log.e("CloseSocket", "�ر��쳣:"+e.getMessage() );
		}
	}
	//*********************************************
	//
	public boolean  connectServer( String  strIP,  int   nPort ){
		
		boolean   		bConnect = false;
		try{				
			oSocket = new Socket();
			SocketAddress socketAddress = new InetSocketAddress(strIP, nPort);			
			oSocket.connect( socketAddress , 10000 );
			//oSocket = new Socket( strIP, nPort );
			if( oSocket.isConnected() ){
								
				bConnect = true;
				in = (InputStream)oSocket.getInputStream();
	    		out = (OutputStream)oSocket.getOutputStream();
	    		GlobalData.addRecvProgramType( ProgramType.LOGIN_CONNECT_SERVER );
			}
		}
		catch( ConnectException e ){
			e.printStackTrace();
			Log.e("Socket Connect",  "Socket���ӳ�ʱ:"+ e.getMessage() );
		}
    	catch( BindException e ){
    		e.printStackTrace();
    		Log.e("Socket Connect", "�˿ڼ���:"+e.getMessage() );
    	}
		catch (UnknownHostException e){
			
			e.printStackTrace();
			Log.e("Socket Connect",  "UnknownHost:"+ e.getMessage() );			
		} 
		catch (IOException e) {
			e.printStackTrace();
			Log.e("Socket Connect",  "IOException:"+ e.getMessage() );			
		}
		return bConnect;
	}
	//*********************************************
	//  Socket��������
	public void  sendServerData( Data inData ){
		
		try {
			if( oSocket.isOutputShutdown() == false ){
				out.write( inData.GetDataBuf() );
				Log.e("sendServerData", "sned");
			}
			else{
				Log.e("sendServerData", "error");
			}
		}
    	catch( SocketException e ){		    		
    		e.printStackTrace();
    		Log.e("sendServerData", "�Է�SOCKET���Ͽ�,���쳣�˳�!"+e.getMessage() );
    	}
		catch (IOException e) {
			e.printStackTrace();
			Log.e("sendServerData", "Socket�Ͽ�"+e.getMessage() );
		} 
	}
	//*********************************************
	//  Socket��������
	public void  recvServerData(){
		
		byte[]		  	nRecvBuf = null;
		byte[]			nOutBuf = null;
		boolean			bRunCode = false;
		boolean			bLoadData = false;	//�������ݱ��
		int			  	nRecvLen = 0;
		int			  	nOffset = 0;
		int			  	nLen = 0;
		long		  	nEnterTime = 0; 
		ComposeData		oComposeData = null;
    	CLProtocolData 	oProtocolData = null;
		
		oComposeData =  new ComposeData();
		oProtocolData = new CLProtocolData();
		nEnterTime = System.currentTimeMillis();						
		while( true ){
			
			if( (oSocket.isConnected() == false) && oSocket.isInputShutdown() ){
				return ;
			}
			try {
				nLen = in.available();					
				if( nLen > 0 ){		
					nRecvBuf = new byte[nLen+1];
					nRecvLen = in.read(nRecvBuf, nOffset, nLen );
					nRecvBuf[nRecvLen] = 0x00;
					oComposeData.AddRecvData(nRecvBuf, nRecvLen);
					// �ȴ����ݴ���
					while( true ){
						nOutBuf = oComposeData.GetOutData();						
						if( nOutBuf == null ){
							break;
						}
						Log.e("TAG_out",nOutBuf.length+" "+new  String(nOutBuf) );
						// ���ͽ�������
    		    		bRunCode  = false;
    		    		if( oProtocolData.ParseALRecvData(nOutBuf, nOutBuf.length) ){
    		    			while( true ){
    		    				if ( oProtocolData.GetCLRecvDataEx( ) == false ){
	    		    				if ( bRunCode == true ){
	    								break;
	    							}
	    		    			}
    		    				bRunCode = true;
    		    				if ( oProtocolData.IsLoginCmd() ){
    		    					if( oProtocolData.IsAckOK() ){
    		    						if( bLoadData == false ){
    		    							bLoadData = true;
    		    							GlobalData.addRecvProgramType( ProgramType.LOGIN_LOAD_DATA );
    		    						}
    		    						getLoginInfo(oProtocolData);
    		    						if( oProtocolData.GetRecvSumPackages() == 
		    								oProtocolData.GetRecvCurPackages() ){	
    		    							Log.e("Login", "��½�ɹ�");
    		    							GlobalData.addRecvProgramType( ProgramType.LOGIN_SUCCEED );
    		    							return;
    		    						}
    		    					}
    		    					else{
    		    						GlobalData.addRecvProgramType( ProgramType.LOGIN_FAIL );
    		    						Log.e("Login", "��½ʧ��");
    		    						return;
    		    					}
    		    				}
    		    				if ( oProtocolData.IsRegisterUserCmd() ){	//ע���û�					
    		    					if ( oProtocolData.IsAckOK() )	{
    		    						GlobalData.addRecvProgramType( ProgramType.REGISTER_USER_SUCCEED );
    		    						return;
    		    					}
    		    					else{						
    		    						GlobalData.addRecvProgramType( ProgramType.REGISTER_USER_FAIL );
    		    						return;
    		    					}					
    		    				}
    		    				if( oProtocolData.IsModifyUserCmd() ){
    		    					if ( oProtocolData.IsAckOK() ){
    		    						Log.e("�޸��û�", "�ɹ�!");
    		    						GlobalData.addRecvProgramType( ProgramType.MODIFY_USER_SUCCEED );
    		    						return;
			    					}
			    					else{						
			    						Log.e("�޸��û�", "ʧ��!");
			    						GlobalData.addRecvProgramType( ProgramType.MODIFY_USER_FAIL );
			    						return;
			    					}    		    					
    		    				}
    		    				if( oProtocolData.IsAddCarCmd() ){
    		    					if ( oProtocolData.IsAckOK() ){
    		    						GlobalData.addRecvProgramType( ProgramType.ADD_VEHICLE_SUCCEED );
    		    						return;
			    					}
			    					else{						
			    						GlobalData.addRecvProgramType( ProgramType.ADD_VEHICLE_FAIL );
			    						return;
			    					}   
    		    				}
    		    				if( oProtocolData.IsModifyCarCmd() ){
    		    					if ( oProtocolData.IsAckOK() ){
    		    						GlobalData.addRecvProgramType( ProgramType.MODIFY_VEHICLE_SUCCEED );
    		    						return;
			    					}
			    					else{						
			    						GlobalData.addRecvProgramType( ProgramType.MODIFY_VEHICLE_FAIL );
			    						return;
			    					}   
    		    				}
    		    				if( oProtocolData.IsDelCarCmd() ){
    		    					if ( oProtocolData.IsAckOK() ){
    		    						GlobalData.addRecvProgramType( ProgramType.DEL_VEHICLE_SUCCEED );
    		    						return;
			    					}
			    					else{						
			    						GlobalData.addRecvProgramType( ProgramType.DEL_VEHICLE_FAIL );
			    						return;
			    					}   
    		    				}
    		    				if( oProtocolData.IsQueryGPSDataCmd() ){
    		    					if( oProtocolData.IsAckOK() ){
    		    						if( getResultGPSData( oProtocolData ) ){
    		    							return ;
    		    						}    		    							
    		    					}
    		    					else{
    		    						if( bPlayCmd == true ){
    		    							Log.e("��ѯ����", "û�в鵽�ط�����!");
    		    						}
    		    						else{
	    		    						GlobalData.addRecvProgramType( ProgramType.RECV_NO_GPSDATA );
	    		    						Log.e("��ѯ����", "û��GPS����!");
    		    						}
    		    						return ;
    		    					}
    		    				}
    		    				if( oProtocolData.IsQueryRamDataCmd() ){
    		    					
    		    					if( oProtocolData.IsAckOK() ){
    		    						if( getRamGPSData(oProtocolData)){
    		    							return ;
    		    						}
    		    					}
    		    					else{
    		    						return ;
    		    					}
    		    				}
    		    				if( oProtocolData.IsQueryAlarmDataCmd() ){
    		    					if( oProtocolData.IsAckOK() ){
    		    						
    		    						if( getAlarmDataResult( oProtocolData ) ){
    		    							return ;
    		    						}    		    							
    		    					}
    		    					else{
    		    						GlobalData.addRecvProgramType( ProgramType.RECV_NO_QUERYALARM );
    		    						return ;
    		    					}
    		    				}
    		    			}
    		    		}
					}
				}
			} catch (IOException e) {					
				e.printStackTrace();
	    		Log.e("recvServerData","���������쳣:" + e.getMessage() );
	    		break;
			}
			// �ȴ��ʱ��Ϊ��ʮ���˳�
			if( (System.currentTimeMillis() - nEnterTime) > 1000 * 50 ){
				
				if( bPlayCmd == true ){  //���ػط����ݳ�ʱ
					GlobalData.addRecvProgramType( ProgramType.RECV_LOAD_PLAYATA_TIMEOUT );
				}
				Log.e("Socket", "��ʱ�˳�");
				break;
			}
		}
	}
	//*********************************************
	//	��ȡGPS���صĽ��
	public boolean getAlarmDataResult( CLProtocolData 	oProtocolData  ){
		
		boolean				bResult = false;
		List<AlarmData>	    lstAlarmData = new ArrayList<AlarmData>();
		
		lstAlarmData.clear();
		if( oProtocolData.GetAlarmDataResult(lstAlarmData) ){
			
			bQueryAlarm = true;
			GlobalData.addQAlarmData(lstAlarmData);
		}

		if( oProtocolData.GetRecvSumPackages() == 
			oProtocolData.GetRecvCurPackages() ){  			
			if( bQueryAlarm ){
				GlobalData.addRecvProgramType( ProgramType.RECV_QUERYALARM );				
			}
			else{
				GlobalData.addRecvProgramType( ProgramType.RECV_NO_QUERYALARM );
			}
			bResult = true;
		}    
		return bResult;
	}
	//*********************************************
	//	��ȡGPS���صĽ��
	public boolean getResultGPSData( CLProtocolData 	oProtocolData  ){
		
		boolean					bResult = false;
		List<GPSData>			lstGPSData = new ArrayList<GPSData>();
		
		lstGPSData.clear();
		if( oProtocolData.GetGpsDataResult(lstGPSData) ){		
			
			
			if( bPlayCmd == true ){
				GlobalData.AddPlayGPSData(lstGPSData);
				if( bGPSData == false ){
					GlobalData.addRecvProgramType( ProgramType.RECV_LOAD_PLAYGPSDATA );
				}
			}
			else{
				GlobalData.AddGPSData( lstGPSData );
			}
			bGPSData = true;
		}
		if( oProtocolData.GetRecvSumPackages() == 
			oProtocolData.GetRecvCurPackages() ){  			
			if( bGPSData ){
				if( bPlayCmd == true ){
					Log.e("PLAY", "����Play���");
					GlobalData.addRecvProgramType( ProgramType.RECV_PLAYGPSDATA );
				}
				else{
					GlobalData.addRecvProgramType( ProgramType.RECV_GPSDATA );
				}
			}
			else{
				if( bPlayCmd == true ){
					Log.e("PLAY", "û��Play����");
					GlobalData.addRecvProgramType( ProgramType.RECV_NO_PLAYGPSDATA );
				}
				else{
					GlobalData.addRecvProgramType( ProgramType.RECV_NO_GPSDATA );
				}
			}
			bResult = true;
		}    
		return bResult;
	}
	//***********************************************
	//
	public  boolean getRamGPSData( CLProtocolData 	oProtocolData ){
		
		boolean					bResult = false;
		List<GPSData>			lstGPSData = new ArrayList<GPSData>();
				
		lstGPSData.clear();
		if( oProtocolData.GetGpsDataResult(lstGPSData) ){			
			
			bGPSData = true;
			GlobalData.AddGPSData( lstGPSData );
		}
		if( oProtocolData.GetRecvSumPackages() == 
			oProtocolData.GetRecvCurPackages() ){
			if( bGPSData ){
				GlobalData.addRecvProgramType( ProgramType.RECV_GPSDATA );
			}
			bResult = true;
		}    
		return bResult;
	}
	//*********************************************
	//  ��ȡ��½��Ϣ
	public void getLoginInfo( CLProtocolData 	oProtocolData ){
		
		List<UserInfo>  		lstUser = new ArrayList<UserInfo>();			
		List<GroupInfo> 		lstGroupInfo = new ArrayList<GroupInfo>();
		List<CarInfo> 			lstCarInfo = new ArrayList<CarInfo>();
		List<SubUserManage>  	lstSubUserManage = new ArrayList<SubUserManage>();
		List<GroupManage>    	lstGroupManage = new ArrayList<GroupManage>();
		List<DeviceSetup>    	lstDeviceSetup = new ArrayList<DeviceSetup>();
								
		
		// �û���Ϣ
		if( oProtocolData.GetUserInfoResult( lstUser ) ){				
			UserInfo   				oUserInfo = null;
			Iterator<UserInfo>		it = null;
			
			it = lstUser.iterator();
			while( it.hasNext() ){					
				oUserInfo = it.next();
				GlobalData.setUserInfo(oUserInfo);
			}
			lstUser.clear();
		}
		// Android���û���Ϣ��������
		if( oProtocolData.GetSubUserInfoResult(lstUser ) ){
			lstUser.clear();
		}
		//  ����Ϣ��������
		if( oProtocolData.GetGroupInfoResult( lstGroupInfo )){
			lstGroupInfo.clear();
		}
		//  ������Ϣ
		if( oProtocolData.GetCarInfoResult( lstCarInfo ) ){				
			GlobalData.AddCarInfo( lstCarInfo );
			lstCarInfo.clear();
		}
		//  ���û���������
		if( oProtocolData.GetSubUserCarManageResult(lstSubUserManage) ){				
			lstSubUserManage.clear();
		}
		//  �����
		if( oProtocolData.GetGroupManageResult(lstGroupManage) ){
			lstGroupManage.clear();
		}
		//  ������������
		if( oProtocolData.GetDeviceSetupResult( lstDeviceSetup ) ){
			GlobalData.setDeviceSetup( lstDeviceSetup );
			lstDeviceSetup.clear();
		}
	}
	//*********************************************
	//
	public static  void  setOperationExit( boolean  bFlag ){
		
		m_bOperationExit = bFlag;
	}
	//*********************************************
	//
	public static  boolean  isOperationExit(){
		return  m_bOperationExit;
	}
	//*********************************************
	//
	public void run(){
		
		Data		oData = null;		
		
		setOperationExit( false );
		//**********************************************
		// �������Ӳ��Ϸ�����,������ܶ�SOCKET�߳�
		// ������ǰȡ������
		oData = GlobalData.getSendServerData();
		if( oData == null ){
			setOperationExit( true );
			return ;
		}
		if( GlobalData.isQueryPlayFlag() == true ){
			bPlayCmd = true;
			GlobalData.clearQueryPlayFlag();
		}
		// ���ӷ�����
		if( connectServer( GlobalData.m_strIP, GlobalData.m_nPort) == false ){
			// �¼�����֪ͨLogin����
			if( GlobalData.isLongSucceed() == false ){
				GlobalData.addRecvProgramType( ProgramType.LOGIN_NETWORK_FAULT );
			}
			setOperationExit( true );
			return ;
		}
		// ��ʼ��������
		while( oData != null ){
			sendServerData( oData );
			oData = GlobalData.getSendServerData();
			// �ǿ���ָ��,�������շ���
			if( GlobalData.isControlTECmd() ){
				
				Log.e("IsControl", "Continue");
				continue;
			}
			else{
				recvServerData();
			}
		}		
		closeSocket();	
		setOperationExit( true );
	}
 }
