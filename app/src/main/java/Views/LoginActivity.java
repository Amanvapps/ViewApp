package Views;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.viewapp.R;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import Services.RetrofitClient;
import models.LoginReponseModel.LoginResponseClass;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;


public class LoginActivity extends AppCompatActivity {

    private EditText LoginEmail , LoginPassword ;
    private Button google_signIn_button ;
    private LoginButton facebook_signIn_button  ;


    static GoogleSignInClient googleSignInClient ;

    private CallbackManager callbackManager ;

    public String AuthToken  ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        LoginEmail = (EditText) findViewById(R.id.login_email);
        LoginPassword = (EditText) findViewById(R.id.login_password);
        google_signIn_button = (Button)findViewById(R.id.google_signin_button);
        facebook_signIn_button =  (LoginButton) findViewById(R.id.facebook_signin_button);
        facebook_signIn_button.setReadPermissions(Arrays.asList("email" , "public_profile"));

        callbackManager = CallbackManager.Factory.create();

        facebook_signIn_button.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult)
            {
                AccessToken accessToken = loginResult.getAccessToken() ;
                Intent i = new Intent(LoginActivity.this , ProfileActivity.class);
                i.putExtra("access_token" , accessToken);
                startActivity(i);
                finish();
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });



        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(this , gso);

        google_signIn_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signInIntent = googleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent , 101);
            }
        });


    }





        public void loginButton(View view)
        {
            String email = LoginEmail.getText().toString().trim();
            String password = LoginPassword.getText().toString().trim();
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("email", email);
            jsonObject.addProperty("password", password);

            Call<LoginResponseClass> call = RetrofitClient
                    .getInstance()
                    .getApi()
                    .userLogin(jsonObject);




            call.enqueue(new Callback<LoginResponseClass>() {
                @Override
                public void onResponse(Call<LoginResponseClass> call, Response<LoginResponseClass> response) {

                    LoginResponseClass loginResponse = response.body();

                    if(!response.isSuccessful())
                    {
                        Toast.makeText(getApplicationContext() , "Request not successful" , Toast.LENGTH_LONG).show();
                    }

                    if(response.isSuccessful()) {
                        try {

                            int content = response.code();
                            if (content == 200) {
                                Toast.makeText(getApplicationContext(), loginResponse.getMessage(), Toast.LENGTH_SHORT).show();
                                AuthToken = loginResponse.getData().getAuthorization() ;
                                sendToSharePrefernces(AuthToken);

                                sendUserToMainActivity();



                            } else {
                                Toast.makeText(getApplicationContext(), "Login Unsucessful", Toast.LENGTH_SHORT).show();
                            }



                        }
                        catch(Exception e)
                        {
                            Log.e("Error" , "" + e);
                        }


                    }





                }

                @Override
                public void onFailure(Call<LoginResponseClass> call, Throwable t)
                {
                    Toast.makeText(getApplicationContext() , "c" , Toast.LENGTH_SHORT).show();
                    Toast.makeText(getApplicationContext() , t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        }

    private void sendUserToMainActivity()
    {
        Intent MainActivityIntent = new Intent(this , MainActivity.class);
        startActivity(MainActivityIntent);
        finish();
    }

    public void sendToSharePrefernces(String auth)
        {
            SharedPreferences sharedpreferences = getSharedPreferences("MyReference", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putString("token", auth);
            editor.commit();
        }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {

        callbackManager.onActivityResult(requestCode , resultCode , data);

        super.onActivityResult(requestCode, resultCode, data);
        Log.d("tag" , "" + resultCode);
        if(resultCode == Activity.RESULT_OK)
        {
            switch (requestCode)
            {
                case 101  :
                    try {
                        Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                        GoogleSignInAccount account = task.getResult(ApiException.class);
                        onLoggedIn(account);
                        }
                        catch(ApiException e)
                        {
                            Log.e("SignInResult:failed : " ,"" + e.getStatusCode());
                        }
                    break;

                    }
            }
        }

    private void onLoggedIn(GoogleSignInAccount account)
    {
        Intent intent = new Intent(LoginActivity.this , ProfileActivity.class);
        intent.putExtra("google_account" , account);
        startActivity(intent);
        finish();

    }






    @Override
    protected void onStart() {
        super.onStart();

        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        if (accessToken != null)
        {
            Log.e("Facebook ","already logged in");
          Intent i  = new Intent(LoginActivity.this , ProfileActivity.class);
          i.putExtra("access_token" , accessToken);
          startActivity(i);
          finish();
        }


        GoogleSignInAccount already_logged_in_account = GoogleSignIn.getLastSignedInAccount(this);
        if(already_logged_in_account != null)
        {onLoggedIn(already_logged_in_account);
        Log.v("Logged in ? : " ,already_logged_in_account.toString());
        }
        else
        {
            Log.v("Logged in ?" , "Not logged in");
        }
    }
}
