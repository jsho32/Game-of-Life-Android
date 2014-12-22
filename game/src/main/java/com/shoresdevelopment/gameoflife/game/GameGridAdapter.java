package com.shoresdevelopment.gameoflife.game;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.view.*;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class GameGridAdapter extends BaseAdapter {
    private Context context;
    private int[] data = null;
    private int cellDimension;

    /** Constructor */
    public GameGridAdapter(Context context, int[] data, int columnCount) {
        this.context = context;
        this.data = data;
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        cellDimension = (size.x) / (columnCount);
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
        final View cell;
        final TextView life;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            cell = inflater.inflate(R.layout.life_cell, null);
        } else {
            cell = view;
        }
        life = (TextView) cell.findViewById(R.id.cell);
        cell.setClickable(true);
        cell.setBackgroundColor(Color.BLACK);
        cell.setLayoutParams(new AbsListView.LayoutParams(cellDimension, cellDimension));

        if (data[position] == 1) {
            life.setBackgroundColor(Color.RED);
        } else {
            life.setBackgroundColor(Color.GRAY);
        }

        cell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (data[position] == 0) {
                    life.setBackgroundColor(Color.RED);
                    data[position] = 1;
                } else {
                    life.setBackgroundColor(Color.GRAY);
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
