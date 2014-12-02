package com.shoresdevelopment.gameoflife.game;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class GameGridAdapter extends BaseAdapter {
    private Context context;
    private int[] data = null;

    public GameGridAdapter(Context context, int[] data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.length;
    }

    @Override
    public Object getItem(int position) {
        return data[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, final View view, final ViewGroup parent) {
        final View cell;
        if (view == null) {
            cell = new View(context);
        } else {
            cell = view;
        }
        cell.setClickable(true);
        cell.setBackgroundColor(Color.GRAY);
        cell.setLayoutParams(new AbsListView.LayoutParams(35, 50));
        cell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (data[position] == 0) {
                    cell.setBackgroundColor(Color.RED);
                    data[position] = 1;
                } else {
                    cell.setBackgroundColor(Color.GRAY);
                    data[position] = 0;
                }
            }
        });
        return cell;
    }

    /** return the value stored in a data position */
    public int getPositionValue(int position) {
        return data[position];
    }

    /** set a new value to a data position */
    public void setPositionValue(int position, int value) {
        data[position] = value;
    }
}
