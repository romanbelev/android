package belev.org.warface_app;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.List;
import androidx.annotation.Nullable;
import androidx.fragment.app.ListFragment;

public class SniperFragment extends ListFragment {
    private Context mContext;

    private static final String BUNDLE_LINK = "bundle_link";
    private static final String BUNDLE_MAPS = "bundle_maps";
    private Integer achivmentid;
    private Integer mapsid;
    LayoutInflater inf;

    String[] name;
    String[] about;
    String[] type;
    TypedArray icons;

    android.widget.Adapter adapter;
    private List<Maps> rowItems;

    public static int[] name_arr = { R.array.sniper_verb_name, R.array.sniper_kred_name, R.array.sniper_box_name };
    public static int[] about_arr = { R.array.sniper_verb_about, R.array.sniper_kred_about, R.array.sniper_box_about };
    public static int[] type_arr = { R.array.sniper_verb_type, R.array.sniper_kred_type, R.array.sniper_box_type };
    public static int[] icon_arr = { R.array.sniper_verb_icon, R.array.sniper_kred_icon, R.array.sniper_box_icon };
    public static int[] link_arr = { R.array.sniper_verb_link, R.array.sniper_kred_link, R.array.sniper_box_link };

    private List<Maps> myAbout = new ArrayList<Maps>();
    public int classid;
    MainActivity mainActivity;
    boolean isLoadedAd;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (getArguments() != null && getArguments().containsKey(BUNDLE_MAPS)) {
            mapsid = getArguments().getInt(BUNDLE_MAPS);
        }

        View view = inflater.inflate(R.layout.framelist_maps, container, false);
        ListView listView = (ListView) view.findViewById(android.R.id.list);

        mainActivity = (MainActivity) getActivity();
        listView.addHeaderView(new View(mainActivity.getApplicationContext()));
        listView.addFooterView(new View(mainActivity.getApplicationContext()));

        return view;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        String[] maps_array = getResources().getStringArray(link_arr[mapsid]);

        int positionShift = isLoadedAd ? position - 2 : position - 1;
        if (mainActivity.isOnline()) {
            Intent myIntent = new Intent(getActivity(), SniperWebActivity.class);
            myIntent.putExtra("BUNDLE_LINK", maps_array[positionShift]);
            getActivity().startActivity(myIntent);

        } else {
            Intent myIntent = new Intent(getActivity(), ConnectionActivity.class);
            getActivity().startActivity(myIntent);
        }
    }

    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

        name = getResources().getStringArray(name_arr[mapsid]);
        about = getResources().getStringArray(about_arr[mapsid]);
        type = getResources().getStringArray(type_arr[mapsid]);
        //icons = getResources().obtainTypedArray(icon_arr[0]);
        TypedArray intarray = getResources().obtainTypedArray(icon_arr[mapsid]);

        rowItems = new ArrayList<Maps>();

        for (int i = 0; i < name.length; i++ ) {

            //Drawable drawable = icons.getDrawable(i);
            rowItems.add(new Maps(name[i], about[i], type[i], intarray.getResourceId(i, -1)));
        }

        adapter = new RiflemanAdapter(getActivity(), rowItems);
        setListAdapter((ListAdapter) adapter);

    }
}
