package edu.calvin.kpb23students.calvindining.fragments.DailyView;

import android.content.Context;
import android.content.res.Resources;
import android.media.Rating;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import edu.calvin.kpb23students.calvindining.R;

/**
 * <p>
 * Handles displaying display items
 * <p/>
 *
 * @author Kristofer
 * @version Fall, 2016
 */
public class TimeLabel extends RelativeLayout {
    private TextView name;
    private TextView beginTime;
    private TextView endTime;
    private TextView description;

    public TimeLabel(Context context) {
        super(context);
    }

    public TimeLabel(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TimeLabel(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public TimeLabel(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    // http://stackoverflow.com/a/17277714/2948122
    private void setIsCurrent(Boolean isCurrent) {
        // Get theme
        TypedValue typedValue = new TypedValue();
        Resources.Theme theme = getContext().getTheme();
        theme.resolveAttribute(android.R.attr.colorBackground, typedValue, true);
        int color = typedValue.data;
        theme.resolveAttribute(android.R.attr.colorActivatedHighlight, typedValue, true);
        int colorPrimary = typedValue.data;
        // TODO make own color for this http://stackoverflow.com/a/30905173/2948122
        if (isCurrent) { // Highlight block
            setBackgroundColor(colorPrimary);
        } else { // Dehighlight block
            setBackgroundColor(color);
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        this.name = (TextView)findViewById(R.id.name);
        this.beginTime = (TextView)findViewById(R.id.beginTime);
        this.endTime = (TextView)findViewById(R.id.endTime);
        this.description = (TextView)findViewById(R.id.description);
    }

    public void set(Boolean isCurrent, String name, String beginTime, String endTime, String description) {
        setIsCurrent(isCurrent);
        this.name.setText(name);
        this.beginTime.setText(beginTime);
        this.endTime.setText(endTime);
        this.description.setText(description);
    }
}
