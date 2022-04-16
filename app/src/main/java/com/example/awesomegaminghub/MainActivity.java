package com.example.awesomegaminghub;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.view.WindowManager;

import com.example.awesomegaminghub.entities.Account;
import com.example.awesomegaminghub.entities.Chat;
import com.example.awesomegaminghub.entities.HighScore;
import com.example.awesomegaminghub.entities.News;
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
    private Chat mChat;
    private Account mAccount;
    private News mNews;
    private HighScore mHighScore;
    private List<Account> users;

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        String accountInfo = "admin";

        NetworkManager networkManager = NetworkManager.getInstance(this);

        // getChat
        networkManager.getChat(accountInfo, new iNetworkCallback<Chat>() {
            @Override
            public void onSuccess(Chat result) {
                mChat = result;
                Log.d(TAG, "Get chat list: " + mChat.getChat());
            }

            @Override
            public void onFailure(String errorString) {
                Log.e(TAG, "Failed to get Chat: " + errorString);
            }
        });

        // getNews
        networkManager.getNews(new iNetworkCallback<News>() {
            @Override
            public void onSuccess(News result) {
                mNews = result;
                Log.d(TAG, "Get news : " + mNews.getNews());
            }

            @Override
            public void onFailure(String errorString) {
                Log.e(TAG, "Failed to get news: " + errorString);
            }
        });

        networkManager.getHighScore(new iNetworkCallback<HighScore>() {
            @Override
            public void onSuccess(HighScore result) {
                mHighScore = result;
                Log.d(TAG, "Get HighScore : " + mHighScore.getHighscores());
            }

            @Override
            public void onFailure(String errorString) {
                Log.e(TAG, "Failed to get highscore: " + errorString);
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

    public void enableChat(){
        Menu menu = binding.navView.getMenu();
        MenuItem item = menu.getItem(1);
        item.setVisible(true);
        item.setEnabled(true);
    }

    public void disableChat(){
        Menu menu = binding.navView.getMenu();
        MenuItem item = menu.getItem(1);
        item.setVisible(false);
        item.setEnabled(false);
    }

    public Account login(String username, String password){
        NetworkManager networkManager = NetworkManager.getInstance(this);
        // getAccount
        String loginInfo = "username=" + username + "&password=" + password;
        networkManager.getAccount(loginInfo, new iNetworkCallback<Account>() {
            @Override
            public void onSuccess(Account result) {
                mAccount = result;
                if(mAccount != null){
                    Log.d(TAG, "Get user : " + mAccount.getUsername() + " isAdmin: " +  mAccount.getAdmin());
                }

            }

            @Override
            public void onFailure(String errorString) {
                mAccount = null;
                Log.e(TAG, "Failed to get user: " + errorString);
            }
        });
        return mAccount;
    }

    public Account create(String username, String password){
        NetworkManager networkManager = NetworkManager.getInstance(this);
        // getAccount
        String loginInfo = "username=" + username + "&password=" + password;
        networkManager.createAccount(loginInfo, new iNetworkCallback<Account>() {
            @Override
            public void onSuccess(Account result) {
                mAccount = result;
                if(mAccount != null){
                    Log.d(TAG, "Get user : " + mAccount.getUsername() + " isAdmin: " +  mAccount.getAdmin());
                }

            }

            @Override
            public void onFailure(String errorString) {
                mAccount = null;
                Log.e(TAG, "Failed to get user: " + errorString);
            }
        });
        return mAccount;
    }

    public Account setAdmin(String username){
        NetworkManager networkManager = NetworkManager.getInstance(this);
        String loginInfo = "username=" + username;
        networkManager.setAdmin(loginInfo, new iNetworkCallback<Account>() {
            @Override
            public void onSuccess(Account result) {
                mAccount = result;
                if(mAccount != null){
                    Log.d(TAG, "Get user : " + mAccount.getUsername() + " isAdmin: " +  mAccount.getAdmin());
                }

            }

            @Override
            public void onFailure(String errorString) {
                mAccount = null;
                Log.e(TAG, "Failed to get user: " + errorString);
            }
        });
        return mAccount;
    }

    public Account setNotAdmin(String username){
        NetworkManager networkManager = NetworkManager.getInstance(this);
        String loginInfo = "username=" + username;
        networkManager.setNotAdmin(loginInfo, new iNetworkCallback<Account>() {
            @Override
            public void onSuccess(Account result) {
                mAccount = result;
                if(mAccount != null){
                    Log.d(TAG, "Get user : " + mAccount.getUsername() + " isAdmin: " +  mAccount.getAdmin());
                }

            }

            @Override
            public void onFailure(String errorString) {
                mAccount = null;
                Log.e(TAG, "Failed to get user: " + errorString);
            }
        });
        return mAccount;
    }

    public Account setMuted(String admin, String username){
        NetworkManager networkManager = NetworkManager.getInstance(this);
        String loginInfo = "username=" + admin + "&mute=" + username;
        networkManager.setMuted(loginInfo, new iNetworkCallback<Account>() {
            @Override
            public void onSuccess(Account result) {
                mAccount = result;
                if(mAccount != null){
                    Log.d(TAG, "Get user : " + mAccount.getUsername() + " isAdmin: " +  mAccount.getAdmin());
                }

            }

            @Override
            public void onFailure(String errorString) {
                mAccount = null;
                Log.e(TAG, "Failed to get user: " + errorString);
            }
        });
        return mAccount;
    }

    public Account setUnMuted(String admin, String username){
        NetworkManager networkManager = NetworkManager.getInstance(this);
        String loginInfo = "username=" + admin + "&mute=" + username;
        networkManager.setUnMuted(loginInfo, new iNetworkCallback<Account>() {
            @Override
            public void onSuccess(Account result) {
                mAccount = result;
                if(mAccount != null){
                    Log.d(TAG, "Get user : " + mAccount.getUsername() + " isAdmin: " +  mAccount.getAdmin());
                }

            }

            @Override
            public void onFailure(String errorString) {
                mAccount = null;
                Log.e(TAG, "Failed to get user: " + errorString);
            }
        });
        return mAccount;
    }




    public List<Account> getAccounts(){
        NetworkManager networkManager = NetworkManager.getInstance(this);
        networkManager.getAccounts(new iNetworkCallback<List<Account>>() {
            @Override
            public void onSuccess(List<Account> result) {
                users = result;
                if(users != null){
                    Log.d(TAG,"Succsess");
                }

            }

            @Override
            public void onFailure(String errorString) {
                users = null;
                Log.e(TAG, "Failed to get user: " + errorString);
            }
        });
        return users;
    }

    public void deleteAccount(String admin, String username){
        NetworkManager networkManager = NetworkManager.getInstance(this);
        String deleteInfo = "admin=" + admin + "&account=" + username;
        networkManager.DeleteUser(deleteInfo, new iNetworkCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.d(TAG,"Succsess");

            }

            @Override
            public void onFailure(String errorString) {
                users = null;
                Log.e(TAG, "Failed to get user: " + errorString);
            }
        });
    }

    public void resetAccount(){
        mAccount = null;
    }
}