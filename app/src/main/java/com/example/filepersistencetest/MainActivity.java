package com.example.filepersistencetest;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;


public class MainActivity extends Activity {
    private EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText=(EditText)findViewById(R.id.edit_text);
        String s=load();
        if (!TextUtils.isEmpty(s))
        {
            editText.setText(s);
            editText.setSelection(s.length());
            Toast.makeText(this,"Restoring succeeded",Toast.LENGTH_SHORT).show();
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
    protected void onDestroy() {
        super.onDestroy();
        String s=editText.getText().toString();
        save(s);
    }
    public void save(String s){
        FileOutputStream output=null;
        BufferedWriter writer=null;
        try {
            output=openFileOutput("data",MODE_PRIVATE);
            writer=new BufferedWriter(new OutputStreamWriter(output));
            writer.write(s);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (writer!=null)
            {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public String load(){
        FileInputStream input=null;
        BufferedReader reader=null;
        StringBuilder stringBuilder=new StringBuilder();
        try {
            input = openFileInput("data");
            reader=new BufferedReader(new InputStreamReader(input));
            String s="";
            if((s=reader.readLine())!=null)
            {
                stringBuilder.append(s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(reader!=null)
            {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return stringBuilder.toString();
    }
}
