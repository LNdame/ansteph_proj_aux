package ansteph.com.beecabfordrivers.view.profile;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.android.volley.toolbox.ImageLoader;

import ansteph.com.beecabfordrivers.R;

public class Profile extends AppCompatActivity implements FieldEditorFragment.OnFragmentInteractionListener {

    //Imageloader to load images
    private ImageLoader imageLoader;

    private String modifiable;

    private String hint;

    private boolean isEditorMode;

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

        isEditorMode = false;

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    @Override
    public void onFragmentInteraction(String text, String hint) {
            setModifiable(text);
            setHint(hint);
            setEditorMode(true);
    }

    public String getModifiable() {
        return modifiable;
    }

    public void setModifiable(String modifiable) {
        this.modifiable = modifiable;
    }

    public boolean isEditorMode() {
        return isEditorMode;
    }

    public void setEditorMode(boolean editorMode) {
        isEditorMode = editorMode;
    }

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
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