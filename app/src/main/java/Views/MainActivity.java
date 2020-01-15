package Views;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.CursorLoader;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.viewapp.BuildConfig;
import com.example.viewapp.R;
import com.google.gson.JsonObject;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.single.PermissionListener;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;

import Presenters.MainActivityPresenter;
import Presenters.MainActivityPresenterImpl;



public class MainActivity extends AppCompatActivity  implements MainActivityView
{

    private EditText Fullname, EmailId, DOB, Gender, Country, PhoneCode, PhoneNumber, City, Language, SkypeId, Header, Desc;
    private Button NextButton;
    private de.hdodenhof.circleimageview.CircleImageView profile_image ;
    private TextView change_profie_pic ;
    private String imagepath ;
    String Auth ;
    MainActivityPresenter presenter ;
    private DatePickerDialog mDatePickerDialog;
    private int yearr, day, month ;
    private static final int PICK_IMAGE = 100;
    private Uri imageUri;
    public boolean set_external_storage_granted = false ;

    private RelativeLayout relativeLayout ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        relativeLayout = findViewById(R.id.main_relative_layout);

        Auth = "Bearer " + getFromSharedPrefernces();
        presenter = new MainActivityPresenterImpl(this);
        Fullname = (EditText) findViewById(R.id.user_details_fullname);
        EmailId = (EditText) findViewById(R.id.user_details_email_id);
        DOB = (EditText) findViewById(R.id.user_details_dob);
        Gender = (EditText) findViewById(R.id.user_details_gender);
        Country = (EditText) findViewById(R.id.user_details_country);
        PhoneCode = (EditText) findViewById(R.id.user_details_phone_code);
        PhoneNumber = (EditText) findViewById(R.id.user_details_phone);
        City = (EditText) findViewById(R.id.user_details_city);
        Language = (EditText) findViewById(R.id.user_details_language);
        SkypeId = (EditText) findViewById(R.id.user_details_skype_id);
        Header = (EditText) findViewById(R.id.user_details_header);
        Desc = (EditText) findViewById(R.id.user_details_description);
        NextButton = (Button) findViewById(R.id.user_details_next_button);
        profile_image = (de.hdodenhof.circleimageview.CircleImageView)findViewById(R.id.profile_image);
        change_profie_pic = (TextView)findViewById(R.id.change_profile_pic);

        addExternalStoragePermission();

        change_profie_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeDp();
            }
        });

        setDateTimeField();

        DOB.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mDatePickerDialog.show();
                return false;
            }
        });

        NextButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v)
            {
                if(set_external_storage_granted)
                {
                    presenter.setonRegisterImage(Auth , imagepath);

                    relativeLayout.setAlpha((float) 0.5);

                    getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);



                }

                else
                    Log.e("Permission" , "Not granted for external storage");

                String fname = Fullname.getText().toString().trim();
                String gender = Gender.getText().toString().trim();
                String phcode = PhoneCode.getText().toString().trim();
                String phnumber = PhoneNumber.getText().toString().trim();
                String city = City.getText().toString().trim();
                String language = Language.getText().toString().trim();
                String skype = SkypeId.getText().toString().trim();
                String header = Header.getText().toString().trim();
                String desc = Desc.getText().toString().trim();
                String ss = changeDataFormat();

                presenter.setonRegisterDetails(fname , phnumber , phcode  , skype , city , ss , language
                , gender , header , desc  , Auth);




            }
        });

    }

private void changeDp()
    {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE)
        {
            imageUri = data.getData();
            CropImage.activity().setGuidelines(CropImageView.Guidelines.ON)
                    .setAspectRatio(1,1).start(this);
        }
        if(requestCode==CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
            CropImage.ActivityResult result=CropImage.getActivityResult(data);
            if(resultCode==RESULT_OK)
            {
                Uri resultUri=result.getUri();
                imagepath = "" ;
                if(imagepath!=null)
                    imagepath = getRealPathFromUri(imageUri);
            else
                {
                    Log.e("Image Url" , "no url");
                }

                Picasso.get().load(resultUri.toString()).into(profile_image);

            }
            else
            {
                Log.e("Cropped" , "Image can't be cropped");
            }

        }

    }




    private String getRealPathFromUri(Uri uri)
    {
       String[] projection = {MediaStore.Images.Media.DATA};
        CursorLoader loader = new CursorLoader(getApplicationContext() , uri , projection , null , null , null);
        Cursor cursor = loader.loadInBackground();
        int column_idx = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(column_idx);
        cursor.close();
        return result ;
   }


    public String getFromSharedPrefernces()
    {
        SharedPreferences prefs = getSharedPreferences("MyReference", MODE_PRIVATE);
        String token = prefs.getString("token", "");
        return token;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public String changeDataFormat()
    {
        LocalDate d = LocalDate.of(yearr,month ,day);

        LocalTime t = LocalTime.now();

        LocalDateTime dt = LocalDateTime.of(d , t);

        return dt.toString() + "Z" ;
    }

    private void setDateTimeField() {

        Calendar newCalendar = Calendar.getInstance();
        mDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                SimpleDateFormat sd = new SimpleDateFormat("dd-MM-yyyy");
                final Date startDate = newDate.getTime();
                String fdate = sd.format(startDate);

                day = dayOfMonth ;
                month = monthOfYear + 1 ;
                yearr = year ;


                DOB.setText(fdate);

            }
        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        mDatePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());

    }



    public void addExternalStoragePermission()
    {
        Dexter.withActivity(this)
                .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
                    @Override public void onPermissionGranted(PermissionGrantedResponse response)
                    {
                        set_external_storage_granted = true ;
                    }

                    @Override public void onPermissionDenied(PermissionDeniedResponse response)
                    {
                        Log.e("permission" , "denied");
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(com.karumi.dexter.listener.PermissionRequest permission, PermissionToken token) {
                        token.continuePermissionRequest();
                    }

                }).check();
    }





    @Override
    public void onSuccessDetails()
    {
        Log.e("User_details_set : " , "Successfull" ) ;

    }

    @Override
    public void onSuccessImage()
    {
        Log.e("User_image_details :" ,  "Successfull");
        relativeLayout.setAlpha((float)1);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

    }

    @Override
    public void onError(String errorMessage)
    {
        Log.v("Error message : " , errorMessage ) ;
    }

//
//
//    class MyTask extends AsyncTask<Integer , Integer , String>
//    {
//
//        @Override
//        protected String doInBackground(Integer... integers)
//        {
//
//            getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
//                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
//
//
//            for(int i=0 ; i<integers[0]; i++)
//            {
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                publishProgress(i);
//            }
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(String s) {
//            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
//
//
//            linearLayout.setAlpha(1);
//            output.setText("Result");
//            btn.setText("Restart");
//            progressBar.setVisibility(View.GONE);
//
//        }
//
//
//        @Override
//        protected void onPreExecute() {
//            linearLayout.setAlpha((float) .5);
//            output.setText("Starting task");
//
//        }
//
//
//        @Override
//        protected void onProgressUpdate(Integer... values) {
//
//            output.setText("Running  " + values[0]);
//            progressBar.setProgress(values[0]);
//
//
//        }
//    }
//
//

}
