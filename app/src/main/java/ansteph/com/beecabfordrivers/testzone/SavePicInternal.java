package ansteph.com.beecabfordrivers.testzone;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import ansteph.com.beecabfordrivers.R;

public class SavePicInternal extends AppCompatActivity implements View.OnClickListener{


    //Declaring views
    private Button buttonChoose;
    private Button buttonUpload;
    private Button buttongetimages;
    private ImageView imageView;
    private EditText editText;

    //Image request code
    private int PICK_IMAGE_REQUEST = 1;

    //storage permission code
    private static final int STORAGE_PERMISSION_CODE = 123;

    //Bitmap to get image from gallery
    private Bitmap bitmap;

    //Uri to store the image uri
    private Uri filePath;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_pic_internal);


        //Initializing views
        buttonChoose = (Button) findViewById(R.id.buttonChoose);
        buttonUpload = (Button) findViewById(R.id.buttonUpload);
        buttongetimages = (Button) findViewById(R.id.btnSeeImage);
        imageView = (ImageView) findViewById(R.id.imageView);
        editText = (EditText) findViewById(R.id.editTextName);

        //Setting clicklistener
        buttonChoose.setOnClickListener(this);
        buttonUpload.setOnClickListener(this);
        buttongetimages.setOnClickListener(this);


    }


    //handling the image choooser activity


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PICK_IMAGE_REQUEST && resultCode  == RESULT_OK && data !=null && data.getData()!=null){
            filePath = data.getData();
            try{
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),filePath);

                imageView.setImageBitmap(bitmap);

                String path = saveTointernalStorage(bitmap);
                Log.e("path", path);
             }catch (IOException e){
                e.printStackTrace();
            }
        }
    }



    private String saveTointernalStorage(Bitmap bitmapImage) throws IOException {
        ContextWrapper cw  = new ContextWrapper(getApplicationContext());
            // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        //create imageDir
        File mypath = new File(directory, "profile.jpg");

        FileOutputStream fos = null;
        try{
            fos = new FileOutputStream(mypath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);

        }catch (IOException e)
        {
            e.printStackTrace();
        }finally {
            fos.close();
        }

        return directory.getAbsolutePath();


    }


    public void showFileChooser()
    {
        Intent intent = new Intent ();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Picture"),PICK_IMAGE_REQUEST);
    }

    @Override
    public void onClick(View v) {
        if (v == buttonChoose) {
          showFileChooser();
        }
        if (v == buttonUpload) {
            //uploadMultipart();
        }

        if (v == buttongetimages) {
            Intent i = new Intent(getApplicationContext(), GetImages.class);

        }
    }
}
