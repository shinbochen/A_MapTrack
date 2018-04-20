package com.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import android.content.Context;
import android.util.Log;

import com.Language.Language;
import com.Protocol.AlarmData;
import com.Protocol.AppData;
import com.Protocol.CarInfo;
import com.Protocol.DESProtocolData;
import com.Protocol.Data;
import com.Protocol.DeviceSetup;
import com.Protocol.GPSData;
import com.Protocol.MemoryGPSData;
import com.Protocol.PlayHistoryData;
import com.Protocol.UserInfo;
import com.Protocol.VehicleManage;
import com.database.DBManager;
import com.AMaptrack.LogInfo;
import com.AMaptrack.MapVehicle;
import com.smsmanager.GSMPDU;
import com.smsmanager.SMSData;


//***************************************************
//
public class GlobalData {
	//智利IP地址: 190.82.104.250 数据库密码：iufile2011
	//自己IP地址: 203.86.9.28
	//小张客户     : gps.geekpatrol.cc
	//小张客户    : 190.142.152.115
	//小张客户    : monitoreo.itksoluciones.com
	//TOM地址     : 211.154.149.98 
	//AMT波兰     : 79.190.216.118
	//SUKE	   : 203.86.8.180
	//小张客户    :121.37.43.51
	//小张客户   : 121.37.59.90

	public static String				m_strIP = "121.37.59.90";
	public static int					m_nPort = 9990;
	public static int					m_sCreateMenu = 0;
	public static boolean				m_bLongSucceed = false;
	public static VehicleManage			m_sVehiclemanage = null;
	public static MemoryGPSData			m_sMemoryGPSData = null;
	public static PlayHistoryData		m_sPlayHistoryData = null;
	public static UserInfo				m_sUserInfo = null;
	public static List<ServerCommand>	m_sLstServerCmd = null;			//报表查询标志
	public static List<AlarmData>		m_sLstQAlarmData = null;
	public static List<Integer>			m_sLstLogType = null;     		//登陆定时检测
	public static List<Integer>			m_sLstchkAlarmReport = null;	//报警统计检测
	public static List<Integer>			m_sLstchkLogInfo = null; 	    //报警信息检测
	public static List<Integer>			m_sLstchkMonitor = null;   	    //车辆列表检测
	public static List<Integer>			m_sLstchkMap = null;			//地图检测
	public static List<Integer>			m_sLstchkUserInfo = null;		//增加用户窗口
		
	public static List<MapVehicle>		m_lstShowOrDelMap = null;
	public static List<LogInfo>			m_lstOperating; 				//操作指令标志
	public static boolean				m_bContrelTECmd;				//控制命令
	public static boolean				m_bQueryPlayFlag = false;		//查询回放数据
	public static  List<SMSData>  		m_lstRecvSMSData = null;
	public static DBManager				m_sDBManager = null;			//数据库的管理类 
	public static List<CmdTimingCheck>  m_lstCmdTimingCheck = null;
	
	public static  int 					m_nTabTitleHeight = 0;
	public static  int					m_nTaHostHeight = 0;
	
	public  static  final   int	   		MAX_SMS_LEN = 160;
	
	// 不创建菜单
	public static final int				CREATE_MENU_NO = 0x00;
	// 报警记录菜单
	public static final int				CREATE_MENU_ALARM_INFO = 0x01;
	// 地图菜单
	public static final int				CREATE_MENU_MAP_WINDOW = 0x02;
	// 监控菜单
	public static final int				CREATE_MENU_MONITOR_OBJECT = 0x03;
	// 报警统计菜单
	public static final int				CREATE_MENU_ALARM_REPORT = 0x04;

	//****************************************************************
	//
	public static void init( Context  oContext ){
		
		if( m_sVehiclemanage == null ){
			m_sVehiclemanage = new VehicleManage();
		}
		if( m_sMemoryGPSData == null ){
			m_sMemoryGPSData = new MemoryGPSData();
		}
		if( m_sPlayHistoryData == null ){
			m_sPlayHistoryData = new PlayHistoryData();
		}
		if( m_sUserInfo == null ){
			m_sUserInfo = new UserInfo();
		}
		if( m_sLstServerCmd == null ){			
			m_sLstServerCmd = new ArrayList<ServerCommand>();
			m_sLstServerCmd.clear();
		}
		if( m_sLstQAlarmData == null ){
			m_sLstQAlarmData = new ArrayList<AlarmData>();
			m_sLstQAlarmData.clear();
		}
		if( m_sLstLogType == null ){
			m_sLstLogType = new ArrayList<Integer>();
			m_sLstLogType.clear();
		}
		if( m_sLstchkAlarmReport == null ){
			m_sLstchkAlarmReport = new ArrayList<Integer>();
			m_sLstchkAlarmReport.clear();
		}
		if( m_sLstchkLogInfo == null ){
			m_sLstchkLogInfo = new ArrayList<Integer>();
			m_sLstchkLogInfo.clear();
		}
		if( m_sLstchkMonitor == null ){
			m_sLstchkMonitor = new ArrayList<Integer>();
			m_sLstchkMonitor.clear();
		}	
		if( m_sLstchkMap == null ){
			m_sLstchkMap = new ArrayList<Integer>();
			m_sLstchkMap.clear();
		}
		if( m_sLstchkUserInfo == null ){
			m_sLstchkUserInfo = new ArrayList<Integer>();
			m_sLstchkUserInfo.clear();
		}
		if( m_lstShowOrDelMap == null ){
			m_lstShowOrDelMap =  new ArrayList<MapVehicle>();
			m_lstShowOrDelMap.clear();
		}		
		if( m_lstOperating == null ){
			m_lstOperating = new ArrayList<LogInfo>();
			m_lstOperating.clear();
		}		
		if( m_lstRecvSMSData == null ){
			m_lstRecvSMSData = new ArrayList<SMSData>();
			m_lstRecvSMSData.clear();
		}
		if( m_sDBManager == null ){
			m_sDBManager = new DBManager( oContext );
			m_sDBManager.open();
		}
		if( m_lstCmdTimingCheck == null ){			
			m_lstCmdTimingCheck = new ArrayList<CmdTimingCheck>();
			m_lstCmdTimingCheck.clear();
		}
	}
	public   static void  setCmdTimingCheck( String  strDEUID ){
		
		CmdTimingCheck  oCmdTimingCheck = new CmdTimingCheck();
		
		oCmdTimingCheck.setDEUID(strDEUID);
		m_lstCmdTimingCheck.add(oCmdTimingCheck);
	}
	public  static ArrayList<String> getCmdTimingCheck(){
		
		CmdTimingCheck			oCmdTimingCheck = null;
		ArrayList<String>		lstArray = new ArrayList<String>();
		
		for( int nCnt = 0; nCnt < m_lstCmdTimingCheck.size(); nCnt++ ){
			
			oCmdTimingCheck = (CmdTimingCheck)m_lstCmdTimingCheck.get(nCnt);
			if( oCmdTimingCheck.isQueryDEUIDData() == true ){
				lstArray.add( oCmdTimingCheck.getDEUID() );
			}
			else{
				m_lstCmdTimingCheck.remove(oCmdTimingCheck);
			}
		}
		return  lstArray;
	}
	public  static  void  delCmdTimingCheck( GPSData   oGPSData ){
		
		CmdTimingCheck			oCmdTimingCheck = null;
		
		if( oGPSData.getCodeState() > 0 ){

			for( int nCnt = 0; nCnt < m_lstCmdTimingCheck.size(); nCnt++ ){
				
				oCmdTimingCheck = (CmdTimingCheck)m_lstCmdTimingCheck.get(nCnt);
				if( oCmdTimingCheck.getDEUID().equals(oGPSData.getDEUID())  ){
					
					Log.e("控制命令", "删除成功!");
					m_lstCmdTimingCheck.remove(oCmdTimingCheck);
				}
			}
		}
	}
	public  static void setTabHostHeight( int nTaHostHeight){
		m_nTaHostHeight = nTaHostHeight;
	}
	public  static int  getTabHostHeight(){
		return  m_nTaHostHeight;
	}
	public  static void setTabTitleHeight( int nTabTitleHeight){
		
		m_nTabTitleHeight = nTabTitleHeight;
	}
	public  static int  getTabTitleHeight(){
		
		return  m_nTabTitleHeight;
	}
	public static void  setOperating( String  strDEUID,  int  nCmdType ){

		m_lstOperating.add( new LogInfo( strDEUID, nCmdType ) );
		addRecvProgramType( ProgramType.LOG_INFO );
	}
	public  static LogInfo getOperation(){
		LogInfo		oLog = null;
		
		if( m_lstOperating.size() > 0 ){
			oLog = m_lstOperating.get(0);
			m_lstOperating.remove(0);
		}
		return  oLog;
	}
	public static void  setIPPort( String  strIP,  int  nPort ){
		
		m_strIP = strIP;
		m_nPort = nPort;
	}
	public static void  setLongSucceed( boolean  bSucceed ){
		m_bLongSucceed = bSucceed;
	}
	public static boolean isLongSucceed(){
		return m_bLongSucceed;
	}
	public static  boolean isQueryPlayFlag(){
		return m_bQueryPlayFlag; 
	}
	public static void    clearQueryPlayFlag(){
		m_bQueryPlayFlag = false;
	}
	//****************************************************
	//
	public static void   FreeMemory(){

		m_sVehiclemanage.delAllVehicle();
		m_sMemoryGPSData.RecmoveAllGPSData();
		m_sPlayHistoryData.freeMemory();
		m_sLstServerCmd.clear();
		m_sLstQAlarmData.clear();
		m_sLstLogType.clear();
		m_sLstchkAlarmReport.clear();
		m_sLstchkLogInfo.clear();
		m_sLstchkMonitor.clear();
		m_lstShowOrDelMap.clear();
		m_sDBManager.close();
	}
	//****************************************************
	//
	public static void    setCreateMenu( int  nMenuMode ){
		
		m_sCreateMenu = nMenuMode;
	}
	//****************************************************
	//
	public static int    getCreateMenu( ){
	
		return m_sCreateMenu;
	}
	//****************************************************
	//  增加车辆数据
	public static  void     AddCarInfo( List<CarInfo>  inData ){
		
		CarInfo   					oCarInfo = null;
		Iterator<CarInfo>			it = null;
		
		if( inData == null ){
			return ;
		}
		it = inData.iterator();
		while( it.hasNext() ){			
			oCarInfo = it.next();
			m_sVehiclemanage.SetVehicleData(oCarInfo.GetDEUID(), oCarInfo );			
		}		
	}
	//****************************************************
	//  增加车辆数据
	public static  void     AddCarInfo( CarInfo  inData ){
		
		m_sVehiclemanage.SetVehicleData(inData.GetDEUID(), inData );	
	}
	//****************************************************
	//
	public static  ArrayList<CarInfo>  getAllVehicle( ){
		
		return  m_sVehiclemanage.getAllVehicle();
	}
	//****************************************************
	// DEUID查找车辆信息
	public static  CarInfo  findDEUIDByCarInfo( String  strDEUID ){
		
		return m_sVehiclemanage.GetVehicleData(strDEUID);
	}	
	//************************************************
	//
	public static CarInfo  findDESIMByCarInfo( String  strPhone ){
		
		CarInfo				oCarInfo = null;
		List<CarInfo>		lstCarInfo = null;
		
		lstCarInfo =	getAllVehicle();
		for( int nCnt = 0; nCnt < lstCarInfo.size(); nCnt++ ){
			
			oCarInfo = lstCarInfo.get(nCnt);
			if( strcmp_back( strPhone, lstCarInfo.get(nCnt).GetDESIM(), 6 ) == true){
				oCarInfo = lstCarInfo.get(nCnt);
				break;
			}
		}
		return oCarInfo;
	}
	//****************************************************
	//  DEUID查找车辆信息
	public static  CarInfo findCarLicenseByCarInfo( String  strCarLicense ){
		
		return m_sVehiclemanage.GetLicenseByCarInfo( strCarLicense );
	}
	//****************************************************
	//
	public static  void    DelCarInfo( String  strDEUID ){
		
		m_sVehiclemanage.RemoveVehicleData(strDEUID);
	}
	//****************************************************
	//
	public static  void    setDeviceSetup( List<DeviceSetup> inData ){
		
		DeviceSetup   					oDeviceSetup = null;
		Iterator<DeviceSetup>			it = null;
		
		if( inData == null ){
			return ;
		}
		it = inData.iterator();
		while( it.hasNext() ){			
			oDeviceSetup = it.next();
			m_sVehiclemanage.setDeviceSetup( oDeviceSetup.GetDEUID(), oDeviceSetup );			
		}	
	}
	//****************************************************
	//
	public static  DeviceSetup getDeviceSetup( String  strKey ){
		
		return m_sVehiclemanage.getDeviceSetup(strKey);
	}
	//****************************************************
	// 
	public static void   AddGPSData( GPSData  oGPSData ){
		
		m_sMemoryGPSData.SetGPSData(oGPSData.getDEUID(), oGPSData );
	}
	//****************************************************
	//  增加GPS PLAY数据
	public static void  AddPlayGPSData( List<GPSData> inData ){
		
		GPSData					oGPSData = null;
		Iterator<GPSData>		it = null;
		
		if( inData == null ){
			return ;
		}
		it = inData.iterator();
		while(it.hasNext() ){
			
			oGPSData = it.next();
			m_sPlayHistoryData.AddPlayGPSData( oGPSData );
		}
	}
	//****************************************************
	//  获取播放数据
	public  static  GPSData  getNextPlayData(){
		
		return  m_sPlayHistoryData.Next();
	}
	//****************************************************
	//  清除播放数据
	public  static  void  clearPlayGPSData(){
		m_sPlayHistoryData.freeMemory();
	}
	//****************************************************
	// 
	public static void  AddGPSData( List<GPSData>  inData ){
		
		GPSData					oGPSData = null;
		Iterator<GPSData>		it = null;
		
		if( inData == null ){
			return ;
		}
		it = inData.iterator();
		while(it.hasNext() ){
			
			oGPSData = it.next();
			m_sMemoryGPSData.SetGPSData(oGPSData.getDEUID(), oGPSData );
		}
	}
	//****************************************************
	//
	public static ArrayList<GPSData>	findNewGPSData(){
		
		return m_sMemoryGPSData.getNewGPSData();
	}
	//****************************************************
	//
	public static ArrayList<GPSData>   findNewAlarmGPSData(){
		
		return m_sMemoryGPSData.getNewAlarmGPSData();
	}
	//****************************************************
	//
	public static ArrayList<GPSData>	findNewControlGPSData(){
		
		return m_sMemoryGPSData.getNewControlGPSData();
	}
	//****************************************************
	//
	public static ArrayList<GPSData>	getAllDataAddr(){
		
		return m_sMemoryGPSData.getAllGPSData();
	}	
	//*****************************************************
	//  获取GPS数据
	public static  GPSData  getRamDBGPSData( String  strDEUID ){
		
		GPSData			oGPSData = null;
		
		oGPSData = GlobalData.findDEUIDByGPSData(strDEUID);
		if( oGPSData == null ){
			if( GlobalData.findDEUIDByGPSDataEx(strDEUID)  ){	
				
				oGPSData = GlobalData.findDEUIDByGPSData(strDEUID);
			}
		}	
		return  oGPSData;
	}
	
	//****************************************************
	//  查询GPS数据
	public static GPSData  findDEUIDByGPSData( String  strDEUID ){
	
		return m_sMemoryGPSData.GetGPSData( strDEUID );			
	}
	//****************************************************
	//
	public  static boolean  findDEUIDByGPSDataEx( String strDEUID ){
		
		boolean			bResult = false;
		GPSData			oGPSData = null;
		List<GPSData>	lstGPS = null;		
		
		// 查询最后位置
		lstGPS = m_sDBManager.queryGPSData(strDEUID, 1 );
		if( lstGPS.size() > 0 ){
			oGPSData = lstGPS.get(0);
			AddGPSData( oGPSData );
			addRecvProgramType( ProgramType.RECV_GPSDATA );
			bResult = true;
		}
		return bResult;	
	}
	//****************************************************
	//
	public  static void setUserInfo( UserInfo  oUserInfo){
				
		m_sUserInfo = oUserInfo;		
	}
	//****************************************************
	//
	public  static  UserInfo getUserInfo( ){
		
		return m_sUserInfo;
	}
	//****************************************************
	//
	public static  void  addSendServerData( ServerCommand  obj ){
		
		if( obj != null ){
			m_sLstServerCmd.add(obj);
		}
	}
	//****************************************************
	//
	public  static SMSData  getSendSMSData(){
		
		Data				oData = null;
		SMSData				oSMSData = null;
		ServerCommand		oSerCmd = null;

		Iterator<ServerCommand>  it = m_sLstServerCmd.iterator();
		if( it.hasNext() ){
			
			oSerCmd = it.next();
			oData = oSerCmd.getSendSMSCmdData();
			oSMSData = new SMSData();
			oSMSData.setPhone( oSerCmd.getDESIM() );
			oSMSData.setSMSData(oData.GetDataBuf(), 0, oData.GetDataLen() );
			setControlTECmd( oSerCmd.getCmdType() );
			m_sLstServerCmd.remove(oSerCmd);
		}		
		return oSMSData;
	}
	//****************************************************
	//
	public  static  Data getSendServerData(){
		
		Data				oData = null;
		ServerCommand		oSerCmd = null;
		
		Iterator<ServerCommand>  it = m_sLstServerCmd.iterator();
		if( it.hasNext() ){
			oSerCmd = it.next();
			oData = oSerCmd.getSendCmdData();	
			setControlTECmd( oSerCmd.getCmdType() );
			m_sLstServerCmd.remove(oSerCmd);
		}		
		return oData;
	}
	//****************************************************
	//
	public static void  setControlTECmd( int nCmdType ){
		
		switch( nCmdType ){
		case  CLCmdCode.TECMD_TRACKONCE:
		case  CLCmdCode.TECMD_CAR_LISTEN:
		case  CLCmdCode.TECMD_CAR_TALK:
		case  CLCmdCode.TECMD_STOP_ENGINE:
		case  CLCmdCode.TECMD_RESUME_ENGINE:	
		case  CLCmdCode.TECMD_LOCK:
		case  CLCmdCode.TECMD_UNLOCK:
			m_bContrelTECmd = true;
		break;
		case  CLCmdCode.TECMD_QUERY_PLAYDATA:
			m_bQueryPlayFlag = true;
			m_bContrelTECmd = false;
			break;
		default:
			m_bContrelTECmd = false;
			break;
		}
	}
	//****************************************************
	//
	public  static  boolean  isControlTECmd( ){
				
		return  m_bContrelTECmd;
	}
	//****************************************************
	//
	public  static boolean  isSendServerData(){
		
		if( m_sLstServerCmd.size() > 0 ){
			return true;
		}
		else{
			return false;
		}
	}
	//****************************************************
	//
	public static  void  addQAlarmData( List<AlarmData>  inData ){
		
		AlarmData					oData = null;
		Iterator<AlarmData>			it = null;
		
		if( inData == null ){
			return ;
		}
		it = inData.iterator();
		while(it.hasNext() ){			
			oData = it.next();
			m_sLstQAlarmData.add(oData);
		}
	}//****************************************************
	//
	public static  List<AlarmData>  getQAlarmData( ){
		
		return	m_sLstQAlarmData;
	}
	//****************************************************
	//
	public static  void  addQAlarmData( AlarmData oData ){
		
		m_sLstQAlarmData.add(oData);
	}
	//****************************************************
	//
	public  static void  clearAlarmData(){
		
		m_sLstQAlarmData.clear();
	}
	//****************************************************
	//
	public static  void  addRecvProgramType( int  nCmd ){
		
		switch(nCmd){
		case  ProgramType.LOGIN_SUCCEED:			//登陆成功
			m_sLstLogType.add( nCmd );
			m_sLstchkMonitor.add(nCmd);
			m_sLstchkAlarmReport.add(nCmd);
			m_sLstchkMap.add(nCmd);
			setLongSucceed(true);
			break;
		case ProgramType.RECV_GPSDATA:
			m_sLstchkMonitor.add(nCmd);
			m_sLstchkMap.add(nCmd);
			m_sLstchkLogInfo.add(nCmd);
			break;
		case ProgramType.RECV_LOAD_PLAYGPSDATA:
		case ProgramType.RECV_PLAYGPSDATA:
		case ProgramType.RECV_NO_PLAYGPSDATA:
		case ProgramType.RECV_LOAD_PLAYATA_TIMEOUT:
			m_sLstchkMap.add(nCmd);
			break;
		case ProgramType.RECV_NO_GPSDATA:
		case ProgramType.ADD_VEHICLE_SUCCEED:
		case ProgramType.ADD_VEHICLE_FAIL:
		case ProgramType.MODIFY_VEHICLE_SUCCEED:
		case ProgramType.MODIFY_VEHICLE_FAIL:
		case ProgramType.DEL_VEHICLE_SUCCEED:
		case ProgramType.DEL_VEHICLE_FAIL:
			m_sLstchkMonitor.add(nCmd);
			break;
		case ProgramType.UPDATE_VEHICLE_LIST_ADDR:
			m_sLstchkMonitor.add(nCmd);
			m_sLstchkLogInfo.add(nCmd);
			break;
		case ProgramType.UPDATE_VEHICLE:
			m_sLstchkAlarmReport.add(nCmd);
			m_sLstchkMonitor.add(nCmd);
			break;
		case ProgramType.MODIFY_USER_SUCCEED:
		case ProgramType.MODIFY_USER_FAIL:
		case ProgramType.UPDATE_LOG_LIST_ADDR:
			m_sLstchkLogInfo.add( nCmd );
			break;
		case  ProgramType.GENERAL_TIMEOUT:		//登陆超时
			if( isLongSucceed() ){
				m_sLstchkUserInfo.add(nCmd);
			}
			else{
				m_sLstLogType.add( nCmd );
			}
			break;
		case  ProgramType.LOGIN_FAIL:			//登陆失败	
		case  ProgramType.LOGIN_NETWORK_FAULT:;	//网络故障
		case  ProgramType.LOGIN_LOAD_DATA:		//登陆加载数据
		case  ProgramType.LOGIN_CONNECT_SERVER:	//连接服务器成功
			m_sLstLogType.add( nCmd );
			break;
		case ProgramType.VEHICLE_SHOWMAP:
			m_sLstchkMap.add(nCmd);
			break;
		case ProgramType.REGISTER_USER_SUCCEED: 
		case ProgramType.REGISTER_USER_FAIL:
			m_sLstchkUserInfo.add(nCmd);
			break;
		case ProgramType.RECV_QUERYALARM:
		case ProgramType.RECV_NO_QUERYALARM:
		case ProgramType.UPDATE_ALARM_REPORT_LIST_ADDR:
			m_sLstchkAlarmReport.add(nCmd);
			break;
		case ProgramType.LOG_INFO:
		case ProgramType.UPDATE_LANGUAGE:
			m_sLstchkLogInfo.add(nCmd);
			break;
		}
	}
	//****************************************************
	//
	public static int  getRecvChkUserInfo(){
		int		nResult = 0;

		if( m_sLstchkUserInfo.size() > 0 ){
						
			nResult = m_sLstchkUserInfo.get(0);
			m_sLstchkUserInfo.remove(0);
		}
		return  nResult;
	}
	//****************************************************
	//
	public  static int  getRecvLoginType( ){
		
		int		nResult = 0;

		if( m_sLstLogType.size() > 0 ){
						
			nResult = m_sLstLogType.get(0);
			m_sLstLogType.remove(0);
		}
		return  nResult;
	}
	//****************************************************
	//
	public static int getRecvAlarmReport(  ){
		
		int		nResult = 0;
		
		if( m_sLstchkAlarmReport.size() > 0 ){
						
			nResult = m_sLstchkAlarmReport.get(0);
			m_sLstchkAlarmReport.remove(0);
		}
		return  nResult;
	}
	//****************************************************
	//
	public static int getRecvLogInfo(){
		
		int		nResult = 0;
		
		if( m_sLstchkLogInfo.size() > 0 ){
						
			nResult = m_sLstchkLogInfo.get(0);
			m_sLstchkLogInfo.remove(0);
		}
		return  nResult;
	}
	//****************************************************
	//
	public static  int   getRecvMonitor(  ){
		
		int		nResult = 0;
		
		if( m_sLstchkMonitor.size() > 0 ){
						
			nResult = m_sLstchkMonitor.get(0);
			m_sLstchkMonitor.remove(0);
		}
		return  nResult;
	}
	//****************************************************
	//
	public static int  getRecvAMap(){
		
		int		nResult = 0;
		
		if( m_sLstchkMap.size() > 0 ){
						
			nResult = m_sLstchkMap.get(0);
			m_sLstchkMap.remove(0);
		}
		return  nResult;
	}
	//****************************************************
	//
	public static void  addCarToMap( String  strDEUID,  boolean  bShow, boolean bCenterShow ){
		
		boolean		bFalg = false;
		MapVehicle 	obj = null;
		
		for( int nCnt = 0; nCnt < m_lstShowOrDelMap.size(); nCnt++ ){
			
			obj = m_lstShowOrDelMap.get(nCnt);
			if( obj.getDEUID().equals(strDEUID) ){
				
				bFalg = true;
				obj.setShow(bShow);
				obj.setCenterShow(bCenterShow);
				break;
			}
		}
		if( bFalg == false ){
			obj = new MapVehicle( strDEUID, bShow, bCenterShow );
			m_lstShowOrDelMap.add( obj );
		}
	}
	//****************************************************
	//
	public static void  delShowOrDelMapDEUID( MapVehicle obj ){
		m_lstShowOrDelMap.remove( obj );
	}
	//****************************************************
	//
	public static void  delAllMapDEUID( ){
		m_lstShowOrDelMap.clear();
	}
	//****************************************************
	//
	public static List<MapVehicle>  getAllShowMapDEUID(){
		
		return m_lstShowOrDelMap;
	}
	//****************************************************
	//
	public static List<MapVehicle>  getShowMapDEUID( ){
	
		MapVehicle			oMapVehicle = null;
		List<MapVehicle>	result = new ArrayList<MapVehicle>();
		
		result.clear();
		for( int  nCnt = 0; nCnt < m_lstShowOrDelMap.size(); nCnt++ ){
			
			oMapVehicle = m_lstShowOrDelMap.get(nCnt);
			if( oMapVehicle.isShow() ){
				result.add(oMapVehicle);
			}
		}		
		return result;
	}	
	//****************************************************
	//
	public static List<MapVehicle>  getDelMapDEUID(){
		
		MapVehicle			oMapVehicle = null;
		List<MapVehicle>	result = new ArrayList<MapVehicle>();
		
		result.clear();
		for( int  nCnt = 0; nCnt < m_lstShowOrDelMap.size(); nCnt++ ){
			
			oMapVehicle = m_lstShowOrDelMap.get(nCnt);
			if( oMapVehicle.isShow() == false){
				result.add(oMapVehicle);
				m_lstShowOrDelMap.remove(oMapVehicle);
			}
		}		
		return result;
	}
	//***************************************************
	public static  String  getOperaCmdToStr( int  nCmdType ){

		String 			strResult = "";
		
		switch( nCmdType ){
		case CLCmdCode.TECMD_TRACKONCE:
			strResult = Language.getLangStr( Language.TEXT_TRACK_ONCE );
			break;
		case CLCmdCode.TECMD_TRACKLAST:	
			strResult = Language.getLangStr( Language.TEXT_LAST_POSITION );
			break;	
		case CLCmdCode.TECMD_CAR_LISTEN:
			strResult = Language.getLangStr( Language.TEXT_LISTEN_IN );
			break;
		case CLCmdCode.TECMD_CAR_TALK:
			strResult = Language.getLangStr( Language.TEXT_TALK );
			break;
		case CLCmdCode.TECMD_STOP_ENGINE:
			strResult = Language.getLangStr( Language.TEXT_FUEL_OFF );
			break;
		case CLCmdCode.TECMD_RESUME_ENGINE:	
			strResult = Language.getLangStr( Language.TEXT_FUEL_ON );
			break;
		case CLCmdCode.TECMD_LOCK:
			strResult = Language.getLangStr( Language.TEXT_LOCK );
			break;
		case CLCmdCode.TECMD_UNLOCK:
			strResult = Language.getLangStr( Language.TEXT_UNLOCK );
			break;
		case CLCmdCode.TECMD_NOGPS:
			strResult = Language.getLangStr( Language.TEXT_WARING_NO_DATA );
			break;
		default:
			break;
		}
		return strResult;
	}
	//***********************************************
	//
	public static boolean  isUserInfo( ){
		
		boolean 	bResult = false;
		UserInfo    oUserInfo = null;
		
		oUserInfo = new UserInfo();
		if( m_sDBManager.getUserInfo(oUserInfo) ){
			//m_sDBManager.delUserInfo("sa");
			setUserInfo( oUserInfo );
			bResult = true;
		}
		return bResult;
	}
	//************************************************
	//
	public static boolean addDBUserInfo( UserInfo  oUserInfo ){
		
		if( m_sDBManager.addUserInfo(oUserInfo) ){
			setUserInfo( oUserInfo );
			return true;
		}
		else{
			return false;
		}
	}
	//************************************************
	//
	public static boolean  modifyDBUserInfo( UserInfo oUserInfo ){
		
		return m_sDBManager.modifyUserInfo(oUserInfo);
	}
	//************************************************
	//
	public static void  getDBVehicleData( ){
		
		List<CarInfo>	 lstCarInfo = new ArrayList<CarInfo>();
		
		if( m_sDBManager.getDeviceData(lstCarInfo ) ){
			AddCarInfo(lstCarInfo);
			lstCarInfo.clear();
		}		
	}
	public static boolean  addDBCarInfo( CarInfo  oCarInfo ){
		
		return m_sDBManager.addDeviceData(oCarInfo);
	}
	public static boolean  modifyDBCarInfo( CarInfo oCarInfo ){
		
		return m_sDBManager.modifyDeviceData(oCarInfo.GetDEUID(), oCarInfo);
	}
	public static boolean  delDBCarInfo( String  strDEUID ){
		
		return m_sDBManager.delDeviceData(strDEUID);
	}
	public  static List<AlarmData>  queryDBGPSAlarmData( String  strDEUID, 
						   								int 	nStartTime,
						   								int	    nEndTime,
						   								int     nCondition){
		
		return m_sDBManager.queryAlarmData(strDEUID, nStartTime, nEndTime, nCondition);
	}
	//************************************************
	//
	public static boolean  IsDESIM( String  strPhone ){
		
		boolean				bResult = false;
		CarInfo				oCarInfo = null;
		List<CarInfo>		lstCarInfo = null;
		
		lstCarInfo =	getAllVehicle();
		for( int nCnt = 0; nCnt < lstCarInfo.size(); nCnt++ ){
			
			oCarInfo = lstCarInfo.get(nCnt);
			if( strcmp_back( strPhone, oCarInfo.GetDESIM(), 6 ) == true){
				bResult = true;
				break;
			}
		}
		return bResult;
	}
	//******************************************************
	//
	public  static boolean strcmp_back( String  str, String str2, int nLen ){
		
		boolean			bResult = false;
		int				nLen1 = str.length();
		int				nLen2 = str2.length();
		
		if( (nLen1 < nLen) || nLen2 < nLen ){
			return bResult;
		}		
		String  strTmp = str.substring(nLen1-nLen, nLen1 );
		String 	strTmp2 = str2.substring(nLen2-nLen, nLen2);
		if( strTmp.equals(strTmp2) ){
			bResult = true;
		}
		return bResult;		
	}
	//************************************************
	// 接收存储短信数据
	public static void  addRecvSMSData( byte[]	nPDUBuf ){
		
		SMSData		oSMSData = null;
		
		oSMSData = GSMPDU.GSM_ParsePDUSMS(nPDUBuf );
		if( oSMSData != null ){
			
			String str = new String();
			byte  [] nBuf = oSMSData.getSMSData();
			for( int nCnt = 0; nCnt < nBuf.length; nCnt++){
				
				str += Integer.toHexString( nBuf[nCnt]&0xFF );
				str += " ";
			}
			Log.e("SMSData", str );
			
			Log.e("Phone", oSMSData.getPhone() );
			Log.e("CenterPhone", oSMSData.getCenterPhone());
			
			m_lstRecvSMSData.add(oSMSData);
		}
	}	
	//***************************************************
	//
	public static boolean isRecvSMSData(){
		
		if( m_lstRecvSMSData.size() <= 0 ){
			return false;
		}
		else{
			return true;
		}
	}
	//***************************************************
	//  解释短信接收的数据
	public static boolean parseRecvSMSData(){
		
		boolean				bResult = false;
		GPSData				oGPSData = null;
		SMSData				oSMSData = null;
		DESProtocolData 	oPro = new DESProtocolData();
		
		if( m_lstRecvSMSData.size() <= 0 ){
			
			return bResult;
		}
		oSMSData = m_lstRecvSMSData.get(0);
		m_lstRecvSMSData.remove(0);
		Log.e("1", "0");
		if( oPro.ParseDESData( oSMSData.getSMSData(), oSMSData.getSMSLen() ) ){
			Log.e("1", "1");
			if( oPro.getGPSDataSize() > 0 ){
				
				Log.e("1", "3");
				bResult = true;
			}			
		}
		else{
			Log.e("1", "2");
			byte[]		nBuf = new byte[MAX_SMS_LEN];
			int			nLen = 0;
			
			Arrays.fill(nBuf, (byte)0 );
			nLen = oSMSData.getSMSLen();
			if( nLen > MAX_SMS_LEN){
				nLen = MAX_SMS_LEN;
			}
			switch( oSMSData.getDCSType() ){
			case GSMPDU.DCS_7BIT:			
				GSMPDU.P_Decode7bitTo8Bit(nBuf, oSMSData.getSMSData(), nLen );
				Log.e("Recv-sms-7", AppData.ByteToString(nBuf) );	
				break;
			case GSMPDU.DCS_8BIT:
				System.arraycopy( oSMSData.getSMSData(), 0, nBuf, 0, nLen );
				Log.e("Recv-sms-8", AppData.ByteToString(nBuf) );
				break;
			case GSMPDU.DCS_UNICODE:
				break;
			}			
			CarInfo  oCarInfo = findDESIMByCarInfo( oSMSData.getPhone() );
			if( oPro.ParseMingData( AppData.ByteToString(nBuf),oCarInfo.GetTEType(), oCarInfo.GetDEUID() ) ){
				bResult = true;
			}
		}	
		// 提取GPS数据
		if( bResult == true ){
			Log.e("1", "4");
			while( oPro.getGPSDataSize() > 0 ){
				
				Log.e("1", "5");
				oGPSData = oPro.getGPSData();
				AddGPSData( oGPSData );
				Log.e("1", "6");
				// 保存GPS数据
				m_sDBManager.addGPSData(oGPSData);
				Log.e("1", "7");
				addRecvProgramType( ProgramType.RECV_GPSDATA );
			}
		}
		else{
			Log.e("处理提示", "短信格式错误");
		}
		return  bResult;
	}
}



