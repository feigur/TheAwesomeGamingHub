package com.example.awesomegaminghub.networking;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.awesomegaminghub.entities.Account;
import com.example.awesomegaminghub.entities.Chat;
import com.example.awesomegaminghub.entities.HighScore;
import com.example.awesomegaminghub.entities.News;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.FormatFlagsConversionMismatchException;
import java.util.List;

public class NetworkManager {
    private static final String BASE_URL = "http://10.0.2.2:8080/";

    private static NetworkManager mInstance;
    private static RequestQueue mQueue;
    private Context mContext;

    private static final String TAG = "NetworkManager1";

    public static synchronized NetworkManager getInstance(Context context) {
        if(mInstance == null) {
            mInstance = new NetworkManager(context);
        }
        return mInstance;
    }

    private NetworkManager(Context context) {
        mContext = context;
        mQueue = getRequestQueue();
    }

    public RequestQueue getRequestQueue() {
        if(mQueue == null) {
            mQueue = Volley.newRequestQueue(mContext.getApplicationContext());
        }

        return mQueue;
    }


    public void getAccount(String accInfo, final iNetworkCallback<Account> callback) {
        String addToUrl = "account/login?" + accInfo;
        StringRequest request = new StringRequest(
                Request.Method.GET, BASE_URL + addToUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                if(response != null){
                    Account account = gson.fromJson(response, Account.class);
                    callback.onSuccess(account);
                }
                else{
                    callback.onFailure("No account found");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onFailure(error.toString());
            }
        }
        );
        mQueue.add(request);
    }
    public void createAccount(String accInfo, final iNetworkCallback<Account> callback) {
        String addToUrl = "account/create?" + accInfo;
        StringRequest request = new StringRequest(
                Request.Method.GET, BASE_URL + addToUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                if(response != null){
                    Account account = gson.fromJson(response, Account.class);
                    callback.onSuccess(account);
                }
                else{
                    callback.onFailure("No account found");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onFailure(error.toString());
            }
        }
        );
        mQueue.add(request);
    }

    public void getChat(String accInfo, final iNetworkCallback<Chat> callback) {
        String addToUrl = "chat/saekja?username=" + accInfo;
        StringRequest request = new StringRequest(
                Request.Method.GET, BASE_URL + addToUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                Chat chat = gson.fromJson(response, Chat.class);
                callback.onSuccess(chat);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onFailure(error.toString());
            }
        }
        );
        mQueue.add(request);
    }

    public void sendChat(String accInfo, String msg, final iNetworkCallback<String> callback) {
        String addToUrl = "chat/add?username=" + accInfo+"&msg="+msg;
        StringRequest request = new StringRequest(
                Request.Method.GET, BASE_URL + addToUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                callback.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onFailure(error.toString());
            }
        }
        );
        mQueue.add(request);
    }

    public void getNews(final iNetworkCallback<News> callback) {
        String addToUrl = "news/saekja";
        StringRequest request = new StringRequest(
                Request.Method.GET, BASE_URL + addToUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                News news = gson.fromJson(response, News.class);
                callback.onSuccess(news);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onFailure(error.toString());
            }
        }
        );
        mQueue.add(request);
    }

    public void getHighScore(final iNetworkCallback<HighScore> callback) {
        String addToUrl = "highscore/saekja?gameId=1";
        StringRequest request = new StringRequest(
                Request.Method.GET, BASE_URL + addToUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                HighScore highScore = gson.fromJson(response, HighScore.class);
                callback.onSuccess(highScore);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onFailure(error.toString());
            }
        }
        );
        mQueue.add(request);
    }

    public void getAccounts(final iNetworkCallback<List<Account>> callback) {
        String addToUrl = "account/findAll";
        StringRequest request = new StringRequest(
                Request.Method.GET, BASE_URL + addToUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                Log.d(TAG,response);
                Type listType = new TypeToken<List<Account>>(){}.getType();
                List<Account> accounts = gson.fromJson(response,listType);
                callback.onSuccess(accounts);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onFailure(error.toString());
            }
        }
        );
        mQueue.add(request);
    }

    public void setAdmin(String accInfo, final iNetworkCallback<Account> callback) {
        String addToUrl = "account/setadmin?" + accInfo;
        StringRequest request = new StringRequest(
                Request.Method.GET, BASE_URL + addToUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                if(response != null){
                    Account account = gson.fromJson(response, Account.class);
                    callback.onSuccess(account);
                }
                else{
                    callback.onFailure("No account found");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onFailure(error.toString());
            }
        }
        );
        mQueue.add(request);
    }

    public void setNotAdmin(String accInfo, final iNetworkCallback<Account> callback) {
        String addToUrl = "account/setuadmin?" + accInfo;
        StringRequest request = new StringRequest(
                Request.Method.GET, BASE_URL + addToUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                if(response != null){
                    Account account = gson.fromJson(response, Account.class);
                    callback.onSuccess(account);
                }
                else{
                    callback.onFailure("No account found");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onFailure(error.toString());
            }
        }
        );
        mQueue.add(request);
    }

    public void setMuted(String accInfo, final iNetworkCallback<Account> callback) {
        String addToUrl = "account/setmuted?" + accInfo;
        StringRequest request = new StringRequest(
                Request.Method.GET, BASE_URL + addToUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                if(response != null){
                    Account account = gson.fromJson(response, Account.class);
                    callback.onSuccess(account);
                }
                else{
                    callback.onFailure("No account found");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onFailure(error.toString());
            }
        }
        );
        mQueue.add(request);
    }

    public void setUnMuted(String accInfo, final iNetworkCallback<Account> callback) {
        String addToUrl = "account/setunmuted?" + accInfo;
        StringRequest request = new StringRequest(
                Request.Method.GET, BASE_URL + addToUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                if(response != null){
                    Account account = gson.fromJson(response, Account.class);
                    callback.onSuccess(account);
                }
                else{
                    callback.onFailure("No account found");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onFailure(error.toString());
            }
        }
        );
        mQueue.add(request);
    }

    public void DeleteUser(String accInfo, final iNetworkCallback<String> callback) {
        String addToUrl = "account/deleteaccount?" + accInfo;
        StringRequest request = new StringRequest(
                Request.Method.GET, BASE_URL + addToUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                callback.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onFailure(error.toString());
            }
        }
        );
        mQueue.add(request);
    }


}