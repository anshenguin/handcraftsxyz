package com.kinitoapps.handcraftsxyz;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ProductPageFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ProductPageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProductPageFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    ViewPager viewPager;
    ViewPagerAdapterProductImages viewPagerAdapter;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public ProductPageFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProductPageFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProductPageFragment newInstance(String param1, String param2) {
        ProductPageFragment fragment = new ProductPageFragment();
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
        final View view = inflater.inflate(R.layout.fragment_product_page, container, false);
        viewPager = view.findViewById(R.id.viewpager);
        final ImageView arrowDown = view.findViewById(R.id.about_product_head_i);
        TextView about_header_t = view.findViewById(R.id.about_product_head_t);
        final TextView productAbout = view.findViewById(R.id.productAbout);
        about_header_t.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!(arrowDown.getRotation() >= 90)) {
                    arrowDown.setRotation(arrowDown.getRotation() + 90);
                    productAbout.setVisibility(View.VISIBLE);
                }
                else{
                    arrowDown.setRotation(arrowDown.getRotation() - 90);
                    productAbout.setVisibility(View.GONE);
                }
            }
        });
        arrowDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!(view.getRotation()>=90)) {
                    view.setRotation(view.getRotation() + 90);
                    productAbout.setVisibility(View.VISIBLE);

                }
                else
                {
                    view.setRotation(view.getRotation() - 90);
                    productAbout.setVisibility(View.GONE);
                }
            }
        });
        viewPagerAdapter = new ViewPagerAdapterProductImages(getActivity());
        viewPager.setAdapter(viewPagerAdapter);
        return view;

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
