package com.example.awesomegaminghub.ui.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.awesomegaminghub.MainActivity;
import com.example.awesomegaminghub.data.model.LoggedInUser;
import com.example.awesomegaminghub.databinding.FragmentLoginBinding;

import com.example.awesomegaminghub.R;
import com.example.awesomegaminghub.entities.Account;
import com.google.gson.Gson;

public class fragment_login extends Fragment {

    private LoginViewModel loginViewModel;
    private FragmentLoginBinding binding;
    private Context context;
    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;

    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private Button registerButton;
    private ProgressBar loadingProgressBar;
    private Account loggedInAccount;
    private int count;

    private Handler handler = new Handler();
    private Runnable runnable;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        ((MainActivity)getActivity()).resetAccount();
        loginViewModel = new ViewModelProvider(this, new LoginViewModelFactory())
                .get(LoginViewModel.class);
        binding = FragmentLoginBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        final TextView textView = binding.loginTextView;
        loginViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        context = getActivity();
        sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        editor = sharedPref.edit();
        loginViewModel.welcomeMsg();

        return root;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        usernameEditText = binding.username;
        passwordEditText = binding.password;
        loginButton = binding.login;
        registerButton = binding.register;
        loadingProgressBar = binding.loading;

        passwordEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean focused) {
                InputMethodManager keyboard = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                if (focused)
                    keyboard.showSoftInput(passwordEditText, 0);
                else
                    keyboard.hideSoftInputFromWindow(passwordEditText.getWindowToken(), 0);
            }
        });

        loginViewModel.getLoginFormState().observe(getViewLifecycleOwner(), new Observer<LoginFormState>() {
            @Override
            public void onChanged(@Nullable LoginFormState loginFormState) {
                if (loginFormState == null) {
                    return;
                }
                loginButton.setEnabled(loginFormState.isDataValid());
                registerButton.setEnabled(loginFormState.isDataValid());
                if (loginFormState.getUsernameError() != null) {
                    usernameEditText.setError(getString(loginFormState.getUsernameError()));
                }
                if (loginFormState.getPasswordError() != null) {
                    passwordEditText.setError(getString(loginFormState.getPasswordError()));
                }
            }
        });

        loginViewModel.getLoginResult().observe(getViewLifecycleOwner(), new Observer<LoginResult>() {
            @Override
            public void onChanged(@Nullable LoginResult loginResult) {
                if (loginResult == null) {
                    return;
                }
                loadingProgressBar.setVisibility(View.GONE);
                if (loginResult.getError() != null) {
                    showLoginFailed(loginResult.getError());
                }
                if (loginResult.getSuccess() != null) {
                    updateUiWithUser(loginResult.getSuccess());
                }
            }
        });

        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
                loginViewModel.loginDataChanged(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
        };
        usernameEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    loginViewModel.login(usernameEditText.getText().toString(),
                            passwordEditText.getText().toString());
                }
                return false;
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingProgressBar.setVisibility(View.VISIBLE);
                getDataLogin(view);
                /*Account loggedInAccount = ((MainActivity)getActivity()).login(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());
                if(loggedInAccount != null){
                    Gson gson = new Gson();
                    String json = gson.toJson(loggedInAccount);
                    editor.putString("loggedUser",json);
                    editor.apply();
                    Navigation.findNavController(view).navigate(R.id.action_username_to_nav_home);
                }
                else{
                    loadingProgressBar.setVisibility(View.INVISIBLE);
                    loginViewModel.loginFailed();
                }*/
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingProgressBar.setVisibility(View.VISIBLE);
                getDataCreate(view);
                //Account loggedInAccount = ((MainActivity)getActivity()).create(usernameEditText.getText().toString(),
                //        passwordEditText.getText().toString());
                //if(loggedInAccount != null){
                //    Gson gson = new Gson();
                //    String json = gson.toJson(loggedInAccount);
                //    editor.putString("loggedUser",json);
                //    editor.apply();
                //    Navigation.findNavController(view).navigate(R.id.action_username_to_nav_home);
                //}
                //else{
                //    loadingProgressBar.setVisibility(View.INVISIBLE);
                //    loginViewModel.loginFailed();
                //}
            }
        });
    }

    private void getDataLogin(View view){
        int delay = 100;

        count = 0;
        handler.postDelayed( runnable = new Runnable() {
            public void run() {
                loggedInAccount = ((MainActivity)getActivity()).login(usernameEditText.getText().toString(),
                                passwordEditText.getText().toString());
                if(count < 3){
                    count = count + 1;
                    handler.postDelayed(runnable, delay);
                }
                else if(loggedInAccount == null){
                    loadingProgressBar.setVisibility(View.INVISIBLE);
                    loginViewModel.loginFailed();
                }
                else{
                    Gson gson = new Gson();
                    String json = gson.toJson(loggedInAccount);
                    editor.putString("loggedUser",json);
                    editor.apply();
                    Navigation.findNavController(view).navigate(R.id.action_username_to_nav_home);
                }

            }
        }, delay);

        super.onResume();
    }

    private void getDataCreate(View view){
        int delay = 100;

        count = 0;
        handler.postDelayed( runnable = new Runnable() {
            public void run() {
                Account loggedInAccount = ((MainActivity)getActivity()).create(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());
                loggedInAccount = ((MainActivity)getActivity()).getCreated();
                if(count < 3){
                    count = count + 5;
                    handler.postDelayed(runnable, delay);
                }
                else if(loggedInAccount == null){
                    loadingProgressBar.setVisibility(View.INVISIBLE);
                    loginViewModel.loginFailed();
                }
                else{
                    Gson gson = new Gson();
                    String json = gson.toJson(loggedInAccount);
                    editor.putString("loggedUser",json);
                    editor.apply();
                    Navigation.findNavController(view).navigate(R.id.action_username_to_nav_home);
                }

            }
        }, delay);

        super.onResume();
    }

    private void updateUiWithUser(LoggedInUserView model) {
        String welcome = getString(R.string.welcome) + model.getDisplayName();
        // TODO : initiate successful logged in experience
        if (getContext() != null && getContext().getApplicationContext() != null) {
            Toast.makeText(getContext().getApplicationContext(), welcome, Toast.LENGTH_LONG).show();
        }
    }

    private void showLoginFailed(@StringRes Integer errorString) {
        if (getContext() != null && getContext().getApplicationContext() != null) {
            Toast.makeText(
                    getContext().getApplicationContext(),
                    errorString,
                    Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
    }
    @Override
    public void onStop() {
        super.onStop();
        ((AppCompatActivity)getActivity()).getSupportActionBar().show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        handler.removeCallbacks(runnable);
        binding = null;
    }
}