package hammoud.sami.mhmd.moviesmaster;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class SignUp extends AppCompatActivity {

    private TextView LoginText;
    private Context mContext;
    private EditText FullName ;
    private EditText Email ;
    private EditText Password ;
    private Button Continue ;

    private FirebaseAuth mAuth;
    private ProgressDialog progressDialog;
    private RelativeLayout relativeLayout;
    private static final String TAG = "test";

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        LoginText = findViewById(R.id.have_account);
        mContext = this;
        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = mFirebaseDatabase.getReference();

        FullName = findViewById(R.id.user_name_id);
        Email= findViewById(R.id.email_sign_up_id);
        Password= findViewById(R.id.password_sign_up_id);
        Continue=findViewById(R.id.continue_id);

Continue.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        progressDialog = new ProgressDialog(mContext);
        progressDialog.setTitle("Logging In");
        progressDialog.setMessage("Please wait a second");
        progressDialog.setCanceledOnTouchOutside(false);

        final String full_name = FullName.getText().toString();
        String email = Email.getText().toString();
        String password = Password.getText().toString();


        if (!(TextUtils.isEmpty(full_name)) && !(TextUtils.isEmpty(email)) && !(TextUtils.isEmpty(password)&&
                ! (TextUtils.isEmpty(email))) ){
            progressDialog.setTitle("Registering user");
            progressDialog.setMessage("Please waite a second");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
            register_user(full_name, email, password);


        } else {

            Snackbar snackbar = Snackbar.make(relativeLayout
                    , "Please Make sure you filled all spaces", Snackbar.LENGTH_LONG);
            snackbar.show();

        }



    }
});


        LoginText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext,Login.class));
            }
        });
    }

    private void register_user(final String display_name, final String email, final String password) {

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {


                    FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                    String userID = firebaseUser.getUid();


                    DatabaseReference users_Reference = databaseReference.child("users").child(userID);

                    HashMap<String, String> user_info_map = new HashMap<>();
                    user_info_map.put("name",display_name );
                    user_info_map.put("email", email);
                    user_info_map.put("password", password);


                    users_Reference.setValue(user_info_map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {

                                progressDialog.dismiss();
                                Toast.makeText(mContext, "Registration success", Toast.LENGTH_SHORT).show();
                                Intent loggetIntent = new Intent(mContext, interets_activity.class);
                                loggetIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

                                startActivity(loggetIntent);
                                finish();

                            } else {

                            }
                        }
                    });


                } else {



                    progressDialog.hide();

                    Log.d(TAG, "onComplete: "+task.getException().toString());
                    Toast.makeText(mContext, "Some Error happend", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "onFailure: error msg is :"+e);
            }
        });


    }


}
