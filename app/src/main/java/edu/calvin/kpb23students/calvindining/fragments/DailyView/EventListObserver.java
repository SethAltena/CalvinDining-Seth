package edu.calvin.kpb23students.calvindining.fragments.DailyView;

import android.database.DataSetObserver;
import android.widget.BaseAdapter;

/**
 * <p>
 * This handles Observers
 * <p/>
 *
 * @author Kristofer
 * @version Fall, 2016
 */
public abstract class EventListObserver extends BaseAdapter {
    private int observerCount;
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
