package com.square.apple.pdf_app.activities;

import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.square.apple.pdf_app.R;
import com.square.apple.pdf_app.adapter.DrawerAdapter;
import com.square.apple.pdf_app.adapter.SimpleDrawerItem;
import com.square.apple.pdf_app.fragments.AboutUsFragment;
import com.square.apple.pdf_app.fragments.ContactUsFragment;
import com.square.apple.pdf_app.fragments.HomeFragment;
import com.square.apple.pdf_app.fragments.OurCoachesFragment;
import com.square.apple.pdf_app.fragments.WebviewFragment;
import com.square.apple.pdf_app.utils.Utilities;
import com.yarolegovich.slidingrootnav.SlidingRootNav;
import com.yarolegovich.slidingrootnav.SlidingRootNavBuilder;

import java.util.Arrays;

public class MainDrawerActivity extends AppCompatActivity implements DrawerAdapter.OnItemSelectedListener {


    private SlidingRootNav slidingRootNav;

    private static final int HOME = 0;
    private static final int ABOUT_US = 1;
    private static final int OUR_COACHES = 2;
    private static final int BLOGS = 3;
    private static final int PODCASTS = 4;
    private static final int VIDEOS = 5;
    private static final int TAKETHEDISC = 6;
    private static final int CONTACT_US = 7;
    private String[] screenTitles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_drawer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();

            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        toolbar.setBackgroundColor(getResources().getColor(R.color.black_overlay));
        slidingRootNav = new SlidingRootNavBuilder(this)
                .withToolbarMenuToggle(toolbar)
                .withMenuOpened(false)
                .withDragDistance(140) //Horizontal translation of a view. Default == 180dp
                .withRootViewScale(0.7f) //Content view's scale will be interpolated between 1f and 0.7f. Default == 0.65f;
                .withRootViewElevation(10) //Content view's elevation will be interpolated between 0 and 10dp. Default == 8.
                .withRootViewYTranslation(4)
                .withContentClickableWhenMenuOpened(false)
                .withSavedState(savedInstanceState)
                .withMenuLayout(R.layout.menu_left_drawer)
                .inject();


        screenTitles = loadScreenTitles();

        DrawerAdapter adapter = new DrawerAdapter(Arrays.asList(
                createItemFor(HOME).setChecked(true),
                createItemFor(ABOUT_US),
                createItemFor(OUR_COACHES),
                createItemFor(BLOGS),
                createItemFor(PODCASTS),
                createItemFor(VIDEOS),
                createItemFor(TAKETHEDISC),
                createItemFor(CONTACT_US)));
        ;
        adapter.setListener((DrawerAdapter.OnItemSelectedListener) this);

        RecyclerView list = findViewById(R.id.list);
        list.setNestedScrollingEnabled(false);
        list.setLayoutManager(new LinearLayoutManager(this));
        list.setAdapter(adapter);
        adapter.setSelected(HOME);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    private SimpleDrawerItem createItemFor(int position) {
        return new SimpleDrawerItem(screenTitles[position])
                .withTextTint(color(R.color.colorPrimary))
                .withSelectedTextTint(color(R.color.colorAccent));
    }

    private String[] loadScreenTitles() {
        return getResources().getStringArray(R.array.ld_activityScreenTitles);
    }

    @ColorInt
    private int color(@ColorRes int res) {
        return ContextCompat.getColor(this, res);
    }

    @Override
    public void onItemSelected(int position) {

        if (position == HOME) {
            Fragment selectedScreen = HomeFragment.createFor(screenTitles[position]);
            showFragment(selectedScreen);
        } else if (position == ABOUT_US) {
            Fragment selectedScreen = AboutUsFragment.createFor(screenTitles[position]);
            showFragment(selectedScreen);
        } else if (position == OUR_COACHES) {
            Fragment selectedScreen = OurCoachesFragment.createFor(screenTitles[position]);
            showFragment(selectedScreen);
        } else if (position == BLOGS) {

            Utilities.putValueInEditor(this).putString("title", "'Blogs'").commit();
            Utilities.putValueInEditor(this).putString("url", "https://www.lizbentley.com/blogs").commit();
            Fragment selectedScreen = WebviewFragment.createFor(screenTitles[position]);
            showFragment(selectedScreen);
        } else if (position == PODCASTS) {


            Utilities.putValueInEditor(this).putString("title", "Podcasts").commit();
            Utilities.putValueInEditor(this).putString("url", "https://www.lizbentley.com/podcasts").commit();
            Fragment selectedScreen = WebviewFragment.createFor(screenTitles[position]);
            showFragment(selectedScreen);
        } else if (position == VIDEOS) {


            Utilities.putValueInEditor(this).putString("title", "Videos").commit();
            Utilities.putValueInEditor(this).putString("url", "https://www.youtube.com/channel/UC8uNWtuSXrbdyovyoAuPQmw").commit();
            Fragment selectedScreen = WebviewFragment.createFor(screenTitles[position]);
            showFragment(selectedScreen);

        } else if (position == TAKETHEDISC) {

            Utilities.putValueInEditor(this).putString("title", "Take the DISC").commit();
            Utilities.putValueInEditor(this).putString("url", "https://www.lizbentley.com/disc-assessments").commit();
            Fragment selectedScreen = WebviewFragment.createFor(screenTitles[position]);
            showFragment(selectedScreen);
        } else if (position == CONTACT_US) {
            Fragment selectedScreen = ContactUsFragment.createFor(screenTitles[position]);
            showFragment(selectedScreen);
        }
        slidingRootNav.closeMenu();

    }


    private void showFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
    }
}