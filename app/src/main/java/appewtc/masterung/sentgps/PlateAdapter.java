package appewtc.masterung.sentgps;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Created by masterUNG on 4/21/16 AD.
 */
public class PlateAdapter extends BaseAdapter{

    //Explicit
    private Context context;
    private String[] titleStrings;

    public PlateAdapter(Context context, String[] titleStrings) {
        this.context = context;
        this.titleStrings = titleStrings;
    }   // Constructor

    @Override
    public int getCount() {
        return titleStrings.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view1 = layoutInflater.inflate(R.layout.plate_listview, viewGroup, false);

        TextView numTextView = (TextView) view1.findViewById(R.id.textView8);
        String strNum = Integer.toString(i + 1);
        numTextView.setText(strNum + ". ");

        TextView titleTextView = (TextView) view1.findViewById(R.id.textView9);
        titleTextView.setText(titleStrings[i]);

        return view1;
    }
}   // Main Class
