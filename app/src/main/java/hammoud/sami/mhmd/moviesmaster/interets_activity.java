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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;
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
import java.util.Map;

import static com.google.firebase.auth.FirebaseAuth.getInstance;

public class interets_activity extends AppCompatActivity {
    private Button mButtonInterest;
    private EditText mCountery, mAge;
    private CheckBox mInterestDrama, mInterestAction, mInterestComedy, mInterestHorror, mInterestFamily, mInterestRomance;
    private RelativeLayout relativeLayout;
    private ProgressDialog progressDialog;
    private FirebaseAuth mAuth;
    private Context mContext;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference databaseReference;
    private static final String TAG = "test";
    private  String interest = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interets_activity);

        mButtonInterest = findViewById(R.id.finish_intersest_id);
        mCountery = findViewById(R.id.country_id);
        mAge = findViewById(R.id.age_id);
        mContext = this;
        mInterestDrama = findViewById(R.id.checkbox_drama);
        mInterestAction= findViewById(R.id.checkbox_action);
        mInterestComedy= findViewById(R.id.checkbox_comedy);
        mInterestHorror= findViewById(R.id.checkbox_horrer);
        mInterestFamily= findViewById(R.id.checkbox_family);
        mInterestRomance= findViewById(R.id.checkbox_romance);






        mButtonInterest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Map intersetHasmap = new HashMap();

                if (mInterestDrama.isChecked()) {
                    intersetHasmap.put("drama" , true);
                }
                if (mInterestAction.isChecked()) {
                    intersetHasmap.put("action" , true);
                }
                if (mInterestComedy.isChecked()) {
                    intersetHasmap.put("comedy" , true);
                }
                if (mInterestHorror.isChecked()) {
                    intersetHasmap.put("horror" , true);
                }
                if (mInterestFamily.isChecked()) {
                    intersetHasmap.put("family" , true);
                }
                if (mInterestRomance.isChecked()) {
                    intersetHasmap.put("romance" , true);
                }


               FirebaseUser firebaseUser = getInstance().getCurrentUser();
                String userID = firebaseUser.getUid();
                String email = firebaseUser.getEmail();


                FirebaseAuth auth = getInstance();
                FirebaseUser user = auth.getCurrentUser();

                String country = mCountery.getText().toString();
                String age = mAge.getText().toString();

                DatabaseReference users_Reference = FirebaseDatabase.getInstance().getReference();
                databaseReference = users_Reference.child("users").child(userID);;


//              DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

                Map  user_info_map = new HashMap<>();
                user_info_map.put("country",country );
                user_info_map.put("age", age);
                user_info_map.put("interest", intersetHasmap);

                databaseReference.updateChildren(user_info_map).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(mContext, "Registration success", Toast.LENGTH_SHORT).show();
                            Intent loggetIntent = new Intent(mContext, Home.class);
                            loggetIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(loggetIntent);
                            finish();

                        } else {

                        }
                    }
                });


                Toast.makeText(mContext, ""+userID, Toast.LENGTH_SHORT).show();
                Toast.makeText(mContext, ""+email, Toast.LENGTH_SHORT).show();
                Toast.makeText(mContext, ""+user, Toast.LENGTH_SHORT).show();

            }
        });

    }


}
