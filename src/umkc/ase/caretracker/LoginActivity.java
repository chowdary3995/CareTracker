package umkc.ase.caretracker;


import org.ksoap2.serialization.SoapObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
		.permitAll().build();
        StrictMode.setThreadPolicy(policy);
       // requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.login);
       // getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, value);
        TextView registerScreen = (TextView) findViewById(R.id.link_to_register);
 
        // Listening to register new account link
        registerScreen.setOnClickListener(new View.OnClickListener() {
 
            public void onClick(View v) {
                // Switching to Register screen
                Intent i = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(i);
            }
        });
        
        
    }
    
    public void checkAndLogin(View view){
    	EditText usrname = (EditText)findViewById(R.id.login_usrname);
    	EditText pwd = (EditText)findViewById(R.id.login_pwd);
    	//SoapObject soapObject = new SoapObject(RegisterActivity.NAMESPACE, RegisterActivity.CHECKLOGIN_METHOD);
		//soapObject.addProperty("userName", usrname.getText().toString());
		//soapObject.addProperty("password", pwd.getText().toString());
		//String Response = RegisterActivity.serviceCall(soapObject,RegisterActivity.SOAP_ACTION_CHKLOGIN);
    	String Response = "true";
		if (Response != null && Response.equals("true")){
			Intent intent = new Intent(getApplicationContext(),HomeActivity.class);
			startActivity(intent);
			finish();
		}else{
			Toast.makeText(getApplicationContext(), "Login Failed!!", Toast.LENGTH_SHORT).show();
		}
    }
    
}
