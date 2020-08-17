package com.example.cidemo.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.cidemo.R;
import com.example.cidemo.databinding.ActivityMainBinding;
import com.example.cidemo.other.DatabaseHelper;
import com.example.cidemo.viewmodel.LoginDialogViewModel;

public class LoginDialog extends AppCompatDialogFragment {
    private static final String TAG = "LoginDialog";

    public ActivityMainBinding mActivityMainBinding;
    private LoginDialogViewModel loginDialogViewModel;

    private EditText editTextUrl;
    public String url;
    private String defaultUrl;
    private EditText editTextUserName;
    public String userName;
    private String defaulUserName;
    private EditText editTextPassword;
    public String password;
    private String defaulPassword;
    private Boolean isFirstOpen;

    public LoginDialog(String urlStr, String usernameStr, String passwordStr) {
        defaultUrl = urlStr;
        defaulUserName = usernameStr;
        defaulPassword = passwordStr;
        isFirstOpen = true;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_login, null);

        builder.setView(view)
                .setTitle("");

        editTextUrl = view.findViewById(R.id.edit_url);
        editTextUrl.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                url = editTextUrl != null ? editTextUrl.getText().toString() : "";
            }
        });

        editTextUserName = view.findViewById(R.id.edit_username);
        editTextUserName.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                userName = editTextUserName != null ? editTextUserName.getText().toString() : "";
            }
        });

        editTextPassword = view.findViewById(R.id.edit_password);
        editTextPassword.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                password = editTextPassword != null ? editTextPassword.getText().toString() : "";
            }
        });


        if (isFirstOpen) {
            editTextUrl.setText(defaultUrl);
            editTextUserName.setText(defaulUserName);
            editTextPassword.setText(defaulPassword);
            isFirstOpen = false;
        }

        return builder.create();
    }
}
