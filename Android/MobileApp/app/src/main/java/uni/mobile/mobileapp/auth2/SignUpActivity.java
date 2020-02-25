package uni.mobile.mobileapp.auth2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import uni.mobile.mobileapp.R;

public class SignUpActivity extends AppCompatActivity {

    private EditText SignUpMail, SignUpPass, SignUpConfirmPass;
    private MaterialButton SignUpButton;
    private FirebaseAuth auth;

    private static final int STORAGE_PERMISSION_CODE = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // Permission
        if (ContextCompat.checkSelfPermission(SignUpActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)  == PackageManager.PERMISSION_DENIED) {
            // Requesting the permission
            ActivityCompat.requestPermissions(SignUpActivity.this, new String[] { Manifest.permission.WRITE_EXTERNAL_STORAGE }, STORAGE_PERMISSION_CODE);
        }

        SignUpMail = findViewById(R.id.SignUpMail);
        SignUpPass = findViewById(R.id.SignUpPass);
        SignUpConfirmPass = findViewById(R.id.SignUpConfirmPass);
        auth = FirebaseAuth.getInstance();
        SignUpButton = findViewById(R.id.SignUpButton);

        SignUpButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                String email = SignUpMail.getText().toString();
                String pass = SignUpPass.getText().toString();
                String confirmPass = SignUpConfirmPass.getText().toString();

                if(TextUtils.isEmpty(email)){
                    Toast.makeText(getApplicationContext(),"Please enter your E-mail address",Toast.LENGTH_LONG).show();
                    return;
                }
                if(TextUtils.isEmpty(pass)){
                    Toast.makeText(getApplicationContext(),"Please enter your Password",Toast.LENGTH_LONG).show();
                    return;
                }
                if(TextUtils.isEmpty(confirmPass)){
                    Toast.makeText(getApplicationContext(),"Please confirm your Password",Toast.LENGTH_LONG).show();
                    return;
                }
                if (pass.length() == 0){
                    Toast.makeText(getApplicationContext(),"Please enter your Password",Toast.LENGTH_LONG).show();
                    return;
                }
                if (confirmPass.length() == 0){
                    Toast.makeText(getApplicationContext(),"Please confirm your Password",Toast.LENGTH_LONG).show();
                    return;
                }
                if (pass.length()<8){
                    Toast.makeText(getApplicationContext(),"Password must be more than 8 digit",Toast.LENGTH_LONG).show();
                    return;
                }
                if (!pass.equals(confirmPass)) {
                    Toast.makeText(getApplicationContext(),"Passwords must be equals",Toast.LENGTH_LONG).show();
                    return;
                }
                else{
                    auth.createUserWithEmailAndPassword(email,pass)
                            .addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                                public void onComplete(@NonNull Task<AuthResult> task) {

                                    if (!task.isSuccessful()) {
                                        Toast.makeText(SignUpActivity.this, "ERROR",Toast.LENGTH_LONG).show();
                                    }
                                    else {
                                        startActivity(new Intent(SignUpActivity.this, EditProfileActivity.class));
                                        finish();
                                    }
                                }
                            });}
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(SignUpActivity.this, "Storage Permission Granted", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(SignUpActivity.this, "Storage Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void navigateSignIn(View v){
        Intent intent = new Intent(this, SignInActivity.class);
        startActivity(intent);
        finish();
    }
}