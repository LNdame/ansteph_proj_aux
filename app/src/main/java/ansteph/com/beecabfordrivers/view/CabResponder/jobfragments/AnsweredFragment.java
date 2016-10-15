package ansteph.com.beecabfordrivers.view.CabResponder.jobfragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ansteph.com.beecabfordrivers.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AnsweredFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AnsweredFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String TITLE = "param1";
    private static final String PAGE = "param2";

    // TODO: Rename and change types of parameters
    private String title;
    private String page;


    public AnsweredFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param title Parameter 1.
     * @param page Parameter 2.
     * @return A new instance of fragment AnsweredFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AnsweredFragment newInstance(String title, String page) {
        AnsweredFragment fragment = new AnsweredFragment();
        Bundle args = new Bundle();
        args.putString(TITLE, title);
        args.putString(PAGE, page);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            title = getArguments().getString(TITLE);
            page = getArguments().getString(PAGE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_answered, container, false);
    }

}
