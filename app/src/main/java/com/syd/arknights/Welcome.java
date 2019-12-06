package com.syd.arknights;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.navigation.NavigationView;

public class Welcome extends AppCompatActivity {

    private NavigationView navigationView;
    private DrawerLayout draw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        setStatusBar();
        draw=findViewById(R.id.welcome);
        navigationView = findViewById(R.id.navigation_wel);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.action_chouka:
                        changeFragment(new ChouKaFragment());
                        menuItem.setChecked(true);
                        draw.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.action_wisdom:
                        changeFragment(new WisdomFragment());
                        menuItem.setChecked(true);
                        draw.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.action_map:
                        changeFragment(new MapFragment());
                        menuItem.setChecked(true);
                        draw.closeDrawer(GravityCompat.START);
                        break;
                    default:
                        break;
                }
                return false;
            }
        });
        changeFragment(new ChouKaFragment());
        navigationView.getMenu().getItem(0).setChecked(true);

        ViewGroup.LayoutParams params = navigationView.getLayoutParams();
        params.width = getResources().getDisplayMetrics().widthPixels * 2 / 3;
        navigationView.setLayoutParams(params);
    }
    protected void setStatusBar() {
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
    }
    private void changeFragment(Fragment fragment){
        //实例化碎片管理器对象
        FragmentManager fm=getSupportFragmentManager();
        FragmentTransaction ft=fm.beginTransaction().setCustomAnimations(R.anim.slide_top_enter,R.anim.slide_down_exit);
        //选择fragment替换的部分
        ft.replace(R.id.welContent,fragment);
        ft.commit();
    }
}
