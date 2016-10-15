package ansteph.com.beecabfordrivers.view.registration;


import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import ansteph.com.beecabfordrivers.R;
import ansteph.com.beecabfordrivers.app.Config;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RegistrationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegistrationFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match

    public final static String TAG = RegistrationFragment.class.getSimpleName();

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    //Volley RequestQueue
    private RequestQueue requestQueue;
    EditText txtFullName;
    EditText txtEmail;
    EditText txtCompanyName;
    EditText txtMobile;
    EditText txtCarModel;
    EditText txtNumPlate;
    EditText txtLicence;
    EditText txtYear;
    EditText txtPassword,txtConPassword;

    String fullName ="", email="",companyname="",carmodel="", numPlate="" ,licence="", year="", mobile="",pwd="";
    TextView txtReg;

    private String driverClass;
    ImageView imgValid;


    public RegistrationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RegistrationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RegistrationFragment newInstance(String param1, String param2) {
        RegistrationFragment fragment = new RegistrationFragment();
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
        View rootView= inflater.inflate(R.layout.fragment_registration, container, false);


        txtFullName = (EditText) rootView.findViewById(R.id.input_name);
        txtEmail = (EditText) rootView.findViewById(R.id.input_email);
        txtCompanyName = (EditText) rootView.findViewById(R.id.input_companyname);
        txtMobile = (EditText) rootView.findViewById(R.id.input_mobile);
        txtCarModel = (EditText) rootView.findViewById(R.id.input_model);

        txtNumPlate = (EditText) rootView.findViewById(R.id.input_plate);
        txtLicence = (EditText) rootView.findViewById(R.id.input_cablicence);
        txtYear = (EditText) rootView.findViewById(R.id.input_year);
        txtPassword = (EditText) rootView.findViewById(R.id.input_password);
        txtConPassword = (EditText) rootView.findViewById(R.id.input_confirm_password);

        imgValid = (ImageView) rootView.findViewById(R.id.imgValid);

        txtReg = (TextView) rootView.findViewById(R.id.txtRegistration);

        requestQueue = Volley.newRequestQueue(getActivity());
          Button btnCreateAcc = (Button) rootView.findViewById(R.id.btn_signup);



       driverClass =  ((Registration)getActivity()).getDriverClass();

        txtConPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String npwd = txtPassword.getText().toString().trim();
                String cpwd = txtConPassword.getText().toString().trim();
                if(!npwd.isEmpty() && npwd.length() == cpwd.length())
                {
                    if (npwd.equals(cpwd))
                    {
                        //show the tick mark
                        imgValid.setVisibility(View.VISIBLE);

                    }else {
                        //no show the tick mark
                        imgValid.setVisibility(View.INVISIBLE);
                    }
                }else{
                    //no show the tick mark
                    imgValid.setVisibility(View.INVISIBLE);
                }
            }
        });

        txtPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String npwd = txtPassword.getText().toString().trim();
                String cpwd = txtConPassword.getText().toString().trim();
                if(!npwd.isEmpty() && npwd.length() == cpwd.length())
                {
                    if (npwd.equals(cpwd))
                    {
                        //show the tick mark
                        imgValid.setVisibility(View.VISIBLE);

                    }else {
                        //no show the tick mark
                        imgValid.setVisibility(View.INVISIBLE);
                    }
                }else{
                    //no show the tick mark
                    imgValid.setVisibility(View.INVISIBLE);
                }
            }
        });


        btnCreateAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Do registration

                if (!isEmailOk())
                {
                    txtReg.setText(getString(R.string.invalid_email));
                    txtReg.setTextColor(Color.RED);
                    return;
                }

                if(txtFullName.getText().toString().isEmpty() || txtMobile.getText().toString().isEmpty())
                {
                    txtReg.setText(getString(R.string.missing_info));
                    txtReg.setTextColor(Color.YELLOW);
                    return;
                }

                if(txtPassword.getText().toString().length()<6)
                {
                    txtReg.setText(getString(R.string.pwd_short));
                    txtReg.setTextColor(Color.YELLOW);
                    return;
                }


                if(imgValid.getVisibility()== View.VISIBLE)
                {
                    //Do registration
                    try {
                        registerClient();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else{
                    txtReg.setText(getString(R.string.pwd_mismatch));
                    txtReg.setTextColor(Color.RED);
                }




            }
        });




        return rootView;
    }


    private boolean isEmailOk(){
        if(!txtEmail.getText().toString().isEmpty())
        {
            return Patterns.EMAIL_ADDRESS.matcher(txtEmail.getText().toString().trim()).matches();
        }else{
            return false;
        }
    }



    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

    }

    @Override
    public void onResume() {
        super.onResume();
        setTitle("Registration");
    }


    @Override
    public void onDetach() {
        super.onDetach();
    }


    private ActionBar getActionBar() {
        return ((AppCompatActivity) getActivity()).getSupportActionBar();
    }

    private void setTitle(String title)
    {


        // ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle();

        ActionBar actionBar = getActionBar();
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setTitle(title);
    }


    //this method will register the user
    private void registerClient() throws JSONException {

        //Displaying a progress dialog
        final ProgressDialog loading = ProgressDialog.show(getActivity(), "Registering", "Please wait... you will soon be in our awesome network",false,false);

        //Getting user data
        fullName = txtFullName.getText().toString().trim();
        email = txtEmail.getText().toString().trim();
        mobile = txtMobile.getText().toString().trim();
        pwd = txtPassword.getText().toString().trim();
        companyname=txtCompanyName.getText().toString().trim();
        carmodel=txtCarModel.getText().toString().trim();
        numPlate=txtNumPlate.getText().toString().trim();
        licence=txtLicence.getText().toString().trim();
        year=txtYear.getText().toString().trim();


        //create the string request
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.REGISTER_URL,
                new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response) {
                        loading.dismiss();

                        try{
                            //creating the Json object from the response
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean error = jsonResponse.getBoolean(Config.ERROR_RESPONSE);
                            String serverMsg = jsonResponse.getString("message");
                            //if it is success
                            if(!error)
                            {
                                //asking user to confirm OTP
                                confirmOtp();
                            }else{
                                Toast.makeText(getActivity(), serverMsg, Toast.LENGTH_LONG).show();

                            }
                           /* if(jsonResponse.getString(Config.TAG_RESPONSE).equalsIgnoreCase("success")){
                                //asking user to confirm OTP
                                confirmOtp();
                            }else{
                                //if not succcess
                                Toast.makeText(getActivity(), "Welcome back to our network, we happy to have you back", Toast.LENGTH_LONG).show();
                            }*/

                        }catch (JSONException e)
                        {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        loading.dismiss();
                        Toast.makeText(getActivity(), error.getMessage(),Toast.LENGTH_LONG).show();
                    }
                }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                //Adding the parameters to the request name','email','mobile','password', 'company_name',  'carmodel', 'numplate', 'license', 'year
                params.put(Config.KEY_NAME, fullName);
                params.put(Config.KEY_EMAIL, email);
                params.put(Config.KEY_MOBILE, mobile);
                params.put(Config.KEY_COMNAME, companyname);
                params.put(Config.KEY_CARMODEL, carmodel);
                params.put(Config.KEY_NUMPLATE, numPlate);
                params.put(Config.KEY_LICENSE, licence);
                params.put(Config.KEY_YEAR, year);

                params.put(Config.KEY_PWD, pwd);

                return params;
            }
        };
        //Adding request the queue
        requestQueue.add(stringRequest);
    }


    private void confirmOtp(){
        //switch fragment to await confirmation of the otp
        Fragment fragment = new CheckOTPFragment();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction()

                .addToBackStack(CheckOTPFragment.class.getSimpleName());

        fragmentTransaction.replace(R.id.container_body, fragment);
        fragmentTransaction.commit();
        setTitle("Activate User");
    }

}

