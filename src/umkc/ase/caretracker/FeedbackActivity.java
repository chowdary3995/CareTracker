package umkc.ase.caretracker;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;

public class FeedbackActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.feedback);

		LinearLayout layout = (LinearLayout) findViewById(R.id.feedbackLinearLayout);
		layout.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View view, MotionEvent ev) {
				hideKeyboard(view);
				return false;
			}
		});
	}

	public void sendFeedback(View v) {
		final EditText nameField = (EditText) findViewById(R.id.EditTextName);
		String name = nameField.getText().toString();
		final EditText emailField = (EditText) findViewById(R.id.EditTextEmail);
		String email = emailField.getText().toString();
		final RadioGroup rGroup = (RadioGroup) findViewById(R.id.radioGroup1);
		RadioButton checkedRadioButton = (RadioButton) rGroup
				.findViewById(rGroup.getCheckedRadioButtonId());
		String radiogrpValue = checkedRadioButton.getText().toString();
		final EditText feedbackField = (EditText) findViewById(R.id.EditTextMessage);
		String feedback = feedbackField.getText().toString();
		final RatingBar ratingField = (RatingBar) findViewById(R.id.ratingBar);
		String rating = String.valueOf(ratingField.getRating());
		StringBuffer message = new StringBuffer();
		message.append("Rating : " + rating + "\nFeedback Type: "
				+ radiogrpValue + "\nFeedback comments: " + feedback);
		sendData(name, "kcsc95@mail.umkc.edu", email,
				"Caretracker App Feedback", message.toString());

		finish();

	}

	public static void sendData(String name, String to, String from,
			String subject, String message) {
		String content = "";

		try {
			/* Sends data through a HTTP POST request */
			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(
					"http://php.umkc.edu/intapps/android/android_feedback.php");
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("name", name));
			params.add(new BasicNameValuePair("to", to));
			params.add(new BasicNameValuePair("from", from));
			params.add(new BasicNameValuePair("subject", subject));
			params.add(new BasicNameValuePair("message", message));
			httpPost.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));

			/* Reads the server response */
			HttpResponse response = httpClient.execute(httpPost);
			InputStream in = response.getEntity().getContent();

			StringBuffer sb = new StringBuffer();
			int chr;
			while ((chr = in.read()) != -1) {
				sb.append((char) chr);
			}
			content = sb.toString();
			in.close();

			/* If there is a response, display it */
			if (!content.equals("")) {
				Log.i("HTTP Response", content);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void hideKeyboard(View view) {
		InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		in.hideSoftInputFromWindow(view.getWindowToken(),
				InputMethodManager.HIDE_NOT_ALWAYS);
	}

}
