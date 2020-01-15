package Presenters;

import Views.MainActivityView;
import models.ActivityModels.MainActivityModel;
import models.ActivityModels.MainActivityModelImpl;

public class MainActivityPresenterImpl implements MainActivityPresenter
{

    private MainActivityView mainActivityView ;
    private MainActivityModel mainActivityModel ;


    public MainActivityPresenterImpl(MainActivityView mainActivityView)
    {
        this.mainActivityView = mainActivityView ;
        this.mainActivityModel = new MainActivityModelImpl(this ) ;
    }


    @Override
    public void setonRegisterDetails(String fname, String phnumber, String phcode, String skype,
                                     String city, String ss, String language, String gender,
                                     String header, String desc , String Auth)
    {
        mainActivityModel.registerUser(fname , phnumber , phcode  , skype , city , ss , language
                , gender , header , desc  , Auth);
    }

    @Override
    public void setonRegisterImage(String Auth , String imagepath) {
        mainActivityModel.registerProfilePic(Auth , imagepath);
    }

    @Override
    public void setonSuccess()
    {
        mainActivityView.onSuccessDetails();
    }

    @Override
    public void setOnSuccessImage()
    {
        mainActivityView.onSuccessImage();
    }

    @Override
    public void setonError(String errorMessage) {
        mainActivityView.onError(errorMessage);
    }
}
