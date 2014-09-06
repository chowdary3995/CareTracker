package umkc.ase.caretracker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class HomeActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home);
		
		Button trackerButton = (Button)findViewById(R.id.trackerButton);
		trackerButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(new Intent(getApplicationContext(),TrackerMapActivity.class));
			}
		});
		
		Button setBoundaryButton = (Button)findViewById(R.id.setBoundary);
		setBoundaryButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(new Intent(getApplicationContext(),SetBoundaryActivity.class));
			}
		});
		
		Button getBoundaryButton = (Button)findViewById(R.id.getBoundary);
		getBoundaryButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(new Intent(getApplicationContext(),GetBoundaryActivity.class));
			}
		});
		
		Button feedback = (Button)findViewById(R.id.feedback);
		feedback.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(new Intent(getApplicationContext(),FeedbackActivity.class));
			}
		});
	}

}
