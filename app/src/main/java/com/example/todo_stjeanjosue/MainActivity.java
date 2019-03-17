package com.example.todo_stjeanjosue;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import org.apache.commons.io.FileUtils;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


import static org.apache.commons.io.FileUtils.writeLines;

@SuppressWarnings("ALL")
public class MainActivity extends AppCompatActivity {
    ArrayList<String> todoItems;
    ArrayAdapter<String> aToDoAdapter;
    ListView lvItems;
    EditText etEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        populateArrayItems();
        lvItems = (ListView) findViewById(R.id.lvItems);
        lvItems.setAdapter(aToDoAdapter);
        etEditText = (EditText) findViewById(R.id.etEditText);
        lvItems.setOnItemLongClickListener
                (new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick
                            (AdapterView<?> parent, View view, int position,
                             long id){
                        todoItems.remove(position);
                        aToDoAdapter.notifyDataSetChanged();
                        writeItems();
                        return true;
                    }
                });
    }
    public void populateArrayItems(){
        todoItems = new ArrayList<String>();
        readItems();
        aToDoAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_list_item_1, todoItems);

    }
    @SuppressWarnings("deprecation")
    private void readItems(){
        File filesDir = getFilesDir();
        File file = new File(filesDir, "todo.txt");
        try{
            //noinspection deprecation
            todoItems = new ArrayList<String>(FileUtils.readLines(file));
        } catch (IOException e){

        }
    }
    private void writeItems(){
        File filesDir = getFilesDir();
        File file = new File(filesDir, "todo.txt");
        try{
            //noinspection deprecation
            FileUtils.writeLines(file, todoItems);
        } catch (IOException e){

        }
    }

    public void onAddItem(View view) {
        aToDoAdapter.add(etEditText.getText().toString());
        etEditText.setText("");
        writeItems();
    }
}
