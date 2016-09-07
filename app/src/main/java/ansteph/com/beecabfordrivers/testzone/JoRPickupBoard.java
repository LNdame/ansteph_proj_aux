package ansteph.com.beecabfordrivers.testzone;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ansteph.com.beecabfordrivers.R;
import ansteph.com.beecabfordrivers.app.Config;
import ansteph.com.beecabfordrivers.card.Card;
import ansteph.com.beecabfordrivers.card.CardProvider;
import ansteph.com.beecabfordrivers.card.MaterialListView;
import ansteph.com.beecabfordrivers.card.OnActionClickListener;
import ansteph.com.beecabfordrivers.card.TextViewAction;
import ansteph.com.beecabfordrivers.card.listener.OnDismissCallback;
import ansteph.com.beecabfordrivers.card.listener.RecyclerItemClickListener;
import ansteph.com.beecabfordrivers.model.JourneyRequest;
import ansteph.com.beecabfordrivers.view.CabResponder.JobDetails;
import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator;

public class JoRPickupBoard extends AppCompatActivity {

    private Context mContext;
    private MaterialListView mListView;
    private List<JourneyRequest> jobList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jo_rpickup_board);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        jobList = new ArrayList<>();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        // Save a reference to the context
        mContext = this;

        // Bind the MaterialListView to a variable
        mListView = (MaterialListView) findViewById(R.id.material_listview);
        mListView.setItemAnimator(new SlideInLeftAnimator());
        mListView.getItemAnimator().setAddDuration(300);
        mListView.getItemAnimator().setRemoveDuration(300);


        final ImageView emptyView = (ImageView) findViewById(R.id.imageView);
        emptyView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        mListView.setEmptyView(emptyView);
        Picasso.with(this)
                .load(R.mipmap.ic_launcher)
                .resize(100, 100)
                .centerInside()
                .into(emptyView);


        try {
            retrieveJobs();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Set the dismiss listener
        mListView.setOnDismissCallback(new OnDismissCallback() {
            @Override
            public void onDismiss(@NonNull Card card, int position) {
                // Show a toast
                Toast.makeText(mContext, "You have dismissed a " + card.getTag(), Toast.LENGTH_SHORT).show();
            }
        });

// Add the ItemTouchListener
        mListView.addOnItemTouchListener(new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull Card card, int position) {
                Log.d("CARD_TYPE", "" + card.getTag());
            }

            @Override
            public void onItemLongClick(@NonNull Card card, int position) {
                Log.d("LONG_CLICK", "" + card.getTag());
            }
        });


    }


    private void fillArray() {
        List<Card> cards = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            cards.add(getaCard(i));
        }
        mListView.getAdapter().addAll(cards);
    }


    private void loadCard(JSONArray jobArray){
        List<Card> cards = new ArrayList<>();

        for (int i=0; i<jobArray.length(); i++)
        {
            try {
                JSONObject job = jobArray.getJSONObject(i);
                JourneyRequest j=  new JourneyRequest(job.getInt("id"), job.getString("jr_pickup_add"),job.getString("jr_destination_add"),job.getString("jr_pickup_time"),String.valueOf(job.getInt("jr_proposed_fare"))
                        ,job.getString("jr_pickup_coord"),job.getString("jr_destination_coord"),job.getString("jr_tc_id"));

                SimpleDateFormat sdf = new SimpleDateFormat(Config.DATE_FORMAT);

                try{
                    Date mDate = sdf.parse(job.getString("jr_time_created"));
                    j.setTimeCreated(mDate);
                }catch (ParseException e)
                {
                    e.printStackTrace();
                }



                cards.add(createCard(j));

                jobList.add(j);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        mListView.getAdapter().addAll(cards);
    }


    private Card createCard(final JourneyRequest j){
        return new Card.Builder(this)
                .setTag("JOR_BUTTONS_CARD")
                .setDismissible()
                .withProvider(new CardProvider())
                .setLayout(R.layout.journey_request_card_layout)
                .setPickupAdd(j.getPickupAddr())
                .setDestinationAdd(j.getDestinationAddr())
                .setFare("R\n"+j.getProposedFare())
                .setTime(j.getPickupTime())
                .addAction(R.id.left_text_button, new TextViewAction(this)
                        .setText("view Details")
                        .setTextResourceColor(R.color.black_button).setListener(new OnActionClickListener() {
                            @Override
                            public void onActionClicked(View view, Card card) {
                                Intent i = new Intent(mContext, JobDetails.class);
                                i.putExtra("job", j);
                                startActivity(i);
                            }
                        }))

                .addAction(R.id.right_text_button, new TextViewAction(this)
                        .setText("Accept")
                        .setTextResourceColor(R.color.orange_button))

                .endConfig()
                .build();
    }

    private Card getaCard(final int position){

        String title = "Card number " + (position + 1);
        String description = "Lorem ipsum dolor sit amet";

        switch (position % 2) {

            case 0:{ return new Card.Builder(this)
                    .setTag("badaboom")
                    .setDismissible()
                    .withProvider(new CardProvider())
                    .setLayout(R.layout.material_small_image_card)
                    .setTitle(title)
                    .setSubtitle("This is a new subtitle")
                    .setDescription(description)

                    .endConfig()
                    .build();


            }
            case 1:{return new Card.Builder(this)
                    .setTag("JOR_BUTTONS_CARD")
                    .setDismissible()
                    .withProvider(new CardProvider())
                    .setLayout(R.layout.journey_request_card_layout)
                    .setPickupAdd("Somewhere in town")
                    .setDestinationAdd("Somewhere in the country side")
                    .setFare("R\n888")
                    .setTime("10:10")
                    .addAction(R.id.left_text_button, new TextViewAction(this)
                            .setText("left")
                            .setTextResourceColor(R.color.black_button))
                    .addAction(R.id.right_text_button, new TextViewAction(this)
                            .setText("right")
                            .setTextResourceColor(R.color.orange_button))
                    .setDrawable(R.drawable.img1934x)
                    .endConfig()
                    .build();

            }
            default: {
                return new Card.Builder(this)
                        .setTag("BASIC_IMAGE_BUTTONS_CARD")
                        .setDismissible()
                        .withProvider(new CardProvider())
                        .setLayout(R.layout.material_basic_image_buttons_card_layout)
                        .setTitle("Hi there")
                        .setDescription("I've been added on top!")
                        .addAction(R.id.left_text_button, new TextViewAction(this)
                                .setText("left")
                                .setTextResourceColor(R.color.black_button))
                        .addAction(R.id.right_text_button, new TextViewAction(this)
                                .setText("right")
                                .setTextResourceColor(R.color.orange_button))
                        .setDrawable(R.drawable.img1934x)
                        .endConfig()
                        .build();
            }

        }




    }




    public void retrieveJobs () throws JSONException {

        // Displaying the progress dialog
        final ProgressDialog loading = ProgressDialog.show(this, "Getting Jobs","Just retrieving the jobs", false, false);


        StringRequest stringRequest = new StringRequest(Request.Method.GET, Config.RETRIEVE_JOBS_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        try{
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean error = jsonResponse.getBoolean(Config.ERROR_RESPONSE);
                            // String serverMsg = jsonResponse.getString(Config.MSG_RESPONSE);
                            if(!error){
                                loading.dismiss();
                                Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
                                //JSONObject jobsList = jsonResponse.getJSONObject("jobs");
                                JSONArray jobsjsonArray = jsonResponse.getJSONArray("jobs");

                               loadCard(jobsjsonArray);

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
        }){};
        RequestQueue requestQueue =  Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);

    }


}
