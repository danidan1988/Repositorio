package com.example.control_remoto;
 
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

 public class MainActivity extends Activity implements OnClickListener {
 
        Button  Control_manual,Control_giroscopio,Control_voz;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.main);
 
                Control_manual = (Button) findViewById(R.id.controlm);
                Control_manual.setOnClickListener(this);
                Control_giroscopio = (Button) findViewById(R.id.controlg);
                Control_giroscopio.setOnClickListener(this);
                Control_voz= (Button) findViewById(R.id.controlv);
                Control_voz.setOnClickListener(this);
               
        }
 
        @Override
        public void onClick(View control) {
        	
                switch (control.getId()) {
                
                case R.id.controlm:
                    
                	Intent j = new Intent(this,controlmanualActivity.class);
                	startActivity(j);
                	
                break;
                
                case R.id.controlg:
                    
                	Intent i = new Intent(this,controlgiroActivity.class);
                	startActivity(i);
                	
                break;
                
                case R.id.controlv:
                    
                	Intent k = new Intent(this,controlvozActivity.class);
                	startActivity(k);
                	
                break;

                }
        }
}
 

       
