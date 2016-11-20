package ansteph.com.beecabfordrivers.view.profile;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

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

    public FieldEditorFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FieldEditorFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FieldEditorFragment newInstance(String param1, String param2) {
        FieldEditorFragment fragment = new FieldEditorFragment();
        Bundle args = new Bundle();
        args.putString(HINT, param1);
        args.putString(TITLE, param2);
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

      //  txtEditee.setHint(mHint);
        TextInputLayout textInputLayout = (TextInputLayout) rootView.findViewById(R.id.input_layout_editee);
        textInputLayout.setHint(mHint);
        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
    }


}
