package ansteph.com.beecabfordrivers.view.extraAction;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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

import java.util.Map;

import ansteph.com.beecabfordrivers.R;
import ansteph.com.beecabfordrivers.app.Config;
import ansteph.com.beecabfordrivers.app.GlobalRetainer;
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

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    //Imageloader to load images
    private ImageLoader imageLoader;

    LinearLayout lnImage1;
    LinearLayout lnImage2;
    LinearLayout lnImage3;

    ImageView goleft1,goleft2,goleft3, goright1, goright2, goright3;

    ImageView imgEditEmail, imgEditCarModel,imgEditNumPlate,imgEditCabLicence,imgEditYear;

    TextView  txtEmail,txtPhone, txtName, txtCarModel, txtNumberPlate,txtCabLicence,txtYear, txtRating, txtCurrentCity;
    ViewAnimator viewAnimator;

    DriverProfile driverProfile  ;

    GlobalRetainer mGlobalRetainer;

    public ProfileFragment() {
        // Required empty public constructor
    }

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

        lnImage1 = (LinearLayout) rootView.findViewById(R.id.lnImage1) ;
        lnImage2 = (LinearLayout) rootView.findViewById(R.id.lnImage2) ;
        lnImage3 = (LinearLayout) rootView.findViewById(R.id.lnImage3) ;

        goleft1 = (ImageView)rootView.findViewById(R.id.goleft1) ;
        goleft2 = (ImageView)rootView.findViewById(R.id.goleft2) ;
        goleft3 = (ImageView)rootView.findViewById(R.id.goleft3) ;

        goright1 = (ImageView)rootView.findViewById(R.id.goright1) ;
        goright2 = (ImageView)rootView.findViewById(R.id.goright2) ;
        goright3 = (ImageView)rootView.findViewById(R.id.goright3) ;


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

        getImageData();



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

            default:


        }

    }
}
