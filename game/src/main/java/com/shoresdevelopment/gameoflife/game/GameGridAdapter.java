package com.shoresdevelopment.gameoflife.game;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.BaseAdapter;

public class GameGridAdapter extends BaseAdapter {
    private Context context;
    private int[] data = null;
    int cellDimension;

    public GameGridAdapter(Context context, int[] data) {
        this.context = context;
        this.data = data;
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        cellDimension = (size.x - 32) / 25;
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
        cell.setLayoutParams(new AbsListView.LayoutParams(cellDimension, cellDimension));

        if (data[position] == 1) {
            cell.setBackgroundColor(Color.RED);
        } else {
            cell.setBackgroundColor(Color.GRAY);
        }

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
