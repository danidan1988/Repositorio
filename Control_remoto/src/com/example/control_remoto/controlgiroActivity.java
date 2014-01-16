package com.example.control_remoto;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.UUID;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class controlgiroActivity extends Activity implements SensorEventListener {
	
	Button Connect2;
	private String datos_envio;
    private OutputStream outStream = null;
    private BluetoothSocket btSocket = null;
    private BluetoothAdapter mBluetoothAdapter = null;
    Handler handler = new Handler();
    private static final String TAG = "Conectado";
    byte delimiter = 10;
    boolean stopWorker = false;
    int readBufferPosition = 0;
    byte[] readBuffer = new byte[1024];
    private static String address = "98:D3:31:50:0D:AC";
    private static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
	TextView x,y,z;
	StringBuilder builder = new StringBuilder();
	private  Sensor mAccelerometer;

	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.giro);

    x = (TextView)findViewById(R.id.xID);
    y = (TextView)findViewById(R.id.yID);
    z = (TextView)findViewById(R.id.zID);
	ComprobarBt();
    BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);
    Log.e("Conectado", device.toString());
    Conectar();

   	}

					

	@Override
	public void onSensorChanged(SensorEvent event) {
		
		float valx,valy;
		
		valx = event.values[0];
		valy = event.values[1];
		
		if(valy<-2){
			
			if((valx<2)&&(valx>-2)){
				
				datos_envio = "a";
	        	writeData(datos_envio);
	        	
			}
				         
			if(valx<-2){
    			
				datos_envio = "b";
    			writeData(datos_envio);
    			
    		}
        
			if(valx>2){
    			
				datos_envio = "c";
            	writeData(datos_envio);
            	
    		}
            
        }
				
		if(valy>2){
			
			if((valx<2)&&(valx>-2)){
				
				datos_envio = "d";
	        	writeData(datos_envio);
	        	
			}
			
			if(valx<-2){
    			
				datos_envio = "e";
                writeData(datos_envio);
                
    		}
			
            if(valx>2){
    			
            	datos_envio = "f";
                writeData(datos_envio);
                
    		}
			
		}
		
		if((valy<2)&&(valy>-2)){
			
			datos_envio = "s";
            writeData(datos_envio);
            
		}
		
		
		 this.x.setText("X = "+event.values[SensorManager.DATA_X]);
		 this.y.setText("Y = "+event.values[SensorManager.DATA_Y]);
		 this.z.setText("Z = "+event.values[SensorManager.DATA_Z]);

	}
	
    protected void onResume(){
    	
    	 super.onResume();
         SensorManager sm = (SensorManager) getSystemService(SENSOR_SERVICE);
         List<Sensor> sensors = sm.getSensorList(Sensor.TYPE_ACCELEROMETER);
         if (sensors.size() > 0) 
         {
             sm.registerListener(this, sensors.get(0), SensorManager.SENSOR_DELAY_GAME);
         }
         
    }
    protected void onPause(){
    	
    	SensorManager mSensorManager=(SensorManager) getSystemService(SENSOR_SERVICE);
    	mSensorManager.unregisterListener(this, mAccelerometer);
    	super.onPause();
    	
    }
    protected void onStop(){
    	
    	SensorManager mSensorManager=(SensorManager) getSystemService(SENSOR_SERVICE);
    	mSensorManager.unregisterListener(this, mAccelerometer);
    	super.onStop();
    	
    }
    
	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy){
		
		}
	
private void writeData(String data){
	
    try {
            outStream = btSocket.getOutputStream();
    } catch (IOException e) {
            Log.d(TAG, "Error antes de enviar información", e);
    }

    String message = data;
    byte[] msgBuffer = message.getBytes();

    try {
            outStream.write(msgBuffer);
    } catch (IOException e) {
            Log.d(TAG, "Error mientras se envía la información", e);
    }
}

private void ComprobarBt() {
    mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

    if (!mBluetoothAdapter.isEnabled()) {
            Toast.makeText(getApplicationContext(), "Bluetooth Disabled !",Toast.LENGTH_SHORT).show();
    }

    if (mBluetoothAdapter == null) {
            Toast.makeText(getApplicationContext(),"Bluetooth null !", Toast.LENGTH_SHORT).show();
    }
}

public void Conectar() {
	
    Log.d(TAG, address);
    BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);
    Log.d(TAG, "Connecting to ... " + device);
    mBluetoothAdapter.cancelDiscovery();
    try {
            btSocket = device.createRfcommSocketToServiceRecord(MY_UUID);
            btSocket.connect();
            Log.d(TAG, "Conexión realizada.");
    } catch (IOException e) {
            try {
                    btSocket.close();
            } catch (IOException e2) {
                    Log.d(TAG, "No se pudo terminar la conexión");
            }
            Log.d(TAG, "Fallo al crear la conexión");
    }
   
 }

@Override
protected void onDestroy(){
	
super.onDestroy();

    try {
            btSocket.close();
    } catch (IOException e) {
    }
    
}

}
