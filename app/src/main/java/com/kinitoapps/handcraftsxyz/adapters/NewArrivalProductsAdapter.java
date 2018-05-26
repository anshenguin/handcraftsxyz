package com.kinitoapps.handcraftsxyz.adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kinitoapps.handcraftsxyz.Product;
import com.kinitoapps.handcraftsxyz.R;
import com.kinitoapps.handcraftsxyz.activities.RootActivity;
import com.kinitoapps.handcraftsxyz.fragments.ProductPageFragment;

import java.util.List;

/**
 * Created by HP INDIA on 05-Apr-18.
 */
public class NewArrivalProductsAdapter extends RecyclerView.Adapter<NewArrivalProductsAdapter.ViewHolder> {

    private Context mCtx;
    private List<Product> productList;
    RootActivity activity;

    public NewArrivalProductsAdapter(Context mCtx, List<Product> productList) {
        this.mCtx = mCtx;
        this.productList = productList;
        activity = (RootActivity) mCtx;
    }





    @Override
    public NewArrivalProductsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.product_layout, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NewArrivalProductsAdapter.ViewHolder holder, int position) {
        final Product product = productList.get(position);

        //loading the image
        Glide.with(mCtx)
                .load(product.getImage())
                .into(holder.imageView);

        holder.textViewTitle.setText(product.getProductName());
        holder.textViewBy.setText(product.getSellerName());
        holder.textViewPrice.setText(String.valueOf(product.getPrice()));

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.setPadding(24,24,24,24);
                android.support.v4.app.Fragment fragment = null;
                Class fragmentClass = null;
                fragmentClass = ProductPageFragment.class;
                try {
                    fragment = (android.support.v4.app.Fragment) fragmentClass.newInstance();
                    Bundle b = new Bundle();
                    b.putString("productID",product.getProductID());
                    fragment.setArguments(b);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //ERROR
                FragmentManager fragmentManager = activity.getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.main_content, fragment, "productPage").addToBackStack("productPage").commit();
            }
        });

//        holder.textViewPrice.setText(String.valueOf(product.getPrice()));
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView textViewTitle, textViewBy, textViewPrice;
        ImageView imageView;

        public ViewHolder(final View itemView) {
            super(itemView);

            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewPrice = itemView.findViewById(R.id.textViewPrice);
            textViewBy = itemView.findViewById(R.id.textViewBy);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }
}