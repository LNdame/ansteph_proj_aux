package ansteph.com.beecabfordrivers.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import ansteph.com.beecabfordrivers.R;
import ansteph.com.beecabfordrivers.model.JourneyRequest;
import ansteph.com.beecabfordrivers.view.CabResponder.JobDetails;

/**
 * Created by loicStephan on 11/08/16.
 */
public class JobCardAdapter extends RecyclerView.Adapter<JobCardAdapter.JobViewHolder> {

    private Context mContext;
    private List<JourneyRequest> jobList;

    public JobCardAdapter(Context mContext, List<JourneyRequest> jobList) {
        this.mContext = mContext;
        this.jobList = jobList;
    }

    @Override
    public JobViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.job_card,parent,false);

        return new JobViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final JobViewHolder holder, int position) {
       final JourneyRequest job = jobList.get(position);
        holder.lowBid.setText("R "+job.getProposedFare());
        holder.destination.setText(job.getDestinationAddr());
        holder.pickup.setText(job.getPickupAddr());
      //  holder.proposedFare.setText("R "+job.getProposedFare());
        holder.putime.setText(job.getPickupTime());

        // loading job car using Glide library not in this case though

        holder.overflow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PopupMenu popupMenu = new PopupMenu(mContext, v);
                MenuInflater inflater = popupMenu.getMenuInflater();
                inflater.inflate(R.menu.menu_job_card,popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.action_accept_job:
                            case R.id.action_view_job:
                                Intent i = new Intent(mContext, JobDetails.class);
                                 i.putExtra("job", job);
                                mContext.startActivity(i);
                                return true;
                            default:
                        }
                        return false;
                    }
                });
                popupMenu.show();

                // showPopupMenu(holder.overflow);
            }
        });
    }


    /**
     * Showing popup menu when tapping on 3 dots
     */
    private void showPopupMenu(View view)
    {
        PopupMenu popupMenu = new PopupMenu(mContext, view);
        MenuInflater inflater = popupMenu.getMenuInflater();
        inflater.inflate(R.menu.menu_job_card,popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new JobMenuItemClickListener());
        popupMenu.show();
    }

    @Override
    public int getItemCount() {
        return jobList.size();
    }


    //****************************************Inner Class*********************************//

    class JobMenuItemClickListener implements PopupMenu.OnMenuItemClickListener{
        public JobMenuItemClickListener() {
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {

            switch (item.getItemId()){
                case R.id.action_accept_job:
                case R.id.action_view_job:
                    Intent i = new Intent(mContext, JobDetails.class);
                   // i.putExtra("job",)
                    mContext.startActivity(i);
                    return true;
                default:
            }

            return false;
        }
    }




    public class JobViewHolder extends RecyclerView.ViewHolder{

        public TextView lowBid, destination, pickup,  putime;
        public ImageView overflow;

        public JobViewHolder(View itemView) {
            super(itemView);
            lowBid  =(TextView) itemView.findViewById(R.id.txtlowbid);
            destination  =(TextView) itemView.findViewById(R.id.txtDestination);
            pickup  =(TextView) itemView.findViewById(R.id.txtPickup);
           // proposedFare  =(TextView) itemView.findViewById(R.id.txtFare);
            putime  =(TextView) itemView.findViewById(R.id.txtTime);
            overflow  =(ImageView) itemView.findViewById(R.id.overflow);
        }
    }

}
