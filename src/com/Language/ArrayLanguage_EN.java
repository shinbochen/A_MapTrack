package com.Language;

//*************************************************
//
class  ArrayLanguage_EN{
	
	private  static  String[]  m_strLang = new String[]{
		Language_EN.TEXT_CONTROL,
		Language_EN.TEXT_OIL_WAY,
		Language_EN.TEXT_DOOR,
		Language_EN.TEXT_TRACK_ONCE,
		Language_EN.TEXT_SETUP,
		Language_EN.TEXT_LISTEN_IN,
		Language_EN.TEXT_CB_MODE,
		Language_EN.TEXT_TELNO,
		Language_EN.TEXT_SERVER_IP,
		Language_EN.TEXT_ALARM,
		Language_EN.TEXT_DEVICE_RESET,
		Language_EN.TEXT_RESTORE_FACTORY,
		Language_EN.TEXT_FENCE_SETUP,
		Language_EN.TEXT_FUEL_OFF,
		Language_EN.TEXT_FUEL_ON,
		Language_EN.TEXT_LOCK,
		Language_EN.TEXT_UNLOCK,
		Language_EN.TEXT_SMSBOX_INFO,
		Language_EN.TEXT_CAPTUREING,
		Language_EN.TEXT_IMAGE_CAPTUREING_PLESASE_WAIT,
		Language_EN.TEXT_VOICE_BROADCAST,
		Language_EN.TEXT_UPLOADING_PLEASE_WAIT,
		Language_EN.TEXT_ALARM_FUEL_LIAK,
		Language_EN.TEXT_GAS_RESISTANCE,
		Language_EN.TEXT_TEXT_INFO,
		Language_EN.TEXT_COREECTION_TIME,
		Language_EN.TEXT_CLOCK_SHOW,
		Language_EN.TEXT_SUCCEED,
		Language_EN.TEXT_FAIL,
		Language_EN.TEXT_CURRENT_POSITION,
		Language_EN.TEXT_NORTH,
		Language_EN.TEXT_NORTH_EAST,
		Language_EN.TEXT_EAST,
		Language_EN.TEXT_SOUTH_EAST,
		Language_EN.TEXT_SOUTH,
		Language_EN.TEXT_SOUTHE_WEST,
		Language_EN.TEXT_WEST,
		Language_EN.TEXT_NORTH_WEST,
		Language_EN.TEXT_ACC_ON, 
		Language_EN.TEXT_ACC_OFF,
		Language_EN.TEXT_DOOR_ON,
		Language_EN.TEXT_DOOR_OFF,
		
		Language_EN.TEXT_ALARM_SOS,
		Language_EN.TEXT_ALARM_OVERSPEED,
		Language_EN.TEXT_ALARM_PARKING,
		Language_EN.TEXT_ALARM_TOW,
		Language_EN.TEXT_ALARM_IN_AREA,
		Language_EN.TEXT_ALARM_OUT_AREA,
		Language_EN.TEXT_ALARM_POWER_OFF,
		Language_EN.TEXT_ALARM_LOW_POWER,
			
		Language_EN.TEXT_ALARM_GPS_OPEN,
		Language_EN.TEXT_ALARM_GPS_SHORT,
		Language_EN.TEXT_ALARM_ILL_DOOROPEN,
		Language_EN.TEXT_ALARM_ILL_ACCON,
		Language_EN.TEXT_ALARM_CUSTOM1,
		Language_EN.TEXT_ALARM_CUSTOM2,
		Language_EN.TEXT_ALARM_CUSTOM3,
		Language_EN.TEXT_ALARM_CUSTOM4,
		Language_EN.TEXT_ALARM_TIRED_DRIVE,
		Language_EN.TEXT_ACC_ALARM,			   //  ACC关报警			
		Language_EN.TEXT_NOGPS_ALARM,		   //  GPS未定位报警
		Language_EN.TEXT_TEMPERATURE_ALARM,
		Language_EN.TEXT_STARTTIME,
		Language_EN.TEXT_ENDTIME,
		Language_EN.TEXT_MAP_WINDOW,
		Language_EN.TEXT_MONITOR_OBJECT,
		Language_EN.TEXT_ALARM_REPORT,
		Language_EN.TEXT_ALARM_INFO,
		
		Language_EN.TEXT_MAP_LAYER_MANAGE,
		Language_EN.TEXT_USER_MANAGE,
		Language_EN.TEXT_SWITCH_ACCOUNT_NUMBER,
		Language_EN.TEXT_MODIFY_PASD,
		Language_EN.TEXT_HELP,
		Language_EN.TEXT_EXIT,

		Language_EN.TEXT_ADD,
		Language_EN.TEXT_MODIFY,
		Language_EN.TEXT_DEL,
		Language_EN.TEXT_VIEW,
		Language_EN.TEXT_DEL_ALL,
		Language_EN.TEXT_OK,
		Language_EN.TEXT_CANCEL,
		Language_EN.TEXT_LAYERCTRL,

		Language_EN.TEXT_SATELLITE,
		Language_EN.TEXT_STREETVIEW,
		Language_EN.TEXT_TRAFFIC,
		Language_EN.TEXT_YOUR_CAUSE_FOLLOWING,
		Language_EN.TEXT_MSG_HEARD,
		Language_EN.TEXT_MSG_ONE,
		Language_EN.TEXT_MSG_TWO,
		Language_EN.TEXT_MSG_THREE,
		Language_EN.TEXT_MSG_FOUR,
		Language_EN.TEXT_MSG_DESCRIPTION,
		Language_EN.TEXT_AGREE,
		Language_EN.TEXT_DENY,
		Language_EN.TEXT_POWER,
		Language_EN.TEXT_WARING_NO_DATA,
		Language_EN.TEXT_WARNING,
		Language_EN.TEXT_REGISTER_OK,
		Language_EN.TEXT_REGISTER_FAILED,
		
		Language_EN.TEXT_SIMPLEA,	
		Language_EN.TEXT_SIMPLEB,
		Language_EN.TEXT_SIMPLEC,
		Language_EN.TEXT_SIMPLED,
		Language_EN.TEXT_STANDARDA,
		Language_EN.TEXT_STANDARDB,
		Language_EN.TEXT_ENHANCE,
		Language_EN.TEXT_ADVANCE,
		Language_EN.TEXT_UNKNOW,
		Language_EN.TEXT_ALARM_ALL,
		Language_EN.TEXT_DURATION,
		Language_EN.TEXT_DAY,
		Language_EN.TEXT_HOUR,
		Language_EN.TEXT_MIN,
		Language_EN.TEXT_SEC,
		Language_EN.TEXT_M,
		Language_EN.TEXT_VEHICLE_SHOW,
		
		Language_EN.TEXT_ALARM_TYPE,
		Language_EN.TEXT_STARTADDR,
		Language_EN.TEXT_ENDADDR,
		Language_EN.TEXT_LAST_POSITION,
		Language_EN.TEXT_TALK,
		Language_EN.TEXT_GPS_LOCATE,
		Language_EN.TEXT_GPS_UNLOCATE,
		Language_EN.TEXT_QUERY,
		Language_EN.TEXT_TIME,
		Language_EN.TEXT_REMARK,
		Language_EN.TEXT_DEUID,
		Language_EN.TEXT_TYPE,
		Language_EN.TEXT_DEVICE_SIM,
		Language_EN.TEXT_FNAME,
		Language_EN.TEXT_LNAME,
		Language_EN.TEXT_ADDRESS,
		Language_EN.TEXT_PORT,
		Language_EN.TEXT_ACCOUNT,
		Language_EN.TEXT_PASSWORD,
		Language_EN.TEXT_LOGIN,
		Language_EN.TEXT_REGISTER,
		Language_EN.TEXT_OLD_PASSWORD,
		Language_EN.TEXT_NEW_PASSWORD,
		Language_EN.TEXT_NEW_PASSWORD2,
		Language_EN.TEXT_EMAIL,
		Language_EN.TEXT_COMPANY,
		Language_EN.TEXT_GPS_GPS,
		
		Language_EN.TEXT_OPERATION_RECORD	,
		Language_EN.TEXT_WARRIT_DEUID_ERROR ,
		Language_EN.TEXT_MONITORY_EMPTY		,
		Language_EN.TEXT_USER_AND_PSD_INCORRECT ,
		Language_EN.TEXT_LOADING_DATA_PLEASE_WAIT ,
		Language_EN.TEXT_NETWORK_FAIL_PLEASE_CHECK ,
		Language_EN.TEXT_LOGIN_TIMEOUT ,
		Language_EN.TEXT_CONNECT_SERVER_SUCCESS ,
		Language_EN.TEXT_USER_NOT_EMPTY	,
		Language_EN.TEXT_PROGRAM_LOAD_PLEASE_CHECK ,
		Language_EN.TEXT_SOCKET_CONNECT_LATER_QUIT ,
		Language_EN.TEXT_ARE_YOU_SURE,
		Language_EN.TEXT_ENTER_PSD_OLD_PSD ,
		Language_EN.TEXT_ENTER_TWICE_PASSWORD ,
		Language_EN.TEXT_RUN_PLEASE_WAIT,
		Language_EN.TEXT_CHECK_NETWORK ,
		Language_EN.TEXT_SELECT_VEHICLE ,
		Language_EN.TEXT_ONLY_TICK_CAR  ,
		Language_EN.TEXT_NUMBER_SUB_RULE ,
		Language_EN.TEXT_WARING_NO_ACCOUNT,
		Language_EN.TEXT_REMEMBER,
		Language_EN.TEXT_PLEASE_WAIT,
		Language_EN.TEXT_ADD_VEHICLE_NOW,
		Language_EN.TEXT_SPEED_KMH,
		Language_EN.TEXT_LATITUDE,
		Language_EN.TEXT_LONGITUDE,
		Language_EN.TEXT_NAME,
		Language_EN.TEXT_DRIVING_TIME,
		Language_EN.TEXT_ZOOMALL,
		Language_EN.TEXT_VEHICLE_HISTORY_TRACE,
		Language_EN.TEXT_PLAY,
		Language_EN.TEXT_PAUSE,
		Language_EN.TEXT_STOP,
		Language_EN.TEXT_RANGE_EXCEED_DAYS,
		Language_EN.TEXT_REFRESH,
		Language_EN.TEXT_OTHER,
		Language_EN.TEXT_CLICK_ME,
	};
	
	public  static String  getLanguage( int  nId ){
		
		if( nId > m_strLang.length ){
			return "";
		}		
		return m_strLang[nId];
	}
}