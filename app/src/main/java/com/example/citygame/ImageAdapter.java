package com.example.citygame;

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

import java.io.File;

public class ImageAdapter extends PagerAdapter {
    private Context mContext;
    private int[] imageIds = new int[20]; //TODO
    private LayoutInflater inflater;
    private int countOfPhotos = 0;

    public ImageAdapter(Context context){
        this.mContext = context;
        getCountPhotos();
    }

    private void getCountPhotos(){
        File f = new File(Environment.getExternalStorageDirectory() + "/city_game");
        File files[] = f.listFiles();

        for (int i = 0; i< files.length; i++) {
            imageIds[i] = i;
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

        TextView txt = item.findViewById(R.id.description);
        txt.setText("Jakiś opis..." + String.valueOf(position));
        container.addView(item);
        return item;
    }
}