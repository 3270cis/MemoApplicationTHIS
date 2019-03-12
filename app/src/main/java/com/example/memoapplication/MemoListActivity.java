package com.example.memoapplication;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MemoListActivity extends ListActivity {

    ArrayList<Memo> memos;
    boolean isDeleting = false;

    MemoAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo_list);

        initDeleteButton();

//        MemoDataSource ds = new MemoDataSource(this);
//        ds.getSpecificMemo()


    }

    @Override
    public void onResume() {
        super.onResume();
        String sortBy = getSharedPreferences("MyMemoListPreferences", Context.MODE_PRIVATE).getString("sortfield", "memoDate");
        // String sortOrder = getSharedPreferences("MyMemoListPreferences", Context.MODE_PRIVATE).getString("sortorder", "ASC");

        MemoDataSource ds = new MemoDataSource(this);
        try {
            ds.open();
            memos = ds.getMemos(sortBy);  //, sortOrder);
            ds.close();

            adapter = new MemoAdapter(this,  memos);
            setListAdapter(adapter);

        } catch (Exception e) {
            Toast.makeText(this, "Error retrieving memos", Toast.LENGTH_LONG).show();
        }

        if (memos.size() > 0) {

            ListView listView = getListView();


            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View itemClicked, int position, long id) {
                    Memo selectedMemo = memos.get(position);

                    if (isDeleting) {
                        adapter.showDelete(position, itemClicked, MemoListActivity.this, selectedMemo);
                    } else {
                        Intent intent = new Intent(MemoListActivity.this, MemoEditActivity.class); //HERE!
                        intent.putExtra("memoid", selectedMemo.getMemoID());
                        startActivity(intent);
                    }
                }
            });
        }

//        else {
//            Intent intent = new Intent(MemoListActivity.this, MemoActivity.class);
//            startActivity(intent);
//        }


    }

    private void initDeleteButton() {
        final Button deleteButton = (Button) findViewById(R.id.buttonDelete);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (isDeleting) {
                    deleteButton.setText("Delete");
                    isDeleting = false;
                    adapter.notifyDataSetChanged();
                }
                else {
                    deleteButton.setText("Done Deleting");
                    isDeleting = true;
                }
            }
        });
    }

}
