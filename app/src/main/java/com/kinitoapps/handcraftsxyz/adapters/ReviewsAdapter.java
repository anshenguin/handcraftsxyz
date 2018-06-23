package com.kinitoapps.handcraftsxyz.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kinitoapps.handcraftsxyz.R;
import com.kinitoapps.handcraftsxyz.Review;

import java.util.List;

/**
 * Created by HP INDIA on 02-Jun-18.
 */

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ViewHolder> {
    private Context mCtx;
    private List<Review> reviewList;
    public ReviewsAdapter(Context mCtx, List<Review> reviewList)
    {
        this.reviewList = reviewList;
        this.mCtx = mCtx;
    }

    @Override
    public ReviewsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.layout_review_item, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Review review = reviewList.get(position);
        holder.textViewName.setText(review.getRevName());
        StringBuilder stars= new StringBuilder();
        for(int i = 0 ; i < review.getStars() ; i++){
            stars.append("★");
        }
        holder.textViewStars.setText(stars);
        if(review.getRevText().equals("null"))
            holder.textViewText.setVisibility(View.GONE);
        else
            holder.textViewText.setText(review.getRevText());

    }


    @Override
    public int getItemCount() {
        return reviewList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView textViewName, textViewStars, textViewText;

        public ViewHolder(final View itemView) {
            super(itemView);

            textViewName = itemView.findViewById(R.id.reviewer_name);
            textViewStars = itemView.findViewById(R.id.review_stars);
            textViewText = itemView.findViewById(R.id.review_text);
        }
    }
}
