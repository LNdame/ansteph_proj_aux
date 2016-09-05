package ansteph.com.beecabfordrivers.view.extraAction;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ViewAnimator;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import ansteph.com.beecabfordrivers.R;
import ansteph.com.beecabfordrivers.app.Config;
import ansteph.com.beecabfordrivers.testzone.CustomVolleyRequest;
import ansteph.com.beecabfordrivers.testzone.GridViewAdapter;
import ansteph.com.beecabfordrivers.view.CabResponder.BoardLangdingFragment;

public class Profile extends AppCompatActivity  {

    //Imageloader to load images
    private ImageLoader imageLoader;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Fragment fragment = new ProfileFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction()
                .addToBackStack(ProfileFragment.TAG);
        fragmentTransaction.replace(R.id.container_body, fragment,ProfileFragment.TAG);
        fragmentTransaction.commit();


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }





}



/*RelativeLayout relativeLayout = new RelativeLayout(this);
RelativeLayout.LayoutParams rlp = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT);
setContentView(relativeLayout, rlp);

But, now, every time I want to add a view, I have to add it to this relativeLayout this way:

 final ViewAnimator viewAnimator = (ViewAnimator) rootView.findViewById(R.id.viewAnimator);

        Button btnNext = (Button) rootView.findViewById(R.id.btnNext);
        Button btnPrv = (Button) rootView.findViewById(R.id.btnPrevious);

        final Animation inAmin = AnimationUtils.loadAnimation(getActivity(),android.R.anim.slide_in_left);
        final Animation outAmin = AnimationUtils.loadAnimation(getActivity(),android.R.anim.slide_out_right);

        viewAnimator.setInAnimation(inAmin);
        viewAnimator.setOutAnimation(outAmin);

        viewAnimator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewAnimator.showNext();
            }
        });



relativeLayout.addView(View, Params);*/