package ansteph.com.beecabfordrivers.view.CabResponder;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ViewAnimator;

import com.bumptech.glide.Glide;

import ansteph.com.beecabfordrivers.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BoardLangdingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BoardLangdingFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    public static final String TAG =BoardLangdingFragment.class.getSimpleName();


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public BoardLangdingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BoardLangdingFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BoardLangdingFragment newInstance(String param1, String param2) {
        BoardLangdingFragment fragment = new BoardLangdingFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
    View rootView =inflater.inflate(R.layout.fragment_board_langding, container, false);

        Button btnCaller = (Button) rootView.findViewById(R.id.btnCaller);
        btnCaller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), PickupBoard.class);
                startActivity(i);
            }
        });




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


        try{
            Glide.with(getActivity()).load(R.drawable.advert).into((ImageView) rootView.findViewById(R.id.imgadvplace));
            Glide.with(getActivity()).load(R.drawable.denys).into((ImageView) rootView.findViewById(R.id.imgadvplace2));
            Glide.with(getActivity()).load(R.drawable.auto).into((ImageView) rootView.findViewById(R.id.imgadvplace3));
        }catch (Exception e)
        {
            e.printStackTrace();
        }

        return rootView;
    }

}
