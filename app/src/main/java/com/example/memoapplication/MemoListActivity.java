package com.example.memoapplication;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MemoListActivity extends ListActivity {

    ArrayList<Memo> memos;
    boolean isDeleting = false;
    MemoDataSource dataSource;

    int currentMemoId;


    private static final String STATE_LIST = "State Adapter Data";

    ListView listView;

    MemoAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo_list);

        initDeleteButton();
        initSortByPriorityButton();
        initSortByDateButton();


        String sortBy = getSharedPreferences("MyMemoListPreferences", Context.MODE_PRIVATE).getString("sortfield", "memoDate DESC");
        // String sortOrder = getSharedPreferences("MyMemoListPreferences", Context.MODE_PRIVATE).getString("sortorder", "ASC");

         dataSource = new MemoDataSource(this);

        try {

            dataSource.open();
            memos = dataSource.getMemos(sortBy);  //, sortOrder);
            dataSource.close();

            adapter = new MemoAdapter(this, memos);
            setListAdapter(adapter);

        } catch (Exception e) {
            Toast.makeText(this, "Error retrieving memos", Toast.LENGTH_LONG).show();
        }

        if (memos.size() > 0) {

            listView = getListView();


            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View itemClicked, int position, long id) {
                    Memo selectedMemo = memos.get(position);

                    //debugging purposes
                    //String currentMemoIdToString = Integer.toString(currentMemoId);

                    if (isDeleting) {
                        adapter.showDelete(position, itemClicked, MemoListActivity.this, selectedMemo);
                    } else {


                        try {
                            //debugging purposes
                            //final String currentMemoIdSTRING =  dataSource.getCurrentMemoIdInDB(selectedMemo.getMemoMessage(), selectedMemo.getPriority(), selectedMemo.getDateOfMemo());


                            dataSource.open();
                            currentMemoId =  dataSource.getCurrentMemoIdInDB(selectedMemo.getMemoMessage(), selectedMemo.getPriority(), selectedMemo.getDateOfMemo());

                            //debugging purposes
                            //Toast.makeText(MemoListActivity.this,Integer.toString(currentMemoId) , Toast.LENGTH_LONG).show();

                            dataSource.close();

                        }catch (Exception ex) {
                            Toast.makeText(MemoListActivity.this,"DB Problem in List", Toast.LENGTH_LONG).show();
                        }

                        Intent intent = new Intent(MemoListActivity.this, MemoEditActivity.class);
                        intent.putExtra("memo", selectedMemo.getMemoMessage());
                        intent.putExtra("priority", selectedMemo.getPriority());
                        intent.putExtra("memoId", currentMemoId);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);

                       // MemoEditActivity memoEditActivity = new MemoEditActivity(); //Maybe send the memo message and radio through here if can't figure it out

                    }
                }
            });
        }


//        MemoDataSource dataSource = new MemoDataSource(this);
//        dataSource.getSpecificMemo()


    }




    //THE ON_RESUME METHOD WAS RESETTING THE LIST EVERYTIME YOU TABBED OUT OF THE APP, SO I PUT THE CODE IN ON_CREATE -KELLY
//    @Override
//    public void onResume() {
//        super.onResume();                                                                                   //the list will displays newest memos on top
//        String sortBy = getSharedPreferences("MyMemoListPreferences", Context.MODE_PRIVATE).getString("sortfield", "memoDate DESC");
//        // String sortOrder = getSharedPreferences("MyMemoListPreferences", Context.MODE_PRIVATE).getString("sortorder", "ASC");
//
//        MemoDataSource dataSource = new MemoDataSource(this);
//        try {
//
//            dataSource.open();
//            memos = dataSource.getMemos(sortBy);  //, sortOrder);
//            dataSource.close();
//
//            adapter = new MemoAdapter(this, memos);
//            setListAdapter(adapter);
//
//        } catch (Exception e) {
//            Toast.makeText(this, "Error retrieving memos", Toast.LENGTH_LONG).show();
//        }
//
//        if (memos.size() > 0) {
//
//            ListView listView = getListView();
//
//
//            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//
//                @Override
//                public void onItemClick(AdapterView<?> parent, View itemClicked, int position, long id) {
//                    Memo selectedMemo = memos.get(position);
//
//                    if (isDeleting) {
//                        adapter.showDelete(position, itemClicked, MemoListActivity.this, selectedMemo);
//                    } else {
//                        Intent intent = new Intent(MemoListActivity.this, MemoEditActivity.class);
//                        intent.putExtra("memoid", selectedMemo.getMemoID());
//                        startActivity(intent);
//                    }
//                }
//            });
//        }
//
////        else {
////            Intent intent = new Intent(MemoListActivity.this, MemoActivity.class);
////            startActivity(intent);
////        }
//
//    }






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

    public void initSortByPriorityButton() {
        Button priorityButton = (Button) findViewById(R.id.priorityButton);
        priorityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                 String sortBy = getSharedPreferences("MyMemoListPreferences", Context.MODE_PRIVATE).getString("sortfield", "priority");


                MemoDataSource ds = new MemoDataSource(MemoListActivity.this);
                try {
                    ds.open();
                    memos = ds.getMemosByPriority(sortBy);
                    ds.close();



                    adapter = new MemoAdapter(MemoListActivity.this,  memos);
                    setListAdapter(adapter);



                } catch (Exception e) {
                    Toast.makeText(MemoListActivity.this, "Error sorting by priority", Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    public void initSortByDateButton(){

        final Button sortDateButton = (Button) findViewById(R.id.dateButton);
        sortDateButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                String sortBy = getSharedPreferences("MyMemoListPreferences", Context.MODE_PRIVATE).getString("sortfield", "memoDate DESC");
                // String sortOrder = getSharedPreferences("MyMemoListPreferences", Context.MODE_PRIVATE).getString("sortorder", "ASC");

                MemoDataSource ds = new MemoDataSource(MemoListActivity.this);
                try {

                    ds.open();
                    memos = ds.getMemos(sortBy); //, sortOrder);
                    ds.close();

                    adapter = new MemoAdapter(MemoListActivity.this, memos);
                    setListAdapter(adapter);
                } catch (Exception e) {
                    Toast.makeText(MemoListActivity.this, "Error retrieving memos", Toast.LENGTH_LONG).show();
                }


//                if (memos.size() > 0) {
//
//                    ListView listView = getListView();
//                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//
//                        @Override
//                        public void onItemClick(AdapterView<?> parent, View itemClicked, int position, long id) {
//                            Memo selectedMemo = memos.get(position);
//
//                            if (isDeleting) {
//                                adapter.showDelete(position, itemClicked, MemoListActivity.this, selectedMemo);
//                            } else {
//                                Intent intent = new Intent(MemoListActivity.this, MemoEditActivity.class);
//                                intent.putExtra("memoid", selectedMemo.getMemoID());
//                                startActivity(intent);
//                            }
//                        }
//                    });
//                }
            }
        });
    }





}
