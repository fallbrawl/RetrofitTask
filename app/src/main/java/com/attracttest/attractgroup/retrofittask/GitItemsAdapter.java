package com.attracttest.attractgroup.retrofittask;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.attracttest.attractgroup.retrofittask.pojos.Item;

import java.util.List;

/**
 * Created by nexus on 30.09.2017.
 */
public class GitItemsAdapter extends ArrayAdapter<Item> {

    private LayoutInflater mInflater;

    public GitItemsAdapter(Context context, List<Item> objects) {
        super(context, 0, objects);
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
            holder.item_id = convertView.findViewById(R.id.item_id);
            holder.url = convertView.findViewById(R.id.url);
            holder.description = convertView.findViewById(R.id.description);
            holder.language = convertView.findViewById(R.id.language);

            convertView.setTag(holder);
        } else {

            // Get the ViewHolder back to get fast access to the TextView
            holder = (ViewHolder) convertView.getTag();
        }

        Item item = getItem(position);

        // Bind the data efficiently with the holder.
        holder.item_id.setText(String.valueOf(item.getId()));
        holder.url.setText(item.getUrl());
        holder.description.setText(item.getDescription());
        holder.language.setText(item.getLanguage());

        //Picasso.with(getContext()).load(item.getOwner().getAvatarUrl()).placeholder(R.mipmap.ic_launcher).fit().into(holder.language);

        return convertView;
    }

    static class ViewHolder {
        TextView item_id;
        TextView url;
        TextView description;
        TextView language;
    }
}
