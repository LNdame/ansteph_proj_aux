package ansteph.com.beecabfordrivers.view.profile;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewAnimator;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Hashtable;
import java.util.Map;

import ansteph.com.beecabfordrivers.R;
import ansteph.com.beecabfordrivers.app.Config;
import ansteph.com.beecabfordrivers.app.GlobalRetainer;
import ansteph.com.beecabfordrivers.helper.SessionManager;
import ansteph.com.beecabfordrivers.model.DriverProfile;
import ansteph.com.beecabfordrivers.testzone.CustomVolleyRequest;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    public static final String TAG = ProfileFragment.class.getSimpleName();
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private static final String KEY_DRIVERIMG = "BeeCabDriver";
    private static final String KEY_DRIVER2IMG = "BeeCabDriver2";
    private static final String KEY_CARBACKIMG = "BeeCabCarBack";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private int PICK_IMAGE_REQUEST = 1;
    private int RESULT_OK = -1;


    //Imageloader to load images
    private ImageLoader imageLoader;

    LinearLayout lnImage1;
    LinearLayout lnImage2;
    LinearLayout lnImage3;

    ImageView goleft1,goleft2,goleft3, goright1, goright2, goright3;

    ImageView imgEditEmail, imgEditCarModel,imgEditNumPlate,imgEditCabLicence,imgEditYear;

    TextView  txtEmail,txtPhone, txtName, txtCarModel, txtNumberPlate,txtCabLicence,txtYear, txtRating, txtCurrentCity;
    Button btnChangePic1, btnChangePic2,btnChangePic3;
    ViewAnimator viewAnimator;

    DriverProfile driverProfile  ;

    GlobalRetainer mGlobalRetainer;

    SessionManager sessionManager;

    public ProfileFragment() {
        // Required empty public constructor
    }


    public enum PicTag {
        Driver, Driver2, Car_Back
    }

    PicTag mPicTag;
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        driverProfile =null ;
        mGlobalRetainer = (GlobalRetainer) getActivity().getApplicationContext();
        sessionManager = new SessionManager(getActivity().getApplicationContext());

        lnImage1 = (LinearLayout) rootView.findViewById(R.id.lnImage1) ;
        lnImage2 = (LinearLayout) rootView.findViewById(R.id.lnImage2) ;
        lnImage3 = (LinearLayout) rootView.findViewById(R.id.lnImage3) ;

        goleft1 = (ImageView)rootView.findViewById(R.id.goleft1) ;
        goleft2 = (ImageView)rootView.findViewById(R.id.goleft2) ;
        goleft3 = (ImageView)rootView.findViewById(R.id.goleft3) ;

        goright1 = (ImageView)rootView.findViewById(R.id.goright1) ;
        goright2 = (ImageView)rootView.findViewById(R.id.goright2) ;
        goright3 = (ImageView)rootView.findViewById(R.id.goright3) ;

        btnChangePic1 = (Button) rootView.findViewById(R.id.Changepic1) ;
        btnChangePic2 = (Button) rootView.findViewById(R.id.Changepic2) ;
        btnChangePic3 = (Button) rootView.findViewById(R.id.Changepic3) ;



        getProfileData();

        setEditor(rootView);
        setTextField(rootView);

        viewAnimator =(ViewAnimator) rootView.findViewById(R.id.viewAnimator);

        final Animation inAmin = AnimationUtils.loadAnimation(getActivity(),android.R.anim.slide_in_left);
        final Animation outAmin = AnimationUtils.loadAnimation(getActivity(),android.R.anim.slide_out_right);

        viewAnimator.setInAnimation(inAmin);
        viewAnimator.setOutAnimation(outAmin);

        goleft1.setOnClickListener(this);
        goleft2.setOnClickListener(this);
        goleft3.setOnClickListener(this);

        goright1.setOnClickListener(this);
        goright2.setOnClickListener(this);
        goright3.setOnClickListener(this);

        btnChangePic1.setOnClickListener(this);
        btnChangePic2.setOnClickListener(this);
        btnChangePic3.setOnClickListener(this);

      //  getImageData();
        loadProfileImageFromInternalStorage();


        return rootView;
    }

    private void setEditor(View rootView)
    {
        imgEditEmail= (ImageView)rootView.findViewById(R.id.imgEditEmail) ;
        imgEditCarModel= (ImageView)rootView.findViewById(R.id.imgEditCarModel) ;
        imgEditNumPlate= (ImageView)rootView.findViewById(R.id.imgEditNumberPlate) ;
        imgEditCabLicence= (ImageView)rootView.findViewById(R.id.imgEditCabLicence) ;
        imgEditYear= (ImageView)rootView.findViewById(R.id.imgEditYear) ;

        imgEditEmail.setOnClickListener(this);
        imgEditCarModel.setOnClickListener(this);
        imgEditNumPlate.setOnClickListener(this);
        imgEditCabLicence.setOnClickListener(this);
        imgEditYear.setOnClickListener(this);

    }


    private void setTextField(View rootView)
    {

        // initialize and give a value
        txtName = (TextView) rootView.findViewById(R.id.txtfullname);
        txtPhone = (TextView) rootView.findViewById(R.id.txtCellphone);
        txtEmail = (TextView) rootView.findViewById(R.id.txtemail);
        txtCarModel = (TextView) rootView.findViewById(R.id.txtCarModel);
        txtNumberPlate= (TextView) rootView.findViewById(R.id.txtNumberPlate);
        txtCabLicence = (TextView) rootView.findViewById(R.id.txtCabLicence);
        txtCurrentCity = (TextView) rootView.findViewById(R.id.txtCurrentCity);
        txtRating = (TextView) rootView.findViewById(R.id.txtRating);

        txtYear = (TextView) rootView.findViewById(R.id.txtyear);

        txtName.setText(mGlobalRetainer.get_grDriver().getName());
        txtPhone.setText(mGlobalRetainer.get_grDriver().getMobile());
        txtEmail.setText(mGlobalRetainer.get_grDriver().getEmail());

      txtCabLicence.setText(mGlobalRetainer.get_grDriver().getLicence());
       txtYear.setText(mGlobalRetainer.get_grDriver().getYear());



    }

    //try to load the image from their internal storage before the network call

    private void loadProfileImageFromInternalStorage()
    {
        Boolean IsLoadSuccess = false;

        if(sessionManager.getDriverPath() !=null){

           try{
               lnImage1.removeAllViews();
               File f=new File(sessionManager.getDriverPath(), "DriverProfile_1.jpg");
               Bitmap b = BitmapFactory.decodeStream(new FileInputStream( f));

              ImageView networkImageView = new ImageView(getActivity());
               networkImageView.setImageBitmap(b);
               networkImageView.setScaleType(ImageView.ScaleType.FIT_XY);

               LinearLayoutCompat.LayoutParams params = new LinearLayoutCompat.LayoutParams(LinearLayoutCompat.LayoutParams.MATCH_PARENT,LinearLayoutCompat.LayoutParams.MATCH_PARENT);
               lnImage1.addView(networkImageView, params);

               IsLoadSuccess = true;

           }catch (FileNotFoundException e)
           {
               e.printStackTrace();
           }

        }else {IsLoadSuccess = false;}


        if(sessionManager.getDriver2Path() !=null){

            try{
                lnImage2.removeAllViews();

                File f=new File(sessionManager.getDriver2Path(), "DriverProfile_2.jpg");
                Bitmap b = BitmapFactory.decodeStream(new FileInputStream( f));

              ImageView networkImageView = new ImageView(getActivity());
                networkImageView.setImageBitmap(b);
                networkImageView.setScaleType(ImageView.ScaleType.FIT_XY);

                LinearLayoutCompat.LayoutParams params = new LinearLayoutCompat.LayoutParams(LinearLayoutCompat.LayoutParams.MATCH_PARENT,LinearLayoutCompat.LayoutParams.MATCH_PARENT);
                lnImage2.addView(networkImageView, params);

                IsLoadSuccess = true;

            }catch (FileNotFoundException e)
            {
                e.printStackTrace();
            }

        }else {IsLoadSuccess = false;}


        if(sessionManager.getCarBackPath() !=null){

            try{
                lnImage3.removeAllViews();

                File f=new File(sessionManager.getDriverPath(), "DriverProfile_3.jpg");
                Bitmap b = BitmapFactory.decodeStream(new FileInputStream( f));

               ImageView networkImageView = new ImageView(getActivity());
                networkImageView.setImageBitmap(b);
                networkImageView.setScaleType(ImageView.ScaleType.FIT_XY);

                LinearLayoutCompat.LayoutParams params = new LinearLayoutCompat.LayoutParams(LinearLayoutCompat.LayoutParams.MATCH_PARENT,LinearLayoutCompat.LayoutParams.MATCH_PARENT);
                lnImage3.addView(networkImageView, params);

                IsLoadSuccess = true;

            }catch (FileNotFoundException e)
            {
                e.printStackTrace();
            }

        }else {IsLoadSuccess = false;}


            if(!IsLoadSuccess)
            {
                getImageData();
            }



    }



    private void updateUI() {

        if(driverProfile!=null)
        {
            txtCarModel.setText(driverProfile.getCarModel());
            txtNumberPlate.setText(driverProfile.getCarNumberPlate());
            txtCurrentCity.setText(driverProfile.getCurrentCity());
            txtRating.setText(driverProfile.getProfileRating());
        }
    }

    private void getProfileData()
    {
        final ProgressDialog loading = ProgressDialog.show(getActivity(), "Please wait ","Fetching data...",false,false);
        String url = String.format(Config.RETRIEVE_USER_PROFILE_URL,mGlobalRetainer.get_grDriver().getId());

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loading.dismiss();
                JSONObject jsonResponse = null;


                try {

                    jsonResponse = new JSONObject(response);
                    boolean error = jsonResponse.getBoolean(Config.ERROR_RESPONSE);
                    String serverMsg = jsonResponse.getString(Config.MSG_RESPONSE);
                    if(!error)
                    {
                        //get the user detail from the server
                        JSONArray profile = jsonResponse.getJSONArray("profile");
                        JSONObject user = profile.getJSONObject(0); //getString(Config.KEY_ID)
                       // String carModel, String carNumberPlate, String currentCity, String profileRating
                        driverProfile = new DriverProfile(user.getString(Config.KEY_CAR_MODEL),
                                                             user.getString(Config.KEY_CAR_NUMPLATE),
                                                             user.getString(Config.KEY_CURRENT_CITY),
                                        user.getString(Config.KEY_PRO_RATING)

                        );



                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }finally {
                    updateUI();
                }




            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loading.dismiss();
             Toast.makeText(getActivity(),"Oops! Profile unreachable! Please check your internet connection", Toast.LENGTH_LONG).show();
            }
        }){

        };

        //Creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        //Adding our request to the queue
        requestQueue.add(stringRequest);

    }

    private void getImageData()
    {
        final ProgressDialog loading = ProgressDialog.show(getActivity(), "Please wait ","Fetching data...",false,false);

        String url = String.format(Config.RETRIEVE_USER_IMAGE_URL,mGlobalRetainer.get_grDriver().getId());

        //Creating a json array request to get the json from our api
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        //Dismissing the progressdialog on response
                        loading.dismiss();

                        //Displaying our grid
                        loadImage(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );
        //Creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        //Adding our request to the queue
        requestQueue.add(jsonArrayRequest);
    }

    private void loadImage(JSONArray jsonArray)
    {

        //Creating a json object of the current index
        JSONObject obj = null;
        String imageurl1 =null;
        String imageurl2 =null;
        String imageurl3 =null;

        try {
            obj = jsonArray.getJSONObject(0);
            imageurl1 = obj.getString(Config.TAG_IMAGE_URL);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        if(imageurl1!=null){
            //NetworkImageView
            NetworkImageView networkImageView = new NetworkImageView(getActivity());

            imageLoader = CustomVolleyRequest.getInstance(getActivity()).getImageLoader();
            imageLoader.get(imageurl1, ImageLoader.getImageListener(networkImageView,R.mipmap.ic_launcher,android.R.drawable.ic_dialog_alert));

            //seting the image to load
            networkImageView.setImageUrl(imageurl1,imageLoader);

            networkImageView.setScaleType(ImageView.ScaleType.FIT_XY);

            LinearLayoutCompat.LayoutParams params = new LinearLayoutCompat.LayoutParams(LinearLayoutCompat.LayoutParams.MATCH_PARENT,LinearLayoutCompat.LayoutParams.MATCH_PARENT);
            // networkImageView.setLayoutParams(new GridView.LayoutParams(400,400));
            lnImage1.addView(networkImageView, params);

            //save internally

        }


        try {
            obj = jsonArray.getJSONObject(1);
            imageurl2 = obj.getString(Config.TAG_IMAGE_URL);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        if(imageurl2!=null){
            //NetworkImageView
            NetworkImageView networkImageView = new NetworkImageView(getActivity());

            imageLoader = CustomVolleyRequest.getInstance(getActivity()).getImageLoader();
            imageLoader.get(imageurl2, ImageLoader.getImageListener(networkImageView,R.mipmap.ic_launcher,android.R.drawable.ic_dialog_alert));

            //seting the image to load
            networkImageView.setImageUrl(imageurl2,imageLoader);

            networkImageView.setScaleType(ImageView.ScaleType.FIT_XY);

            LinearLayoutCompat.LayoutParams params = new LinearLayoutCompat.LayoutParams(LinearLayoutCompat.LayoutParams.MATCH_PARENT,LinearLayoutCompat.LayoutParams.MATCH_PARENT);
            // networkImageView.setLayoutParams(new GridView.LayoutParams(400,400));
            lnImage2.addView(networkImageView, params);

            //save internally

        }


        try {
            obj = jsonArray.getJSONObject(2);
            imageurl3 = obj.getString(Config.TAG_IMAGE_URL);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        if(imageurl3!=null){
            //NetworkImageView
            NetworkImageView networkImageView = new NetworkImageView(getActivity());

            imageLoader = CustomVolleyRequest.getInstance(getActivity()).getImageLoader();
            imageLoader.get(imageurl3, ImageLoader.getImageListener(networkImageView,R.mipmap.ic_launcher,android.R.drawable.ic_dialog_alert));

            //seting the image to load
            networkImageView.setImageUrl(imageurl3,imageLoader);

            networkImageView.setScaleType(ImageView.ScaleType.FIT_XY);

            LinearLayoutCompat.LayoutParams params = new LinearLayoutCompat.LayoutParams(LinearLayoutCompat.LayoutParams.MATCH_PARENT,LinearLayoutCompat.LayoutParams.MATCH_PARENT);
            // networkImageView.setLayoutParams(new GridView.LayoutParams(400,400));
            lnImage3.addView(networkImageView, params);

            //save internally

        }


        if(imageurl1!=null && imageurl2!=null && imageurl3!=null)
        {
            SaveImageInternalTask st = new SaveImageInternalTask();
            try {
                st.execute(new URL(imageurl1),new URL(imageurl2),new URL(imageurl3));
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }

    }


    private void showEditor (String hint, String title )
    {


        Fragment fragment =  FieldEditorFragment.newInstance(hint,title );
      //  fragment.setArguments(args);
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction()
                .addToBackStack(FieldEditorFragment.TAG);
        fragmentTransaction.replace(R.id.container_body, fragment,FieldEditorFragment.TAG);
        fragmentTransaction.commit();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
    }


    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle(R.string.title_activity_profile);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.goleft1:
            case R.id.goleft2:
            case R.id.goleft3:viewAnimator.showPrevious(); break;

            case R.id.goright1:
            case R.id.goright2:
            case R.id.goright3:viewAnimator.showNext();break;

            case R.id.imgEditEmail:showEditor("Email","Edit Email field");break;
            case R.id.imgEditNumberPlate:showEditor("Car Number Plate","Car Number Plate");break;
            case R.id.imgEditCarModel:showEditor("Car Model","Edit Car Model");break;
            case R.id.imgEditCabLicence:showEditor("Cab Licence Number","Cab Licence Number");break;
            case R.id.imgEditYear:showEditor("Year of Licence","Edit Email field");break;

            case R.id.Changepic1: showFileChooser(); mPicTag= PicTag.Driver; break;
            case R.id.Changepic2:  showFileChooser();mPicTag= PicTag.Driver2;break;
            case R.id.Changepic3:showFileChooser();mPicTag= PicTag.Car_Back;break;


            default:


        }

    }


    /**************************************************Utility methods*************************************************/
    private  void showFileChooser(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(intent.ACTION_GET_CONTENT);
        startActivityForResult(intent.createChooser(intent,"Select Picture"), PICK_IMAGE_REQUEST);
    }

    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    Bitmap bitmap;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri filePath =data.getData();
            String path;

            try{
               bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), filePath);

                switch (mPicTag){
                    case  Driver: sessionManager.setDriverPath(saveToInternalStorage(bitmap, "DriverProfile_1.jpg")); break;
                    case  Driver2: sessionManager.setDriver2Path(saveToInternalStorage(bitmap, "DriverProfile_2.jpg")); break;
                    case  Car_Back: sessionManager.setCarBackPath(saveToInternalStorage(bitmap, "DriverProfile_3.jpg")); break;
                }

                loadProfileImageFromInternalStorage();
                uploadImage();
            }catch (IOException ie)
            {

            }


        }

    }





    //Here method to catch the pic and save it in the relevant linearlayout while doing 2 thing save it internally and save it to server




    private String saveToInternalStorage(Bitmap bitmapImage, String imageName) throws IOException { //to be deleted the async method works better



        ContextWrapper cw  = new ContextWrapper(getActivity());
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir("BeeCabImageDir", Context.MODE_PRIVATE);
        //create imageDir
        File mypath = new File(directory, imageName);

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


    private class SaveImageInternalTask extends AsyncTask<URL, String, String>
    {

        @Override
        protected String doInBackground(URL... urls) {

            int count = urls.length;

            for (int i =0; i<count ; i++)
            {
                try {
                    Bitmap bitmapImage = BitmapFactory.decodeStream(urls[i].openConnection().getInputStream());

                    ContextWrapper cw  = new ContextWrapper(getActivity());
                    // path to /data/data/yourapp/app_data/imageDir
                    File directory = cw.getDir("BeeCabImageDir", Context.MODE_PRIVATE);
                    //create imageDir
                    File mypath = new File(directory, "DriverProfile_"+(i+1)+".jpg");

                    FileOutputStream fos = null;

                    fos = new FileOutputStream(mypath);
                    bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);

                    fos.close();



                    if(i==0){
                        sessionManager.setDriverPath(directory.getAbsolutePath());
                    }else if( i==1){
                        sessionManager.setDriver2Path(directory.getAbsolutePath());
                    }else if(i==2){
                        sessionManager.setCarBackPath(directory.getAbsolutePath());
                    }




                } catch (IOException e) {
                    e.printStackTrace();
                }
            }



            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            Toast.makeText(getActivity(),"Image saved", Toast.LENGTH_LONG).show();
        }
    }




    private void uploadImage(){
        //Showing the progress dialog
        final ProgressDialog loading = ProgressDialog.show(getActivity(),"Saving the changes...","Please wait...",false,false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.UPLOAD_URL_EN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        //Disimissing the progress dialog
                        loading.dismiss();
                        //Showing toast message of the response
                        Toast.makeText(getActivity(), "Saved" , Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        //Dismissing the progress dialog
                        loading.dismiss();

                        //Showing toast
                        Toast.makeText(getActivity(), volleyError.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //Converting Bitmap to String
                String image = getStringImage(bitmap);
                Log.e("imagecode", image);
                //Getting tag Name
                String name = "";

                switch (mPicTag){
                    case  Driver: name = "driver" ; break;
                    case  Driver2: name = "driver2" ; break;
                    case  Car_Back: name = "car_back"; break;
                }

                //Creating parameters
                Map<String,String> params = new Hashtable<String, String>();

                //Adding parameters
                params.put(Config.KEY_ID,mGlobalRetainer.get_grDriver().getId());
                params.put(Config.KEY_IMAGE, image);
                params.put(Config.KEY_IMAGE_TAG, name);

                //returning parameters
                return params;
            }
        };

        //Creating a Request Queue
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

        //Adding request to the queue
        requestQueue.add(stringRequest);
    }



}
