package ansteph.com.beecabfordrivers.view.CabResponder;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.SphericalUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import ansteph.com.beecabfordrivers.R;
import ansteph.com.beecabfordrivers.app.Config;
import ansteph.com.beecabfordrivers.app.GlobalRetainer;
import ansteph.com.beecabfordrivers.model.JourneyRequest;

public class JobDetails extends AppCompatActivity implements OnMapReadyCallback{

    public TextView lowBid, destination, pickup, proposedFare,  putime, txtdistance, txtTimeLeft;

    MapView mMapView;

    GoogleMap mGoogleMap;
    Projection projection;
    JourneyRequest job;
    private Marker mPE;


    RelativeLayout lytBiding, lytOffer;
    TextView txtbid;

    Button btnSubmit, btnCancelOffer;
    GlobalRetainer mGlobalRetainer;


    String mCounterFare;

    public static final CameraPosition PE =
            new CameraPosition.Builder().target(new LatLng(-33.7139, 25.5207))
                    .zoom(15.5f)
                    .bearing(0)
                    .tilt(25)
                    .build();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mGlobalRetainer =(GlobalRetainer) getApplicationContext();

        lytBiding = (RelativeLayout) findViewById(R.id.lytBiding);
        lytOffer = (RelativeLayout) findViewById(R.id.lytOffer);

        txtbid = (TextView) findViewById(R.id.txtbidtitle);

        job = new JourneyRequest();

        lowBid  =(TextView) findViewById(R.id.txtlowbid);
        destination  =(TextView) findViewById(R.id.txtDestination);
        pickup  =(TextView) findViewById(R.id.txtPickup);
        proposedFare  =(TextView) findViewById(R.id.txtFare);
        putime  =(TextView) findViewById(R.id.txtTime);
        txtdistance=(TextView) findViewById(R.id.txtDistance);
        txtTimeLeft=(TextView) findViewById(R.id.txtTimeLeft);

        Bundle extra = getIntent().getExtras();
        if(extra!=null)
        {
            job = (JourneyRequest) extra.getSerializable("job");
            destination.setText(job.getDestinationAddr());
            pickup.setText(job.getPickupAddr());
            proposedFare.setText("R"+job.getProposedFare());
            putime.setText(job.getPickupTime());
            lowBid.setText("R"+job.getProposedFare());

            if(job.getTimeCreated()!=null) primeTimer(job.getTimeCreated());

            setupLayout(extra.getInt(Config.FLAG_ORIGIN));
        }


        try{
            MapsInitializer.initialize(getApplicationContext());
            mMapView = (MapView) findViewById(R.id.map);
            mMapView.onCreate(savedInstanceState);
            mMapView.getMapAsync(this);
        }catch (Exception e)
        {
           e.printStackTrace();
        }


        btnSubmit = (Button) findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCounterFare = ((EditText) findViewById(R.id.edtCounter)).getText().toString();

                if(mCounterFare.equals("")||mCounterFare.isEmpty()){
                    mCounterFare=job.getProposedFare();
                }

                try {
                    createJobResponse(job);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        btnCancelOffer = (Button) findViewById(R.id.btnCancelOf);
        btnCancelOffer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });



        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    private void primeTimer(Date sentDate)
    {
        long timeinMillisNow = (new Date()).getTime();
        long sentDateMillis = sentDate.getTime();

        long countdownmill = (sentDateMillis +600000) -timeinMillisNow;

        new CountDownTimer(countdownmill, 1000){

            @Override
            public void onTick(long millisUntilFinished) {
                //    txtTimer.setText("Time remaining: " + millisUntilFinished/1000);
                txtTimeLeft.setText(""+String.format("%d : %d  ", TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished),
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished)-
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));
            }

            @Override
            public void onFinish() {
                txtTimeLeft.setText("all bets are off!");
            }
        }.start();
    }


    private void setupLayout(int flag)
    {
        if(flag == Config.FLAG_ASSLIST)
        {
          lytBiding.setVisibility(View.GONE);
            lytOffer.setVisibility(View.GONE);
            txtbid.setText(getString(R.string.bidtitle));
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

        super.onSaveInstanceState(outState);
        mMapView.onSaveInstanceState(outState);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }


    //Calculate path and distance

    //From -> the first coordinate from where we need to calculate the distance
    private double fromLongitude;
    private double fromLatitude;

    //To -> the second coordinate to where we need to calculate the distance
    private double toLongitude;
    private double toLatitude;


    public void getDirection()
    {
//Getting the URL
        String url = makeURL(fromLatitude, fromLongitude, toLatitude, toLongitude);

        //Showing a dialog till we get the route
        final ProgressDialog loading = ProgressDialog.show(this, "Getting Route", "Please wait...", false, false);

        //Creating a string request
        StringRequest stringRequest = new StringRequest(url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        loading.dismiss();
                        //Calling the method drawPath to draw the path
                        drawPath(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        loading.dismiss();
                    }
                });

        //Adding the request to request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public String makeURL (double sourcelat, double sourcelog, double destlat, double destlog ){
        StringBuilder urlString = new StringBuilder();
        urlString.append("https://maps.googleapis.com/maps/api/directions/json");
        urlString.append("?origin=");// from
        urlString.append(Double.toString(sourcelat));
        urlString.append(",");
        urlString
                .append(Double.toString( sourcelog));
        urlString.append("&destination=");// to
        urlString
                .append(Double.toString( destlat));
        urlString.append(",");
        urlString.append(Double.toString(destlog));
        urlString.append("&sensor=false&mode=driving&alternatives=true");
        urlString.append("&key=AIzaSyA6ukiOzzaLCeoxmumYhbB5s0ksvpjWaBU");
        return urlString.toString();
    }

    //The parameter is the server response
    public void drawPath(String  result) {
        //Getting both the coordinates
        LatLng from = new LatLng(fromLatitude,fromLongitude);
        LatLng to = new LatLng(toLatitude,toLongitude);

        //Calculating the distance in meters
        Double distance = SphericalUtil.computeDistanceBetween(from, to);
        Double  distinKM = distance/1000;

        BigDecimal bd = new BigDecimal(distinKM);
         bd= bd.setScale(2,BigDecimal.ROUND_HALF_UP);
        distinKM = bd.doubleValue();
        //Displaying the distance
        txtdistance.setText(String.valueOf(distinKM+" km"));
       // Toast.makeText(this,String.valueOf(distance+" Meters"),Toast.LENGTH_SHORT).show();


        try {
            //Parsing json
            final JSONObject json = new JSONObject(result);
            JSONArray routeArray = json.getJSONArray("routes");
            JSONObject routes = routeArray.getJSONObject(0);
            JSONObject overviewPolylines = routes.getJSONObject("overview_polyline");
            String encodedString = overviewPolylines.getString("points");
            List<LatLng> list = decodePoly(encodedString);
            Polyline line = mGoogleMap.addPolyline(new PolylineOptions()
                    .addAll(list)
                    .width(20)
                    .color(Color.BLUE)
                    .geodesic(true)
            );


        }
        catch (JSONException e) {

        }
    }

    private List<LatLng> decodePoly(String encoded) {
        List<LatLng> poly = new ArrayList<LatLng>();
        int index = 0, len = encoded.length();
        int lat = 0, lng = 0;

        while (index < len) {
            int b, shift = 0, result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;

            shift = 0;
            result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;

            LatLng p = new LatLng( (((double) lat / 1E5)),
                    (((double) lng / 1E5) ));
            poly.add(p);
        }

        return poly;
    }

    //end Calculate path and distance

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mGoogleMap = googleMap;
        mGoogleMap.getUiSettings().setZoomControlsEnabled(true);
        mGoogleMap.getUiSettings().setZoomGesturesEnabled(true);

        //put in a try the coord destination could be null


        String[] coord = job.getDestinationCoord().split(",");

        toLongitude =Double.parseDouble(coord[1]);
       toLatitude =Double.parseDouble(coord[0]);

        LatLng dest = new LatLng(Double.parseDouble(coord[0]),Double.parseDouble(coord[1]));

        String[] coorp = job.getPickupCoord().split(",");

        fromLongitude = Double.parseDouble(coorp[1]);
        fromLatitude =Double.parseDouble(coorp[0]);

        LatLng pick = new LatLng(Double.parseDouble(coorp[0]),Double.parseDouble(coorp[1]));

        Marker pickup = mGoogleMap.addMarker(new MarkerOptions().position(pick).title("Pickup").snippet(job.getPickupAddr()).
                icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)).draggable(false));

        Marker destination = mGoogleMap.addMarker(new MarkerOptions().position(dest).title("Destination").snippet(job.getDestinationAddr()).
                icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)).draggable(false));

        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(pick,12.5f));

        getDirection();

    }


    public void createJobResponse (final JourneyRequest j) throws JSONException {

        // Displaying the progress dialog
        final ProgressDialog loading = ProgressDialog.show(this, "Sending answer","Just wait while we submit your answer", false, false);


        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.CREATE_RESPONSE_JOB_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        try{
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean error = jsonResponse.getBoolean(Config.ERROR_RESPONSE);
                            String serverMsg = jsonResponse.getString(Config.MSG_RESPONSE);
                            if(!error){
                                loading.dismiss();
                                Toast.makeText(getApplicationContext(), serverMsg, Toast.LENGTH_LONG).show();

                            }
                        }catch (JSONException e)
                        {
                            loading.dismiss();
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loading.dismiss();
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();

                params.put(Config.KEY_PRO_FARE, j.getProposedFare());
                params.put(Config.KEY_COUNT_OFFER, mCounterFare);
                params.put(Config.KEY_CALL_ALL, (j.isCallAllowed()==true)?"1":"0");
                params.put(Config.KEY_JRID, String.valueOf(j.getId()) );
                params.put(Config.KEY_TDID, mGlobalRetainer.get_grDriver().getId());

                return params;

            }
        };
        RequestQueue requestQueue =  Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);

    }



}
