package ansteph.com.beecabfordrivers.view.profile;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import ansteph.com.beecabfordrivers.R;
import ansteph.com.beecabfordrivers.app.Config;
import ansteph.com.beecabfordrivers.app.GlobalRetainer;
import ansteph.com.beecabfordrivers.helper.SessionManager;
import ansteph.com.beecabfordrivers.model.Driver;
import ansteph.com.beecabfordrivers.view.CabResponder.JobsBoard;

public class UpdatePassword extends AppCompatActivity {


    GlobalRetainer mGlobalRetainer;

    EditText txtOldPwd, txtNewPwd, txtConfirmPwd;
    TextView alert;
    ImageView imgValid;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_password);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        sessionManager = new SessionManager(getApplicationContext());
        mGlobalRetainer =(GlobalRetainer) getApplicationContext();

        txtOldPwd = (EditText) findViewById(R.id.input_previous_password) ;
        txtNewPwd = (EditText) findViewById(R.id.input_new_password) ;
        txtConfirmPwd = (EditText) findViewById(R.id.input_confirm_password) ;
        alert = (TextView) findViewById(R.id.txtAlertMsg) ;
        imgValid = (ImageView) findViewById(R.id.imgValid);

        txtNewPwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String npwd = txtNewPwd.getText().toString().trim();
                String cpwd = txtConfirmPwd.getText().toString().trim();
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

        txtConfirmPwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String npwd = txtNewPwd.getText().toString().trim();
                String cpwd = txtConfirmPwd.getText().toString().trim();
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

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }



    public void updatePasswordClient (View view)
    {
        alert.setText("");
        alert.setVisibility(View.INVISIBLE);
        try {
            CheckOldPwd(mGlobalRetainer.get_grDriver().getMobile(), txtOldPwd.getText().toString().trim());
            Driver dr = mGlobalRetainer.get_grDriver();
            sessionManager.createLoginSession(dr.getId(),dr.getName(), dr.getEmail(),dr.getMobile(),dr.getApikey(), String.valueOf(dr.getCompanyid()) ,dr.getCompanyname(),dr.getLicence(), dr.getYear());

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }



    public  void CheckOldPwd(String mobile, String pwd) throws JSONException
    {
        String url = String.format(Config.RETRIEVE_USER_URL,mobile,pwd);
        final ProgressDialog loading = ProgressDialog.show(this, "Checking","Checking old password", false, false);

        //Create the string request
        StringRequest stringRequest = new StringRequest(Request.Method.GET,url,
                new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response) {
                        loading.dismiss();

                        try{
                            JSONObject jsonResponse = new JSONObject(response);
                            //if it is a go

                            isOldPassword(jsonResponse);
                        }catch (JSONException e){
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        loading.dismiss();
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }){};
        //Adding request the queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }


    public void  isOldPassword(JSONObject jsonResponse)
    {
        try {
            boolean error = jsonResponse.getBoolean(Config.ERROR_RESPONSE);
            if(!error)
            {
                if(imgValid.getVisibility() == View.VISIBLE){
                    updatePassword(txtNewPwd.getText().toString().trim());
                }else {
                    //alert tell the new password is not confirmed
                    alert.setText("The new password and the confirmed password do not match ");
                    alert.setVisibility(View.VISIBLE);
                }

            }else
            {
                //alert tell the password is bogus
                alert.setText("we could not recognise this password");
                alert.setVisibility(View.VISIBLE);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void updatePassword(String pwd) throws JSONException {
        final ProgressDialog loading = ProgressDialog.show(this, "Updating","Now updating our records", false, false);


        String url = ""+String.format(Config.UPDATE_PWD_URL,pwd,mGlobalRetainer.get_grDriver().getId());

        StringRequest stringRequest = new StringRequest(Request.Method.PUT, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        loading.dismiss();
                        try{
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean error = jsonResponse.getBoolean(Config.ERROR_RESPONSE);
                            String serverMsg = jsonResponse.getString(Config.MSG_RESPONSE);
                            if(!error){

                                Toast.makeText(getApplicationContext(), serverMsg, Toast.LENGTH_SHORT).show();

                                //go back to landing
                                gotoLanding();
                            }
                        }catch (JSONException e)
                        {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loading.dismiss();
            }
        }){};
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }


    public void gotoLanding()
    {
        Intent i = new Intent(getApplicationContext(), JobsBoard.class);
        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        //Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        //Starting Login Activity
        startActivity(i);
    }

}
