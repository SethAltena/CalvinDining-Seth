package edu.calvin.kpb23students.calvindining.fragments.DailyView;

import android.database.DataSetObserver;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Kristofer on 2016-11-06.
 */

public abstract class ObservingFragmentPagerAdapter extends FragmentPagerAdapter {
    private int observerCount;

    public ObservingFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {
        super.registerDataSetObserver(observer);
        observerCount++;
        if (observerCount == 1) {
            gainedFirstDataSetObserver();
        }
    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {
        super.unregisterDataSetObserver(observer);
        observerCount--;
        if (observerCount == 0) {
            lostLastDataSetObserver();
        } else if (observerCount < 0) {
            throw new RuntimeException("observerCount went below 0");
        }
    }

    protected void gainedFirstDataSetObserver() {} // to be overridden

    protected void lostLastDataSetObserver() {} // to be overridden
}
