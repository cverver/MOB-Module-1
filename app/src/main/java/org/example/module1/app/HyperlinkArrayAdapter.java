package org.example.module1.app;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class HyperlinkArrayAdapter extends ArrayAdapter<Hyperlink> {
    Context context;
    int layoutResourceId;
    Hyperlink[] hyperlinks;

    public HyperlinkArrayAdapter(Context context, int resource) {
        super(context, resource);
    }

    public HyperlinkArrayAdapter(Context context, int resource, int textViewResourceId) {
        super(context, resource, textViewResourceId);
    }

    public HyperlinkArrayAdapter(Context context, int resource, Hyperlink[] objects) {
        super(context, resource, objects);
        this.layoutResourceId = resource;
        this.context = context;
        this.hyperlinks = objects;
    }

    public HyperlinkArrayAdapter(Context context, int resource, int textViewResourceId, Hyperlink[] objects) {
        super(context, resource, textViewResourceId, objects);
    }

    public HyperlinkArrayAdapter(Context context, int resource, List<Hyperlink> objects) {
        super(context, resource, objects);
    }

    public HyperlinkArrayAdapter(Context context, int resource, int textViewResourceId, List<Hyperlink> objects) {
        super(context, resource, textViewResourceId, objects);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        HyperlinkHolder holder;

        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new HyperlinkHolder();
            holder.txtID = (TextView) row.findViewById(R.id.hyperlinkID);
            holder.txtDescription = (TextView) row.findViewById(R.id.hyperlinkDescription);
            holder.txtURL = (TextView) row.findViewById(R.id.hyperlinkURL);
            holder.txtCategory = (TextView) row.findViewById(R.id.hyperlinkCategory);
            holder.txtTimestamp = (TextView) row.findViewById(R.id.hyperlinkTimestamp);

            row.setTag(holder);
        } else {
            holder = (HyperlinkHolder) row.getTag();
        }

        Hyperlink h = hyperlinks[position];
        holder.txtID.setText(h.ID.toString());
        holder.txtDescription.setText(h.Description);
        holder.txtURL.setText(h.URL);
        holder.txtCategory.setText(context.getResources().getStringArray(R.array.cats)[h.Category]);
        holder.txtTimestamp.setText(h.Timestamp);

        return row;
    }


    static class HyperlinkHolder {
        TextView txtID;
        TextView txtDescription;
        TextView txtURL;
        TextView txtCategory;
        TextView txtTimestamp;
    }
}
