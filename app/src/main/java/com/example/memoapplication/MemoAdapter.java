package com.example.memoapplication;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
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


    //shows the memo, priorty, and date
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        try {
            Memo memo = items.get(position);

            if (v == null) {

                //instatiate layout xml file to be used
                LayoutInflater vi = (LayoutInflater) adapterContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = vi.inflate(R.layout.list_item, null);
            }



            TextView memoMessage = (TextView) v.findViewById(R.id.textMemo);
            TextView priority = (TextView) v.findViewById(R.id.textPriority);
            TextView memoDate = (TextView) v.findViewById(R.id.textDate);


            memoMessage.setText(memo.getMemoMessage());
            memoDate.setText(memo.getDateOfMemo());

            //sets the text color of the priority
            if(memo.getPriority().equals("Low")) {
                priority.setTextColor(Color.BLACK);
                priority.setText(memo.getPriority());
            }
            if(memo.getPriority().equals("Medium")) {
                priority.setTextColor(Color.BLUE);
                priority.setText(memo.getPriority());
            }
            if(memo.getPriority().equals("High")) {
                priority.setTextColor(Color.RED);
                priority.setText(memo.getPriority());
            }

            // NOT NECESSARY
            //if the position is an even number, then the contact name text will be red
//            if(position % 2 == 0 ) {
//                memoMessage.setTextColor(Color.RED);
//            }
//            //it its not an even number, then contact name will be blue
//            else {
//                memoMessage.setTextColor(Color.BLUE);
//            }


            Button b = (Button) v.findViewById(R.id.buttonDeleteMemo);
            b.setVisibility(View.INVISIBLE);
        }
        catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
        return v;
    }



    public void showDelete(final int position, final View convertView, final Context context, final Memo memo) {
        View v = convertView;
        final Button b = (Button) v.findViewById(R.id.buttonDeleteMemo);


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

    //deletes the memo
    private void deleteOption(int memoToDelete, Context context) {
        MemoDataSource db = new MemoDataSource(context);
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

    //hides the red Delete button
    public void hideDelete(int position, View convertView, Context context) {
        View v = convertView;
        final Button b = (Button) v.findViewById(R.id.buttonDeleteMemo);
        b.setVisibility(View.INVISIBLE);
        b.setOnClickListener(null);
    }

    public ArrayList<Memo> getList() {
        return new ArrayList<Memo>(items);
    }


}
