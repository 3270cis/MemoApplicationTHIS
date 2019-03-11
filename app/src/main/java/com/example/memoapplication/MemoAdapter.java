package com.example.memoapplication;

import android.content.Context;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

public class MemoAdapter extends ArrayAdapter<Memo> {

    private ArrayList<Memo> items;
    private Context adapterContext;

    public MemoAdapter(Context context, ArrayList<Memo> items) {
        super(context, R.layout.list_item, items);
        adapterContext = context;
        this.items = items;
    }

    public void showDelete(final int position, final View convertView, final Context context, final Memo memo) {
        View v = convertView;
        final Button b = (Button) v.findViewById(R.id.buttonDeleteMemo);
        //2
        if (b.getVisibility()==View.INVISIBLE) {
            b.setVisibility(View.VISIBLE);
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    hideDelete(position, convertView, context);
                    items.remove(memo);
                    deleteOption(memo.getMemoID(), context);
                }
            });
        }
        else {
            hideDelete(position, convertView, context);
        }
    }

    private void deleteOption(int memoToDelete, Context context) {
        ContactDataSource db = new ContactDataSource(context);
        try {
            db.open();
            db.deleteMemo(memoToDelete);
            db.close();
        }
        catch (Exception e) {
            Toast.makeText(adapterContext, "Delete Memo Failed", Toast.LENGTH_LONG).show();
        }
        this.notifyDataSetChanged();
    }
    //4
    public void hideDelete(int position, View convertView, Context context) {
        View v = convertView;
        final Button b = (Button) v.findViewById(R.id.buttonDeleteMemo);
        b.setVisibility(View.INVISIBLE);
        b.setOnClickListener(null);
    }


}
