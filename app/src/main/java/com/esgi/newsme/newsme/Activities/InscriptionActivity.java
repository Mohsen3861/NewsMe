package com.esgi.newsme.newsme.Activities;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.esgi.newsme.newsme.Models.User;
import com.esgi.newsme.newsme.R;
import com.esgi.newsme.newsme.SQL.DAO.UserDAO;
import com.esgi.newsme.newsme.Utils.UserUtils;

public class InscriptionActivity extends AppCompatActivity {

    EditText nomEditText;
    EditText prenomEditText;
    EditText emailEditText;
    EditText passEditText;
    EditText confPassEditText;
    Button valideButton;
    View layout;
    UserDAO userDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription);

        layout = findViewById(R.id.inscriptionLayout);

        setTitle("Inscription");
        assignViews();

        userDAO = new UserDAO(this);
        userDAO.open();

        valideButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveUser();
            }
        });

    }


    public void assignViews() {

        nomEditText = (EditText) findViewById(R.id.editTextNom);
        prenomEditText = (EditText) findViewById(R.id.editTextPrenom);
        emailEditText = (EditText) findViewById(R.id.editTextEmail);
        passEditText = (EditText) findViewById(R.id.editTextPassword);
        confPassEditText = (EditText) findViewById(R.id.editTextConfPassword);
        valideButton = (Button) findViewById(R.id.buttonValid);

    }

    public void saveUser() {
        User user = new User();

        user.setEmail(emailEditText.getText().toString());
        user.setNom(nomEditText.getText().toString());
        user.setPrenom(prenomEditText.getText().toString());
        user.setMdp(passEditText.getText().toString());

        if (validate()) {
            if (!passEditText.getText().toString().equals(confPassEditText.getText().toString())) {
                Snackbar snackbar = Snackbar.make(layout, R.string.password_error, Snackbar.LENGTH_LONG);
                snackbar.show();
            } else {
                if(verifyUser(user)) {
                    userDAO.add(user);
                    Intent intent = new Intent(InscriptionActivity.this, MainActivity.class);

                    //save user infos
                    User userSQL = userDAO.getUser(user.getEmail(), user.getMdp());
                    UserUtils.saveUserInfo(userSQL, getApplicationContext());

                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);


                }else{
                    Snackbar snackbar = Snackbar.make(layout, R.string.email_used, Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
            }

        } else {
            Snackbar snackbar = Snackbar.make(layout, R.string.field_empty_error, Snackbar.LENGTH_LONG);
            snackbar.show();
        }

    }

    public boolean verifyUser(User u){
        User user = userDAO.getUser(u.getEmail(),u.getMdp());
        if(user != null && !user.getId().isEmpty())
            return false;
        else
            return true;
    }

    public boolean validate(){
        if(emailEditText.getText().toString().isEmpty())
            return  false;
        if(nomEditText.getText().toString().isEmpty())
            return  false;
        if(prenomEditText.getText().toString().isEmpty())
            return  false;
        if(passEditText.getText().toString().isEmpty())
            return  false;
        if(confPassEditText.getText().toString().isEmpty())
            return  false;

        return true;

    }
}
