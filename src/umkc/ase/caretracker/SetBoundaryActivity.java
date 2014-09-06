package umkc.ase.caretracker;

import org.ksoap2.serialization.SoapObject;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;

public class SetBoundaryActivity extends FragmentActivity {

	private GoogleMap MAP;
	private boolean markClick;
	private PolygonOptions rectoptions;
	private Polygon poliline;
	static final LatLng base = new LatLng(39.032986,-94.579539);
	StringBuilder boundary = new StringBuilder("");

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		// setTheme(R.style.Theme_Sherlock);
		super.onCreate(arg0);
		setContentView(R.layout.setboundarylayout);
		SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
				.findFragmentById(R.id.boundarymap);
		MAP = mapFragment.getMap();
		MAP.setMyLocationEnabled(true);
		MAP.setMapType(GoogleMap.MAP_TYPE_HYBRID);
		CameraPosition cameraP = new CameraPosition.Builder().target(base)
				.zoom(13).bearing(90).tilt(30).build();
		MAP.animateCamera(CameraUpdateFactory.newCameraPosition(cameraP));
		MAP.setOnMapClickListener(new OnMapClickListener() {
			@Override
			public void onMapClick(LatLng point) {
				MAP.addMarker(new MarkerOptions().position(point).title(
						point.toString()));
			}
		});
		MAP.setOnMarkerClickListener(new OnMarkerClickListener() {
			@Override
			public boolean onMarkerClick(Marker marker) {
				if (markClick) {
					if (poliline != null) {
						poliline.remove();
						poliline = null;
					}
					boundary.append(";" + marker.getPosition().latitude + ","
							+ marker.getPosition().longitude);
					rectoptions.add(marker.getPosition());
					rectoptions.fillColor(Color.BLUE);
					poliline = MAP.addPolygon(rectoptions);
				} else {
					if (poliline != null) {
						poliline.remove();
						poliline = null;
					}
					rectoptions = new PolygonOptions().add(marker.getPosition());
					boundary.append(marker.getPosition().latitude + ","
							+ marker.getPosition().longitude);

					markClick = true;
				}
				return false;
			}
		});
		markClick = false;
	}

	public void submitBoundary(View v) {
		if (boundary.toString().length() > 0) {
			System.out.println("boundary--" + boundary);
			SoapObject soapObject = new SoapObject(RegisterActivity.NAMESPACE,
					RegisterActivity.UPDATE_BOUNDARY_METHOD);
			soapObject.addProperty("ChildId", "1000");
			soapObject.addProperty("Boundary", boundary.toString());
			RegisterActivity.serviceCall(soapObject,
			RegisterActivity.SOAP_ACTION_UPDATE_BOUNDARY);
			finish();
		}
		
	}
	
	public void setNormalView(View v){
		SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
				.findFragmentById(R.id.boundarymap);
		MAP = mapFragment.getMap();
		MAP.setMapType(GoogleMap.MAP_TYPE_NORMAL);
	}
	public void setHybridView(View v){
		SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
				.findFragmentById(R.id.boundarymap);
		MAP = mapFragment.getMap();
		MAP.setMapType(GoogleMap.MAP_TYPE_HYBRID);
	}
	
	public void resetMarkers(View v){
		SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
				.findFragmentById(R.id.boundarymap);
		MAP = mapFragment.getMap();
		MAP.clear();
		
	}
}
