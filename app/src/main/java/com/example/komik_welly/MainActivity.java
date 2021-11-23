package com.example.komik_welly;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
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

    String server_url_select = "http://192.168.0.101/server-komik/?komik=all";
    ListView list;
    ListBukuAdapter listBukuAdapter;
    List<ListDataBuku> itemList = new ArrayList<ListDataBuku>();
    Button btn_tambah_komik ;

    SharedPreferences sharedpreferenlogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        list  = (ListView) findViewById(R.id.list);
        btn_tambah_komik = findViewById(R.id.btn_tambah_komik);

        listBukuAdapter = new ListBukuAdapter(MainActivity.this,  itemList, this);
        list.setAdapter(listBukuAdapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ListDataBuku data = itemList.get(i);

                String get_kodebuku = data.getKode_buku();
                String get_judul = data.getJudul();
                String get_tipe = data.getTipe();
                String get_cover = data.getCover();

                Intent i_detail = new Intent( MainActivity.this, DetailActivity.class);
                i_detail.putExtra("get_kodebuku", get_kodebuku);
                i_detail.putExtra("get_judul", get_judul);
                i_detail.putExtra("get_tipe", get_tipe);
                i_detail.putExtra("get_cover", get_cover);
                startActivity(i_detail);

//                Toast.makeText(MainActivity.this, get_kodebuku,Toast.LENGTH_LONG).show();

            }
        });

        tampilDataKomik();

        btn_tambah_komik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent tbh_komik = new Intent(MainActivity.this, TambahKomik.class);
                startActivity(tbh_komik);

                String sess_created_login= "session_created_login";
                String session_create_login= "create_login";
                sharedpreferenlogin= getSharedPreferences(sess_created_login, Context.MODE_PRIVATE);
                String share_session_create_login= sharedpreferenlogin.getString(session_create_login,  null);

                if(share_session_create_login!= null){
                    Toast.makeText(MainActivity.this, share_session_create_login, Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(MainActivity.this, "Silahkan Login UntukMengaksesHalamanini!", Toast.LENGTH_SHORT).show();
                }

            }
        });
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater= getMenuInflater();
        inflater.inflate(R.menu.app_bar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.item_menu_login) {
            Intent intent_login= new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent_login);
            return true;
        }else if (item.getItemId() == R.id.item_menu_setting) {
            Toast.makeText(MainActivity.this, "Menu Setting!", Toast.LENGTH_SHORT).show();
        }else if (item.getItemId() == R.id.item_menu_logout) {
            SharedPreferences sharedpreferences;
            String sess_created_login      = "session_created_login";
            String session_create_login    = "create_login";
            sharedpreferences = getSharedPreferences(sess_created_login, Context.MODE_PRIVATE);

            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.remove(session_create_login);
            editor.clear();
            editor.apply();

        }
        return super.onOptionsItemSelected(item);

    }
}