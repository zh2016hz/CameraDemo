package com.example.aa.cameral;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private ImageView img;
    private Uri uriForFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button bt = (Button) findViewById(R.id.button);
        img = (ImageView) findViewById(R.id.imageView);
        bt.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                File outimage = new File(getExternalCacheDir(), "output_image.jpg");
                if (outimage.exists()) {
                    outimage.delete();
                }
                try {
                    outimage.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //获得URI
                if (Build.VERSION.SDK_INT >= 24) {
                    uriForFile = FileProvider.getUriForFile(MainActivity.this, "com.example.aa.fileprovider", outimage);
                } else {
                    uriForFile = Uri.fromFile(outimage);
                }
//启动相机
                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                intent.putExtra(MediaStore.EXTRA_OUTPUT, uriForFile);
                startActivityForResult(intent, 1);


            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            switch (requestCode) {
                case 1:
                    try {
                        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uriForFile));
                        img.setImageBitmap(bitmap);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    break;
                default:
                    break;
            }
        }


    }
}
