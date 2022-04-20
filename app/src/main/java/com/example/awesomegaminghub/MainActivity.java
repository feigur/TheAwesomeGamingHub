package com.example.awesomegaminghub;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.SearchView;

import com.example.awesomegaminghub.entities.Account;
import com.example.awesomegaminghub.entities.Chat;
import com.example.awesomegaminghub.entities.HighScore;
import com.example.awesomegaminghub.entities.News;
import com.example.awesomegaminghub.networking.NetworkManager;
import com.example.awesomegaminghub.networking.iNetworkCallback;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.awesomegaminghub.databinding.ActivityMainBinding;
import com.google.gson.Gson;

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

    private ImageView profileImg;
    private NavigationView mNavigationView;

    private SharedPreferences mSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        NetworkManager networkManager = NetworkManager.getInstance(this);




        // getNews
        //networkManager.getNews(new iNetworkCallback<News>() {
        //    @Override
        //    public void onSuccess(News result) {
        //        mNews = result;
        //        Log.d(TAG, "Get news : " + mNews.getNews());
        //    }

        //    @Override
        //    public void onFailure(String errorString) {
        //        Log.e(TAG, "Failed to get news: " + errorString);
        //    }
        //});

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
        mNavigationView = (NavigationView) findViewById(R.id.nav_view);
        profileImg = (ImageView) mNavigationView.getHeaderView(0).findViewById(R.id.Profile_image);

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

    public void changeProfilePic(){
        if(profileImg != null){
            ifProfilePic();
        }
        else{
            Log.d(TAG, "onCreate: FFS");
        }
    }

    public void ifProfilePic(){
        mSharedPreferences = this.getPreferences(Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = mSharedPreferences.getString("loggedUser", "");
        Account user = gson.fromJson(json, Account.class);
        Integer photoID = user.getPhotoID();
        if(photoID == 0){
            profileImg.setImageResource(R.drawable.pic_0);
        }
        else if(photoID == 1){
            profileImg.setImageResource(R.drawable.pic_1);
        }
        else if(photoID == 2){
            profileImg.setImageResource(R.drawable.pic_2);
        }
        else if(photoID == 3){
            profileImg.setImageResource(R.drawable.pic_3);
        }
        else if(photoID == 4){
            profileImg.setImageResource(R.drawable.pic_4);
        }
        else if(photoID == 5){
            profileImg.setImageResource(R.drawable.pic_5);
        }
        else if(photoID == 6){
            profileImg.setImageResource(R.drawable.pic_6);
        }
        else if(photoID == 7){
            profileImg.setImageResource(R.drawable.pic_7);
        }
        else if(photoID == 8){
            profileImg.setImageResource(R.drawable.pic_8);
        }
        else if(photoID == 9){
            profileImg.setImageResource(R.drawable.pic_9);
        }


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
        Account returner;
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
        returner = mAccount;
        mAccount = null;
        return returner;
    }
    private Account returner;
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
        if(mAccount != null){
            returner = mAccount;
        }
        return mAccount;
    }

    public Account getCreated(){
        return returner;
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

    public Account setPhotoID(String username,Integer photoID){
        NetworkManager networkManager = NetworkManager.getInstance(this);
        String htmlinfo = "username=" + username + "&photoID=" + photoID.toString();
        networkManager.setPhotoID(htmlinfo, new iNetworkCallback<Account>() {
            @Override
            public void onSuccess(Account result) {
                mAccount = result;
                if(mAccount != null){
                    Log.d(TAG, "Get user photoID set");
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
        List<Account> returner;
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
        returner = users;
        users = null;
        return returner;
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

    public Chat getChat(String username){
        NetworkManager networkManager = NetworkManager.getInstance(this);
        String accountInfo = username;
        Chat returner;
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
        returner = mChat;
        mChat = null;
        return returner;
    }

    public HighScore getHighScore(String gameId){
        NetworkManager networkManager = NetworkManager.getInstance(this);
        HighScore returner;
        networkManager.getHighScore(gameId, new iNetworkCallback<HighScore>() {
            @Override
            public void onSuccess(HighScore result) {
                mHighScore = result;
                Log.d(TAG, "Get chat list: " + mHighScore.getGameName());
            }

            @Override
            public void onFailure(String errorString) {
                Log.e(TAG, "Failed to get Chat: " + errorString);
            }
        });
        returner = mHighScore;
        mHighScore = null;
        return returner;
    }

    public void addHighScore(String username, String score, String gameId){
        NetworkManager networkManager = NetworkManager.getInstance(this);
        networkManager.addHighScore(username,score,gameId, new iNetworkCallback<HighScore>() {
            @Override
            public void onSuccess(HighScore result) {
                Log.d(TAG, "score added");
            }

            @Override
            public void onFailure(String errorString) {
                Log.e(TAG, "Failed to get Chat: " + errorString);
            }
        });
    }

    public void sendChat(String username,String msg){
        NetworkManager networkManager = NetworkManager.getInstance(this);
        String accountInfo = username;
        Chat returner;
        networkManager.sendChat(accountInfo,msg, new iNetworkCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.d(TAG, "sent to chat:" + result);
            }

            @Override
            public void onFailure(String errorString) {
                Log.e(TAG, "Failed to get Chat: " + errorString);
            }
        });
    }

    public void resetAccount(){
        mAccount = null;
    }
}