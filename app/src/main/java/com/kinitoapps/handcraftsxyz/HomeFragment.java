package com.kinitoapps.handcraftsxyz;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator;

import static com.bumptech.glide.Glide.get;
import static com.bumptech.glide.Glide.with;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HomeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    RecyclerView.LayoutManager recyclerViewLayoutManager;
    List<Product> productList;
    RecyclerView recyclerView;
    RecyclerView.Adapter recyclerView_Adapter;


    private OnFragmentInteractionListener mListener;

    public HomeFragment() {
        // Required empty public constructor
    }
    ImageView tile1,tile2,tile3,tile4,tile5,tile6,tile7,tile8;
    private static final String URL_PRODUCTS ="http://handicraft-com.stackstaging.com/myapi/api.php";


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
        View view = inflater.inflate(R.layout.fragment_home_2, container, false);
        ViewPager viewPager = view.findViewById(R.id.viewpager);
        recyclerView = view.findViewById(R.id.recyclerView);
        productList = new ArrayList<>();
        recyclerViewLayoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setOnFlingListener(new RecyclerView.OnFlingListener() {
            @Override
            public boolean onFling(int velocityX, int velocityY) {
                recyclerView.dispatchNestedFling(velocityX, velocityY, false);
                return false;
            }
        });

        recyclerView.setLayoutManager(recyclerViewLayoutManager);

        recyclerView_Adapter = new NewArrivalProductsAdapter(getActivity(),productList);

        recyclerView.setAdapter(recyclerView_Adapter);
        ViewPagerAdapterRootSlider viewPagerAdapter = new ViewPagerAdapterRootSlider(getActivity());
//        tile1 = view.findViewById(R.id.tile_image1);
//        tile2 = view.findViewById(R.id.tile_image2);
//        tile3 = view.findViewById(R.id.tile_image3);
//        tile4 = view.findViewById(R.id.tile_image4);
//        tile5 = view.findViewById(R.id.tile_image5);
//        tile6 = view.findViewById(R.id.tile_image6);
//        tile7 = view.findViewById(R.id.tile_image7);
//        tile8 = view.findViewById(R.id.tile_image8);
        CircleIndicator indicator = view.findViewById(R.id.indicator);
        viewPager.setAdapter(viewPagerAdapter);
        indicator.setViewPager(viewPager);
//        tile1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                view.setPadding(24,24,24,24);
//            }
//        });
//        tile2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                view.setPadding(24,24,24,24);
//
//            }
//        });
//        tile3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                view.setPadding(24,24,24,24);
//
//            }
//        });
//        tile4.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                view.setPadding(24,24,24,24);
//
//            }
//        });
//        tile5.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                view.setPadding(24,24,24,24);
//
//            }
//        });
//        tile6.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                view.setPadding(24,24,24,24);
//
//            }
//        });
//        tile7.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                view.setPadding(24,24,24,24);
//
//            }
//        });
//        tile8.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                view.setPadding(24,24,24,24);
//
//            }
//        });
        loadProducts();

        return view;
    }
    private void loadProducts() {

        /*
        * Creating a String Request
        * The request type is GET defined by first parameter
        * The URL is defined in the second parameter
        * Then we have a Response Listener and a Error Listener
        * In response listener we will get the JSON response as a String
        * */
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_PRODUCTS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //converting the string to json array object
                            JSONArray array = new JSONArray(response);

                            //traversing through all the object
                            for (int i = 0; i < array.length(); i++) {

                                //getting product object from json array
                                JSONObject product = array.getJSONObject(i);

                                //adding the product to product list
                                productList.add(new Product(
                                        product.getInt("id"),
                                        product.getString("image"),
                                        product.getString("productName"),
                                        product.getDouble("price")

                                ));
                            }

                            //creating adapter object and setting it to recyclerview
                            NewArrivalProductsAdapter adapter = new NewArrivalProductsAdapter(getActivity(), productList);
                            recyclerView.setAdapter(adapter);
//                            mAdapter.notifyDataSetChanged();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        //adding our stringrequest to queue
        Volley.newRequestQueue(getActivity()).add(stringRequest);
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
