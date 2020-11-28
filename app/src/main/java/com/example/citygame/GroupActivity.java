package com.example.citygame;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.res.ResourcesCompat;

import com.example.citygame.Models.Marker;
import com.example.citygame.Models.QuestionModel;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

import org.osmdroid.api.IGeoPoint;
import org.osmdroid.api.IMapController;
import org.osmdroid.events.MapEventsReceiver;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.MapEventsOverlay;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

import java.util.ArrayList;
import java.util.List;

public class GroupActivity extends AppCompatActivity implements LocationListener, GoogleMap.OnMapLongClickListener {
    private MyLocationNewOverlay myLocation = null;
    public Context mapContext;
    public static MapView mapView;
    IMapController mapController;
    List<GeoPoint> geoPoints = new ArrayList<>();
    List<Marker> path = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mapContext = GroupActivity.this;

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M)
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }

        setContentView(R.layout.activity_group);

        Button btnCreatePath = findViewById(R.id.btnCreatePath);
        btnCreatePath.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                //TODO save new path

            }
        });

        mapView = findViewById(R.id.chooseMapView);
        mapView.setMultiTouchControls(true);
        mapView.onAttachedToWindow();
        mapView.setClickable(true);

        MapEventsReceiver mReceive = new MapEventsReceiver() {
            @Override
            public boolean singleTapConfirmedHelper(GeoPoint p) {
                Toast.makeText(GroupActivity.this, "one click", Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public boolean longPressHelper(GeoPoint p) {
                setMarker(p);
                return false;
            }
        };

        mapController = mapView.getController();

        mapController.setZoom(14.5);
        GeoPoint startPoint = new GeoPoint(54.4636543, 18.4717798);
        mapController.setCenter(startPoint);

        myLocation = new MyLocationNewOverlay(new GpsMyLocationProvider(mapContext),mapView);
        myLocation.enableMyLocation();
        myLocation.disableFollowLocation();
        myLocation.setOptionsMenuEnabled(true);
        mapView.getOverlays().add(myLocation);

        MapEventsOverlay OverlayEventos = new MapEventsOverlay(getBaseContext(), mReceive);
        mapView.getOverlays().add(OverlayEventos);

        mapView.invalidate();
    }

    protected void setMarker(IGeoPoint arg0) {
        GeoPoint touchPoint = (GeoPoint) arg0;
        final Marker markerInfo = new Marker(0,String.valueOf(touchPoint.getLatitude()), String.valueOf(touchPoint.getLongitude()),"");
        markerInfo.question = new QuestionModel("", "", new ArrayList<String>());

        org.osmdroid.views.overlay.Marker marker = new org.osmdroid.views.overlay.Marker(mapView);
        GeoPoint position = new GeoPoint(touchPoint.getLatitude(), touchPoint.getLongitude());
        geoPoints.add(position);

        marker.setIcon(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_action_location, null));
        marker.setPosition(position);
        marker.setAnchor(org.osmdroid.views.overlay.Marker.ANCHOR_CENTER, org.osmdroid.views.overlay.Marker.ANCHOR_BOTTOM);
        marker.setOnMarkerClickListener(new org.osmdroid.views.overlay.Marker.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(org.osmdroid.views.overlay.Marker marker, MapView mapView) {
                int index = isEmpty(markerInfo);
                if(index == -1) {
                    addQuestionsToMarker(markerInfo);
                } else {
                    editMarker(path.get(index));
                }
                return true;
            }
        });
        mapView.getOverlays().add(marker);
    }

    public void editMarker(final Marker marker){
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
        LayoutInflater layoutInflater = this.getLayoutInflater();
        final View view = layoutInflater.inflate(R.layout.popup_edit_marker, null);

        ArrayAdapter<String> adapter;
        ListView listOfAnswers = (ListView) view.findViewById(R.id.answersList);
        adapter = new ArrayAdapter<String>(this,
                R.layout.item_list,
                R.id.answerContent,
                marker.question.answers);
        listOfAnswers.setAdapter(adapter);

        final EditText contentQuestion = view.findViewById(R.id.editTextContentEdit);
        final EditText titleMarker = view.findViewById(R.id.editTextMarkerNameEdit);

        Button btnEditMarker = view.findViewById(R.id.btnEditMarker);
        btnEditMarker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                marker.question = new QuestionModel(contentQuestion.getText().toString(),"", marker.question.answers);
            }
        });

        titleMarker.setText(marker.title);
        contentQuestion.setText(marker.question.content);

        pictureDialog.setView(view);
        pictureDialog.show();
    }

    public int isEmpty(Marker marker){
        for(int i =0; i < path.size(); i++){
            if(marker.lon == path.get(i).lon && marker.lat == path.get(i).lat){
                return i;
            }
        }

        return -1;
    }

    public void addQuestionsToMarker(final Marker marker){
        final List<String> answers = new ArrayList<>();
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);

        LayoutInflater layoutInflater = this.getLayoutInflater();
        final View view = layoutInflater.inflate(R.layout.popup_new_question, null);

        final EditText answerText = view.findViewById(R.id.editTexAnswer);
        final EditText contentQuestion = view.findViewById(R.id.editTextContent);
        final EditText titleMarker = view.findViewById(R.id.editTextMarkerName);

        Button btnaddAnswer = view.findViewById(R.id.btnAddAnswer);
        Button btnaddQuestion = view.findViewById(R.id.btnAddQuestion);

        btnaddAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                String answer = answerText.getText().toString();
                if(answer != "" && answer != null){
                    answers.add(answer);
                }

                answerText.setText("");
            }
        });

        btnaddQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                marker.question = new QuestionModel(contentQuestion.getText().toString(),"", answers);
                marker.title = titleMarker.getText().toString();

                path.add(marker);
                //TODO: save QUESTION to server
            }
        });

        pictureDialog.setView(view);
        pictureDialog.show();
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    @Override
    public void onMapLongClick(LatLng latLng) {
        Toast.makeText(this, "help", Toast.LENGTH_SHORT).show();
    }
}
