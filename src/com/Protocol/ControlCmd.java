package com.Protocol;

//**********************************************************************
//CS《==》CL通信控制命码
public 	class ControlCmd{
	
	public static final byte  CM_ADD_FENCE = 0x01;					// 增加围栏
	public static final byte  CM_MODIFY_FENCE = 0x02;				// 修改围栏
	public static final byte  CM_DEL_FENCE = 0x03;					// 删除围栏
	public static final byte  CM_QUERY_FENCE = 0x04;
	public static final byte  CM_ADD_POI = 0x05;					// 增加POI
	public static final byte  CM_MODIFY_POI = 0x06;					// 修改POI
	public static final byte  CM_DEL_POI = 0x07;					// 删除POI
	public static final byte  CM_QUERY_POI = 0x08;
	public static final byte  CM_QUERY_POI_REPORT = 0x09;			// 查询POI区域
	public static final byte  CM_QUERY_FENCE_REPORT = 0x0A;			// 查询围栏报表
	public static final byte  CM_QUERY_TEMPERATURE_REPORT= 0x0B;	// 查询温度报表
	
	public static final byte  CM_HANDSHAKE = 0x0C;					// 握手指令 HandShake
	
	// CS<->DBS
	public static final byte  CM_LOGIN = 0x10;
	public static final byte  CM_QUERY_GPSDATA = 0x11;
	public static final byte  CM_QUERY_TESETUP = 0x12;				// not use
	public static final byte  CM_REGISTER_USER = 0x13;
	public static final byte  CM_MODIFY_USER = 0x14;
	public static final byte  CM_MODIFY_CAR = 0x15;
	public static final byte  CM_ADD_CAR = 0x16;
	public static final byte  CM_ADD_TERMINALUID = 0x17;
	public static final byte  CM_DEL_CAR = 0x18;	
	// TES<->DBS
	public static final byte  CM_UPLOAD_GPSDATA = 0x19;
	public static final byte  CM_UPLOAD_TESETUP = 0x1A;	
	// CL<->CS
	public static final byte  CM_LOGOUT = 0x1B;
	public static final byte  CM_CTRL_DE = 0x1C;	
	//BS<-->DB
	public static final byte  CM_VEHICLE_DATA = 0x1D;
	public static final byte  CM_QUERY_MILEAGE_DATA = 0x1E;
	public static final byte  CM_VERSION_EDITION = 0x1F;
	//TES<-->DBS
	public static final byte  CM_UPLOAD_GPSDATA_BLINDSPOT = 0x20;	//盲点数据 
	public static final byte  CM_UPLOAD_IMAGEDATA = 0x21;
	public static final byte  CM_QUERY_IMGDATA = 0x22;				//按条件查询图片
	//CS<-->DBS	
	public static final byte  CM_QUERY_NEWIMAGEDATA = 0x23;			//查询新图像数据
	//CL<-->CS
	public static final byte  CM_SUBUSER_DATA = 0x24;
	public static final byte  CM_SUBUSER_MANAGEDATA = 0x25;
	//CL<-->CS
	public static final byte  CM_REGISTER_SUBUSER = 0x26;			//注册子用户
	public static final byte  CM_MODIFY_SUBUSER = 0x27;				//修改子用户
	public static final byte  CM_DEL_SUBUSER = 0x28;				//删除子用户
	
	public static final byte  CM_ADDCAR_TOSUBUSER = 0x29;			//车辆分组
	public static final byte  CM_DELCAR_FMSUBUSER = 0x2A;			//删除车辆分组
	
	public static final byte  CM_CHECK_USER = 0x2B;					//检测是否有此用户
	//TE-->DB
	public static final byte  CM_UPLOAD_ATTEMPER = 0x2C;			//调度信息
	//DB<-->CS
	public static final byte  CM_QUERY_NEWATTEMPER = 0x2D;		    //查询调度信息
	//CL<->DB						
	public static final byte  CM_QUERY_ATTEMPER_CONDITION = 0x2E;    //条件查询调度信息
	
	public static final byte  CM_GROUP_DATA = 0x2F;
	public static final byte  CM_GROUP_MANAGEDATA = 0x30;
	
	public static final byte  CM_ADD_GROUP = 0x31;
	public static final byte  CM_DEL_GROUP = 0x32;
	public static final byte  CM_MODIFY_GROUP = 0x33;
	
	public static final byte  CM_ADDCAR_TOGROUP = 0x34;
	public static final byte  CM_DELCAR_FMGROUP = 0x35;
	
	public static final byte  CM_LOGIN_COMPLETE = 0x36;
	public static final byte  CM_SEND_TECMD_RESULT = 0x37;
	public static final byte  CM_QUERY_SERVER_USERPSD = 0x38;
	public static final byte  CM_QUERY_RUNDATA = 0x39;				//查询RUN报表
	public static final byte  CM_QUERY_ALARMDATA = 0x3A;			//查询报警数据
	public static final byte  CM_QUERY_DEUID = 0x3B;				//查询车辆资料
	public static final byte  CM_QUERY_RAMDATA = 0x3C;				//读取内存GPS;GAS;IMAGE等数据
	public static final byte  CM_QUERY_GASDATA = 0x3D;				//查询油量数据
}
