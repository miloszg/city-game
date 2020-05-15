package com.example.citygame;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.Fragment;

import com.example.citygame.Gallery.GalleryProvider;
import com.example.citygame.Map.MapActivity;

import org.osmdroid.views.overlay.Marker;

import java.io.IOException;

public class MarkerDialog extends AppCompatDialogFragment {
    Context mapContext;
    private static final int CAMERA_REQUEST_CODE = 100;

    public MarkerDialog(Context mapContext){
        this.mapContext = mapContext;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        Toast.makeText(getActivity(), "On Activity Result Called", Toast.LENGTH_SHORT).show();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState ) {
        AlertDialog.Builder markerDialog = new AlertDialog.Builder(getActivity());

        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        final View view = layoutInflater.inflate(R.layout.info_window, null);

        ImageButton selectPhoto = (ImageButton) view.findViewById(R.id.addPhotoBtn);
        selectPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                // Do something
                if(mapContext != null) {
                    //GalleryProvider gallery = new GalleryProvider(mapContext);
                    Toast.makeText(mapContext, "click marker dialog", Toast.LENGTH_SHORT).show();
                    Intent presentation = new Intent(getActivity(), GalleryProvider.class);
                    startActivity(presentation);
                    //gallery.showPictureDialog();
                }
            }
        });

        markerDialog.setView(view)
                .setTitle("Dialog")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Button selectPhoto = view.findViewById(R.id.addPhotoBtn);
                        selectPhoto.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view)
                            {
                                // Do something
                                Toast.makeText(mapContext, "click marker dialog", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });

        return markerDialog.create();
    }
}
