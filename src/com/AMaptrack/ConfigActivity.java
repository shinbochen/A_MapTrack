package com.AMaptrack;

import com.Language.Language;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ConfigActivity extends Activity {
	
	public  Button			m_bthOK = null;
	public  Button			m_bthCancel = null;
	public  EditText		m_editIP = null;
	public  EditText		m_editPort = null;
	
	public  TextView		m_txtIP = null;		
	public  TextView		m_txtPort = null;	
	
	//***********************************************
	//
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.config);
		initEventObj();
		InitEvent();
		initLabel();
		getIntentData();
	}
	//********************************************
	//
	public  void  initEventObj(){
				
		m_editIP = (EditText)findViewById(R.id.edit_IP);
		m_editPort = (EditText)findViewById(R.id.edit_Port );
		
		m_txtIP = (TextView)findViewById(R.id.text_IP);
		m_txtPort = (TextView)findViewById(R.id.text_Port);
		
		m_bthOK = (Button)findViewById(R.id.OKButton);
		m_bthCancel = (Button)findViewById(R.id.cancelButton);
		
		m_editIP.setEnabled(false);
		m_editPort.setEnabled(false);
	}
	//********************************************
	//
	public  void  initLabel(){
		
		m_txtIP.setText(Language.getLangStr(Language.TEXT_SERVER_IP) );
		m_txtPort.setText(Language.getLangStr(Language.TEXT_PORT) );
		
		m_bthOK.setText( Language.getLangStr(Language.TEXT_OK) );
		m_bthCancel.setText( Language.getLangStr(Language.TEXT_CANCEL) );
	}
	//********************************************
	//
	public void  InitEvent(){
		
		m_bthOK.setOnClickListener( eventOk );
		m_bthCancel.setOnClickListener( eventCancel );
	}
	//***************************************************
	//
	public void  getIntentData(){
		
		Bundle bunde = this.getIntent().getExtras();
		
		m_editIP.setText( bunde.getString(ConfigKey.KEY_IP) );
		m_editPort.setText( Integer.toString(bunde.getInt(ConfigKey.KEY_PORT)));
	}
	//********************************************
	//
	OnClickListener  eventOk = new OnClickListener(){

		@Override
		public void onClick(View v) {

			Bundle bunde = new Bundle();
			
			bunde.putString(ConfigKey.KEY_IP,  m_editIP.getText().toString() );
			bunde.putInt(ConfigKey.KEY_PORT,  Integer.parseInt( m_editPort.getText().toString() ) );
			
			Intent	intent = new Intent();
			intent.setClass(ConfigActivity.this, Login.class);   
			intent.putExtras( bunde );			
			setResult(RESULT_OK, intent );
			finish();
		}
	};
	//********************************************
	//
	OnClickListener  eventCancel = new OnClickListener(){

		@Override
		public void onClick(View v) {

			finish();
		}
	};
	//***********************************************
	//
	@Override
	public void finish() {

		super.finish();
	}
	//***********************************************
	//
	@Override
	protected void onDestroy() {

		super.onDestroy();
	}
}
