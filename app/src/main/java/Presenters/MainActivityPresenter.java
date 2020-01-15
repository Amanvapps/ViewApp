package Presenters;

public interface MainActivityPresenter
{

    void setonRegisterDetails(String name , String phnumber , String phcode  , String skype , String city ,
                              String ss , String language
            , String gender , String header , String desc , String Auth);
    void setonRegisterImage(String imagepath , String Auth);
    void setonSuccess();
    void setOnSuccessImage();
    void setonError(String errorMessage);

}
