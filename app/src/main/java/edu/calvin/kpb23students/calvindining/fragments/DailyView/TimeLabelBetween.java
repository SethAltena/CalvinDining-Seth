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
 * Handles what is between events
 * <p/>
 *
 * @author Kristofer
 * @version Fall, 2016
 */
public class TimeLabelBetween extends RelativeLayout{
    private TextView duration;

    /**
     *
     * @param context
     */
    public TimeLabelBetween(Context context) {
        super(context);
    }

    /**
     *
     * @param context
     * @param attrs
     */
    public TimeLabelBetween(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     *
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    public TimeLabelBetween(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     *
     * @param context
     * @param attrs
     * @param defStyleAttr
     * @param defStyleRes
     */
    public TimeLabelBetween(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    /**
     * Sets color of current to a different color
     * @param isCurrent
     */
    // http://stackoverflow.com/a/17277714/2948122
    private void setIsCurrent(Boolean isCurrent) {
        // Get theme
        TypedValue typedValue = new TypedValue();
        Resources.Theme theme = getContext().getTheme();
        theme.resolveAttribute(android.R.attr.colorPrimaryDark, typedValue, true);
        int color = typedValue.data;

        // TODO make own color for this http://stackoverflow.com/a/30905173/2948122
        if (isCurrent) { // Highlight block
            setBackgroundColor(0xff24ff12);
        } else { // Dehighlight block
            setBackgroundColor(color);
        }
    }

    /**
     * inflates the view
     */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        this.duration = (TextView)findViewById(R.id.duration);
    }

    /**
     *  Sets isCurrent and string duration
     * @param isCurrent
     * @param duration
     */
    public void set(Boolean isCurrent, String duration) {
        setIsCurrent(isCurrent);
        this.duration.setText(duration);
    }
}

