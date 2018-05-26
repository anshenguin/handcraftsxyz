package com.kinitoapps.handcraftsxyz.adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kinitoapps.handcraftsxyz.R;
import com.kinitoapps.handcraftsxyz.activities.RootActivity;
import com.kinitoapps.handcraftsxyz.fragments.AllPurposeProductListFragment;

import java.util.List;

/**
 * Created by HP INDIA on 09-May-18.
 */

public class CategoryListAdapter extends RecyclerView.Adapter<CategoryListAdapter.ViewHolder> {

    private Context mCtx;
    private List<String> categoryList;
    RootActivity activity;

    public CategoryListAdapter(Context mCtx, List<String> categoryList) {
        this.mCtx = mCtx;
        this.categoryList = categoryList;
        activity = (RootActivity) mCtx;
    }






    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.category_list_item, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final String cat = categoryList.get(position);
        holder.textViewCategory.setText(cat);
        holder.textViewCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                android.support.v4.app.Fragment fragment = null;
                Class fragmentClass = null;
                fragmentClass = AllPurposeProductListFragment.class;
                try {
                    fragment = (android.support.v4.app.Fragment) fragmentClass.newInstance();
                    Bundle b = new Bundle();
                    b.putString("category",cat);
                    fragment.setArguments(b);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //ERROR
                FragmentManager fragmentManager = activity.getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.main_content, fragment, "productPage").addToBackStack("productPage").commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView textViewCategory;

        public ViewHolder(final View itemView) {
            super(itemView);

            textViewCategory = itemView.findViewById(R.id.category_name);
//            textViewPrice = itemView.findViewById(R.id.textViewPrice);
        }
    }
}