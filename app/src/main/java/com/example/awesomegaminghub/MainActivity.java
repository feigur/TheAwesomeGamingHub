package com.example.awesomegaminghub;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;

import com.example.awesomegaminghub.entities.Chat;
import com.example.awesomegaminghub.networking.NetworkManager;
import com.example.awesomegaminghub.networking.iNetworkCallback;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.awesomegaminghub.databinding.ActivityMainBinding;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    private List<Chat> mTest;

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Prófa að sækja gögn frá bakenda
        NetworkManager networkManager = NetworkManager.getInstance(this);
        networkManager.getRecipes(new iNetworkCallback<List<Chat>>() {
            @Override
            public void onSuccess(List<Chat> result) {
                mTest = result;
                Log.d(TAG, "First recipe in list: " + mTest.get(0).getUsername());
            }

            @Override
            public void onFailure(String errorString) {
                Log.e(TAG, "Failed to get Recipes: " + errorString);
            }
        });

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.appBarMain.toolbar);
        binding.appBarMain.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_chat, R.id.nav_news,R.id.nav_admin_settings,R.id.nav_user_settings)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        //  store the menu to var when creating options menu
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public void setAdmin(){
        Menu menu = binding.navView.getMenu();
        int len = menu.size();
        MenuItem item = menu.getItem(len-1);
        item.setVisible(true);
        item.setEnabled(true);
    }

    public void disableAdmin(){
        Menu menu = binding.navView.getMenu();
        int len = menu.size();
        MenuItem item = menu.getItem(len-1);
        item.setVisible(false);
        item.setEnabled(false);
    }
}