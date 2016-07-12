package com.esgi.newsme.newsme.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ButtonBarLayout;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.esgi.newsme.newsme.Models.User;
import com.esgi.newsme.newsme.R;
import com.esgi.newsme.newsme.SQL.DAO.UserDAO;
import com.esgi.newsme.newsme.Utils.UserUtils;

public class LoginActivity extends AppCompatActivity {

    EditText emailEditText;
    EditText passwordEditText;
    Button connexionButton;
    Button registerButton;
    View layout;

    UserDAO userDAO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        assignViews();
        setTitle("Connexion");

        userDAO = new UserDAO(this);
        userDAO.open();

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,InscriptionActivity.class);
                startActivity(intent);

            }
        });

        connexionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              User user =  userDAO.getUser(emailEditText.getText().toString(),passwordEditText.getText().toString());
              Intent intent = new Intent(LoginActivity.this,MainActivity.class);

                if(!emailEditText.getText().toString().isEmpty() &&
                        !passwordEditText.getText().toString().isEmpty()){

                    if( user!=null) {
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);

                        //save user infos
                        UserUtils.saveUserInfo(user,getApplicationContext());
                    } else{
                        Snackbar snackbar = Snackbar.make(layout, R.string.bad_login_error, Snackbar.LENGTH_LONG);
                        snackbar.show();
                    }

                }else{
                    Snackbar snackbar = Snackbar.make(layout, R.string.field_empty_error, Snackbar.LENGTH_LONG);
                    snackbar.show();
                }

            }
        });

    }

    public void assignViews(){
        emailEditText = (EditText) findViewById(R.id.editTextIdentifiant);
        passwordEditText = (EditText) findViewById(R.id.editTextPassword);
        connexionButton = (Button) findViewById(R.id.buttonConnexion);
        registerButton = (Button) findViewById(R.id.buttonRegister);
        layout = findViewById(R.id.loginLayout);

    }

}
