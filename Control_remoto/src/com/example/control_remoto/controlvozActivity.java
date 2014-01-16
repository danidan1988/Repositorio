package com.example.control_remoto;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.UUID;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class controlvozActivity  extends Activity {
	 private static final int VOICE_RECOGNITION_REQUEST_CODE = 1;
	 private Button boton_voz;
	 Button Connect3;
	 private String datos_envio;
	 private OutputStream outStream = null;
	 private BluetoothSocket btSocket = null;
	 private BluetoothAdapter mBluetoothAdapter = null;
	 Handler handler = new Handler();
	 private static final String TAG = "Conectar";
	 byte delimiter = 10;
	 boolean stopWorker = false;
	 int readBufferPosition = 0;
	 byte[] readBuffer = new byte[1024];
	 private static String address = "98:D3:31:50:0D:AC";
	 private static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");


	 @Override 
	 protected void onCreate(Bundle savedInstanceState) {
	  super.onCreate(savedInstanceState);
	  setContentView(R.layout.voz); 
      ComprobarBt();
      BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);
      Log.e("Conectado", device.toString());
      Conectar();
      boton_voz = (Button)findViewById(R.id.button1); 
      boton_voz.setOnClickListener(new OnClickListener() {
	   @Override
	   public void onClick(View v) {
	
	    startVoiceRecognitionActivity();
	    datos_envio = "s";
    	writeData(datos_envio);
    	
	   }
	  });

	 }
	 
	  private void startVoiceRecognitionActivity() {
		  
		  Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
		  intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
		  RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
		  intent.putExtra(RecognizerIntent.EXTRA_PROMPT,"Diga, hacia donde quiere ir");
		  startActivityForResult(intent, VOICE_RECOGNITION_REQUEST_CODE);
		  
	 }
	 
	 @Override
	
	 protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	 
	  if(requestCode == VOICE_RECOGNITION_REQUEST_CODE && resultCode == RESULT_OK){
	  
	    ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
	    String [ ] palabras = matches.get(0).toString().split(" ");
	
	    if((palabras[0].equals("adelante"))&&(palabras[1].equals("recto"))){
	    	
	    	datos_envio = "a";
        	writeData(datos_envio);
	    	
     	    }
	    
	    if((palabras[0].equals("adelante"))&&(palabras[1].equals("derecha"))){
	    	
	    	datos_envio = "b";
        	writeData(datos_envio);
	    	
       	    }
	    
	    if((palabras[0].equals("adelante"))&&(palabras[1].equals("izquierda"))){
	    	
	    	datos_envio = "c";
        	writeData(datos_envio);
	    	
       	    }
	    
	     if(palabras[0].equals("para")){
	    	
	    	datos_envio = "s";
        	writeData(datos_envio);
        	
	     	}	
 
	     if((palabras[0].equals("atrás"))&&(palabras[1].equals("recto"))){
	    	 
	    	 datos_envio = "d";
	    	 writeData(datos_envio);
	    	 
	     	}
	     if((palabras[0].equals("atrás"))&&(palabras[1].equals("derecha"))){
 	
	    	 datos_envio = "e";
	    	 writeData(datos_envio);
	     
	     	}
	     
	     if((palabras[0].equals("atrás"))&&(palabras[1].equals("izquierda"))){
 	
	    	 datos_envio = "f";
	    	 writeData(datos_envio);
	    	 
	    	 }
	  }
}
	 
	 private void writeData(String data) {
		    try {
		            outStream = btSocket.getOutputStream();
		    } catch (IOException e) {
		            Log.d(TAG, "Error antes de enviar la información", e);
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
		    	
		            Toast.makeText(getApplicationContext(), "Bluetooth Desactivado!",Toast.LENGTH_SHORT).show();
		    }

		    if (mBluetoothAdapter == null) {
		    	
		            Toast.makeText(getApplicationContext(),"No hay Bluetooth!", Toast.LENGTH_SHORT).show();
		            
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
		            Log.d(TAG, "Conexión realizada");
		            
		    } catch (IOException e) {
		            try {
		                    btSocket.close();
		            } catch (IOException e2) {
		                    Log.d(TAG, "No se pudo realizar la conexión");
		            }
		            Log.d(TAG, "Fallo en la creación de la conexión");
		    }
		   
		 }
}
