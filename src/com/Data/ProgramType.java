package com.Data;


//*************************************************
//

public class ProgramType{
	
	//***************************************************************
	// ��½����
	public static final int		LOGIN_SUCCEED =  1;			//��½�ɹ� 
	public static final	int		LOGIN_FAIL = 2;				//��½ʧ��	
	public static final int		LOGIN_NETWORK_FAULT = 3;		//�������
	public static final int		LOGIN_LOAD_DATA = 4;			//��½��������
	public static final int		GENERAL_TIMEOUT = 5;			//��½��ʱ
	public static final int		LOGIN_CONNECT_SERVER = 6;		//���ӷ������ɹ�
	
	public static final int		RECV_GPSDATA = 7;				//�յ�GPS����
	public static final int		RECV_NO_GPSDATA = 8;			//û��GPS����
	public static final int		QUERY_DATA_TIMEOUT = 9;       //��ѯ���ݳ�ʱ

	public static final int		VEHICLE_SHOWMAP =  11;	    //������ʾ�ڵ�ͼ
	public static final int		REGISTER_USER_SUCCEED = 12;	//�����û��ɹ�
	public static final int		REGISTER_USER_FAIL = 13;		//�����û�ʧ��
	public static final int		MODIFY_USER_SUCCEED = 14;		//�޸��û�ʧ��
	public static final int		MODIFY_USER_FAIL = 15;		//�޸��û�ʧ��
	
	public static final int		ADD_VEHICLE_SUCCEED = 16;
	public static final int		ADD_VEHICLE_FAIL = 17;
	
	public static final int		MODIFY_VEHICLE_SUCCEED = 18;
	public static final int		MODIFY_VEHICLE_FAIL = 19;
	
	public static final int		DEL_VEHICLE_SUCCEED = 20;
	public static final int		DEL_VEHICLE_FAIL = 21;
	
	public static final int		RECV_QUERYALARM = 22;
	public static final int		RECV_NO_QUERYALARM = 23;	
	
	public static final int		UPDATE_VEHICLE_LIST_ADDR = 24;
	public static final int		UPDATE_LOG_LIST_ADDR = 25;
	public static final int		UPDATE_ALARM_REPORT_LIST_ADDR = 26;
	public static final int		LOG_INFO = 27;
	public static final int     UPDATE_LANGUAGE = 28;
	public static final int 	UPDATE_VEHICLE = 29;
	public static final int		RECV_PLAYGPSDATA = 30;
	public static final int		RECV_NO_PLAYGPSDATA = 31; 
	public static final int	    RECV_LOAD_PLAYGPSDATA = 32;
	public static final int	    RECV_LOAD_PLAYATA_TIMEOUT = 33;
	
}