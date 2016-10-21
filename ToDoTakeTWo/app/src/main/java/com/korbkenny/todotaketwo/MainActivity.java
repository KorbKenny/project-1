package com.korbkenny.todotaketwo;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.io.Serializable;
import java.util.List;

public class MainActivity extends AppCompatActivity{
    private ListView mListView;
    private Button mAddNewList;
    final Context context = this;
    //public BaseAdapter mBaseAdapter;
    public ArrayAdapter<ToDoList> mArrayAdapter;
    public BigOldListOfLists bigOldListOfLists;
    //public static final String SENDINGCODE = "Cool";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //REFERENCES
        bigOldListOfLists = BigOldListOfLists.getInstance();
        mListView = (ListView)findViewById(R.id.LOLView);
        mAddNewList = (Button)findViewById(R.id.addNewList);
//
//        bigOldListOfLists.getListOfLists().add(new ToDoList("Hello there"));
//        bigOldListOfLists.getListOfLists().add(new ToDoList("HIIIIii"));


//        mBaseAdapter = new BaseAdapter() {
//            @Override
//            public int getCount() {
//                return (bigOldListOfLists.getListOfLists().size() == 0) ? 0 : bigOldListOfLists.getListOfLists().size();
//            }
//
//            @Override
//            public Object getItem(int position) {
//                return bigOldListOfLists.getListOfLists().get(position);
//            }
//
//            @Override
//            public long getItemId(int position) {
//                return 0;
//            }
//
//            @Override
//            public View getView(int position, View convertView, ViewGroup parent) {
//                if(convertView==null){
//                    convertView = getLayoutInflater().inflate(R.layout.list_lists_item_main, parent, false);
//                }
//                ((TextView) convertView).setText(getItem(position));
//
//                return convertView;
//
//            }
//        };



        //WHEN ADD-NEW-LIST IS CLICKED
        mAddNewList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater theInflater = LayoutInflater.from(context);
                View thePrompt = theInflater.inflate(R.layout.dialog_add_new_list, null);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

                alertDialogBuilder.setView(thePrompt);

                final EditText userInput = (EditText) thePrompt
                        .findViewById(R.id.EnterTextForNewList);

                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("Add",
                                new DialogInterface.OnClickListener(){
                                    public void onClick(DialogInterface dialog,int id){
                                        bigOldListOfLists.getListOfLists().add(new ToDoList(userInput.getText().toString()));
                                        mArrayAdapter.notifyDataSetChanged();
                                    }
                                })
                        .setNegativeButton("Nvm",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });

        if (bigOldListOfLists != null) {

            mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    ToDoList item = mArrayAdapter.getItem(position);
                    Intent i = new Intent(MainActivity.this, ListActivity.class);
                    //WHAT I'M SENDING OVER
                    i.putExtra("Title", item.getTitle().toString());
                    i.putExtra("Index", bigOldListOfLists.getListOfLists().indexOf(item));
                    startActivity(i);
                }
            });

            //if (!(bigOldListOfLists.getListOfLists().equals(null))) {
               // mListView.setAdapter(new BaseAdapterMain(context, bigOldListOfLists.getListOfLists()));
           // }
        }
        mArrayAdapter = new ArrayAdapter<ToDoList>(context,android.R.layout.simple_list_item_1,bigOldListOfLists.getListOfLists());
        mListView.setAdapter(mArrayAdapter);

    }
}