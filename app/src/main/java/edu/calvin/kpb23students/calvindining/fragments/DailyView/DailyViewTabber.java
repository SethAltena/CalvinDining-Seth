package edu.calvin.kpb23students.calvindining.fragments.DailyView;

import android.app.ActionBar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import edu.calvin.kpb23students.calvindining.CalvinDiningService;
import edu.calvin.kpb23students.calvindining.MyApplication;
import edu.calvin.kpb23students.calvindining.R;

/**
 * <p>
 *  This handles changing between different daily views
 * <p/>
 *
 * @author Kristofer
 * @version Fall, 2016
 */
public class DailyViewTabber extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_daily_view_tabber, container, false);

        ViewPager pager = (ViewPager) view.findViewById(R.id.pager);
        // When using fragment nesting, be sure to use the right fragment manager! http://stackoverflow.com/a/13466829/2948122
        pager.setAdapter(new MyPagerAdapter(getChildFragmentManager(), MyApplication.getMyApplication().getCalvinDiningService()));

        // Give the TabLayout the ViewPager
        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(pager);

        return view;
    }

    private class MyPagerAdapter extends ObservingFragmentPagerAdapter {
        private List<CalvinDiningService.Venue> venues;
        final private CalvinDiningService diningService;
        final Observer diningServiceObserver;
        public MyPagerAdapter(FragmentManager fm, final CalvinDiningService diningService) {
            super(fm);
            this.diningService = diningService;
            venues = new ArrayList<CalvinDiningService.Venue>();
            diningServiceObserver = new Observer() {
                @Override
                public void update(Observable o, Object arg) {
                    venues = diningService.getVenues();
                    notifyDataSetChanged(); // rerun getView to notice new changes
                }
            };
        }

        @Override
        protected void gainedFirstDataSetObserver() {
            diningService.addObserver(diningServiceObserver);
            // Do initial load of data after observer is added so that
            // we get updated with any changes that happened between
            // construction and when a subscriber was added.
            diningServiceObserver.update(null, null);
        }

        @Override
        protected void lostLastDataSetObserver() {
            diningService.deleteObserver(diningServiceObserver);
        }

        @Override
        public Fragment getItem(int pos) {
            return DailyView.newInstance(venues.get(pos).getName());
        }

        @Override
        public int getCount() {
            return venues.size();
        }

        @Override
        public CharSequence getPageTitle(int pos) {
            Log.v("x", "venues:" + venues.get(pos).getName());
            return venues.get(pos).getName();
        }
    }
}