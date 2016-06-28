package com.codepath.listdemo.Activities;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.codepath.listdemo.R;
import com.codepath.listdemo.database.ToDoItemData;
import com.codepath.listdemo.dialogs.DeleteItemDialog;
import com.codepath.listdemo.model.ToDoItem;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class EditItemActivity extends AppCompatActivity implements View.OnClickListener, DeleteItemDialog.ConfirmDeleteListener, AdapterView.OnItemSelectedListener {

    Button btnSave;
    EditText etEditText;
    private int position;
    private DatePickerDialog dueDatePicker;
    private EditText etDueDate;
    private SimpleDateFormat simpleDateFormat;

    private static final int ADD_ITEM=1;
    private static final int EDIT_ITEM=2;

    private static int action =ADD_ITEM;
    private ToDoItemData todoItemData;
    private Spinner spinPriority;
    private String prioritySelected;
    private int updateRecordId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        todoItemData = ToDoItemData.getInstance();

        setViews();

    }

    private void setViews() {

        btnSave = (Button) findViewById(R.id.btnSave);
        btnSave.setOnClickListener(this);

        etEditText = (EditText) findViewById(R.id.editText2);
        etDueDate = (EditText)findViewById(R.id.etDueDate);

        etDueDate.setOnClickListener(this);
        etDueDate.setInputType(InputType.TYPE_NULL);

        spinPriority = (Spinner) findViewById(R.id.spinPriority);
        spinPriority.setOnItemSelectedListener(this);

        initDatePicker();

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if(bundle !=null){
            position = bundle.getInt("position");
            action = EDIT_ITEM;

            populateItemInfo();
        }else{
            action = ADD_ITEM;

        }
    }

    private void populateItemInfo() {

        ToDoItem toDoItem = todoItemData.getItemRecord(position);
        if(toDoItem !=null) {
            updateRecordId= toDoItem.getId();
            etEditText.setText(toDoItem.getName());
            if(!toDoItem.getName().isEmpty())
                etEditText.setSelection(toDoItem.getName().length());
            etDueDate.setText(toDoItem.getDate());
            String priority= toDoItem.getPriority();
            if(!priority.isEmpty()) {
                String priorityArray[] = getResources().getStringArray(R.array.priority_array);

                int i=0;
                for (String temp :priorityArray){

                    if(temp.equals(priority)){
                        spinPriority.setSelection(i);
                        break;
                    }
                    i++;
                }
            }

        }else{
            Log.i("edit", "item info null");
        }
    }

    private void initDatePicker(){

        Calendar calendar = Calendar.getInstance();
        simpleDateFormat = new SimpleDateFormat("dd MMMM yyyy", Locale.US);
        dueDatePicker = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar calDueDate = Calendar.getInstance();
                calDueDate.set(year, monthOfYear, dayOfMonth);
                String strDate = simpleDateFormat.format(calDueDate.getTime());
                etDueDate.setText(strDate);
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

    }

    private ToDoItem setToDoItemData(){

        String name = etEditText.getText().toString();
        String date = etDueDate.getText().toString();
        ToDoItem todoItem = new ToDoItem(name,date,prioritySelected);
        return todoItem;
    }

    @Override
    public void onClick(View view) {


        if(view.getId() == R.id.btnSave){
            ToDoItem todoItem = setToDoItemData();
            if(action == EDIT_ITEM){
                todoItemData.updateItem(updateRecordId,todoItem);
            }else {
                todoItemData.addItemData(todoItem);
            }hideSoftKeyboard(etEditText);
            finish();
        }
        else if(view.getId()== R.id.etDueDate){
            dueDatePicker.show();
        }
    }
    public void hideSoftKeyboard(View view){
        InputMethodManager imm =(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    public void onConfirmDelete(boolean shouldDelete) {

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        prioritySelected = (String)parent.getItemAtPosition(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
