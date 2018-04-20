//****************************************************
//  数据库的管理

package com.database;

import java.util.ArrayList;
import java.util.List;

import com.Protocol.AlarmData;
import com.Protocol.CarInfo;
import com.Protocol.GPSData;
import com.Protocol.UserInfo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;



public class DBManager {

	public   Context		 m_oContext = null;
	private  DBSQLManager	 m_dbSQLManager = null;
	
	public   static final String 	TABLE_USER_NAME = "table_user";
    public   static final String 	TABLE_DEVICE_DATA_NAME = "table_device_data";
    public   static final String 	TABLE_TRACK_NAME = "table_track";
    
    //***用户信息***********************************************
    public  static  final  String  UR_VCHR_USER  =  "ur_vchr_user";
    public  static  final  String  UR_VCHR_PSD   =  "ur_vchr_psd";
    public  static  final  String  UR_VCHR_FNAME =  "ur_vchr_fname";
    public  static  final  String  UR_VCHR_LNAME =  "ur_vchr_fname";
    public  static  final  String  UR_VCHR_TEL   =  "ur_vchr_tel";
    public  static  final  String  UR_VCHR_ADDR  =  "ur_vchr_addr";
    public  static  final  String  UR_VCHR_EMAIL =  "ur_vchr_email";
    public  static  final  String  UR_VCHR_REMARK=  "ur_vchr_remark";
    //***车辆信息***********************************************
    public  static  final  String  CR_VCHR_DEUID  = "cr_vchr_deuid";
    public  static  final  String  CR_VCHR_LICENSE= "cr_vchr_license";
    public  static  final  String  CR_TINT_TYPE   = "cr_tint_type";
    public  static  final  String  CR_VCHR_DESIM  = "cr_vchr_desim";
    public  static  final  String  CR_VCHR_FNAME  = "cr_vchr_fname";
    public  static  final  String  CR_VCHR_LNAME  = "cr_vchr_lname";
    public  static  final  String  CR_VCHR_TEL    = "cr_vchr_tel";
    public  static  final  String  CR_VCHR_ADDR   = "cr_vchr_addr";
    public  static  final  String  CR_VCHR_REMARK = "cr_vchr_remark";
    //***GPS数据信息***********************************************
    public  static  final  String  TK_VCRH_DEUID      = "tk_vchr_deuid";
    public  static  final  String  TK_INT_UTC         = "tk_int_UTC";
    public  static  final  String  TK_SHORT_GPSVALID    = "tk_short_gpsvalid";
    public  static  final  String  TK_DB_LNG          = "tk_db_lng";
    public  static  final  String  TK_DB_LAT          = "tk_db_lat";
    public  static  final  String  TK_SHORT_SPEED       = "tk_short_speed";
    public  static  final  String  TK_SHORT_HEADING     = "tk_short_heading";
    public  static  final  String  TK_INT_MILEAGE     = "tk_int_mileage";
    public  static  final  String  TK_INT_ALSTATE     = "tk_int_alstate";
    public  static  final  String  TK_INT_HWSTATE     = "tk_int_hwstate";
    public  static  final  String  TK_INT_CODESTATE   = "tk_int_codestate";
    public  static  final  String  TK_SHORT_DRIVETIME   = "tk_short_drivetime";
    
	// 用户表
	private static final String TABLE_USER = "CREATE TABLE table_user("+
											 "ur_vchr_user 	   TEXT not null,"+ 
											 "ur_vchr_psd 	   TEXT not null,"+
											 "ur_vchr_fname	   TEXT,"+
											 "ur_vchr_lname     TEXT,"+
											 "ur_vchr_tel	   TEXT,"+
											 "ur_vchr_addr      TEXT,"+
											 "ur_vchr_email     TEXT,"+
											 "ur_vchr_remark    TEXT);";
	// 车辆信息表
	private static final  String  TABLE_DEVICE_DATA = "CREATE TABLE table_device_data("+
													"cr_vchr_deuid   TEXT not null,"+
													"cr_vchr_license TEXT not null,"+
													"cr_tint_type    INTEGER,"+
													"cr_vchr_desim   TEXT,"+
													"cr_vchr_fname   TEXT,"+
													"cr_vchr_lname   TEXT,"+
													"cr_vchr_tel     TEXT,"+
													"cr_vchr_addr    TEXT,"+
													"cr_vchr_remark  TEXT);";
	
	// GPS数据表
	private static final String  TABLE_TRACK = "CREATE TABLE  table_track( "+
												"tk_vchr_deuid	  TEXT not null,"+
											    "tk_int_UTC	      INTEGER,"+
											    "tk_short_gpsvalid  SHORT,"+
											    "tk_db_lng	      DOUBLE,"+
											    "tk_db_lat	      DOUBLE,"+
											    "tk_short_speed	  SHORT,"+
											    "tk_short_heading SHORT,"+
											    "tk_int_mileage	  INTEGER,"+
											    "tk_int_alstate	  INTEGER,"+
											    "tk_int_hwstate   INTEGER,"+
											    "tk_int_codestate INTEGER,"+
											    "tk_short_drivetime SHORT );";	
		
	//*************************************************
	//
	public  DBManager( Context  oContext ){
		
		m_oContext = oContext;
	}	
	//**************************************************
	//	
	public DBManager open(){	
		
		try{
			m_dbSQLManager = new DBSQLManager(m_oContext);
		}
		catch(SQLException ex ){
			Log.e("open" , ex.getMessage()); 
		}
		return this;
	}
	//**************************************************
	//
	public void close() {		
		m_dbSQLManager.close();
	}
	//*************************************************
	//
	public  boolean  addUserInfo( UserInfo  oUserInfo ){
		
		boolean 	bResult = false;
		
		ContentValues initialValues = new ContentValues();
		initialValues.put(UR_VCHR_USER, oUserInfo.getUserName() );
		initialValues.put(UR_VCHR_PSD,  oUserInfo.getUserPsd() );
		initialValues.put(UR_VCHR_FNAME,  oUserInfo.getFName() );
		initialValues.put(UR_VCHR_LNAME,  oUserInfo.getLName() );
		initialValues.put(UR_VCHR_TEL,  oUserInfo.getTelNum() );
		initialValues.put(UR_VCHR_ADDR,  oUserInfo.getAddr() );
		initialValues.put(UR_VCHR_EMAIL,  oUserInfo.getEmail() );
		initialValues.put(UR_VCHR_REMARK,  oUserInfo.getRemark() );
						
		try{			
			SQLiteDatabase sqliteDB = m_dbSQLManager.getWritableDatabase();
			sqliteDB.insert(TABLE_USER_NAME, null, initialValues);
			bResult = true;
			sqliteDB.close();
    	} 
		catch (Exception ex) {   
    		Log.e("addUserInfo" , ex.getMessage());    		
    	}  		
		return bResult;
	}
	//*************************************************
	//  修改用户信息
	public  boolean  modifyUserInfo( UserInfo  oUserInfo ){
		
		boolean   bResult = false;
		
		ContentValues initialValues = new ContentValues();
		initialValues.put(UR_VCHR_PSD,  oUserInfo.getUserPsd() );
		initialValues.put(UR_VCHR_FNAME,  oUserInfo.getFName() );
		initialValues.put(UR_VCHR_LNAME,  oUserInfo.getLName() );
		initialValues.put(UR_VCHR_TEL,  oUserInfo.getTelNum() );
		initialValues.put(UR_VCHR_ADDR,  oUserInfo.getAddr() );
		initialValues.put(UR_VCHR_EMAIL,  oUserInfo.getEmail() );
		initialValues.put(UR_VCHR_REMARK,  oUserInfo.getRemark() );
		
		try{
			SQLiteDatabase sqliteDB = m_dbSQLManager.getWritableDatabase();
			sqliteDB.update(   TABLE_USER_NAME, 
							   initialValues, 
							   UR_VCHR_USER +"='"+ oUserInfo.getUserName()+"'", 
							   null );
			bResult = true;
			sqliteDB.close();
		}
		catch( Exception ex ){
			Log.e("ModifyUserInfo", ex.getMessage() );
		}
		return  bResult;
	}
	//*************************************************
	//
	public  boolean  delUserInfo( String   strUser ){
		
		boolean  bResult = false;
		
		try{			
			SQLiteDatabase sqliteDB = m_dbSQLManager.getWritableDatabase();
			sqliteDB.delete( TABLE_USER_NAME, 
							   UR_VCHR_USER + "='"+ strUser+"'", 
							   null);
			bResult = true;
			sqliteDB.close();
		}
		catch( Exception  ex){
			
			Log.e("delUserInfo", ex.getMessage() );
		}
		return  bResult;
	}
	//*************************************************
	//
	public  boolean  getUserInfo( UserInfo oUserInfo ){
		
		boolean   		bResult = false;
		String[]    	strArray = null;
		try{
			strArray = new String[]{
					UR_VCHR_USER,
					UR_VCHR_PSD,
					UR_VCHR_FNAME,
					UR_VCHR_LNAME,
					UR_VCHR_TEL,
					UR_VCHR_ADDR,
					UR_VCHR_EMAIL,
					UR_VCHR_REMARK,
			};
			SQLiteDatabase sqliteDB = m_dbSQLManager.getReadableDatabase();
			Cursor oCursor =  sqliteDB.query( TABLE_USER_NAME, 
												strArray, 
												null, 
												null, 
												null, 
												null, 
												null);
			if( oCursor.getCount() <= 0 ){
				return  bResult;
			}
			bResult = true;
			oCursor.moveToFirst();
			
			oUserInfo.setUserName( oCursor.getString( oCursor.getColumnIndex(UR_VCHR_USER) ));
			
			oUserInfo.setUserPsd( oCursor.getString( oCursor.getColumnIndex(UR_VCHR_PSD) ));
			oUserInfo.setFName( oCursor.getString( oCursor.getColumnIndex(UR_VCHR_FNAME) ));
			oUserInfo.setLName( oCursor.getString( oCursor.getColumnIndex(UR_VCHR_LNAME) ));
			oUserInfo.setTelNum( oCursor.getString( oCursor.getColumnIndex(UR_VCHR_TEL) ));
			oUserInfo.setAddr( oCursor.getString( oCursor.getColumnIndex(UR_VCHR_ADDR) ));
			oUserInfo.setEmail( oCursor.getString( oCursor.getColumnIndex(UR_VCHR_EMAIL) ));
			oUserInfo.setRemark( oCursor.getString( oCursor.getColumnIndex(UR_VCHR_REMARK) ));
			oCursor.close();
			sqliteDB.close();
		}
		catch( Exception  ex){			
			Log.e("getUserInfo", ex.getMessage() );
		}
		
		return  bResult;
	}
	//*************************************************
	//
	public  boolean  addDeviceData( CarInfo  oCarInfo ){
		
		boolean  bResult = false;
		
		if( isDeviceData( oCarInfo.GetDEUID()) ){
			return bResult;
		}
		ContentValues initialValues = new ContentValues();
		
		initialValues.put(CR_VCHR_DEUID, oCarInfo.GetDEUID() );
		initialValues.put(CR_VCHR_LICENSE, oCarInfo.GetCarLicense() );
		initialValues.put(CR_TINT_TYPE, oCarInfo.GetTEType() );
		initialValues.put(CR_VCHR_DESIM, oCarInfo.GetDESIM() );
		initialValues.put(CR_VCHR_FNAME, oCarInfo.GetFName() );
		initialValues.put(CR_VCHR_LNAME, oCarInfo.GetLName() );
		initialValues.put(CR_VCHR_TEL, oCarInfo.GetOwnerTel() );
		initialValues.put(CR_VCHR_ADDR, oCarInfo.GetOwnerAddr() );
		initialValues.put(CR_VCHR_REMARK, oCarInfo.GetRemark() );				
		try{			
			SQLiteDatabase sqliteDB = m_dbSQLManager.getWritableDatabase();
			sqliteDB.insert(TABLE_DEVICE_DATA_NAME, null, initialValues);	
			
			bResult = true;
    		Log.e("addDeviceData" , "成功"); 
    		sqliteDB.close();
    	} 
		catch (Exception ex) {   
    		Log.e("addDeviceData" , ex.getMessage());    		
    	}  		
		return bResult;
	}
	//*************************************************
	//
	public  boolean  isDeviceData( String  strDEUID ){
		
		boolean 	bResult = false;
		
		try{
			SQLiteDatabase sqliteDB = m_dbSQLManager.getReadableDatabase();
			
			Cursor  oCursor =  sqliteDB.query( TABLE_DEVICE_DATA_NAME, 
												  new String[]{CR_VCHR_DEUID}, 
												  CR_VCHR_DEUID+"=?", 
												  new String[]{strDEUID}, 
												  null, 
												  null, 
												  null);
			if( oCursor.getCount() >0 ){
				bResult = true;
			}
			oCursor.close();
			sqliteDB.close();
		}
		catch(Exception ex){
			Log.e("getDeviceData" , ex.getMessage()); 
		}		
		return  bResult;
	}
	//*************************************************
	//
	public  boolean  modifyDeviceData( String  strDEUID, CarInfo oCarInfo ){
		
		boolean 	bResult = false;		
		ContentValues initialValues = new ContentValues();
		
		initialValues.put(CR_VCHR_LICENSE, oCarInfo.GetCarLicense() );
		initialValues.put(CR_TINT_TYPE, oCarInfo.GetTEType() );
		initialValues.put(CR_VCHR_DESIM, oCarInfo.GetDESIM() );
		initialValues.put(CR_VCHR_FNAME, oCarInfo.GetFName() );
		initialValues.put(CR_VCHR_LNAME, oCarInfo.GetLName() );
		initialValues.put(CR_VCHR_TEL, oCarInfo.GetOwnerTel() );
		initialValues.put(CR_VCHR_ADDR, oCarInfo.GetOwnerAddr() );
		initialValues.put(CR_VCHR_REMARK, oCarInfo.GetRemark() );	
		try{
			SQLiteDatabase sqliteDB = m_dbSQLManager.getWritableDatabase();
			sqliteDB.update( TABLE_DEVICE_DATA_NAME, 
							   initialValues, 
							   CR_VCHR_DEUID +"='"+strDEUID+"'", 
							   null );
			bResult = true;
    		Log.e("modifyDeviceData" , "成功"); 
    		sqliteDB.close();
		}
		catch(Exception ex){
			Log.e("modifyDeviceData" , ex.getMessage());  
		}
		return  bResult;
	}
	//*************************************************
	//
	public  boolean  delDeviceData( String  strDEUID ){
		
		boolean  bResult = false;		
		
		try{
			SQLiteDatabase sqliteDB = m_dbSQLManager.getWritableDatabase();
			sqliteDB.delete( TABLE_DEVICE_DATA_NAME, 
							   CR_VCHR_DEUID+"='"+strDEUID+"'", 
							   null );
			bResult = true;
    		Log.e("delDeviceData" , "成功"); 
    		sqliteDB.close();
		}
		catch(Exception ex){
			Log.e("delDeviceData" , ex.getMessage()); 
		}
		return  bResult;
	}
	//*************************************************
	//
	public  boolean  getDeviceData( List<CarInfo> outData ){
		
		boolean 	bResult = false;
		String[]	strArray = null;
		CarInfo		oCarInfo = null;
		
		try{
			outData.clear();
			strArray = new String[]{
					CR_VCHR_DEUID, 
					CR_VCHR_LICENSE,
					CR_TINT_TYPE,
					CR_VCHR_DESIM,
					CR_VCHR_FNAME,
					CR_VCHR_LNAME,
					CR_VCHR_TEL,
					CR_VCHR_ADDR,
					CR_VCHR_REMARK,
			};
			SQLiteDatabase sqliteDB = m_dbSQLManager.getReadableDatabase();
			Cursor  oCursor =  sqliteDB.query( TABLE_DEVICE_DATA_NAME, 
												  strArray, 
												  null, 
												  null, 
												  null, 
												  null, 
												  null);
			if( oCursor.getCount() <= 0 ){
				return bResult;
			}
			oCursor.moveToFirst();
			do{
				oCarInfo = new CarInfo();

			    oCarInfo.SetDEUID( oCursor.getString( oCursor.getColumnIndex(CR_VCHR_DEUID)));
			    oCarInfo.SetCarLicense( oCursor.getString( oCursor.getColumnIndex(CR_VCHR_LICENSE)));
			    oCarInfo.SetTEType( oCursor.getInt( oCursor.getColumnIndex(CR_TINT_TYPE)));
			    oCarInfo.SetDESIM( oCursor.getString( oCursor.getColumnIndex(CR_VCHR_DESIM)));
			    oCarInfo.SetFName( oCursor.getString( oCursor.getColumnIndex(CR_VCHR_FNAME)));
			    oCarInfo.SetLName( oCursor.getString( oCursor.getColumnIndex(CR_VCHR_LNAME)));
			    oCarInfo.SetOwnerTel( oCursor.getString( oCursor.getColumnIndex(CR_VCHR_TEL)));
			    oCarInfo.SetOwnerAddr( oCursor.getString( oCursor.getColumnIndex(CR_VCHR_ADDR)));
			    oCarInfo.SetRemark( oCursor.getString( oCursor.getColumnIndex(CR_VCHR_REMARK)));
								
				outData.add( oCarInfo );
				bResult = true;
			}while( oCursor.moveToNext() );
			oCursor.close();
			sqliteDB.close();			
		}
		catch(Exception ex){
			Log.e("getDeviceData" , ex.getMessage()); 
		}		
		return  bResult;
	}
	//*************************************************
	//
	public  boolean  isGPSData( String  strDEUID,  int  nTime ){

		boolean 	 bResult = false;

		try{
			SQLiteDatabase sqliteDB = m_dbSQLManager.getReadableDatabase();
			Cursor oCursor = sqliteDB.query( TABLE_TRACK_NAME, 
							  null, 
							  TK_VCRH_DEUID+"=? and "+TK_INT_UTC+"="+nTime, 
							  new String[]{strDEUID}, 
							  null, 
							  null,
							  null );
				
			if( oCursor.getCount() > 0 ){
				bResult = true;
			}
			oCursor.close();
			sqliteDB.close();
		}
		catch(Exception ex) {
    		Log.e("addGPSData" , ex.getMessage()); 
		}
		return  bResult;
	}
	//*************************************************
	//
	public  boolean  addGPSData( GPSData  oGPSData ){
		
		boolean	 bResult = false;		
		
		if( isGPSData( oGPSData.getDEUID(), oGPSData.getGPSTime()) ){
			return bResult;
		}
		ContentValues initialValues = new ContentValues();		
		
		initialValues.put(TK_VCRH_DEUID, oGPSData.getDEUID() );
		initialValues.put(TK_INT_UTC, oGPSData.getGPSTime() );
		initialValues.put(TK_SHORT_GPSVALID, oGPSData.getGPSValid() );
		initialValues.put(TK_DB_LNG, oGPSData.getLon() );
		initialValues.put(TK_DB_LAT, oGPSData.getLat() );
		initialValues.put(TK_SHORT_SPEED,(short) oGPSData.getSpeed() );
		initialValues.put(TK_SHORT_HEADING, oGPSData.getDirection() );
		initialValues.put(TK_INT_MILEAGE, oGPSData.getMileage() );
		initialValues.put(TK_INT_ALSTATE, oGPSData.getAlarmState() );
		initialValues.put(TK_INT_HWSTATE, oGPSData.getHWState() );
		initialValues.put(TK_INT_CODESTATE, oGPSData.getCodeState() );
		initialValues.put(TK_SHORT_DRIVETIME, oGPSData.getDriverTime() );
									    
		try{
			SQLiteDatabase sqliteDB = m_dbSQLManager.getWritableDatabase();
			sqliteDB.insert(TABLE_TRACK_NAME, null, initialValues);			
			bResult = true;
			sqliteDB.close();
    	} 
		catch (Exception ex) {
    		Log.e("addGPSData" , ex.getMessage());    		
    	}  		
		return bResult;
	}
	//*****************************************************
	//
	public  List<GPSData>  queryGPSData( String  strDEUID, int	nCondition ){
		
		String				strData = "";
		String[]			strArray = null;
		List<GPSData>		lstGPSData = null;
		
		lstGPSData = new ArrayList<GPSData>();
		lstGPSData.clear();
		try{
			strArray = new String[]{
						TK_VCRH_DEUID,
						TK_INT_UTC,
						TK_SHORT_GPSVALID,
						TK_DB_LNG,
						TK_DB_LAT,
						TK_SHORT_SPEED,
						TK_SHORT_HEADING,
						TK_INT_MILEAGE,
						TK_INT_ALSTATE,
						TK_INT_HWSTATE,
						TK_INT_CODESTATE,
						TK_SHORT_DRIVETIME
			};
			if( nCondition == 1 ){	//查最后位置
				strData = TK_INT_UTC+" DESC";
			}
			else{
				strData = TK_INT_UTC+" ASC";
			}

			SQLiteDatabase sqliteDB = m_dbSQLManager.getReadableDatabase();
			Cursor oCursor = sqliteDB.query( TABLE_TRACK_NAME, 
							  strArray, 
							  TK_VCRH_DEUID+"=?",//TK_VCRH_DEUID+"='"+strDEUID+"'", 
							  new String[]{strDEUID}, 
							  null, 
							  null,
							  strData );
				
			if( oCursor.getCount() <= 0 ){				
				return lstGPSData;
			}
			oCursor.moveToFirst();
			do{
			   GPSData  oGPSData = new GPSData();
			   			   
			   oGPSData.setDEUID( oCursor.getString( oCursor.getColumnIndex( TK_VCRH_DEUID )) );
			   oGPSData.setGPSTime( oCursor.getInt( oCursor.getColumnIndex( TK_INT_UTC )) );
			   oGPSData.setLon( oCursor.getDouble( oCursor.getColumnIndex( TK_DB_LNG )) );
			   oGPSData.setLat( oCursor.getDouble( oCursor.getColumnIndex( TK_DB_LAT )) );
			   oGPSData.setGPSValid((byte)(oCursor.getShort( oCursor.getColumnIndex( TK_SHORT_GPSVALID ))&0xFF) );
			   oGPSData.setSpeed((byte)(oCursor.getShort( oCursor.getColumnIndex( TK_SHORT_SPEED ))&0xFF) );
			   
			   oGPSData.setDirection( oCursor.getShort( oCursor.getColumnIndex( TK_SHORT_HEADING )) );
			   oGPSData.setMileage( oCursor.getInt( oCursor.getColumnIndex( TK_INT_MILEAGE )) );
			   oGPSData.setAlarmState( oCursor.getInt( oCursor.getColumnIndex( TK_INT_ALSTATE )) );
			   oGPSData.setHWState( oCursor.getInt( oCursor.getColumnIndex( TK_INT_HWSTATE )) );
			   oGPSData.setCodeState( oCursor.getInt( oCursor.getColumnIndex( TK_INT_CODESTATE )) );
			   oGPSData.setDriverTime( oCursor.getShort( oCursor.getColumnIndex( TK_SHORT_DRIVETIME )) );
			  		
			   lstGPSData.add(oGPSData);
			   if( nCondition == 1 ){
				   break;
			   }
			}while( oCursor.moveToNext() );
			oCursor.close();
			sqliteDB.close();
		}
		catch(Exception ex) {
    		Log.e("addGPSData" , ex.getMessage()); 
		}
		return  lstGPSData;
	}
	//****************************************************
	//
	public  List<GPSData>    queryGPSData( String  strDEUID,
										   int 	   nStartTime,
										   int	   nEndTime ){
		
		List<GPSData>		lstGPSData = null;
		String[]			strArray = null;
		
		lstGPSData = new ArrayList<GPSData>();
		lstGPSData.clear();
		try{
			
			strArray = new String[]{
					TK_VCRH_DEUID,
					TK_INT_UTC,
					TK_SHORT_GPSVALID,
					TK_DB_LNG,
					TK_DB_LAT,
					TK_SHORT_SPEED,
					TK_SHORT_HEADING,
					TK_INT_MILEAGE,
					TK_INT_ALSTATE,
					TK_INT_HWSTATE,
					TK_INT_CODESTATE,
					TK_SHORT_DRIVETIME
			};
			SQLiteDatabase sqliteDB = m_dbSQLManager.getReadableDatabase();
			Cursor oCursor = sqliteDB.query( TABLE_TRACK_NAME, 
							  strArray, 
							  TK_VCRH_DEUID+"=? and "+TK_INT_UTC+" >="+nStartTime+" and TK_INT_UTC <="+nEndTime, 
							  new String[]{strDEUID}, 
							  null, 
							  null,
							  TK_INT_UTC+" ASC" );
				
			if( oCursor.getCount() <= 0 ){				
				return lstGPSData;
			}
			oCursor.moveToFirst();
			do{
			   GPSData  oGPSData = new GPSData();
			   
			   oGPSData.setDEUID( oCursor.getString( oCursor.getColumnIndex( TK_VCRH_DEUID )) );
			   oGPSData.setGPSTime( oCursor.getInt( oCursor.getColumnIndex( TK_INT_UTC )) );
			   oGPSData.setLon( oCursor.getDouble( oCursor.getColumnIndex( TK_DB_LNG )) );
			   oGPSData.setLat( oCursor.getDouble( oCursor.getColumnIndex( TK_DB_LAT )) );
			   oGPSData.setGPSValid((byte)(oCursor.getShort( oCursor.getColumnIndex( TK_SHORT_GPSVALID ))&0xFF) );
			   oGPSData.setSpeed((byte)(oCursor.getShort( oCursor.getColumnIndex( TK_SHORT_SPEED ))&0xFF) );
			   
			   oGPSData.setDirection( oCursor.getShort( oCursor.getColumnIndex( TK_SHORT_HEADING )) );
			   oGPSData.setMileage( oCursor.getInt( oCursor.getColumnIndex( TK_INT_MILEAGE )) );
			   oGPSData.setAlarmState( oCursor.getInt( oCursor.getColumnIndex( TK_INT_ALSTATE )) );
			   oGPSData.setHWState( oCursor.getInt( oCursor.getColumnIndex( TK_INT_HWSTATE )) );
			   oGPSData.setCodeState( oCursor.getInt( oCursor.getColumnIndex( TK_INT_CODESTATE )) );
			   oGPSData.setDriverTime( oCursor.getShort( oCursor.getColumnIndex( TK_SHORT_DRIVETIME )) );
			  					   
			   lstGPSData.add(oGPSData);			   
			}while( oCursor.moveToNext() );
			
			oCursor.close();
			sqliteDB.close();
		}
		catch(Exception ex) {
    		Log.e("addGPSData" , ex.getMessage()); 
		}
		return  lstGPSData;
	}
	//***************************************************************
	// 	按条件获取报警值 
	//	返回值：	返回  
	//				 0: 没有报警值 	 
	//				 2: 所有报警
	//		     	 3: 紧急报警
	//		    	 4: 超速报警
	//		     	 5: 断电报警
	//				 6: 低电压报警
	//				 7: 停车报警
	//				 8: 拖车报警	
	//				 9: 越界报警	
	//				10: 备用
	//				11: 非法开门报警
	//				12: 非法点火报警
	//				13: 自定义报警1
	//				14: 自定义报警2
	//				15: 自定义报警3
	//				16: 自定义报警4
	//				17: 疲劳驾驶报警
	//				18: 油路报警
	//				19: ACC关报警
	//				20: GPS未定位报警
	//				21: 温度报警
	public  int  getAlarmBit( int nCondition ){
		
		int		nResult = 0;
		
		switch( nCondition ){
		case 2:
			nResult = 0x7FFFFFFF;
			break;
		case 3:
			nResult = 1;
			break;
		case 4:
			nResult = 2;
			break;
		case 5:
			nResult = 64;
			break;
		case 6:
			nResult = 128;
			break;
		case 7:
			nResult = 4;
			break;
		case 8:
			nResult = 8;
			break;
		case 9:
			nResult = 48;
			break;
		case 11:
			nResult = 1024;
			break;
		case 12:
			nResult = 2048;
			break;
		case 13:
			nResult = 4096;
			break;
		case 14:
			nResult = 8192;
			break;
		case 15:
			nResult = 16384;
			break;
		case 16:
			nResult = 32768;
			break;
		case 17:
			nResult = 65536;
			break;
		case 18:
			nResult = 131072;
			break;
		case 19:
			nResult = 262144;
			break;
		case 20:
			nResult = 524288;
			break;
		case 21:
			nResult = 1048576;
			break;
		default:
			break;
		}
		return  nResult;
	}
	//****************************************************
	//
	public  List<AlarmData>   queryAlarmData( String  strDEUID, 
			   								int 	nStartTime,
			   								int	    nEndTime,
			   								int     nCondition){
		
		List<AlarmData>		lstAlarmData = null;
		String[]			strArray = null;
		int					nAlarmBit = 0;
		int					nAlarmState =  0;
		double 				dbLat = 0.0;
		double				dbLon = 0.0;		
		AlarmData			oAlarmData = null;
		
		lstAlarmData = new ArrayList<AlarmData>();
		lstAlarmData.clear();
		try{			
			strArray = new String[]{
					TK_VCRH_DEUID,
					TK_INT_UTC,
					TK_SHORT_GPSVALID,
					TK_DB_LNG,
					TK_DB_LAT,
					TK_SHORT_SPEED,
					TK_SHORT_HEADING,
					TK_INT_MILEAGE,
					TK_INT_ALSTATE,
					TK_INT_HWSTATE,
					TK_INT_CODESTATE,
					TK_SHORT_DRIVETIME
			};
			nAlarmBit = getAlarmBit( nCondition );
			SQLiteDatabase sqliteDB = m_dbSQLManager.getReadableDatabase();
			Cursor oCursor = sqliteDB.query( TABLE_TRACK_NAME, 
							  strArray, 
							  TK_VCRH_DEUID+"=? and "+TK_INT_ALSTATE+" > 0 and "+TK_INT_UTC+" >="+nStartTime+" and TK_INT_UTC <="+nEndTime, 
							  new String[]{strDEUID}, 
							  null, 
							  null,
							  TK_INT_UTC+" ASC" );
				
			if( oCursor.getCount() <= 0 ){
				return lstAlarmData;
			}			
			oCursor.moveToFirst();
			do{
				nAlarmState = oCursor.getInt( oCursor.getColumnIndex( TK_INT_ALSTATE ));
				if( (nAlarmBit & nAlarmState) > 0 ){
					
					oAlarmData = new AlarmData();
					oAlarmData.setDEUID( oCursor.getString( oCursor.getColumnIndex( TK_VCRH_DEUID )) );
					dbLat = oCursor.getDouble( oCursor.getColumnIndex( TK_DB_LAT ));
					dbLon = oCursor.getDouble( oCursor.getColumnIndex( TK_DB_LNG ));
					oAlarmData.setStartUTC( oCursor.getInt( oCursor.getColumnIndex( TK_INT_UTC )));
					oAlarmData.setEndUTC( oAlarmData.getStartUTC() );
				    oAlarmData.setAlarmType(nAlarmState);
				    oAlarmData.setBeginLonAndLat(dbLon, dbLat);
				    lstAlarmData.add(oAlarmData);
				}					
			}while( oCursor.moveToNext() );
			
			oCursor.close();
			sqliteDB.close();
		}
		catch(Exception ex) {
    		Log.e("queryAlarmData" , ex.getMessage()); 
		}
		return  lstAlarmData;
	}
	//***************************************************
	//
	public  boolean   delGPSData( String  strDEUID ){
		
		boolean 	bResult = false;
		
		try{

			SQLiteDatabase sqliteDB = m_dbSQLManager.getWritableDatabase();
			sqliteDB.delete(TABLE_TRACK_NAME, TK_VCRH_DEUID+"='"+strDEUID+"'", null);
			bResult = true;
			sqliteDB.close();
		}
		catch(Exception ex) {
    		Log.e("delGPSData" , ex.getMessage()); 
		}		
		return  bResult;
	}
	//**************************************************
	//  数据库类
	//
	//
	public static class DBSQLManager extends SQLiteOpenHelper {

		
		public static final String 		DATABASE_NAME = "AMapTrack.db";
		public static final int 		DATABASE_VERSION = 1;
		//**************************************************
		//
		public DBSQLManager(Context context) {
			
	        super(context, DATABASE_NAME, null, DATABASE_VERSION);	 
			Log.e("DBSQLManager","DBSQLManager");       
		}
		//**************************************************
		//
		@Override
		public void onCreate(SQLiteDatabase db) {
			
			Log.e("DBSQLManager","onCreate");
			db.execSQL(TABLE_USER);
			db.execSQL(TABLE_DEVICE_DATA);
			db.execSQL(TABLE_TRACK);
		}
		//**************************************************
		//
		@Override
		public synchronized void close() {

			Log.e("DBSQLManager", "close");
			super.close();
		}
		//**************************************************
		//
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

			Log.e("DBSQLManager","onUpgrade");
			String 		strSQL = "drop from " + TABLE_USER_NAME;
			db.execSQL(strSQL);
			strSQL = "drop from " + TABLE_DEVICE_DATA_NAME;
			db.execSQL(strSQL);
			strSQL = "drop from " + TABLE_TRACK_NAME;
			db.execSQL(strSQL);
			onCreate(db);
		}	    
	}
}
