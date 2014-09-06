package umkc.ase.caretracker;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Patterns;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends Activity {

	public final static String SOAP_ACTION_CHKUSR = "http://tempuri.org/UsernameAvailability";
	public final static String SOAP_ACTION_REGISTER = "http://tempuri.org/addUser";
	public final static String SOAP_ACTION_CHKLOGIN = "http://tempuri.org/checkLogin";
	public final static String SOAP_ACTION_GET_CHILD_LOC = "http://tempuri.org/getChildLocation";
	public final static String SOAP_ACTION_UPDATE_BOUNDARY = "http://tempuri.org/updateBoundary";
	public final static String SOAP_ACTION_GET_BOUNDARY = "http://tempuri.org/getCurrentBoundary";
	public final static String NAMESPACE = "http://tempuri.org/";
	public final static String CHECK_USERNAME_METHOD = "UsernameAvailability";
	public final static String REGISTER_METHOD = "addUser";
	public final static String CHECKLOGIN_METHOD = "checkLogin";
	public final static String GET_CHILD_LOC_METHOD = "getChildLocation";
	public final static String UPDATE_BOUNDARY_METHOD = "updateBoundary";
	public final static String GET_BOUNDARY_METHOD = "getCurrentBoundary";
	public final static String URL = "http://170.224.165.253/aspnet_client/LoginService/LoginService.asmx";
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
		StrictMode.setThreadPolicy(policy);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);
		TextView loginScreen = (TextView) findViewById(R.id.link_to_login);

		loginScreen.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				finish();
			}
		});
	}

	public void validateAndProceed(View view) {
		if (isInputvalid()) {
			Toast.makeText(getApplicationContext(), "Registration Successful!", Toast.LENGTH_SHORT).show();
			finish();
		}else{
			Toast.makeText(getApplicationContext(), "Invalid Inputs!", Toast.LENGTH_SHORT).show();
		}

	}

	private boolean isInputvalid() {
		boolean isInputValid = true;
		TextView firstName = (TextView) findViewById(R.id.reg_firstname);
		if (firstName.getText().toString().length() == 0) {
			firstName.setError("Must enter first name!");
			isInputValid = false;
		} else {
			firstName.setError(null);
		}
		TextView lastName = (TextView) findViewById(R.id.reg_lastname);
		if (lastName.getText().toString().length() == 0) {
			lastName.setError("Must enter last name!");
			isInputValid = false;
		} else {
			lastName.setError(null);
		}

		TextView email = (TextView) findViewById(R.id.reg_email);
		if (email.getText().toString().length() == 0
				|| !Patterns.EMAIL_ADDRESS.matcher(email.getText().toString())
						.matches()) {
			email.setError("Must enter a valid email!");
			isInputValid = false;
		} else {
			email.setError(null);
		}

		TextView city = (TextView) findViewById(R.id.reg_city);
		if (city.getText().toString().length() == 0) {
			city.setError("Must enter city!");
			isInputValid = false;
		} else {
			city.setError(null);
		}

		TextView username = (TextView) findViewById(R.id.reg_username);
		if (username.getText().toString().length() == 0) {
			username.setError("Must enter a user name!");
			isInputValid = false;
		} else {
			if (!checkUsername(username.getText().toString())) {
				username.setError("User name not available!");
				isInputValid = false;
			} else {
				username.setError(null);
			}
		}

		TextView pwd = (TextView) findViewById(R.id.reg_password);
		if (pwd.getText().toString().length() == 0) {
			pwd.setError("Must enter a password!");
			isInputValid = false;
		} else {
			pwd.setError(null);
		}

		if (isInputValid) {
			SoapObject soapObject = new SoapObject(NAMESPACE, REGISTER_METHOD);
			soapObject.addProperty("UserName",username.getText().toString());
			soapObject.addProperty("password",pwd.getText().toString().length());
			soapObject.addProperty("EmailId",email.getText().toString());
			soapObject.addProperty("FirstName",firstName.getText().toString());
			soapObject.addProperty("LastName",lastName.getText().toString());
			soapObject.addProperty("City",city.getText().toString());
			String Response = serviceCall(soapObject,SOAP_ACTION_REGISTER);
			if (Response == null || Response.equals("false"))
				isInputValid = false;
		}
		
		return isInputValid;
	}

	private boolean checkUsername(String username) {
		boolean result = false;

		SoapObject soapObject = new SoapObject(NAMESPACE, CHECK_USERNAME_METHOD);
		soapObject.addProperty("userName", username);
		String Response = serviceCall(soapObject,SOAP_ACTION_CHKUSR);
		if (Response != null && Response.equals("true"))
			result = true;

		return result;
	}

	public static String serviceCall(SoapObject soapObject, String SOAP_ACTION) {
		HttpTransportSE transport = new HttpTransportSE(URL);
		transport.debug = true;

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.dotNet = true;
		envelope.setOutputSoapObject(soapObject);
		String Response = null;

		try {
			transport.call(SOAP_ACTION, envelope);
			Response = envelope.getResponse().toString().trim();

		} catch (Exception e) {
			System.out.println("~~Call to web servie failed!!!!!!s&&"
					+ e.getMessage());
		}

		return Response;
	}
	
	public static String[] serviceCall2(SoapObject soapObject, String SOAP_ACTION) {
		HttpTransportSE transport = new HttpTransportSE(URL);
		transport.debug = true;

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.dotNet = true;
		envelope.setOutputSoapObject(soapObject);
		String[] arr = null;
		try {
			transport.call(SOAP_ACTION, envelope);
			SoapObject so = (SoapObject)envelope.getResponse();
			System.out.println("" + so.toString());
			int num = so.getPropertyCount();
			arr = new String[num];
			for(int i=0;i<num;i++){
				String property = so.getPropertyAsString(i);
			        arr[i] = property;
			}
		} catch (Exception e) {
			System.out.println("~~Call to web servie failed!!!!!!s&&"
					+ e.getMessage());
		}

		return arr;
	}
}
