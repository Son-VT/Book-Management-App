package com.example.lab1_duanmau.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lab1_duanmau.MainActivity;
import com.example.lab1_duanmau.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    Button btnLogin ,btndk;
    private FirebaseAuth mAuth;
    TextInputLayout inputEmail,inputPass;
    TextInputEditText edtemail, edtpassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        edtemail = findViewById(R.id.edtUserName);
        edtpassword = findViewById(R.id.edtPassword);
        inputEmail = findViewById(R.id.inputEmail);
        inputPass = findViewById(R.id.inputPass);
        btndk = findViewById(R.id.btnRegister);
        btnLogin = findViewById(R.id.btnLogin);
        mAuth = FirebaseAuth.getInstance();
        edtemail.addTextChangedListener(new LoginActivity.ValidationTextWatcher(edtemail));
        edtpassword.addTextChangedListener(new LoginActivity.ValidationTextWatcher(edtpassword));
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateEmail() & validatePassword() == true){
                String email = edtemail.getText().toString();
                String pass = edtpassword.getText().toString();
                mAuth.signInWithEmailAndPassword(email, pass)
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(LoginActivity.this, "????ng nh???p th??nh c??ng",
                                            Toast.LENGTH_SHORT).show();
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    Intent i = new Intent(LoginActivity.this, MainActivity.class);
                                    startActivity(i);
                                    finish();
                                } else {
                                    Toast.makeText(LoginActivity.this, "????ng nh???p th???t b???i",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

            }
            }
        });
        btndk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(i);
            }
        });
    }
    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }
    private boolean validatePassword() {
        if (edtpassword.getText().toString().trim().isEmpty()) {
            inputPass.setError("B???t bu???c nh???p m???t kh???u");
            requestFocus(edtpassword);
            return false;
        }else if(edtpassword.getText().toString().length() < 6){
            inputPass.setError("M???t kh???u ph???i l?? 6 k?? t???");
            requestFocus(edtpassword);
            return false;
        }else if(edtpassword.getText().toString().length() > 6) {
            inputPass.setError("M???t kh???u kh??ng qu?? 6 k?? t???");
            requestFocus(edtpassword);
            return false;
        }else {
            inputPass.setErrorEnabled(false);
        }
        return true;
    }

    private boolean validateEmail() {
        if (edtemail.getText().toString().trim().isEmpty()) {
            inputEmail.setError("B???t bu???c nh???p m???t Email");
            requestFocus(edtemail);
            return false;
        } else {
            String emailId = edtemail.getText().toString();
            Boolean  isValid = android.util.Patterns.EMAIL_ADDRESS.matcher(emailId).matches();
            if (!isValid) {
                inputEmail.setError("Sai ?????nh d???ng Email, ex: abc@example.com");
                requestFocus(edtemail);
                return false;
            } else {
                inputEmail.setErrorEnabled(false);
            }
        }
        return true;
    }
    private class ValidationTextWatcher implements TextWatcher {

        private View view;

        private ValidationTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
                case R.id.edtUserName:
                    validateEmail();
                    break;
                case R.id.edtPassword:
                    validatePassword();
                    break;
            }
        }
    }

}