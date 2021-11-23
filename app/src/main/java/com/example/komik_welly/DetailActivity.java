package com.example.komik_welly;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class DetailActivity extends AppCompatActivity {

    TextView txt_kode_komik, txt_judul_komik, txt_tipe_komik, txt_cover_komik;
    NetworkImageView image;
    Button btn_update_komik, btn_hapus_komik;

    private ImageLoader imageLoader;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent getdata = getIntent();
        Bundle b = getdata.getExtras();
        String kodebuku =(String) b.get("get_kodebuku");
        String cover =(String) b.get("get_cover");
        String judul =(String) b.get("get_judul");
        String tipe =(String) b.get("get_tipe");


        image = findViewById(R.id.networkImageView);;

        txt_kode_komik   = findViewById(R.id.txt_kode_komik);
        txt_judul_komik = findViewById(R.id.txt_judul_komik);
        txt_tipe_komik = findViewById(R.id.txt_tipe_komik);
        btn_hapus_komik = findViewById(R.id.DeleteKomik);
        btn_update_komik = findViewById(R.id.UpdateKomik);


        imageLoader = CustomVolleyRequestQueue.getInstance(DetailActivity.this).getImageLoader();
        final String url = cover;
        imageLoader.get(url, ImageLoader.getImageListener(image, R.mipmap.ic_launcher, R.drawable.ic_launcher_foreground));
        image.setImageUrl(url, imageLoader);
        txt_kode_komik.setText(kodebuku);
        txt_judul_komik.setText(judul);
        txt_tipe_komik.setText(tipe);

        btn_hapus_komik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hapus_komik(kodebuku);
            }
        });

        btn_update_komik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i_update = new Intent(DetailActivity.this, UpdateKomikActivity.class);
                i_update.putExtra("update_kodebuku", kodebuku);
                i_update.putExtra("update_judul", judul);
                i_update.putExtra("update_tipe", tipe);
                i_update.putExtra("update_cover", cover);
                startActivity(i_update);

            }
        });

    }

    private void hapus_komik(String kodebuku) {
        String URI = "http://192.168.0.101/server-komik/?komik=hapus";
        RequestQueue queue          = Volley.newRequestQueue(DetailActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URI, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(DetailActivity.this,response.toString(),Toast.LENGTH_SHORT).show();
                if(response.toString().equals("hapus_data_berhasil")){
                    Intent i_main = new Intent(DetailActivity.this, MainActivity.class);
                    startActivity(i_main);
                    finish();
                }else{
                    Toast.makeText(DetailActivity.this,response.toString(),Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(DetailActivity.this,error.toString(),Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                // Posting parameters ke post url
                Map<String, String> params = new HashMap<String, String>();

                params.put("kode_buku", kodebuku);

                return params;
            }

        };

        queue.add(stringRequest);
    }
}