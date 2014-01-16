package com.example.control_remoto;

import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class controlmanualActivity extends Activity implements OnClickListener {
	
	Button Connect, alante, alante_izq, alante_der, parar, atras_izq, atras_der, atras;
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

@Override
protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manual);
        
        alante = (Button) findViewById(R.id.alante);
        alante_izq = (Button) findViewById(R.id.alante_izq);
        alante_der = (Button) findViewById(R.id.alante_der);
        parar = (Button) findViewById(R.id.parar);
        atras = (Button) findViewById(R.id.atras);
        atras_izq = (Button) findViewById(R.id.atras_izq);
        atras_der = (Button) findViewById(R.id.atras_der);

        alante.setOnClickListener(this);
        alante_izq.setOnClickListener(this);
        alante_der.setOnClickListener(this);
        parar.setOnClickListener(this);
        atras.setOnClickListener(this);
        atras_izq.setOnClickListener(this);
        atras_der.setOnClickListener(this);
        
        ComprobarBt();
        BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);
        Log.e("Conectado", device.toString());
        Conectar();
        
}

@Override
public void onClick(View control) {
        switch (control.getId()) {
       
        case R.id.alante:
            
        	datos_envio = "a";
        	writeData(datos_envio);
        	
        break;
        
        case R.id.alante_izq:
            
        	datos_envio = "c";
        	writeData(datos_envio);
        break;
        
        case R.id.alante_der:
            
        	datos_envio = "b";
        	writeData(datos_envio);
        	
        break;
                
        case R.id.parar:
            
        	datos_envio = "s";
        	writeData(datos_envio);
        	
        break;
        
        case R.id.atras:
            
        	datos_envio = "d";
        	writeData(datos_envio);
        	
        break;
                
        case R.id.atras_izq:

        	datos_envio = "f";
        	writeData(datos_envio);
        	
        break;
        	
        case R.id.atras_der:

        	datos_envio = "e";
        	writeData(datos_envio);
        
        break;
        
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

@Override
protected void onDestroy() {
	
super.onDestroy();

    try {
            btSocket.close();
            
    } catch (IOException e) {
    	
    }
}


}


