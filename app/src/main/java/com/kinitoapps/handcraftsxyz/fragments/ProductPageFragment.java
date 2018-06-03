package com.kinitoapps.handcraftsxyz.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.kinitoapps.handcraftsxyz.Product;
import com.kinitoapps.handcraftsxyz.R;
import com.kinitoapps.handcraftsxyz.Review;
import com.kinitoapps.handcraftsxyz.adapters.NewArrivalProductsAdapter;
import com.kinitoapps.handcraftsxyz.adapters.ReviewsAdapter;
import com.kinitoapps.handcraftsxyz.adapters.ViewPagerAdapterProductImages;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


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
    String productID, sellerName;
    TextView productName, productAbout, productBy, productPrice;
    private static final String URL_PRODUCTS = "http://handicraft-com.stackstaging.com/myapi/api_all_products.php?prod=";
    private static final String URL_REVIEWS = "http://handicraft-com.stackstaging.com/myapi/api_reviews.php?prod=";
    RecyclerView recyclerView;
    ReviewsAdapter reviewsAdapter;
    List<Review> reviewList;

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

            Bundle bundle = this.getArguments();
            if (bundle != null) {
                productID = bundle.getString("productID");
            }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_product_page, container, false);
        viewPager = view.findViewById(R.id.viewpager);
        reviewList = new ArrayList<>();
        final ImageView arrowDown = view.findViewById(R.id.about_product_head_i);
        TextView about_header_t = view.findViewById(R.id.about_product_head_t);
        productName = view.findViewById(R.id.productName);
        productPrice = view.findViewById(R.id.productPrice);
        productBy = view.findViewById(R.id.sellerName);
        recyclerView = view.findViewById(R.id.recycler_all_reviews);
        recyclerView.setOnFlingListener(new RecyclerView.OnFlingListener() {
            @Override
            public boolean onFling(int velocityX, int velocityY) {
                recyclerView.dispatchNestedFling(velocityX, velocityY, false);
                return false;
            }
        });
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        reviewsAdapter = new ReviewsAdapter(getActivity(),reviewList);
        recyclerView.setAdapter(reviewsAdapter);

        productAbout = view.findViewById(R.id.productAbout);
        productBy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Class fragmentClass = null;
                android.support.v4.app.Fragment fragment = null;

                fragmentClass = StorePageFragment.class;
                try {
                    fragment = (android.support.v4.app.Fragment) fragmentClass.newInstance();
                    Bundle b = new Bundle();
                    b.putString("sellerName",sellerName);
                    fragment.setArguments(b);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                FragmentManager fragmentManager = getFragmentManager();

                fragmentManager.beginTransaction().replace(R.id.main_content, fragment,"storePage").addToBackStack("storePage").commit();
            }
        });
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
        populateProductInfo();
        loadReviews();
        return view;

    }
    private void populateProductInfo() {

        /*
        * Creating a String Request
        * The request type is GET defined by first parameter
        * The URL is defined in the second parameter
        * Then we have a Response Listener and a Error Listener
        * In response listener we will get the JSON response as a String
        * */
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_PRODUCTS+productID,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //converting the string to json array object
                            JSONArray array = new JSONArray(response);
                            //traversing through all the object
                                //getting product object from json array
                                JSONObject product = array.getJSONObject(0);
                                //adding the product to product list
                                    productName.setText(product.getString("productName"));
                                    productAbout.setText(product.getString("about"));
                                    sellerName = product.getString("sellerName");
                                    productBy.setText(sellerName);
                                    productPrice.setText("₹ "+product.getString("price"));

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

        //adding our string request to queue
        Volley.newRequestQueue(getActivity()).add(stringRequest);
    }
    private void loadReviews() {

        /*
        * Creating a String Request
        * The request type is GET defined by first parameter
        * The URL is defined in the second parameter
        * Then we have a Response Listener and a Error Listener
        * In response listener we will get the JSON response as a String
        * */
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_REVIEWS+productID,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //converting the string to json array object
                            JSONArray array = new JSONArray(response);

                            //traversing through all the object
                            int z;
                            if(array.length()<=5)
                                z=array.length();
                            else
                                z=5;
                            for (int i = 0; i < z; i++) {

                                //getting product object from json array
                                JSONObject review = array.getJSONObject(i);
                                if(review.getString("text").equals("null"))
                                    reviewList.add(new Review(
                                            review.getString("name"),
                                            "null",
                                            review.getInt("stars")));
                                else
                                reviewList.add(new Review(
                                        review.getString("name"),
                                        review.getString("text"),
                                        review.getInt("stars")));
                            }

                            reviewsAdapter = new ReviewsAdapter(getActivity(), reviewList);
                            recyclerView.setAdapter(reviewsAdapter);
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
