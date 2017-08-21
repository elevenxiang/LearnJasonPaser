package com.htc.eleven.learnjasonpaser;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tv;

    private String filePath = "test.json";
    private String lineBreak = System.getProperty("line.separator");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv = (TextView) findViewById(R.id.tv);
        findViewById(R.id.ReadJasonFile).setOnClickListener(this);
        findViewById(R.id.CreateJasonFile).setOnClickListener(this);

    }

    private void readJasonFile(String path) throws FileNotFoundException, JSONException {

        InputStreamReader inputStreamReader;
        StringBuilder stringBuilder = new StringBuilder();
        try {
            inputStreamReader = new InputStreamReader(getAssets().open(path));
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String line;
            while ((line=bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        JSONObject root = new JSONObject(stringBuilder.toString());
        String data = root.getString("Category");
        tv.append("Category: "+data+lineBreak);

        JSONArray array = root.getJSONArray("Languages");
        for(int i=0; i<array.length(); i++) {
            JSONObject element = array.getJSONObject(i);
            tv.append("ID: " +element.getInt("id")+", Name: "+element.getString("name")+", IDE: "+element.getString("ide")+lineBreak);
        }
    }

    private void createJasonFile() throws JSONException, IOException {

        JSONObject root = new JSONObject();
        root.put("Category", "IT");

        JSONArray array = new JSONArray();

        JSONObject lan1 = new JSONObject();
        lan1.put("id", 1);
        lan1.put("name", "Java");
        lan1.put("ide", "Eclipse");

        JSONObject lan2 = new JSONObject();
        lan2.put("id", 2);
        lan2.put("name", "C++");
        lan2.put("ide", "Visual Studio");

        JSONObject lan3 = new JSONObject();
        lan3.put("id", 3);
        lan3.put("name", "Swift");
        lan3.put("ide", "Xcode");

        array.put(lan1);
        array.put(lan2);
        array.put(lan3);

        root.put("Languages", array);

        FileOutputStream fileOutputStream = openFileOutput("output.json", MODE_PRIVATE);
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);
        outputStreamWriter.write(root.toString()+lineBreak);

        outputStreamWriter.flush();
        fileOutputStream.flush();
        outputStreamWriter.close();
        fileOutputStream.close();


        Toast.makeText(MainActivity.this, "please check output file: " + "output.json", Toast.LENGTH_LONG).show();

    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ReadJasonFile:
                try {
                    readJasonFile(filePath);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;

            case R.id.CreateJasonFile:
                try {
                    createJasonFile();
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;

        }
    }
}
