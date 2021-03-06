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


    //private static final String STATE_LIST = "State Adapter Data";

    ListView listView;
    MemoAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo_list);

        initDeleteButton();
        initSortByPriorityHighButton();
        initSortByPriorityMediumButton();
        initSortByPriorityLowButton();
        initSortByDateButton();


        String sortBy = getSharedPreferences("MyMemoListPreferences", Context.MODE_PRIVATE).getString("sortfield", "memoDate DESC");
        // String sortOrder = getSharedPreferences("MyMemoListPreferences", Context.MODE_PRIVATE).getString("sortorder", "ASC");

         dataSource = new MemoDataSource(this);

        try {

            dataSource.open();

            //the memos are displayed by date, by default
            memos = dataSource.getMemos(sortBy);  //, sortOrder);
            dataSource.close();

            //setting the memos onto list gui
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

                    //debugging purposes, ignore
                    //String currentMemoIdToString = Integer.toString(currentMemoId);

                    if (isDeleting) {

                        adapter.showDelete(position, itemClicked, MemoListActivity.this, selectedMemo);
                    } else {


                        try {
                            //debugging purposes, ignore
                            //final String currentMemoIdSTRING =  dataSource.getCurrentMemoIdInDB(selectedMemo.getMemoMessage(), selectedMemo.getPriority(), selectedMemo.getDateOfMemo());


                            dataSource.open();

                            //it gets the memo id from the database
                            currentMemoId =  dataSource.getCurrentMemoIdInDB(selectedMemo.getMemoMessage(), selectedMemo.getPriority(), selectedMemo.getDateOfMemo());

                            //debugging purposes, ignore
                            //Toast.makeText(MemoListActivity.this,Integer.toString(currentMemoId) , Toast.LENGTH_LONG).show();

                            dataSource.close();

                        }catch (Exception ex) {
                            Toast.makeText(MemoListActivity.this,"DB Problem in List", Toast.LENGTH_LONG).show();
                        }

                        //create the intent and send memo, priority, and the memo id through the next activity
                        Intent intent = new Intent(MemoListActivity.this, MemoEditActivity.class);
                        intent.putExtra("memo", selectedMemo.getMemoMessage());
                        intent.putExtra("priority", selectedMemo.getPriority());
                        intent.putExtra("memoId", currentMemoId);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);


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

    public void initSortByPriorityHighButton() {
        Button priorityButton = (Button) findViewById(R.id.priorityButtonHigh);
        priorityButton.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                        //get the stored preferences
                        String sortBy = getSharedPreferences("MyMemoListPreferences", Context.MODE_PRIVATE).getString("sortfield", "priority");


                        MemoDataSource ds = new MemoDataSource(MemoListActivity.this);
                        try {
                            ds.open();

                            //getting the memos ordered by High, Medium, Low
                            memos = ds.getMemosByHighPriority(sortBy);
                            ds.close();


                            //connects the adapter to show the list onto the gui
                            adapter = new MemoAdapter(MemoListActivity.this, memos);
                            setListAdapter(adapter);


                        } catch (Exception e) {
                            Toast.makeText(MemoListActivity.this, "Error sorting by priority", Toast.LENGTH_LONG).show();
                        }

                    }

        });


    }

    public void initSortByPriorityMediumButton() {
        Button priorityButton = (Button) findViewById(R.id.priorityButtonMedium);
        priorityButton.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                //get the stored preferences
                String sortBy = getSharedPreferences("MyMemoListPreferences", Context.MODE_PRIVATE).getString("sortfield", "priority");


                MemoDataSource ds = new MemoDataSource(MemoListActivity.this);
                try {
                    ds.open();

                    //getting the memos ordered by High, Medium, Low
                    memos = ds.getMemosByMediumPriority(sortBy);
                    ds.close();


                    //connects the adapter to show the list onto the gui
                    adapter = new MemoAdapter(MemoListActivity.this, memos);
                    setListAdapter(adapter);


                } catch (Exception e) {
                    Toast.makeText(MemoListActivity.this, "Error sorting by priority", Toast.LENGTH_LONG).show();
                }

            }

        });


    }

    public void initSortByPriorityLowButton() {
        Button priorityButton = (Button) findViewById(R.id.priorityButtonLow);
        priorityButton.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                //get the stored preferences
                String sortBy = getSharedPreferences("MyMemoListPreferences", Context.MODE_PRIVATE).getString("sortfield", "priority");


                MemoDataSource ds = new MemoDataSource(MemoListActivity.this);
                try {
                    ds.open();

                    //getting the memos ordered by High, Medium, Low
                    memos = ds.getMemosByLowPriority(sortBy);
                    ds.close();


                    //connects the adapter to show the list onto the gui
                    adapter = new MemoAdapter(MemoListActivity.this, memos);
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

                //not needed
                // String sortOrder = getSharedPreferences("MyMemoListPreferences", Context.MODE_PRIVATE).getString("sortorder", "ASC");

                MemoDataSource ds = new MemoDataSource(MemoListActivity.this);
                try {

                    //when the users presses sort by date, it gets the memo data from the database
                    ds.open();
                    memos = ds.getMemos(sortBy); //, sortOrder);
                    ds.close();

                    //set the list to display memos ordered by date
                    adapter = new MemoAdapter(MemoListActivity.this, memos);
                    setListAdapter(adapter);
                } catch (Exception e) {
                    Toast.makeText(MemoListActivity.this, "Error retrieving memos", Toast.LENGTH_LONG).show();
                }

//                  NOT NEEDED, was resetting the view everytime
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
