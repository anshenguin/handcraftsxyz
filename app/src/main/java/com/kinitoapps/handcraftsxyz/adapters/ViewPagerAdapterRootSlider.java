package com.kinitoapps.handcraftsxyz.adapters;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.kinitoapps.handcraftsxyz.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by HP INDIA on 04-Apr-18.
 */

public class ViewPagerAdapterRootSlider extends PagerAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    private List<String> sliderImageLinks;

    public ViewPagerAdapterRootSlider(Context context, List<String> sliderImageLinks){
        this.context = context;
        this.sliderImageLinks = sliderImageLinks;
    }

    @Override
    public int getCount() {
         return sliderImageLinks.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.custom_image_for_slider, null);
        ImageView imageView = view.findViewById(R.id.imageView);
        String imgLink = sliderImageLinks.get(position);
        Glide.with(context)
                .load(imgLink)
                .into(imageView);
        ViewPager vp = (ViewPager) container;
        vp.addView(view, 0);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ViewPager vp = (ViewPager) container;
        View view = (View) object;
        vp.removeView(view);
    }

}
