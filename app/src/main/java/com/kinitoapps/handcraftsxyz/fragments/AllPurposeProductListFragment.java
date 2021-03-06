package com.kinitoapps.handcraftsxyz.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.kinitoapps.handcraftsxyz.adapters.NewArrivalProductsAdapter;
import com.kinitoapps.handcraftsxyz.Product;
import com.kinitoapps.handcraftsxyz.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AllPurposeProductListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AllPurposeProductListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AllPurposeProductListFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    boolean isNewArrival,isCategory;
    // TODO: Rename and change types of parameters
    private String mParam1;
    List<Product> productList;
    String category;
    private String mParam2;
    RecyclerView recyclerView;

    private OnFragmentInteractionListener mListener;

    public AllPurposeProductListFragment() {
        // Required empty public constructor
    }
    private static final String URL_PRODUCTS ="http://handicraft-com.stackstaging.com/myapi/api_all_products.php";
    private static final String URL_CATEGORY = "http://handicraft-com.stackstaging.com/myapi/api_all_category_products.php?cat=";
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AllPurposeProductListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AllPurposeProductListFragment newInstance(String param1, String param2) {
        AllPurposeProductListFragment fragment = new AllPurposeProductListFragment();
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
        if (bundle == null) {
            isNewArrival = true;
            isCategory = false;
        }
        else{
            if(!bundle.getString("category").equals("none")){
                //CHOSE A CATEGORY FROM CATEGORY TAB
                category = bundle.getString("category");
                isNewArrival = false;
                isCategory = true;
            }

            else{
                isNewArrival = false;
                isCategory = false;
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_all_purpose_product_list, container, false);
        recyclerView = root.findViewById(R.id.recyclerViewProducts);
        productList = new ArrayList<>();
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(layoutManager);
        NewArrivalProductsAdapter adapter = new NewArrivalProductsAdapter(getActivity(), productList);
        recyclerView.setAdapter(adapter);
        if(isNewArrival)
            loadAllProducts();
        else if(isCategory)
            loadProductsWithCategory();
        return root;
    }

    private void loadProductsWithCategory() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_CATEGORY+category,
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
                                        product.getString("primaryImage"),
                                        product.getString("productName"),
                                        product.getDouble("price"),
                                        product.getString("sellerName"),
                                        product.getString("productID")

                                ));
                            }

                            NewArrivalProductsAdapter adapter = new NewArrivalProductsAdapter(getActivity(), productList);
                            recyclerView.setAdapter(adapter);
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

    private void loadAllProducts() {
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
                                        product.getString("primaryImage"),
                                        product.getString("productName"),
                                        product.getDouble("price"),
                                        product.getString("sellerName"),
                                        product.getString("productID")

                                ));
                            }

                            NewArrivalProductsAdapter adapter = new NewArrivalProductsAdapter(getActivity(), productList);
                            recyclerView.setAdapter(adapter);
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
