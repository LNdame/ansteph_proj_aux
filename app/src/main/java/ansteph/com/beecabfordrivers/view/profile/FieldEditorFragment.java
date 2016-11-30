package ansteph.com.beecabfordrivers.view.profile;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import ansteph.com.beecabfordrivers.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FieldEditorFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FieldEditorFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    public static  final String TAG = FieldEditorFragment.class.getSimpleName();
    private static final String HINT = "param1";
    private static final String TITLE = "param2";

    // TODO: Rename and change types of parameters
    private String mHint;
    private String mTitle;

    EditText txtEditee;
    LinearLayout lytCity, lytSensitive;
    Spinner spinCity;

    Button btnCancel,btnOK;
    public FieldEditorFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param hint Parameter 1.
     * @param title Parameter 2.
     * @return A new instance of fragment FieldEditorFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FieldEditorFragment newInstance(String hint, String title) {
        FieldEditorFragment fragment = new FieldEditorFragment();
        Bundle args = new Bundle();
        args.putString(HINT, hint);
        args.putString(TITLE, title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mHint = getArguments().getString(HINT);
            mTitle = getArguments().getString(TITLE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_field_editor, container, false);

        getActivity().setTitle(mTitle);

        txtEditee = (EditText) rootView.findViewById(R.id.txtEditee);
        lytCity = (LinearLayout) rootView.findViewById(R.id.lytCity);
        lytSensitive = (LinearLayout) rootView.findViewById(R.id.lytSensitive);
        spinCity= (Spinner) rootView.findViewById(R.id.spinCity);
                //  txtEditee.setHint(mHint);
        TextInputLayout textInputLayout = (TextInputLayout) rootView.findViewById(R.id.input_layout_editee);
        textInputLayout.setHint(mHint);

        btnCancel = (Button) rootView.findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        btnOK = (Button) rootView.findViewById(R.id.btnOK);
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mHint.equals("Current City"))
                {
                    String text = spinCity.getSelectedItem().toString();
                    onButtonPressed(text.trim());
                    getActivity().onBackPressed();

                }else{
                    onButtonPressed(txtEditee.getText().toString().trim());
                    getActivity().onBackPressed();
                }


            }
        });

        Button btnSendEmail = (Button) rootView.findViewById(R.id.btnSendEmail);
        btnSendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendTicket();

            }
        });

        setUpEditor();

        return rootView;
    }


    public void setUpEditor()
    {
        if (mHint.equals("Current City"))
        {
           lytCity.setVisibility(View.VISIBLE);
            txtEditee.setVisibility(View.GONE);




            ArrayAdapter<CharSequence> adapter =  ArrayAdapter.createFromResource(getActivity(),R.array.city,android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinCity.setAdapter(adapter);

        } else if (mHint.equals("Email") || mHint.equals("Cab Licence Number")||mHint.equals("Year of Licence") ){

            lytSensitive.setVisibility(View.VISIBLE);
            txtEditee.setVisibility(View.GONE);
            btnOK.setVisibility(View.GONE);

        }

    }



    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
    }




    private OnFragmentInteractionListener mListener;

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(String text, String hint);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(String text) {
        if (mListener != null) {
            mListener.onFragmentInteraction(text, mHint);
        }
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {

            mListener = (OnFragmentInteractionListener) context;

        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }



    public void sendTicket()
    {
        /* Create the Intent */
        final Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);

/* Fill it with Data */
        emailIntent.setType("plain/text");
        emailIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{"support@beecab.net"});
        emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Please change my " +mHint);
        emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "supporting text");

/* Send it off to the Activity-Chooser */
        getActivity().startActivity(Intent.createChooser(emailIntent, "Send Ticket..."));
    }
}
