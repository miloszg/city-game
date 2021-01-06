package com.example.citygame;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;

import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.res.ResourcesCompat;

import com.example.citygame.Models.Marker;
import com.example.citygame.Models.MarkerRequest;
import com.example.citygame.Models.QuestionModel;
import com.example.citygame.Models.QuestionRequest;
import com.example.citygame.Models.ScenarioRequest;
import com.example.citygame.api.client.ApiClient;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GroupActivity extends AppCompatActivity implements LocationListener, GoogleMap.OnMapLongClickListener {
    private MyLocationNewOverlay myLocation = null;
    public Context mapContext;
    public static MapView mapView;
    IMapController mapController;
    List<GeoPoint> geoPoints = new ArrayList<>();
    List<Marker> path = new ArrayList<>();
    List<MarkerRequest> pathRequest = new ArrayList<>();
    ApiClient apiClient = new ApiClient();

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
                final ScenarioRequest scenario = new ScenarioRequest();
                scenario.name = "Testowy";
                scenario.markers = new ArrayList<MarkerRequest>();
                for(int i=0;i<path.size();i++){
                    MarkerRequest m = new MarkerRequest();
                    m.lat = path.get(i).lat;
                    m.lon = path.get(i).lon;
                    m.title = path.get(i).title;

                    QuestionRequest q = new QuestionRequest();
                    q.correct = path.get(i).question.correct;
                    q.answers = path.get(i).question.answers;
                    q.content = path.get(i).question.content;

                    m.question = q;

                    scenario.markers.add(m);
                }

                    Thread thread = new Thread(new Runnable() {

                        @Override
                        public void run() {
                            try  {
                                apiClient.createScenario(scenario);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
                    thread.start();
            }
        });

        mapView = findViewById(R.id.chooseMapView);
        mapView.setMultiTouchControls(true);
        mapView.onAttachedToWindow();
        mapView.setClickable(true);

        MapEventsReceiver mReceive = new MapEventsReceiver() {
            @Override
            public boolean singleTapConfirmedHelper(GeoPoint p) {
                Toast.makeText(GroupActivity.this, "Musisz dłużej przytrzymać by dodać punkt na mapie.", Toast.LENGTH_SHORT).show();
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
                    editMarker(path.get(index), index);
                }
                return true;
            }
        });
        mapView.getOverlays().add(marker);
    }

    public void editMarker(final Marker marker, final int index){
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
        LayoutInflater layoutInflater = this.getLayoutInflater();
        final View view = layoutInflater.inflate(R.layout.popup_edit_marker, null);

        final ListView listOfAnswers = (ListView) view.findViewById(R.id.answersList);

        final MyCustomAdapter adapter = new MyCustomAdapter(marker.question.answers, this, path.indexOf(marker), 2);
        listOfAnswers.setAdapter(adapter);

        listOfAnswers.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                String item = marker.question.answers.get(position);
                marker.question.correct = item;

                for (int a = 0; a < listOfAnswers.getChildCount(); a++) {
                    if(position == a ){
                        listOfAnswers.getChildAt(a).setBackgroundColor(Color.parseColor("#16951F"));
                        Toast.makeText(GroupActivity.this, "Zaznaczyłeś prawidłową odpowiedź.", Toast.LENGTH_SHORT).show();
                    }else{
                        listOfAnswers.getChildAt(a).setBackgroundColor(Color.TRANSPARENT);
                    }
                }
            }
        });

        final EditText contentQuestion = view.findViewById(R.id.editTextContentEdit);
        final EditText titleMarker = view.findViewById(R.id.editTextMarkerNameEdit);
        final EditText newAnswer = view.findViewById(R.id.editTextNewAnswer);

        ImageButton addNewAnswer = view.findViewById(R.id.imageButtonAddAnswer);
        addNewAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if(newAnswer.getText().toString() != "" && newAnswer.getText().toString() != null) {
                    adapter.list.add(newAnswer.getText().toString());
                    marker.question.answers.add(newAnswer.getText().toString());
                    adapter.notifyDataSetChanged();
                }
            }
        });

        Button btnEditMarker = view.findViewById(R.id.btnEditMarker);
        btnEditMarker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Marker m = new Marker(marker.id, marker.lat, marker.lon, marker.title);
                m.question = new QuestionModel(marker.question.content, marker.question.correct, new ArrayList<>(marker.question.answers));

                marker.title = titleMarker.getText().toString();
                marker.question = new QuestionModel(contentQuestion.getText().toString(),"", marker.question.answers);
                submitChanges(m, index);
            }
        });

        titleMarker.setText(marker.title);
        contentQuestion.setText(marker.question.content);

        pictureDialog.setView(view);
        pictureDialog.show();
    }

    private void submitChanges(final Marker marker, final int index) {
        AlertDialog.Builder dialog=new AlertDialog.Builder(this);
        dialog.setMessage("Czy chcesz zatwierdzić wprowadzone dane?");
        dialog.setTitle("Potwierdzenie");
        dialog.setPositiveButton("TAK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int which) {

                    }
                });
        dialog.setNegativeButton("NIE",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                path.get(index).title = marker.title;
                path.get(index).question = new QuestionModel(marker.question.content, marker.question.correct, marker.question.answers);
            }
        });
        AlertDialog alertDialog=dialog.create();
        alertDialog.show();
    }

    public int isEmpty(Marker marker){
        for(int i =0; i < path.size(); i++){
            if(marker.lon == path.get(i).lon && marker.lat == path.get(i).lat){
                return i;
            }
        }

        return -1;
    }

    public void showShortInfo(String content){
        Toast.makeText(GroupActivity.this, content, Toast.LENGTH_SHORT).show();
    }

    public void addQuestionsToMarker(final Marker marker){
        final String[] correctAnswer = new String[1];
        final List<String> answers = new ArrayList<>();
        final AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);

        LayoutInflater layoutInflater = this.getLayoutInflater();
        final View view = layoutInflater.inflate(R.layout.popup_new_question, null);

        final EditText answerText = view.findViewById(R.id.editTexAnswer);
        final EditText contentQuestion = view.findViewById(R.id.editTextContent);
        final EditText titleMarker = view.findViewById(R.id.editTextMarkerName);

        ImageButton btnaddAnswer = view.findViewById(R.id.btnAddAnswer);
        Button btnaddQuestion = view.findViewById(R.id.btnAddQuestion);

        final ListView listOfAnswers = view.findViewById(R.id.answersListNew);

        final MyCustomAdapter adapter = new MyCustomAdapter(answers, this, path.indexOf(marker), 1);
        listOfAnswers.setAdapter(adapter);

        listOfAnswers.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                 correctAnswer[0] = answers.get(position);

                for (int a = 0; a < listOfAnswers.getChildCount(); a++) {
                    if(position == a ){
                        listOfAnswers.getChildAt(a).setBackgroundColor(Color.parseColor("#16951F"));
                        Toast.makeText(GroupActivity.this, "Zaznaczyłeś prawidłową odpowiedź.", Toast.LENGTH_SHORT).show();
                    }else{
                        listOfAnswers.getChildAt(a).setBackgroundColor(Color.TRANSPARENT);
                    }
                }
            }
        });

        pictureDialog.setView(view);
        final AlertDialog dialog = pictureDialog.create();
        dialog.show();

        btnaddAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                String answer = answerText.getText().toString();
                if(answer != "" && answer != null){
                    answers.add(answer);
                    adapter.list.add(answer);
                    adapter.notifyDataSetChanged();
                }

                answerText.setText("");
            }
        });

        btnaddQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                marker.question = new QuestionModel(contentQuestion.getText().toString(), correctAnswer[0], answers);
                marker.title = titleMarker.getText().toString();

                path.add(marker);
                dialog.dismiss();
                //TODO: save QUESTION to server
            }
        });
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

    class MyCustomAdapter extends BaseAdapter implements ListAdapter {
        public ArrayList<String> list;
        private Context context;
        private int index;
        private int mode;

        public MyCustomAdapter(List<String> list, Context context, int index, int mode) {
            this.list = new ArrayList<>(list);
            this.context = context;
            this.index = index;
            this.mode = mode;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int pos) {
            return list.get(pos);
        }

        @Override
        public long getItemId(int pos) {
            return list.indexOf(pos);
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View view = convertView;
            if (view == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.item_list, null);
            }

            final ListView listOfAnswers = (ListView) view.findViewById(R.id.answersList);

            TextView listItemText = view.findViewById(R.id.answerContent);
            listItemText.setText(list.get(position));

            ImageButton btnDelete = view.findViewById(R.id.imageButtonDelete);
            if (mode == 1){
                btnDelete.setVisibility(View.INVISIBLE);
            } else if (mode == 2){

            }

            btnDelete.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    list.remove(position); //or some other task
                    //TODO zmienić sposób usuwania odp z punktu ścieżki
                    path.get(index).question.answers.remove(position);
                    notifyDataSetChanged();
                }
            });

            return view;
        }
    }
}
