package models.ActivityModels;

import android.util.Log;

import com.google.gson.JsonObject;



import Presenters.AllCallsPresenter;
import Presenters.AllCallsPresenterImpl;
import Presenters.MainActivityPresenter;


public class MainActivityModelImpl implements MainActivityModel , AllCallsView {


    private MainActivityPresenter mainActivityPresenter;
    private AllCallsPresenter allCallsPresenter;


    public MainActivityModelImpl(MainActivityPresenter mainActivityPresenter) {
        this.mainActivityPresenter = mainActivityPresenter;

    }


    @Override
    public void registerUser(String fname, String phnumber, String phcode, String skype, String city, String ss, String language, String gender, String header, String desc, String Auth) {


        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("name", fname);
        jsonObject.addProperty("phoneNumber", phnumber);
        jsonObject.addProperty("phoneCode", phcode);
        jsonObject.addProperty("skypeId", skype);
        jsonObject.addProperty("city", city);
        jsonObject.addProperty("dob", ss);
        jsonObject.addProperty("countryId", "1");
        jsonObject.addProperty("preferredLanguage", language);
        jsonObject.addProperty("gender", gender.toUpperCase());
        jsonObject.addProperty("profileHeadline", header);
        jsonObject.addProperty("profileDescription", desc);


        allCallsPresenter = new AllCallsPresenterImpl(this);


        allCallsPresenter.registerCall(Auth, jsonObject);


    }

    @Override
    public void registerProfilePic(String Auth , String imagepath) {

        allCallsPresenter = new AllCallsPresenterImpl(this);
        allCallsPresenter.registerImage(Auth , imagepath);

    }



    @Override
    public void onSuccessCallRegisterDetails()
    {
       mainActivityPresenter.setonSuccess();
    }

    @Override
    public void onErrorCall(String errorMessage)
    {
        Log.e("Calls error  : " ,errorMessage);
    }

    public void onSucessCallUploadImage()
    {
      mainActivityPresenter.setOnSuccessImage();
    }


}