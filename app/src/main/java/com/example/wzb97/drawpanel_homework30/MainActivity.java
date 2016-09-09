package com.example.wzb97.drawpanel_homework30;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.SeekBar;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    final int RECT_TYPE=1;
    final int CIRCLE_TYPE=2;
    final int REQUEST_CODE_PICK_IMAGE=1;
    private Button getPhoto;
    private MyView vie;
    private String imagePath;
    private SeekBar seekBar;
    private RadioButton rr,rc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(checkSelfPermission(Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS)== PackageManager.PERMISSION_DENIED){
                ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS},1);
            }
            if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_DENIED){
                ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},2);
            }
            if(checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_DENIED){
                ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},3);
            }
        }
        getPhoto=(Button)findViewById(R.id.button);
        vie=(MyView)findViewById(R.id.view);
        seekBar=(SeekBar)findViewById(R.id.seekBar);
        seekBar.setMax(100);
        seekBar.setProgress(40);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if(seekBar.getProgress()>15)vie.setLength(seekBar.getProgress());
                else vie.setLength(15);
            }
        });
        getPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getImageFromAlbum();

            }
        });
        rr=(RadioButton)findViewById(R.id.radioButton);
        rc=(RadioButton)findViewById(R.id.radioButton2);
        rr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vie.setType(RECT_TYPE);
            }
        });
        rc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vie.setType(CIRCLE_TYPE);
            }
        });

//        Bitmap bitmap= BitmapFactory.decodeResource(getResources(), R.drawable.timg);
//        MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, "test", "");
    }
    protected void getImageFromAlbum() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");//相片类型
        startActivityForResult(intent, REQUEST_CODE_PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==REQUEST_CODE_PICK_IMAGE&&resultCode==RESULT_OK){
            Uri originalUri = data.getData();
            String[] proj = {MediaStore.Images.Media.DATA};
            Cursor cursor = managedQuery(originalUri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            imagePath = cursor.getString(column_index);
            vie.setPath(imagePath);
            System.out.println(imagePath);
        }
    }
}
