package umkc.ase.caretracker;

import org.ksoap2.serialization.SoapObject;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

public class TrackerMapActivity extends FragmentActivity {

	String[] childLocations;
	GoogleMap MAP;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.trackermap);
		ProgressDialog mDialog = new ProgressDialog(this);
		mDialog.setMessage("Getting child location...");
		mDialog.setCancelable(false);
		mDialog.show();
		getChildLocation();
		SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
				.findFragmentById(R.id.map);
		MAP  = mapFragment.getMap();
		PolylineOptions lineOptions = new PolylineOptions().width(15)
				.color(Color.BLUE).geodesic(true);
		LatLng ll = null;
		Marker marker = null;
		String title = null;
		for (String loc : childLocations) {
			// 39.03769,-94.58515,4/28/2013 9:05:13 PM
			String[] s = loc.split(",");
			ll = new LatLng(Double.parseDouble(s[0]), Double.parseDouble(s[1]));
			title = s[2];
			lineOptions.add(ll);
			marker = MAP.addMarker(new MarkerOptions().position(ll)
					.title(title));
		}
		marker.remove();
		marker = MAP.addMarker(new MarkerOptions().position(ll).title(title)
				.icon(BitmapDescriptorFactory.fromResource(R.drawable.kidmap)));
		MAP.addPolyline(lineOptions);
		CameraPosition cameraP = new CameraPosition.Builder().target(ll)
				.zoom(14).bearing(90) // Sets the orientation of the camera to
										// east
				.tilt(30) // Sets the tilt of the camera to 30 degrees
				.build();
		MAP.animateCamera(CameraUpdateFactory.newCameraPosition(cameraP));
		MAP.setMapType(GoogleMap.MAP_TYPE_HYBRID);
		mDialog.dismiss();

	}

	public void getChildLocation() {
		SoapObject soapObject = new SoapObject(RegisterActivity.NAMESPACE,
				RegisterActivity.GET_CHILD_LOC_METHOD);
		soapObject.addProperty("parentUsrName", "abc");
		childLocations = RegisterActivity.serviceCall2(soapObject,
				RegisterActivity.SOAP_ACTION_GET_CHILD_LOC);
	}
	
	public void setNormalView(View v){
		SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
				.findFragmentById(R.id.map);
		MAP = mapFragment.getMap();
		MAP.setMapType(GoogleMap.MAP_TYPE_NORMAL);
	}
	public void setHybridView(View v){
		SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
				.findFragmentById(R.id.map);
		MAP = mapFragment.getMap();
		MAP.setMapType(GoogleMap.MAP_TYPE_HYBRID);
	}
}
