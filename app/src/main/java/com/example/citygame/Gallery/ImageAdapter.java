package com.example.citygame.Gallery;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.citygame.MarkersList.Marker;
import com.example.citygame.PhotoModel;
import com.example.citygame.QuestionModel;
import com.example.citygame.R;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ImageAdapter extends PagerAdapter {
    private Context mContext;
    private List<Integer> imageIds = new ArrayList<Integer>();
    private LayoutInflater inflater;
    private int countOfPhotos = 0;
    private int currentId;
    private List<PhotoModel> photos = new ArrayList<PhotoModel>();
    private List<Marker> markers = new ArrayList<>();

    Marker scandic = new Marker(1,"54.356492", "18.646619","Hotel Scandic");
    Marker oldTownHall = new Marker(2,"54.353965", "18.648058","Ratusz Staregomiejski");
    Marker heweliusz = new Marker(3,"54.354187", "18.648856","Pomnik Jana Heweliusza");
    Marker market = new Marker(4,"54.352849", "18.652253","Rynek miejski");
    Marker arsenal = new Marker(5,"54.350708", "18.649321","Zbrojownia");
    Marker goldenGate = new Marker(6,"54.349839", "18.648079","Złota Brama");
    Marker townHall = new Marker(7,"54.349009", "18.652174","Ratusz");
    Marker neptun = new Marker(8,"54.348643", "18.653377","Pomnik Neptun");
    Marker greenGate = new Marker(9,"54.348012", "18.655741","Zielona Brama");
    Marker crane = new Marker(10,"54.350573", "18.657599","Żuraw");
    Marker soldek = new Marker(11,"54.351434", "18.658640","Soldek");
    Marker mariacka = new Marker(12,"54.351434", "18.658640","Ulica Mariacka");
    Marker bazylika = new Marker(13,"54.349941", "18.653961","Bazylika Mariacka");

    QuestionModel placsolidarnosciQ = new QuestionModel("Jaką nazwę nosi instytucja mieszcząca się w budynku-muzeum" +
            "wzniesionym tuż obok dla upamiętnienia walki z totalitaryzmem?\n",
            "Europejskie Centrum Solidarności",
            Arrays.asList("Europejskie Centrum Solidarności", "B", "C"));
    QuestionModel wystawadrogadowolnosciQ = new QuestionModel("Ile kosztuje bilet wstępu na wystawę?",
            "6 zł",
            Arrays.asList("100 zł", "6 zł", "30 zł"));
    QuestionModel arsenalQ = new QuestionModel("Kiedy zbudowano Zbrojownię?",
            "1602 – 1605",
            Arrays.asList("1902 – 1905","2008", "1602 – 1605"));

    QuestionModel goledQ = new QuestionModel("Ile figur- posągów alegorycznych stoi dumnie na szczycie bramy?",
            "8 (po 4 z każdej strony)",
            Arrays.asList("8 (po 4 z każdej strony)", "10", "1"));
    QuestionModel bazyQ = new QuestionModel("Czy bazylika może pomieścić do 25 tysięcy osób?",
            "Prawda",
            Arrays.asList("Prawda", "Fałsz", ""));
    QuestionModel mariackaQ = new QuestionModel("Czy znajdują się tu sklepy z srebrem i bursztynem?",
            "Tak",
            Arrays.asList("Tak", "Nie", ""));
    QuestionModel craneQ = new QuestionModel("Jaki ptak znajduje się na szczycie budynku ?",
            "Żuraw",
            Arrays.asList("Czapla", "Bocian", "Żuraw"));
    QuestionModel soldekQ = new QuestionModel("Czy istnieje połączenie promem przez Motławę między Żurawiem a statkiem-muzeum “Sołdek” ?",
            "Tak",
            Arrays.asList("Tak", "Nie", ""));
    QuestionModel neptunQ = new QuestionModel("Jak się nazywa grecki bóg odpowiednik Neptuna?",
            "Posejdon",
            Arrays.asList("Zeus", "Apollo", "Posejdon"));
    QuestionModel uphagenQ = new QuestionModel("Gdzie mieści się kamienica?",
            "Dom Uphagena,\n" +
                    "Oddział Muzeum Historycznego Miasta Gdańska, ul. Długa 12, Gdańsk",
            Arrays.asList("Oddział Muzeum Historycznego Miasta Gdańska, ul. Długa 30, Gdańsk", "Oddział Muzeum Historycznego Miasta Gdańska, ul. Długa 12, Gdańsk", "Oddział Muzeum Historycznego Miasta Gdańska, ul. Krótka 12, Gdańsk"));


    public ImageAdapter(Context context){
        this.mContext = context;
        neptun.setQuestion(neptunQ);
        soldek.setQuestion(soldekQ);
        crane.setQuestion(craneQ);
        mariacka.setQuestion(mariackaQ);
        bazylika.setQuestion(bazyQ);
        arsenal.setQuestion(arsenalQ);
        goldenGate.setQuestion(goledQ);

        markers.add(scandic);
        markers.add(neptun);
        markers.add(soldek);
        markers.add(crane);
        markers.add(mariacka);
        markers.add(bazylika);
        markers.add(arsenal);
        markers.add(goldenGate);
        getCountPhotos();
    }

    private void getCountPhotos(){
        File f = new File(Environment.getExternalStorageDirectory() + "/city_game");
        File files[] = f.listFiles();

        for (int i = 0; i< files.length; i++) {
            imageIds.add(i);
            countOfPhotos++;
            //Bitmap myBitmap = BitmapFactory.decodeFile(files[i].getAbsolutePath());
        }
    }

    private Bitmap getPhoto(int position){
        String path = Environment.getExternalStorageDirectory().toString()+"/city_game";
        File f = new File(path);
        File files[] = f.listFiles();

        for (int i = 0; i< files.length; i++) {
            if(i == position) {
                PhotoModel photo = new PhotoModel();
                photo.id = i;
                photo.name = files[i].getName();
                Integer index = files[i].getName().indexOf("_");
                Integer indexEnd = files[i].getName().indexOf(".");
                if(index != -1) {
                    String id = files[i].getName().substring(index + 1, indexEnd);
                    photo.idMarker = Integer.parseInt(id);
                    photos.add(photo);
                }
                return BitmapFactory.decodeFile(files[i].getAbsolutePath());
            }
        }
        return null;
    }

    @Override
    public int getCount() {
        return countOfPhotos;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View item = inflater.inflate(R.layout.gallery_item, container, false);

        ImageView imageView = item.findViewById(R.id.photo);
        imageView.setImageBitmap(getPhoto(position));

        if(!photos.isEmpty()) {
            Integer idMarker = photos.get(position) != null ? photos.get(position).idMarker : -1;
            if(idMarker != -1) {
                if (searchMarkerById(photos.get(position).idMarker).question!= null) {
                    TextView txt = item.findViewById(R.id.questionCtn);
                    txt.setText(searchMarkerById(photos.get(position).idMarker).question.content);

                    TextView txtAns = item.findViewById(R.id.questionAns);
                    txtAns.setText(searchMarkerById(photos.get(position).idMarker).question.correctAnswer);
                }
            }
        }
        container.addView(item);
        return item;
    }

    public Marker searchMarkerById(Integer id){
        for (Marker m:
             markers) {
            if(m.id == id){
                return m;
            }
        }
        return null;
    }
}