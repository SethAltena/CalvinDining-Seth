package edu.calvin.kpb23students.calvindining.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.calvin.kpb23students.calvindining.R;


/**
 * <p>
 *     This fragment shows prices for guests and students
 * </p>
 */
public class Prices extends Fragment {
    public Prices() {
        // Required empty public constructor
    }
    // Required empty public constructor


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_prices, container, false);
    }
}


