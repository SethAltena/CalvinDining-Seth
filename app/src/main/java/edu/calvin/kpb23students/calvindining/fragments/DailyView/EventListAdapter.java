package edu.calvin.kpb23students.calvindining.fragments.DailyView;

import android.content.Context;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import android.os.Handler;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import edu.calvin.kpb23students.calvindining.CalvinDiningService;
import edu.calvin.kpb23students.calvindining.R;

/**
 * <p>
 * Handles displaying data given to display.
 * <p/>
 *
 * @author Kristofer
 * @version Fall, 2016
 */
public class EventListAdapter extends EventListObserver {
    /**
     * Events changed to display item
     */
    static class DisplayItem {
        public final CalvinDiningService.Meal meal;
        public final String startTime;
        public final String endTime;
        public String duration = "";
        public DisplayItem(CalvinDiningService.Meal meal, String startTime, String endTime){
            this.meal = meal;
            this.startTime = startTime;
            this.endTime = endTime;
        }
        public void setDuration(String duration) {
            this.duration = duration;
        }
    }

    final Context context;
    final LayoutInflater layoutInflater;
    final ArrayList<DisplayItem> displayItems;
    final CalvinDiningService diningService;
    final Observer diningServiceObserver;
    final String diningVenueName;

    /**
     * Handles the displaying the events
     * @param context
     * @param layoutInflater
     */
    public EventListAdapter(Context context, LayoutInflater layoutInflater, final CalvinDiningService diningService, final String diningVenueName) {
        this.diningVenueName = diningVenueName;
        this.context = context;
        this.layoutInflater = layoutInflater;
        displayItems = new ArrayList<DisplayItem>();
        this.diningService = diningService;
        diningServiceObserver = new Observer() {
            @Override
            public void update(Observable o, Object arg) {
                setEvents();
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

    private void setEvents() {
        final List<CalvinDiningService.Meal> meals = diningService.getEvents(diningVenueName);
        // http://stackoverflow.com/a/21862750/2948122
        new Handler(context.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                displayItems.clear();

                // Set meals to mealArray
                CalvinDiningService.Meal[] mealArray = meals.toArray(new CalvinDiningService.Meal[meals.size()]);


                if (mealArray.length > 0) {
                    // Sort events by start time -------------------------------------------------------------------------------
                    Arrays.sort(mealArray, new Comparator<CalvinDiningService.Meal>() {
                        @Override
                        public int compare(CalvinDiningService.Meal o1, CalvinDiningService.Meal o2) {
                            return o1.getGregStartTime().compareTo(o2.getGregEndTime());
                        }
                    });
                    int startHour = mealArray[0].getGregStartTime().get(Calendar.HOUR_OF_DAY);

                    // Set displayItems

                    // TODO Make this better. It looks terrible right now
                    // Version that does it per hour
                    // Add events that are empty so it can fill in the gaps this assumes events don't overlap.
                    // TODO what if events overlap? oh no
                    GregorianCalendar beginOverlap = (GregorianCalendar) mealArray[0].getGregStartTime().clone();
                    beginOverlap.set(Calendar.HOUR_OF_DAY, startHour);
                    beginOverlap.set(Calendar.MINUTE, 0);
                    GregorianCalendar endOverlap;

                    // TODO make this not use DisplayItem
                    DisplayItem betweenEvents = new DisplayItem(
                            null, null, null
                    );

                    DateFormat timeFormat = android.text.format.DateFormat.getTimeFormat(context);

                    for (CalvinDiningService.Meal meal: mealArray) {
                        // Make time between events
                        betweenEvents.setDuration("DURATION"); // TODO duration

                        // Make event display item
                        DisplayItem displayItem = new DisplayItem(
                                meal,
                                timeFormat.format(meal.getGregStartTime().getTime()),
                                timeFormat.format(meal.getGregEndTime().getTime())
                        );
                        displayItems.add(betweenEvents);
                        displayItems.add(displayItem);
                    }
                    betweenEvents.setDuration("DURATION");
                    displayItems.add(betweenEvents);
                }
                // If no meals tell the user
                // TODO make this better and make between events not use the display item class or not?
                if (displayItems.size() == 0) {
                    DisplayItem betweenEvents = new DisplayItem(
                            null, null, null
                    );
                    betweenEvents.setDuration("THERE ARE NO MEALS TODAY");
                    displayItems.add(betweenEvents);
                }
                notifyDataSetChanged(); // rerun getView to notice new changes
            }
        });
    }

    /**
     * @return count
     */
    @Override
    public int getCount() {
        return this.displayItems.size();
    }
    /**
     *
     * @param position
     * @return item
     */
    @Override
    public Object getItem(int position) {
        return displayItems.get(position);
    }

    /**
     * @param position position of item
     * @return id
     */
    @Override
    public long getItemId(int position) {
        return -1;
    }

    /**
     * Handles view
     * @param position position of displayItem
     * @param convertView handle view
     * @param parent
     * @return timeLabel
     */
    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.v("X", "Changing text " + String.valueOf(position));

        DisplayItem displayItem = this.displayItems.get(position);

        // TODO use something better than an if else
        if (displayItem.meal != null) {
            // If event
            TimeLabel timeLabel = (TimeLabel)layoutInflater.inflate(R.layout.time_label, parent, false);
            timeLabel.set(true, displayItem.meal.getName(), displayItem.startTime, displayItem.endTime, displayItem.meal.getDescription());
            return timeLabel;
        } else {
            // BetweenEvents
            TimeLabelBetween timeLabelBetween = (TimeLabelBetween) layoutInflater.inflate(R.layout.time_label_between, parent, false);
            timeLabelBetween.set(false, displayItem.duration);
            return timeLabelBetween;
        }
    }
}
