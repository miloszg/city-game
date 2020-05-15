package com.example.citygame.Gallery;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import java.io.ByteArrayOutputStream;
import java.io.File;

import android.os.Bundle;
import android.widget.Toast;

import android.media.MediaScannerConnection;
import android.os.Environment;
import android.widget.ImageView;

import com.example.citygame.BuildConfig;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

import static android.app.PendingIntent.getActivity;

public class GalleryProvider extends AppCompatActivity {
    private Context mContext;
    private int GALLERY = 1, CAMERA = 2;
    private ImageView imageview;
    private static final String IMAGE_DIRECTORY = "/city_game";
    private static final int CAMERA_REQUEST_CODE = 100;
    private ImageAdapter adapter;
    private File imgFile;
    private Uri imgUri;

    //public GalleryProvider(Context context) {
        //mContext = context;
    //}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showPictureDialog();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Camera permission granted.", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Camera permission denied.", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == this.RESULT_CANCELED) {
            return;
        }
        if (requestCode == GALLERY) {
            if (data != null) {
                Uri contentURI = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                    String path = saveImage(bitmap, imgFile);
                    Toast.makeText(this, "Dodano zdjęcie", Toast.LENGTH_SHORT).show();
                    //galleryImageView.setImageBitmap(bitmap);

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(this, "Błąd zapisu zdjęcia", Toast.LENGTH_SHORT).show();
                }
            }

        } else if (requestCode == CAMERA) {
            if(data != null) {
                Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
                //galleryImageView.setImageBitmap(thumbnail);
                saveImage(thumbnail, imgFile);
                Toast.makeText(this, "Dodano zdjęcie!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Nie udało się zapisać zdjęcia.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public File createImageFile() {
        File imgDirectory = new File(Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY);

        if (!imgDirectory.exists()) {
            imgDirectory.mkdirs();
        }

        try{
            File f = new File(imgDirectory, Calendar.getInstance().getTimeInMillis() + ".jpg");
            f.createNewFile();
            imgUri = Uri.fromFile(f);

            return f;
        } catch (IOException exp){
            Toast.makeText(this, "Error!", Toast.LENGTH_SHORT).show();
            exp.printStackTrace();
        }
        return null;
    }

    public String saveImage(Bitmap myBitmap, File imageFile) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        //File imgDirectory = new File(Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY);

        //if (!imgDirectory.exists()) {
        //  imgDirectory.mkdirs();
        //}

        try {
            FileOutputStream fo = new FileOutputStream(imageFile);
            fo.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(this,
                    new String[]{imageFile.getPath()},
                    new String[]{"image/jpeg"}, null);
            fo.close();
            //Log.d("TAG", "File Saved::--->" + f.getAbsolutePath());

            return imageFile.getAbsolutePath();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return "";
    }

    public void showPictureDialog(){
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems = {
                "Select photo from gallery",
                "Capture photo from camera" };
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                choosePhotoFromGallary();
                                break;
                            case 1:
                                takePhotoFromCamera();
                                break;
                        }
                    }
                });
        pictureDialog.show();
    }

    public void choosePhotoFromGallary() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        //startActivityForResult(galleryIntent, 1);
        //setResult(RESULT_OK, galleryIntent);
        startActivityForResult(galleryIntent, 1);
    }

    private void takePhotoFromCamera() {
        imgFile = createImageFile();
        Uri photoURI = FileProvider.getUriForFile(this,
                BuildConfig.APPLICATION_ID + ".provider",
                imgFile);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
