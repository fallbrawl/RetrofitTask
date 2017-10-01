package com.attracttest.attractgroup.retrofittask;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by nexus on 30.09.2017.
 */
public class GitItemsAdapter extends ArrayAdapter<GitItem> {

    private List<GitItem> items;
    private LayoutInflater mInflater;

    public GitItemsAdapter(Context context, List<GitItem> objects) {
        super(context, 0, objects);
        items = objects;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // A ViewHolder keeps references to children views to avoid unneccessary calls
        // to findViewById() on each row.
        ViewHolder holder;

        // When convertView is not null, we can reuse it directly, there is no need
        // to reinflate it. We only inflate a new View when the convertView supplied
        // by ListView is null.
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.git_item, null);

            // Creates a ViewHolder and store references to the two children views
            // we want to bind data to.
            holder = new ViewHolder();
            holder.login =  convertView.findViewById(R.id.login);
            holder.url =  convertView.findViewById(R.id.url);
            holder.userId =  convertView.findViewById(R.id.user_id);
            holder.avatar =  convertView.findViewById(R.id.avatar);

            convertView.setTag(holder);
        } else {

            // Get the ViewHolder back to get fast access to the TextView
            holder = (ViewHolder) convertView.getTag();
        }

        // Bind the data efficiently with the holder.
        holder.login.setText(items.get(position).getLogin());
        holder.url.setText(items.get(position).getUrl());
        holder.userId.setText(items.get(position).getId());

        Picasso.with(getContext()).load(items.get(position).getAvatar_url()).placeholder(R.mipmap.ic_launcher).fit().into(holder.avatar);

        return convertView;
    }

    static class ViewHolder {
        TextView login;
        TextView url;
        TextView userId;
        ImageView avatar;
    }
}
