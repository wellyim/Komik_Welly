package com.example.komik_welly;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    String server_url_select = "http://192.168.0.106/server-komik/?komik=all";
    ListView list;
    ListBukuAdapter listBukuAdapter;
    List<ListDataBuku> itemList = new ArrayList<ListDataBuku>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        list  = (ListView) findViewById(R.id.list);

        listBukuAdapter = new ListBukuAdapter(MainActivity.this,  itemList, this);
        list.setAdapter(listBukuAdapter);

        tampilDataKomik();
    }

    private void tampilDataKomik() {

//        listBukuAdapter.notifyDataSetChanged();

        // membuat request JSON
        JsonArrayRequest jArr = new JsonArrayRequest(server_url_select, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                // TextView text = findViewById(R.id.text);
                // Parsing json
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject obj = response.getJSONObject(i);

                        ListDataBuku item = new ListDataBuku();

                        item.setKode_buku(obj.getString("kode_buku"));
                        item.setJudul(obj.getString("judul"));
                        item.setTipe(obj.getString("tipe"));
                        item.setCover(obj.getString("cover"));

                        // menambah item ke array
                        itemList.add(item);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                listBukuAdapter.notifyDataSetChanged();


//                Toast.makeText(MainActivity.this,response.toString(),Toast.LENGTH_SHORT).show();

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(MainActivity.this,error.toString(),Toast.LENGTH_SHORT).show();
            }
        });
        // menambah request ke request queue
        RequestQueue mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        mRequestQueue.add(jArr);

    }

}