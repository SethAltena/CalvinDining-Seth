package edu.calvin.kpb23students.calvindining;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import edu.calvin.kpb23students.calvindining.fragments.About;
import edu.calvin.kpb23students.calvindining.fragments.Calendar;
import edu.calvin.kpb23students.calvindining.fragments.DailyView.DailyViewTabber;
import edu.calvin.kpb23students.calvindining.fragments.Hours;
import edu.calvin.kpb23students.calvindining.fragments.Poll;
import edu.calvin.kpb23students.calvindining.fragments.Prices;
import edu.calvin.kpb23students.calvindining.fragments.Vote;

/**
 * <p>
 * This is a Calvin Dining hall app made for cs 262
 * <p/>
 *
 * @author Kristofer
 * @version Fall, 2016
 */
public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setCheckedItem(R.id.nav_daily_view); // set nav daily view to be selected on startup
        navigationView.setNavigationItemSelectedListener(this);

        // Start with daily view opened
        openFragment(new DailyViewTabber());
    }

    /**
     * Opens a fragment
     *
     * @param fragment the fragments that can be accessed using the sliding menu
     */
    public void openFragment(Fragment fragment) {
        // Framents
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.contentFragment, fragment);
        transaction.commit();
    }

    /**
     * Close the drawer on backpress
     */
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    /**
     * Create the menu options
     * @param menu
     * @return true
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    /**
     * Handle the actionbar items when they are pressed.
     * @param item action bar item pressed
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent i = new Intent(getApplicationContext(), SettingsActivity.class);
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Handle menu items when they are pressed.
     *
     * @param item the item that is selected
     * @return true
     */
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_daily_view) {
            openFragment(new DailyViewTabber());
        } else if (id == R.id.nav_calendar) {
            openFragment(new Calendar());
        } else if (id == R.id.nav_hours) {
            openFragment(new Hours());
        } else if (id == R.id.nav_prices) {
            openFragment(new Prices());
        } else if (id == R.id.nav_about) {
            openFragment(new About());
        } else if (id == R.id.nav_poll) {
            openFragment(new Poll());
        } else if (id == R.id.nav_vote) {
            openFragment(new Vote());
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
