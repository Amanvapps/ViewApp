package Presenters;

import com.google.gson.JsonObject;

public interface AllCallsPresenter
{
    void registerCall(String Auth , JsonObject jsonObject);
    void setOnSuccessCallRegister();
    void setOnErrorCall(String message) ;
    void setOnSuccessImageCall();
    void registerImage(String Auth , String imagepath);
}
