package com.nearme.ui.places;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nearme.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xnorcode on 08/04/2018.
 */

public class ListViewHolder extends RecyclerView.ViewHolder {


    @BindView(R.id.place_icon)
    ImageView mIcon;


    @BindView(R.id.place_info)
    TextView mInfo;


    @BindView(R.id.place_address)
    TextView mAddress;


    public ListViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }
}
