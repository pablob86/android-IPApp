package com.elapco.ipapp;



import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class IPapp extends Activity {
    /** Called when the activity is first created. */
	
	private EditText direccion;		
	private EditText mascara;
	private Button calcular;
	private TextView resultado;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        initControls();
      
    }
    
    private void initControls()

    {
    	direccion = (EditText) findViewById(R.id.editText1);
    	mascara = (EditText) findViewById(R.id.editText2);
    	resultado = (TextView) findViewById(R.id.textView1);
    	calcular = (Button)findViewById(R.id.button1);
    	calcular.setOnClickListener(new Button.OnClickListener() { 
    		public void onClick (View v){ 
    			calcular(); 
    			}
    		});
    	
    }
    
    private void calcular(){
    	
    	
    	
    	 DirIP myIP = new DirIP();
         try {

             myIP.setDir(direccion.getText().toString());
             myIP.setMask(mascara.getText().toString());
         
            
            resultado.setText("*** RESULT ***\n\n");
          
            resultado.append("IP ADDRESS: "+ myIP.getDirString()+"\n");
            resultado.append("("+myIP.getDirBinString()+")\n\n");
            resultado.append("MASK: "+myIP.getMaskString()+"\n");
            resultado.append("("+myIP.getMaskBinString()+")\n");
            resultado.append("Prefix length: "+String.valueOf(myIP.getLongPrefijo())+"\n\n");
            resultado.append("NETWORK ADDRESS: "+myIP.getRedString()+"\n");
            resultado.append("("+myIP.getRedBinString()+")\n\n");
            resultado.append("BROADCAST ADDRESS: "+myIP.getBroadcastString()+"\n");
            resultado.append("("+myIP.getBroadcastBinString()+")\n\n");
            resultado.append("USABLE ADDRESSES:\n");
            resultado.append("Initial: "+myIP.getHostInicioString()+"\n");
            resultado.append("Final: "+myIP.getHostFinalString()+"\n");
             
            // Ocultar el teclado
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(direccion.getWindowToken(), 0);

        
         }catch(IllegalArgumentException ia){
         resultado.setText("¡Address and/or mask/prefix invalid!");
   
         }	
    	
    	
   	
    	
    }
    
}