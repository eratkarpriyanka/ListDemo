package com.codepath.listdemo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

public class EditItemActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnSave;
    EditText etEditText;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        position = bundle.getInt("position");
        String data = bundle.getString("data");

        btnSave = (Button) findViewById(R.id.btnSave);
        btnSave.setOnClickListener(this);

        etEditText = (EditText) findViewById(R.id.editText2);
        etEditText.setText(data);
        etEditText.setSelection(data.length());


    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.btnSave){
            String editedText = etEditText.getText().toString();
            etEditText.setText("");
            Intent intent = new Intent();
            intent.putExtra("position",position);
            intent.putExtra("editedText", editedText);
            setResult(RESULT_OK,intent);
            hideSoftKeyboard(etEditText);
            finish();
        }
    }
    public void hideSoftKeyboard(View view){
        InputMethodManager imm =(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
