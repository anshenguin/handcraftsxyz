package com.kinitoapps.handcraftsxyz.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.kinitoapps.handcraftsxyz.AppConfig;
import com.kinitoapps.handcraftsxyz.AppController;
import com.kinitoapps.handcraftsxyz.Product;
import com.kinitoapps.handcraftsxyz.R;
import com.kinitoapps.handcraftsxyz.adapters.NewArrivalProductsAdapter;
import com.kinitoapps.handcraftsxyz.adapters.SubscriptionsAdapter;
import com.kinitoapps.handcraftsxyz.helper.SQLiteHandler;
import com.kinitoapps.handcraftsxyz.helper.SessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SubscriptionsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SubscriptionsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SubscriptionsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    RecyclerView recyclerView;
    List<Product> productList;
    SubscriptionsAdapter recyclerView_Adapter;

    private OnFragmentInteractionListener mListener;

    public SubscriptionsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SubscriptionsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SubscriptionsFragment newInstance(String param1, String param2) {
        SubscriptionsFragment fragment = new SubscriptionsFragment();
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
        productList = new ArrayList<>();
        View view = inflater.inflate(R.layout.fragment_subscriptions, container, false);
        recyclerView = view.findViewById(R.id.recyclerViewSubs);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView_Adapter = new SubscriptionsAdapter(getActivity(),productList);
        recyclerView.setAdapter(recyclerView_Adapter);
        SQLiteHandler db = new SQLiteHandler(getContext());
        DividerItemDecoration itemDecor = new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecor);

        SessionManager session = new SessionManager(getContext());
        if(session.isLoggedIn())
        subscribedList(db.getUserDetails().get("email"));
        return view;
    }

    private void subscribedList(final String subscriber_uid){

        // another try
        // Creating string request with post method.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppConfig.URL_SUBSCRIPTION_LIST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String ServerResponse) {
                        try {
                            JSONObject obj = new JSONObject(ServerResponse);

                            if(!obj.getBoolean("error")) {
                                JSONArray array;
                                array = obj.getJSONArray("response");

                                for (int i = 0; i < array.length(); i++) {

                                    //getting product object from json array
                                    JSONObject product = array.getJSONObject(i);
                                    Log.d("tagged", String.valueOf(array.getJSONObject(i)));

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
                                recyclerView_Adapter.notifyDataSetChanged();
                                recyclerView.setAdapter(recyclerView_Adapter);

                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

//                        sub_btn.setText("UNSUBSCRIBE");
//                        hasSubscribed = true;
                        // Hiding the progress dialog after all task complete.
//                        Toast.makeText(getActivity(), "Subscribed Successfully!", Toast.LENGTH_SHORT).show();
                        // Showing response message coming from server.
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                        // Hiding the progress dialog after all task complete.

                        // Showing error message if something goes wrong.
//                        Toast.makeText(getContext(), volleyError.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams()  {

                // Creating Map String Params.
                Map<String, String> params = new HashMap<String, String>();

                // Adding All values to Params.
                params.put("subscriber_uid", subscriber_uid);
                Log.v("subs1",subscriber_uid);
                return params;
            }

        };
        AppController.getInstance().addToRequestQueue(stringRequest);
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
