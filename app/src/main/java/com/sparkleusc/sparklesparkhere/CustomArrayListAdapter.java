package com.sparkleusc.sparklesparkhere;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Meghan on 11/27/16.
 */

public class CustomArrayListAdapter extends ArrayAdapter<CustomArrayList> {
    private Context context;
    private List<CustomArrayList> res;

    public CustomArrayListAdapter(Context context, int layoutResourceId, ArrayList<CustomArrayList> res){
        super(context, layoutResourceId, res);
        this.context = context;
        this.res = res;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       CustomArrayList custAry = res.get(position);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.temp, null);

        TextView review = (TextView) view.findViewById(R.id.review);
        RatingBar rating = (RatingBar) view.findViewById(R.id.rating);

        review.setText(custAry.comment);
        rating.setNumStars(custAry.rating);
        return view;
    }

}
