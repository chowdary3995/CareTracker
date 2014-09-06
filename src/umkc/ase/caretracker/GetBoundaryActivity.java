package umkc.ase.caretracker;

import org.ksoap2.serialization.SoapObject;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolygonOptions;

public class GetBoundaryActivity extends FragmentActivity {

	private GoogleMap MAP;
	private PolygonOptions rectoptions = new PolygonOptions();
	String boundary;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.getboundarylayout);
		getCurrentBoundary();
		SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
				.findFragmentById(R.id.currentboundarymap);
		MAP = mapFragment.getMap();
		MAP.setMyLocationEnabled(true);
		MAP.setMapType(GoogleMap.MAP_TYPE_HYBRID);

		LatLng ll = null;
		String[] points = boundary.split(";");
		for (String s : points) {
			String[] boundPoint = s.split(",");
			ll = new LatLng(Double.parseDouble(boundPoint[0]),
					Double.parseDouble(boundPoint[1]));
			rectoptions.add(ll);
			rectoptions.fillColor(Color.BLUE);
		}
		CameraPosition cameraP = new CameraPosition.Builder().target(ll)
				.zoom(14).bearing(90).tilt(30).build();
		MAP.animateCamera(CameraUpdateFactory.newCameraPosition(cameraP));

		MAP.addPolygon(rectoptions);
	}

	public void getCurrentBoundary() {
		SoapObject soapObject = new SoapObject(RegisterActivity.NAMESPACE,
				RegisterActivity.GET_BOUNDARY_METHOD);
		soapObject.addProperty("childId", "1000");
		boundary = RegisterActivity.serviceCall(soapObject,
				RegisterActivity.SOAP_ACTION_GET_BOUNDARY);
	}
	
	public void setNormalView(View v){
		SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
				.findFragmentById(R.id.currentboundarymap);
		MAP = mapFragment.getMap();
		MAP.setMapType(GoogleMap.MAP_TYPE_NORMAL);
	}
	public void setHybridView(View v){
		SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
				.findFragmentById(R.id.currentboundarymap);
		MAP = mapFragment.getMap();
		MAP.setMapType(GoogleMap.MAP_TYPE_HYBRID);
	}

}
