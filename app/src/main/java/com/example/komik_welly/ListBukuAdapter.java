package com.example.komik_welly;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.List;

public class ListBukuAdapter extends BaseAdapter {

    Activity activity;
    List<ListDataBuku> items;
    Context context;

    private LayoutInflater inflater;
    private ImageLoader imageLoader;

    public ListBukuAdapter(Activity activity, List<ListDataBuku> items, Context context) {
        this.activity = activity;
        this.items = items;
        this.context = context;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int i) {
        return items.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(inflater == null)
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (view == null) {
            view = inflater.inflate(R.layout.list, null);
        }
        NetworkImageView image = view.findViewById(R.id.networkImageView);;

        TextView txt_kode_komik   = view.findViewById(R.id.txt_kode_komik);
        TextView txt_judul_komik = view.findViewById(R.id.txt_judul_komik);
        TextView txt_tipe_komik = view.findViewById(R.id.txt_tipe_komik);

        ListDataBuku data = items.get(i);

        imageLoader = CustomVolleyRequestQueue.getInstance(context).getImageLoader();
        final String url = data.getCover();
        imageLoader.get(url, ImageLoader.getImageListener(image, R.mipmap.ic_launcher, R.drawable.ic_launcher_foreground));
        image.setImageUrl(url, imageLoader);
        txt_kode_komik.setText(data.getKode_buku());
        txt_judul_komik.setText(data.getJudul());
        txt_tipe_komik.setText(data.getTipe());
        return view;
    }
}
