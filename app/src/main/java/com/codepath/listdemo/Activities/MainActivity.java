package com.codepath.listdemo.Activities;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.codepath.listdemo.R;
import com.codepath.listdemo.database.ToDoItemData;
import com.codepath.listdemo.dialogs.DeleteItemDialog;

import com.codepath.listdemo.adapters.ToDoItemAdapter;
import com.codepath.listdemo.model.ToDoItem;

import java.lang.ref.WeakReference;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,DeleteItemDialog.ConfirmDeleteListener {

    private final int REQUEST_CODE_EDIT=100;
    ListView lvItems;
    Button btnAddItem;
    private int clickedPosition=-1;
    private ToDoItemAdapter todoListAdapter;
    private ToDoItemData todoItemData;
    private Message msg;
    private UiHandler msgHandler;
    private static int UPDATE_UI=100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        msgHandler = new UiHandler( this );

        lvItems = (ListView) findViewById(R.id.lvItemlist);
        populateListItems();
        todoItemData = ToDoItemData.getInstance();
        View headerView = View.inflate(this,R.layout.listview_header,null);

        lvItems.addHeaderView(headerView);
        lvItems.setHeaderDividersEnabled(true);

        btnAddItem = (Button) findViewById(R.id.btnAddItem);
        btnAddItem.setOnClickListener(this);

        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                if(position==0) {
                    Toast.makeText(MainActivity.this, "Oops the list header cannot be deleted", Toast.LENGTH_LONG).show();
                    return false;
                }
                showDeleteDialog();
                clickedPosition = position;
                return true;
            }
        });

        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, EditItemActivity.class);
                intent.putExtra("position", position);
                startActivityForResult(intent, REQUEST_CODE_EDIT);
            }
        });
    }

    public void populateListItems() {

        ToDoItemData todoItemDate = ToDoItemData.getInstance();
        Cursor cursor = todoItemDate.getItemList();
        if(cursor!=null && cursor.getCount()> 0) {

            todoListAdapter = new ToDoItemAdapter(this, cursor, 0);
            lvItems.setAdapter(todoListAdapter);

        }
        TextView tvEmptyList = (TextView) findViewById(R.id.tv_empty_list);
        lvItems.setEmptyView(tvEmptyList);

    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshList();
    }

    private void showDeleteDialog(){

        FragmentManager fragmentManager = getSupportFragmentManager();
        DeleteItemDialog alertDialog = DeleteItemDialog.singleInstance("Remove item", "Are you sure?");
        alertDialog.show(fragmentManager, "Fragment");

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if(requestCode == REQUEST_CODE_EDIT && resultCode == RESULT_OK){

            Bundle bundle = intent.getExtras();
            int position = bundle.getInt("position");
            Toast.makeText(this,"item at position "+(position+1)+" updated",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){

            case R.id.btnAddItem:
                Intent intent= new Intent(MainActivity.this,EditItemActivity.class);
                startActivity(intent);
                break;

        }
    }

    public void hideSoftKeyboard(View view){
        InputMethodManager imm =(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    public void onConfirmDelete(boolean shouldDelete) {

        if(shouldDelete){

            if(clickedPosition == -1)
                return;

            Cursor oldCursor = (Cursor)todoListAdapter.getItem(clickedPosition-1);
            ToDoItem todoItem = todoItemData.getItemRecord(oldCursor);
            int itemId = todoItem.getId();
            Log.e("main","item ID "+itemId);
            todoItemData.deleteItem(itemId);
            refreshList();
        }
    }

    private void refreshList(){

        msg = Message.obtain(msgHandler, 0);
        msg.what = UPDATE_UI;
        msg.sendToTarget( );

    }

    private void doRefreshList(){

        new Handler().post(new Runnable() {
            @Override
            public void run() {
                Cursor updatedCursor = todoItemData.getItemList();
                if(updatedCursor!=null && updatedCursor.getCount()>0 && todoListAdapter!=null) {
                    todoListAdapter.swapCursor(updatedCursor);
                    todoListAdapter.notifyDataSetChanged();

                    if(todoListAdapter.isEmpty()){
                        todoListAdapter.swapCursor(null);
                        todoListAdapter.notifyDataSetChanged();
                        View v = lvItems.getEmptyView();
                        lvItems.setEmptyView(v);
                    }
                }
            }
        });

    }

    private static class UiHandler extends Handler {

        private final WeakReference<MainActivity> activity;

        public UiHandler(MainActivity mActivity) {
            this.activity = new WeakReference<MainActivity>(mActivity);
        }

        public void handleMessage(Message msg) {

               MainActivity mainActivity = activity.get();

                if(msg.what== UPDATE_UI){
                    if(mainActivity!=null)
                        mainActivity.doRefreshList();
                }
        }
    }
}

