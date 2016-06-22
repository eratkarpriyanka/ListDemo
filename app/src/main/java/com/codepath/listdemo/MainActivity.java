package com.codepath.listdemo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private final int REQUEST_CODE_EDIT=100;
    ListView lvItems;
    ArrayList<String> arrItems;
    ArrayAdapter<String> arrListAdapter;
    Button btnAddItem;
    EditText etAddItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Todo add action bar
        populateArrayItems();
        lvItems = (ListView) findViewById(R.id.lvItemlist);
        lvItems.setAdapter(arrListAdapter);
        btnAddItem = (Button) findViewById(R.id.btnAddItem);
        btnAddItem.setOnClickListener(this);
        etAddItem = (EditText) findViewById(R.id.editText);
        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                /** Todo ask confirmation for deletion **/
                arrItems.remove(position);
                arrListAdapter.notifyDataSetChanged();
                Toast.makeText(MainActivity.this, "item at position "+(position+1)+" deleted", Toast.LENGTH_SHORT).show();
                writeItems();
                arrListAdapter.notifyDataSetChanged();
                return true;
            }
        });

        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this,EditItemActivity.class);
                intent.putExtra("position", position);
                TextView tv = (TextView) view;
                intent.putExtra("data",tv.getText());
                startActivityForResult(intent, REQUEST_CODE_EDIT);
            }
        });
    }

    public void populateArrayItems() {

        readItems();
        arrListAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrItems);
    }

    public void readItems(){

        File filesDir = getFilesDir();
        File file = new File(filesDir,"inputFile.txt");
        try{
            System.out.println(file.length());
            if(file.length()<=0)
                arrItems = new ArrayList<String>();
            else
                arrItems = new ArrayList<String>(FileUtils.readLines(file));

        }catch (IOException ioException){
            arrItems=null;
            ioException.printStackTrace();
        }
    }

    public void writeItems(){

        File filesDir = getFilesDir();
        File file = new File(filesDir,"inputFile.txt");
        try{
            FileUtils.writeLines(file, arrItems);
        }catch (IOException ioException){
            ioException.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if(requestCode == REQUEST_CODE_EDIT && resultCode == RESULT_OK){

            Bundle bundle = intent.getExtras();
            int position = bundle.getInt("position");
            String data = bundle.getString("editedText");
            arrItems.remove(position);
            arrItems.add(position, data);
            arrListAdapter.notifyDataSetChanged();
            writeItems();
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

            case R.id.btnAddItem: String strText = etAddItem.getText().toString();
                arrListAdapter.add(strText);
                if( !strText.isEmpty()) {
                    Toast.makeText(MainActivity.this, strText + " added", Toast.LENGTH_SHORT).show();
                }
                etAddItem.setText("");
                writeItems();
                arrListAdapter.notifyDataSetChanged();
                hideSoftKeyboard(etAddItem);
                break;

        }
    }

    public void hideSoftKeyboard(View view){
        InputMethodManager imm =(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}

