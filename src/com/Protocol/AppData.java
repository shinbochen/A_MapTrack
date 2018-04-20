package com.Protocol;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import org.apache.http.util.EncodingUtils;

//////////////////////////////////////////////////////////////////////////
//应用层
//|   1        |	 1	     |	 2	      |	 n	   |
//---------------------------------
//|应用数据个数|应用数据类型|应用数据长度|应用数据|


//#define PACKAGESIZE			2048

//#define WHERE_SUM_APPDATA	0x00
//#define WHERE_TYPE_APPDATA	0x01
//#define WHERE_LEN_APPDATA		0x02
//#define WHERE_DATA_APPDATA	0x04
//应用数据
//--------------------------------------------------
//|	 1	     |	 2	     |	 n	  |
//-------------------------------------------------
//|应用数据类型|应用数据长度|应用数据|
public class AppData{

	AppData( ){
	
	}
	//***********************************************
	//  nBuf[0]  byte3
	//	nBuf[1]  byte2
	//	nBuf[2]  byte1
	//	nBuf[3]  byte0
	public static int     ByteValueToInt(  byte nHig, byte nLow ){
	
		int			nResult = 0;
		BigInteger  bInt = null;
		byte[]  	nBuf = new byte[4];
		
		nBuf[0] = 0x00;
		nBuf[1] = 0x00;
		nBuf[2] = nHig;
		nBuf[3] = nLow;  
		bInt = new BigInteger(nBuf);
		nResult = bInt.intValue();
		
		return nResult;
	}
	//***********************************************
	//  nBuf[0]  byte4
	//	nBuf[1]  byte3
	//	nBuf[2]  byte2
	//	nBuf[3]  byte1
	public static int     ByteValueToInt(  byte nHig4, 
											byte nHig3,
											byte nHig2,
											byte nHig1){
	
		int			nResult = 0;
		BigInteger  bInt = null;
		byte[]  	nBuf = new byte[4];
		
		Arrays.fill(nBuf, (byte)0x00 );
		nBuf[0] = nHig4;
		nBuf[1] = nHig3;
		nBuf[2] = nHig2;
		nBuf[3] = nHig1;  
		bInt = new BigInteger(nBuf);
		nResult = bInt.intValue();	//1897316355
		
		return nResult;
	}
	//***********************************************
	//	高位在前
	//
	public  static byte[]  doubleToByteValues( double  dbData ){
	
		byte[] nResultBuf = new byte[8];
		
		Arrays.fill(nResultBuf, (byte)0x00 );
		long lData = Double.doubleToLongBits(dbData);
		nResultBuf[0] = (byte)(lData >> 56);
		nResultBuf[1] = (byte)(lData >> 48);
		nResultBuf[2] = (byte)(lData >> 40);
		nResultBuf[3] = (byte)(lData >> 32);
		nResultBuf[4] = (byte)(lData >> 24);
		nResultBuf[5] = (byte)(lData >> 16);
		nResultBuf[6] = (byte)(lData >>  8);
		nResultBuf[7] = (byte)(lData >>  0);
		
		return  nResultBuf;
	}
	//***********************************************
	//  高位在前
	public  static double   ByteValueToDouble( byte  nHig8,
												byte  nHig7,
												byte  nHig6,
												byte  nHig5,
												byte  nHig4,
												byte  nHig3,
												byte  nHig2,
												byte  nHig1	){
	
		byte[]		nBuf = new byte[8];
		
		Arrays.fill(nBuf, (byte)0 );
		nBuf[0] =  nHig8 ;
		nBuf[1] =  nHig7 ;
		nBuf[2] =  nHig6 ;
		nBuf[3] =  nHig5 ;
		nBuf[4] =  nHig4 ;
		nBuf[5] =  nHig3 ;
		nBuf[6] =  nHig2 ;
		nBuf[7] =  nHig1 ;
		
		return Double.longBitsToDouble((((long)nBuf[0] << 56) +
										((long)(nBuf[1] & 255) << 48) +
										((long)(nBuf[2] & 255) << 40) +
										((long)(nBuf[3] & 255) << 32) +
										((long)(nBuf[4] & 255) << 24) +
										((nBuf[5] & 255) << 16) +
										((nBuf[6] & 255) <<  8) +
										((nBuf[7] & 255) <<  0)) );
	}
	//******************************************************
	//
	public static	boolean	ComposeData( Data oData, byte nType, byte[] lpBuf, int nLen ){
	
		oData.MallocBuf( 1 + 2 + nLen );
		oData.AddData( nType );
		oData.AddData( (byte)(nLen&0xFF) );
		oData.AddData( (byte)((nLen >> 8) & 0xFF));
		
		oData.AddData( lpBuf, nLen );
		
		return true;
	}
	//******************************************************
	//
	public static String  ByteToStringGBK( byte[] lpBuf ){
	
		byte[]	  tmpBuf=null;
		int 	  nCnt = 0;
		String    strResult;
		
		for( nCnt = 0; nCnt < lpBuf.length; nCnt++) {
			if( lpBuf[nCnt] == 0x00 ){
				break;
			}
		}		
		if( nCnt > 0 ){			
			tmpBuf = new byte[nCnt];
			System.arraycopy(lpBuf, 0, tmpBuf, 0, nCnt );
			strResult = EncodingUtils.getString( tmpBuf, "GBK" );
		}
		else{
			strResult = "";
		}
		return strResult;
	}
	//******************************************************
	//
	public static String  ByteToString( byte[] lpBuf ){
	
		byte[]	  tmpBuf=null;
		int 	  nCnt = 0;
		String    strResult;
		
		for( nCnt = 0; nCnt < lpBuf.length; nCnt++) {
			if( lpBuf[nCnt] == 0x00 ){
				break;
			}
		}		
		if( nCnt > 0 ){
			
			tmpBuf = new byte[nCnt];
			System.arraycopy(lpBuf, 0, tmpBuf, 0, nCnt );	
			strResult = new String(tmpBuf );
		}
		else{
			strResult = "";
		}
		return strResult;
	}
	   
    //*************************************************
	//
	public static String  getCurrentTime( long  nMillis ){
		
		Date				de = null;
		SimpleDateFormat 	oFormat= null;
		
		de = new Date();		
		de.setTime( nMillis );
		oFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return oFormat.format(de);
	}
	//////////////////////////////////////////////////////////////////////////
	//解析　数据及长度在CData中　　　
	//返回　数据类型
	public static	byte	ParseData( Data oData, byte[] lpBuf, int nLen ){
	
		byte		nAPPType = 0;		
		int			nAPPLen = 0;
		byte[]		nBuf = null;
		
		// 多分配了几个字节
		oData.MallocBuf( nLen );
		nAPPType = lpBuf[0];
		nAPPLen = ByteValueToInt( lpBuf[2], lpBuf[1] );
		
		nBuf = new byte[nAPPLen];
		System.arraycopy(lpBuf, 3, nBuf, 0, nAPPLen );
		oData.AddData( nBuf, nAPPLen );
		return nAPPType;
	}
}