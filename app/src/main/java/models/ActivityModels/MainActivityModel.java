package models.ActivityModels;

public interface MainActivityModel
{
    void registerUser(String fname , String phnumber , String phcode  , String skype , String city ,
                      String ss , String language
            , String gender , String header , String desc , String Auth);
    void registerProfilePic(String Auth , String imagepath);
}
