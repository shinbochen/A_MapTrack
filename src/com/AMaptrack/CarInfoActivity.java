package com.AMaptrack;

import java.util.ArrayList;
import java.util.List;

import com.Language.Language;
import com.Protocol.Structs;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class CarInfoActivity extends Activity {

	public 	EditText			m_editCarLicense = null;
	public  EditText			m_editDEUID = null;
	public  Spinner				m_spDEType = null;
	public  EditText			m_editDESIM = null;
	public  EditText			m_editFName = null;
	public  EditText			m_editLName = null;
	public  EditText			m_editTelNo = null;
	public  EditText			m_editAddr = null;
	public  EditText			m_editRemark = null;
	
	private	TextView	  		m_txtCarLicense = null;
	private	TextView	 		m_txtDEUID = null;
	private	TextView	  		m_txtDEType = null;
	private	TextView	  		m_txtDESIM = null;
	private	TextView	  		m_txtFName = null;
	private	TextView	 	 	m_txtLName = null;
	private	TextView	  		m_txtTelNo = null;
	private	TextView	  		m_txtAddr = null;
	private	TextView	  		m_txtRemark = null;
	
	public  Button				m_bthOK = null;
	public  Button				m_bthCancel = null;
	
	public  int					m_nWorkMode = 0;	// 0:增加 1:修改 2:查看
	public  int					m_nDEType = 0;
	public  List<String>			m_lstDEType = null;		//
	public 	ArrayAdapter<String> 	m_DETypeAdapter = null ;
	

	
	//****************************************************
	//
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		this.setContentView( R.layout.carinfo );
		
		initEventObj();
		initEvent();
		initSpinner();
		initLabel();
		getIntentData();
		
	}
	//****************************************************
	//
	public void initSpinner(){
		
		m_lstDEType = new ArrayList<String>();
		m_lstDEType.clear();
		DEType.GetDeviceTypeArray(m_lstDEType);
		if( m_DETypeAdapter != null ){
			m_DETypeAdapter.clear();
		}
		m_DETypeAdapter = new ArrayAdapter<String>( this,
	        	  								    android.R.layout.simple_spinner_item,
	        	  								    m_lstDEType);		
		m_DETypeAdapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
		m_spDEType.setAdapter(m_DETypeAdapter);
	}
	//****************************************************
	//
	public void initEventObj(){
		
		m_txtCarLicense = (TextView)findViewById(R.id.Text_CarLicense);
		m_txtDEUID = (TextView)findViewById(R.id.Text_DEUID);
		m_txtDEType = (TextView)findViewById(R.id.Text_DEType);
		m_txtDESIM = (TextView)findViewById(R.id.Text_DESIM);
		m_txtFName = (TextView)findViewById(R.id.Text_FName);
		m_txtLName = (TextView)findViewById(R.id.Text_LName);
		m_txtTelNo = (TextView)findViewById(R.id.Text_TelNo);
		m_txtAddr = (TextView)findViewById(R.id.Text_Addr);
		m_txtRemark = (TextView)findViewById(R.id.Text_Remark);
		
		m_editCarLicense = (EditText)findViewById( R.id.Edit_CarLicense );
		m_editDEUID = (EditText)findViewById( R.id.Edit_DEUID );
		m_spDEType = (Spinner)findViewById( R.id.Spinner_DEType );
		m_editDESIM = (EditText)findViewById( R.id.Edit_DESIM );
		m_editFName = (EditText)findViewById( R.id.Edit_FName );
		m_editLName = (EditText)findViewById( R.id.Edit_LName );
		m_editTelNo = (EditText)findViewById( R.id.Edit_TelNo );
		m_editAddr = (EditText)findViewById( R.id.Edit_Addr );
		m_editRemark = (EditText)findViewById( R.id.Edit_Remark );		
		
		m_bthOK = (Button)findViewById( R.id.OkButton );
		m_bthCancel = (Button)findViewById( R.id.cancelButton );		
	}
	//****************************************************
	//
	public  void  initLabel(){
		
		m_bthOK.setText( Language.getLangStr(Language.TEXT_OK) );
		m_bthCancel.setText( Language.getLangStr(Language.TEXT_CANCEL) );
		
		m_txtCarLicense.setText( Language.getLangStr(Language.TEXT_MONITOR_OBJECT) );
		m_txtDEUID.setText( Language.getLangStr(Language.TEXT_DEUID) );
		m_txtDEType.setText( Language.getLangStr(Language.TEXT_TYPE) );
		m_txtDESIM.setText( Language.getLangStr(Language.TEXT_DEVICE_SIM) );
		m_txtFName.setText( Language.getLangStr(Language.TEXT_FNAME) );
		m_txtLName.setText( Language.getLangStr(Language.TEXT_LNAME) );
		m_txtTelNo.setText( Language.getLangStr(Language.TEXT_TELNO) );
		m_txtAddr.setText( Language.getLangStr(Language.TEXT_ADDRESS) );
		m_txtRemark.setText( Language.getLangStr(Language.TEXT_REMARK) );
	}
	//****************************************************
	//
	public void  initEvent(){
		
		m_bthOK.setOnClickListener( eventOK );
		m_bthCancel.setOnClickListener( eventCancel );
	}
	//***************************************************
	//
	public void  getIntentData(){
		
		Bundle bunde = this.getIntent().getExtras();
		
		m_nWorkMode = bunde.getInt( ConfigKey.KEY_WORKMODE );
		if( m_nWorkMode == 0 ){
			m_editDEUID.setEnabled(true);
			this.setTitle( Language.getLangStr(Language.TEXT_ADD) );
		}
		else {
			if ( m_nWorkMode == 1) {				
				this.setTitle( Language.getLangStr(Language.TEXT_MODIFY) );
			}
			else{
				this.setTitle( Language.getLangStr(Language.TEXT_VIEW) );
			}
			m_editDEUID.setEnabled(false);
			m_editCarLicense.setText( bunde.getString( ConfigKey.KEY_CARLICENSE ) );
			m_editDEUID.setText( bunde.getString( ConfigKey.KEY_DEUID) );
			m_editDESIM.setText( bunde.getString( ConfigKey.KEY_DESIM) );
			m_editFName.setText( bunde.getString( ConfigKey.KEY_FNAME) );
			m_editLName.setText( bunde.getString( ConfigKey.KEY_LNAME) );
			m_editTelNo.setText( bunde.getString( ConfigKey.KEY_TEL) );
			m_editAddr.setText( bunde.getString( ConfigKey.KEY_ADDR) );
			m_editRemark.setText( bunde.getString( ConfigKey.KEY_REMARK) );		
			m_nDEType = bunde.getInt( ConfigKey.KEY_DETYPE );
		}
		String  strDEType = DEType.GetDeviceTypeString(m_nDEType);
		for( int nCnt =0; nCnt < m_DETypeAdapter.getCount(); nCnt++ ){
		
			if( m_DETypeAdapter.getItem( nCnt ).equals( strDEType) ){
				
				m_spDEType.setSelection(nCnt);
				break;
			}
		}
	}
	//****************************************************
	//
	public  boolean  getCarInfo( Bundle  bundle ){
		
		String		strData;
		
		strData = m_editDEUID.getText().toString();
		if( (strData.length() >= Structs.TEXT_DEUID_LEN ) ||
			(strData.length() < Structs.TEXT_DEUID_LEN-1 ) )	{
			showWarringDialog( Language.getLangStr(Language.TEXT_WARRIT_DEUID_ERROR));
			return false;
		}
		strData = m_editCarLicense.getText().toString();
	//	if( strData.isEmpty() ){	
		if( strData.length() <= 0){	
			showWarringDialog( Language.getLangStr(Language.TEXT_MONITORY_EMPTY));
			return false;
		}		
		bundle.putString( ConfigKey.KEY_CARLICENSE, m_editCarLicense.getText().toString() );
		bundle.putString( ConfigKey.KEY_DEUID, m_editDEUID.getText().toString() );
		bundle.putString( ConfigKey.KEY_DESIM, m_editDESIM.getText().toString() );
		bundle.putString( ConfigKey.KEY_FNAME, m_editFName.getText().toString() );
		bundle.putString( ConfigKey.KEY_LNAME, m_editLName.getText().toString() );
		bundle.putString( ConfigKey.KEY_TEL, m_editTelNo.getText().toString() );
		bundle.putString( ConfigKey.KEY_ADDR, m_editAddr.getText().toString() );
		bundle.putString( ConfigKey.KEY_REMARK, m_editRemark.getText().toString() );
		
		m_nDEType = DEType.GetDeviceTypeByString(m_spDEType.getSelectedItem().toString() );
		bundle.putInt( ConfigKey.KEY_DETYPE, m_nDEType );
		
		return true;
	}
	//****************************************************
	//
	OnClickListener	 eventOK = new OnClickListener(){

		@Override
		public void onClick(View v) {
			
			Bundle		bundle = null;
			
			if( m_nWorkMode == 2 ){
				finish(); 
				return ;
			}	
			bundle = new Bundle();
			if( getCarInfo(bundle) == true ){
				
				Intent	intent = new Intent();
				intent.setClass(CarInfoActivity.this, VehicleList.class);   
				intent.putExtras( bundle );			
				setResult(RESULT_OK, intent );
				finish();
			}
		}
	};
	//****************************************************
	//
	OnClickListener	 eventCancel = new OnClickListener(){

		@Override
		public void onClick(View v) {
			finish();
		}
	};
	//******************************************************
	//
	public  void   showWarringDialog( String  strWarring ){
		
		
		AlertDialog.Builder  builder = new AlertDialog.Builder(CarInfoActivity.this);
		
		builder.setTitle(Language.getLangStr(Language.TEXT_WARNING));
		builder.setMessage(strWarring )
			   .setPositiveButton(Language.getLangStr(Language.TEXT_OK), null)
			   .setNegativeButton(Language.getLangStr(Language.TEXT_CANCEL), null);	
		AlertDialog dlg = builder.create();
		dlg.show();
	}
	//****************************************************
	//
	@Override
	public void finish() {

		super.finish();
	}
	//****************************************************
	//
	@Override
	protected void onDestroy() {

		super.onDestroy();
	}	
}

//****************************************************
//
//
class DEType{
	
	public static final int  DETYPE_STANDARD_A = 0;
	public static final int  DETYPE_STANDARD_B = 1;
	public static final int  DETYPE_ENHANCE = 2;
	public static final int  DETYPE_ADVANCE = 3;	
	public static final int  DETYPE_SIMPLE_A = 4;
	public static final int  DETYPE_SIMPLE_B = 5;
	public static final int  DETYPE_SIMPLE_C = 6;
	public static final int  DETYPE_SIMPLE_D = 7;
	public static final int  DETYPE_UNKNOW = 8;
	
	
	public static final int [][] g_nDeviceType={
		
		{Language.TEXT_SIMPLEA,		DETYPE_SIMPLE_A},
		{Language.TEXT_SIMPLEB,		DETYPE_SIMPLE_B},
		{Language.TEXT_SIMPLEC,		DETYPE_SIMPLE_C},
		{Language.TEXT_SIMPLED,		DETYPE_SIMPLE_D},
		{Language.TEXT_STANDARDA,		DETYPE_STANDARD_A},
		{Language.TEXT_STANDARDB,		DETYPE_STANDARD_B},
		{Language.TEXT_ENHANCE,		DETYPE_ENHANCE},
		{Language.TEXT_ADVANCE,		DETYPE_ADVANCE},
		{Language.TEXT_UNKNOW,		DETYPE_UNKNOW},
		{-1,						-1}
	};
	//****************************************************
	//
	public static void GetDeviceTypeArray( List<String>  outData ){

		int			nCnt = 0;

		while (true){
			if( g_nDeviceType[nCnt][0] == -1 ){
				break;
			}
			outData.add( Language.getLangStr(g_nDeviceType[nCnt][0]));
			nCnt++;
		}
	}
	//****************************************************
	//
	public static String GetDeviceTypeString( int nType ){
		
		int			nCnt = 0;
		String		strResult;	
		
		while (true){
			if( g_nDeviceType[nCnt][1] == DETYPE_UNKNOW ||
				g_nDeviceType[nCnt][1] == nType ){			
				break;
			}
			nCnt++;
		}
		strResult = Language.getLangStr( g_nDeviceType[nCnt][0] );
		return strResult;
	}
	//****************************************************
	//
	public static int	GetDeviceTypeByString( String str ){
	
		int				nType = DETYPE_UNKNOW;
		int				nCnt = 0;
		
		while (true){
			if( g_nDeviceType[nCnt][1] == DETYPE_UNKNOW ||
				Language.getLangStr(g_nDeviceType[nCnt][0]).equals(str) ){
				break;
			}
			nCnt++;
		}
		nType = g_nDeviceType[nCnt][1];
		return nType;
	}
}
