package objects;

import android.app.Activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.cardgame.R;

public class CustomListView extends ArrayAdapter<String> {

    private String[] name;
    private String[] date;
    private String[] location;
    private String[] score;
    private Context context;

    public CustomListView(Activity context, String[] name, String[] date, String[] Location, String[] score) {
        super(context, R.layout.listview_layout,name);
        this.context = context;
        this.name = name;
        this.date = date;
        this.location = Location;
        this.score = score;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        ViewHolder viewHolder;
        if(v==null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE );
            v = layoutInflater.inflate(R.layout.listview_layout,null,true);
            viewHolder = new ViewHolder(v);
            v.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) v.getTag();
        }
        viewHolder.name.setText(this.name[position]);
        viewHolder.date.setText(this.date[position]);
        viewHolder.location.setText(this.location[position]);
        viewHolder.score.setText(this.score[position]);
        return  v;
    }

    private class ViewHolder {
        TextView name;
        TextView date;
        TextView location;
        TextView score;

        public ViewHolder(View v) {
            name = v.findViewById(R.id.listview_Lbl_name);
            date = v.findViewById(R.id.listview_Lbl_date);
            location = v.findViewById(R.id.listview_Lbl_location);
            score = v.findViewById(R.id.listview_Lbl_score);
        }
    }
}
