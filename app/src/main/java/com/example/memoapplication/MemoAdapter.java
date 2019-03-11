package com.example.memoapplication;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

public class MemoAdapter extends ArrayAdapter<Memo> {

    private ArrayList<Memo> items;
    private Context adapterContext;

    public ContactAdapter(Context context, ArrayList<Memo> items) {
        super(context, R.layout, items);
        adapterContext = context;
        this.items = items;
    }


}
