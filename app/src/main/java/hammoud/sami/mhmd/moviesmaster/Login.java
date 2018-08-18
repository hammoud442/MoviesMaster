package hammoud.sami.mhmd.moviesmaster;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {

    private Button LoginButton;
    private Context mContext;
    private TextView SignUp;
    private EditText email,password;
    private FirebaseAuth mFirebaseAuth;
    private RelativeLayout relativeLayout;
    private ProgressDialog progressDialog;
    private RelativeLayout signInLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        LoginButton = findViewById(R.id.login_id);
        mContext = this;
        SignUp = findViewById(R.id.dont_have_account);
        email = findViewById(R.id.email_sign_in_id);
        password = findViewById(R.id.password_sign_in_id);



        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*startActivity(new Intent(mContext,Home.class));
                Toast.makeText(mContext, "Home Layout", Toast.LENGTH_SHORT).show();*/

                mFirebaseAuth = FirebaseAuth.getInstance();

                progressDialog = new ProgressDialog(mContext);
                progressDialog.setTitle("Logging In");
                progressDialog.setMessage("Please wait a second");
                progressDialog.setCanceledOnTouchOutside(false);

                String emailText = email.getText().toString();
                String passwordText = password.getText().toString();


                if ((!TextUtils.isEmpty(emailText)) && (!TextUtils.isEmpty(passwordText))) {

                    progressDialog.show();
                    loginUser(emailText, passwordText);


                } else {
                    Snackbar snackbar = Snackbar.make(relativeLayout, "Make sure you filled the spaces", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }


                Toast.makeText(mContext, ""+email.getText().toString()+""+password.getText().toString(), Toast.LENGTH_SHORT).show();

            }
        });

        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext,SignUp.class));
            }
        });
    }

    private void loginUser(String email, String password){

        mFirebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()){
                    progressDialog.setMessage("You're ready to go!");
                    progressDialog.dismiss();
                    Intent main = new Intent(mContext,Home.class);
                    main.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

                    startActivity(main);
                    finish();

                }else{

                    progressDialog.dismiss();

                    Snackbar snackbar = Snackbar.make(signInLayout,"Something went Wrong, can't log in",Snackbar.LENGTH_LONG);
                    snackbar.show();

                }
            }
        });



    }
}
