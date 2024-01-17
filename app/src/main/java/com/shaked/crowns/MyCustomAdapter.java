package com.shaked.crowns;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class MyCustomAdapter extends BaseAdapter {

    private String[] songs;
    private Context context;

    public MyCustomAdapter(Context context, String[] songs) {
        this.songs = songs;
        this.context = context;
    }

    private static class ViewHolder{
        TextView textView;
    }

    @Override
    public int getCount() {
        return songs.length;
    }

    @Override
    public String getItem(int pos) {
        return songs[pos];
    }

    @Override
    public long getItemId(int pos) {
        return pos;
    }

    @Override
    public View getView(int pos, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null){
            holder=new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.my_list_item,parent,false);
            holder.textView = (TextView) convertView.findViewById(R.id.text1);
            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder) convertView.getTag();
        }
        holder.textView.setText(songs[pos]);
        return convertView;
    }
}
