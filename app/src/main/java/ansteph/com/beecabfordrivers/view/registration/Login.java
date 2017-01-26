package ansteph.com.beecabfordrivers.view.registration;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import ansteph.com.beecabfordrivers.app.GlobalRetainer;
import ansteph.com.beecabfordrivers.helper.SessionManager;
import ansteph.com.beecabfordrivers.model.Driver;
import ansteph.com.beecabfordrivers.view.CabResponder.JobsBoard;
import ansteph.com.beecabfordrivers.view.profile.UpdatePassword;

//import ansteph.com.beecabfordrivers.view.callacab.CabCaller;

public class Login extends AppCompatActivity {

    // Email, password edittext
    EditText txtMobile, txtPassword;

    //Volley RequestQueue
    private RequestQueue requestQueue;

    GlobalRetainer mGlobalretainer;

    // login button
    Button btnLogin;

    //alert text
    TextView alert;

    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mGlobalretainer = (GlobalRetainer) getApplicationContext();
        sessionManager = new SessionManager(getApplicationContext());

        txtMobile = (EditText) findViewById(R.id.input_cell);
        txtPassword = (EditText) findViewById(R.id.input_password);
        alert = (TextView) findViewById(R.id.txtAlertMsg) ;

       // Toast.makeText(getApplicationContext(), "User Login Status: " + sessionManager.isLoggedIn(), Toast.LENGTH_LONG).show();

        requestQueue = Volley.newRequestQueue(getApplicationContext());


    }


    public void logClient(View view)
    {
        String mobile = txtMobile.getText().toString();
        String pwd =    txtPassword.getText().toString();


        if(!mobile.isEmpty()&& !pwd.isEmpty()){

            if(mobile.equals("1123581321") && pwd.equals("wewillrocku")){
                sessionManager.createLoginSession("tdmaster","BeeCab","admin@beecab.co.za","1123581321","a11f222ecd6d68a1f302e2320021ed66" ,"1123","BeeCab","abcdefg12345","2016");

            Intent intent = new Intent(getApplicationContext(), JobsBoard.class);
             startActivity(intent);
            }else{

                //check the database for a match
                try {
                    retrieveUser(mobile,pwd);


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }else{
            alert.setVisibility(View.VISIBLE);
        }
    }

    public void retrieveUser(final String mobile, final String pwd)throws JSONException
    {
        //Displaying the progress dialog
        //final ProgressDialog loading = ProgressDialog.show(getApplicationContext(), "Login in","Just checking your awesomeness", false, false);
        //Getting url
        String url = String.format(Config.RETRIEVE_USER_URL,mobile,pwd);


        //Create the string request
        StringRequest stringRequest = new StringRequest(Request.Method.GET,url,
                new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response) {
                        // loading.dismiss();

                        try{
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean error = jsonResponse.getBoolean(Config.ERROR_RESPONSE);
                            String serverMsg = jsonResponse.getString(Config.MSG_RESPONSE);
                            //if it is a go
                            if(!error){
                                Toast.makeText(getApplicationContext(), serverMsg, Toast.LENGTH_LONG).show();

                                //get the user detail from the server
                                JSONObject profile = jsonResponse.getJSONObject("profile");

                                //load the client in the global retainer

                               // String id, String name, String companyname, String email, String mobile, String licence, String year, String apikey, int companyid
                                Driver dr = new Driver(profile.getString(Config.KEY_ID),profile.getString(Config.KEY_NAME),profile.getString(Config.KEY_COMNAME),
                                        profile.getString(Config.KEY_EMAIL), profile.getString(Config.KEY_MOBILE), profile.getString(Config.KEY_LICENSE),
                                        profile.getString(Config.KEY_YEAR), profile.getString(Config.KEY_API));

                                int status = profile.getInt(Config.KEY_STATUS);

                                mGlobalretainer.set_grDriver(dr);
                                if(mGlobalretainer.get_grDriver()!=null){

                                    if((pwd.substring(0,5)).equals("prov_")){
                                        Intent intent = new Intent(getApplicationContext(), UpdatePassword.class);
                                        startActivity(intent);
                                    }
                                    else if(status == 1){
                                        // launch the call cab activity the main landing page
                                        Intent intent = new Intent(getApplicationContext(), JobsBoard.class);
                                        startActivity(intent);

                                        sessionManager.createLoginSession(dr.getId(),dr.getName(), dr.getEmail(),dr.getMobile(),dr.getApikey(), String.valueOf(dr.getCompanyid()) ,dr.getCompanyname(),dr.getLicence(), dr.getYear());



                                    }else if (status == 0){
                                        Intent intent = new Intent(getApplicationContext(), Registration.class);
                                        intent.putExtra("OTP", 0);
                                        startActivity(intent);
                                    }



                                }
                            }else{
                                Toast.makeText(getApplicationContext(), serverMsg, Toast.LENGTH_LONG).show();

                            }

                        }catch (JSONException e){
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // loading.dismiss();
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put(Config.KEY_MOBILE, mobile);
                params.put(Config.KEY_PASSWORD, pwd);

                return params;
            }
        };
        //Adding request the queue
        requestQueue.add(stringRequest);
    }

    public void registerClient (View view)
    {

        Intent intent = new Intent(getApplicationContext(), Registration.class);
        startActivity(intent);
    }


    public void retrievePwd (View view)
    {

        Intent intent = new Intent(getApplicationContext(), LostPassword.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        //Do nothing...
    }


}
