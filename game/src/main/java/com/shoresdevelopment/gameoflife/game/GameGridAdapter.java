package com.shoresdevelopment.gameoflife.game;

import android.content.Context;
import android.graphics.Color;
import android.view.*;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class GameGridAdapter extends BaseAdapter {
    private Context context;
    private int[] data = null;
    private int cellDimension;
    private int imageResource;

    /** Constructor */
    public GameGridAdapter(Context context, int[] data, int cellDimension, int imageResource) {
        this.context = context;
        this.data = data;
        this.imageResource = imageResource;
        this.cellDimension = cellDimension;
    }

    /** Gets the number of children */
    @Override
    public int getCount() {
        return data.length;
    }

    /** Get the item at a position index */
    @Override
    public Object getItem(int position) {
        return data[position];
    }

    /** Gets the ID of item at a position */
    @Override
    public long getItemId(int position) {
        return 0;
    }

    /** Sets and gets the view of an item associated with a position in data array */
    @Override
    public View getView(final int position, final View view, final ViewGroup parent) {
        final ImageView cell;
        if (view == null) {
            cell = new ImageView(context);
        } else {
            cell = (ImageView) view;
        }

        cell.setClickable(true);
        cell.setLayoutParams(new AbsListView.LayoutParams(cellDimension, cellDimension));

        if (data[position] == 1) {
            cell.setImageResource(imageResource);
        } else {
            cell.setImageResource(Color.TRANSPARENT);
        }

        cell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (data[position] == 0) {
                    cell.setImageResource(imageResource);
                    data[position] = 1;
                } else {
                    cell.setImageResource(Color.TRANSPARENT);
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
