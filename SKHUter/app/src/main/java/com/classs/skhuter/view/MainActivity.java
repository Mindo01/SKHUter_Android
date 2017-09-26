package com.classs.skhuter.view;


import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;

import com.classs.skhuter.R;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    ImageView ivLogo;
    DrawerLayout drawer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

    }

    Toolbar.OnMenuItemClickListener menuItemClickListener = new Toolbar.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem item) {
            /*int id = item.getItemId();
            if (id == R.id.menu_filter) {
                drawer.openDrawer(GravityCompat.END);
            }*/
            return true;
        }
    };
}
