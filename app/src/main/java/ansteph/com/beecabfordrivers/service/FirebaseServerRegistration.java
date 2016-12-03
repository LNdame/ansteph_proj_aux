package ansteph.com.beecabfordrivers.service;

import android.content.Context;
import android.util.Log;
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

import ansteph.com.beecabfordrivers.app.Config;
import ansteph.com.beecabfordrivers.model.Client;
import ansteph.com.beecabfordrivers.model.Driver;

/**
 * Created by loicStephan on 10/08/16.
 */
public class FirebaseServerRegistration {

    private static final String TAG = FirebaseServerRegistration.class.getSimpleName();

    private Context context;

    private Driver driver;

    private String token;
    private RequestQueue requestQueue;


    public FirebaseServerRegistration(Context context, Driver driver, String token) {
        this.context = context;
        this.driver = driver;
        this.token = token;
    }





    public  void registerFBToken()
    {
// sending gcm token to server
        Log.e(TAG, "sendRegistrationToServer: " + token);

        if(driver!= null)
        {
            requestQueue = Volley.newRequestQueue(context);

            final String mobile = driver.getMobile();
            final String id =  driver.getId();


            StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.REGISTER_FB,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            try{
                                JSONObject jsonResponse = new JSONObject(response);
                                boolean error = jsonResponse.getBoolean(Config.ERROR_RESPONSE);
                                String serverMsg = jsonResponse.getString("message");

                                if(error){
                                   // Toast.makeText(context, serverMsg,Toast.LENGTH_LONG).show();
                                }else{
                                    //Toast.makeText(getContext(), serverMsg,Toast.LENGTH_LONG).show(); //context as oppose to getcontext maybe.
                                }

                            }catch (JSONException e)
                            {
                                e.printStackTrace();
                            }

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, error.getMessage(),Toast.LENGTH_LONG).show();
                }
            }


            ){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();

                    params.put("token",token);
                    params.put("mobile",mobile);
                    params.put("id",id);
                    params.put("flag","1");
                    return params;
                }
            };



            requestQueue.add(stringRequest);


        }



    }






    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }



    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }



}
