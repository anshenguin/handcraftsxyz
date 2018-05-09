package com.kinitoapps.handcraftsxyz;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by HP INDIA on 09-May-18.
 */

public class CategoryListAdapter extends RecyclerView.Adapter<CategoryListAdapter.ViewHolder> {

    private Context mCtx;
    private List<String> categoryList;
//    RootActivity activity;

    public CategoryListAdapter(Context mCtx, List<String> categoryList) {
        this.mCtx = mCtx;
        this.categoryList = categoryList;
//        activity = (RootActivity) mCtx;
    }






    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.category_list_item, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String cat = categoryList.get(position);
        holder.textViewCategory.setText(cat);
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