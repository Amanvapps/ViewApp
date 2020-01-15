package Views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.viewapp.R;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {

    private CircleImageView profile_image ;
    private Button google_signout_button ;
    private TextView profile_name , profile_username ;
    private GoogleSignInAccount googleSignInAccount ;

    AccessToken accessToken ;
    private boolean isGoogle = false ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        profile_image = (CircleImageView) findViewById(R.id.profile_image);
        profile_name = (TextView)findViewById(R.id.profile_name);
        profile_username = (TextView)findViewById(R.id.profile_username);
        google_signout_button = (Button)findViewById(R.id.google_signout_button);

        accessToken = (AccessToken) getIntent().getExtras().get("access_token");
        googleSignInAccount = getIntent().getParcelableExtra("google_account");


        if(googleSignInAccount!=null)
        {
            isGoogle = true ;
            setDataOnView();
        }


        if(!isGoogle)
        {

            if(accessToken!=null)
                useLoginInformation(accessToken);

        }




        google_signout_button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(isGoogle)
                {
                    LoginActivity.googleSignInClient.signOut().addOnCompleteListener(new OnCompleteListener<Void>()
                    {


                        @Override
                        public void onComplete(@NonNull Task<Void> task)
                        {
                            isGoogle = false ;
                            Intent intent = new Intent(ProfileActivity.this , LoginActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            finish();
                        }
                    });
                }
                else
                {
                    LoginManager.getInstance().logOut();
                    Intent i = new Intent(ProfileActivity.this , LoginActivity.class);
                    startActivity(i);
                    finish();
                }

            }
        });
    }

    private void setDataOnView()
    {

        Picasso.get().load(googleSignInAccount.getPhotoUrl()).centerInside().fit().into(profile_image);
        profile_username.setText(googleSignInAccount.getEmail());
        profile_name.setText(googleSignInAccount.getDisplayName());

    }


    private void useLoginInformation(AccessToken accessToken)
    {
        GraphRequest request = GraphRequest.newMeRequest(accessToken , new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response)
            {
                try {

                    String fb_name = object.getString("name");
                    String fb_email = object.getString("email");
                    String fb_image = object.getJSONObject("picture").getJSONObject("data").getString("url");


                    profile_username.setText(fb_email);
                    profile_name.setText(fb_name);
                    Picasso.get().load(fb_image).fit().into(profile_image);


                }
                catch(JSONException e)
                {
                    e.printStackTrace();
                }
            }




        });




        // We set parameters to the GraphRequest using a Bundle.
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,email,picture.width(200)");
        request.setParameters(parameters);
        // Initiate the GraphRequest
        request.executeAsync();


    }




}
