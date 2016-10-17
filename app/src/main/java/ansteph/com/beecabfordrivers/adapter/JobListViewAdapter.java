package ansteph.com.beecabfordrivers.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import ansteph.com.beecabfordrivers.R;
import ansteph.com.beecabfordrivers.app.Config;
import ansteph.com.beecabfordrivers.model.JourneyRequest;
import ansteph.com.beecabfordrivers.view.CabResponder.JobDetails;

/**
 * Created by loicStephan on 26/07/16.
 */
public class JobListViewAdapter extends ArrayAdapter<JourneyRequest> {


    Context context;
    int layoutResourceId;

   ArrayList<JourneyRequest>  jobs  = null;

    public JobListViewAdapter(Context context, int layoutResourceId, ArrayList<JourneyRequest> jobs) {
        super(context, layoutResourceId, jobs);
        this.context = context;
        this.layoutResourceId = layoutResourceId;
        this.jobs = jobs;


    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View row = convertView;
        JourneyRequestHolder holder = null;

        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context ).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent,false);


            holder = new JourneyRequestHolder();
            holder.txtPickup = (TextView) row.findViewById(R.id.txtPickup);
            holder.txtDestination = (TextView) row.findViewById(R.id.txtDestination);
            holder.txtFare = (TextView) row.findViewById(R.id.txtFare);
            holder.txtTime = (TextView) row.findViewById(R.id.txtTime);

           // holder.btnClose = (Button)row.findViewById(R.id.btnClose);
            holder.btnDetails = (Button)row.findViewById(R.id.btnDetails);
            holder.imgbtnDrop = (ImageView) row.findViewById(R.id.imgbtnDrop);



            row.setTag(holder);
        }else
        {
            holder = (JourneyRequestHolder) row.getTag();
        }

        final JourneyRequest jr = jobs.get(position);
        holder.txtPickup.setText(jr.getPickupAddr());
        holder.txtDestination.setText(jr.getDestinationAddr());
        holder.txtFare.setText(jr.getProposedFare());
        holder.txtTime.setText(jr.getPickupTime());


        holder.btnDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(context, JobDetails.class);
                i.putExtra("job",jr);
                i.putExtra(Config.FLAG_ORIGIN,Config.FLAG_ASSLIST);
                context.startActivity(i);
               // Toast.makeText(context, "Clicked", Toast.LENGTH_SHORT).show();
            }
        });

        row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  Intent i = new Intent(context, JobResponder.class);
               // i.putExtra("job",jr);
                //context.startActivity(i);
            }
        });


        return row;
    }



    static class JourneyRequestHolder
    {
        TextView txtPickup, txtDestination, txtFare, txtTime;
        Button btnDetails;
        ImageView imgbtnDrop;
    }

}
