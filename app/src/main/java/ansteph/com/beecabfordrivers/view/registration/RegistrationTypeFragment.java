package ansteph.com.beecabfordrivers.view.registration;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import ansteph.com.beecabfordrivers.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegistrationTypeFragment extends Fragment {

    public final static String TAG = RegistrationTypeFragment.class.getSimpleName();

    public RegistrationTypeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_registration_type, container, false);

        ImageButton btnTypCompany = (ImageButton) rootView.findViewById(R.id.btnTypeCompany);

        btnTypCompany.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new RegistrationFragment();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction()

                        .addToBackStack(RegistrationFragment.TAG);

                fragmentTransaction.replace(R.id.container_body, fragment);
                fragmentTransaction.commit();
                setTitle("Create an account");

            }
        });

        ImageButton btnTypFreelancer = (ImageButton) rootView.findViewById(R.id.btnTypeFreelancer);

        btnTypFreelancer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new RegistrationFragment();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction()

                        .addToBackStack(RegistrationFragment.TAG);

                fragmentTransaction.replace(R.id.container_body, fragment);
                fragmentTransaction.commit();
                setTitle("Create an account");
            }
        });
        return rootView;
    }




    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

    }

    @Override
    public void onResume() {
        super.onResume();
        setTitle("Pick a role");
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

}
