package com.example.lab1_duanmau.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lab1_duanmau.DAO.NguoiDungDAO;
import com.example.lab1_duanmau.R;
import com.example.lab1_duanmau.model.NguoiDung;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignUpActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    TextInputEditText regUser, regPass, regRePass, redSdt;
    TextInputLayout inputRegUser, inputRegPass, inputRegPass1, inputRegPhone;
    NguoiDungDAO nguoiDungDAO;
    Button btnSignUp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        btnSignUp = findViewById(R.id.btnReg);
        regUser = findViewById(R.id.edtRegUser);
        regPass = findViewById(R.id.edtRegPassword);
        regRePass = findViewById(R.id.edtRegRePassword);
        redSdt = findViewById(R.id.edtRegSdt);
        inputRegUser = findViewById(R.id.inputRegUser);
        inputRegPhone = findViewById(R.id.inputRegPhone);
        inputRegPass = findViewById(R.id.inputRegpass);
        inputRegPass1 = findViewById(R.id.inputRegpass1);
        regPass.addTextChangedListener(new ValidationTextWatcher(regPass));
        regUser.addTextChangedListener(new ValidationTextWatcher(regUser));

        nguoiDungDAO = new NguoiDungDAO(SignUpActivity.this);
        mAuth = FirebaseAuth.getInstance();

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateEmail() & validatePassword() == true){
                    final String email= regUser.getText().toString();
                    final String password = regPass.getText().toString();
                    final String sdt = redSdt.getText().toString();
                    mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Log.v(email,password);
                                    NguoiDung nguoiDung = new NguoiDung(email,password,sdt);
                                    nguoiDungDAO.insert(nguoiDung);
                                    Toast.makeText(SignUpActivity.this, "Đăng kí thành công",
                                            Toast.LENGTH_SHORT).show();
                                    Intent i = new Intent(SignUpActivity.this,LoginActivity.class);
                                    startActivity(i);

                                } else {
                                    Log.v(email,password);
                                    Toast.makeText(SignUpActivity.this, "Nhập đúng định dạng email, mật khẩu 6 kí tự",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                }

            }
        });
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private boolean validatePassword() {
        if (regPass.getText().toString().trim().isEmpty()) {
            inputRegPass.setError("Bắt buộc nhập mật khẩu");
            requestFocus(regPass);
            return false;
        }else if(regPass.getText().toString().length() < 6){
            inputRegPass.setError("Mật khẩu phải là 6 ký tự");
            requestFocus(regPass);
            return false;
        }else if(regPass.getText().toString().length() > 6) {
            inputRegPass.setError("Mật khẩu không quá 6 ký tự");
            requestFocus(regPass);
            return false;
        }else {
            inputRegPass.setErrorEnabled(false);
        }
        return true;
    }

    private boolean validateEmail() {
        if (regUser.getText().toString().trim().isEmpty()) {
            inputRegUser.setError("Bắt buộc nhập mật Email");
            requestFocus(regUser);
            return false;
        } else {
            String emailId = regUser.getText().toString();
            Boolean  isValid = android.util.Patterns.EMAIL_ADDRESS.matcher(emailId).matches();
            if (!isValid) {
                inputRegUser.setError("Sai định dạng Email, ex: abc@example.com");
                requestFocus(regUser);
                return false;
            } else {
                inputRegUser.setErrorEnabled(false);
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
                case R.id.edtRegUser:
                    validateEmail();
                    break;
                case R.id.edtRegPassword:
                    validatePassword();
                    break;
            }
        }
    }
}
