package edu.calvin.kpb23students.calvindining.fragments.DailyView;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import edu.calvin.kpb23students.calvindining.MyApplication;
import edu.calvin.kpb23students.calvindining.R;


/**
 * <p>
     This class handles the daily view. It will show important things about the day to the user.
 * <p/>
 *
 * @author Kristofer
 * @version Fall, 2016
 */
public class DailyView extends Fragment {
    final static String DININGVENUENAME = "diningName";
    private String diningVenueKey;
    public DailyView() {
        // Required empty public constructor
    }

    public static DailyView newInstance(String diningVenueKey) {
        DailyView fragment = new DailyView();
        Bundle bundle = new Bundle(2);
        bundle.putString(DININGVENUENAME, diningVenueKey);
        fragment.setArguments(bundle);
        return fragment ;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        diningVenueKey = getArguments().getString(DININGVENUENAME);
    }

    /**
     * Take data and show it to the user using a linear list.
     * @param inflater inflater to make scrolling possible
     * @param container container to put things in
     * @param savedInstanceState for saving states. Not used yet.
     * @return view
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (container == null) {Log.d("x", "container is null :-/"); throw new RuntimeException("Expected non-null container!");}
        View view = inflater.inflate(R.layout.fragment_daily_view, container, false);

        // Added stuffs
        Log.v("X", "hi there !");

        // Make EventList
        // http://stackoverflow.com/a/25329322/2948122
        EventListAdapter timesEventAdapter = new EventListAdapter(container.getContext(), inflater, MyApplication.getMyApplication().getCalvinDiningService(), diningVenueKey);
        ((ListView)view.findViewById(R.id.fragment_daily_view_times)).setAdapter(timesEventAdapter);
        // Test Events
        // Inflate the layout for this fragment
        return view;
    }
}
