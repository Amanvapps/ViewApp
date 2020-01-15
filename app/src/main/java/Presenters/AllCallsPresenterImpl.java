package Presenters;

import com.google.gson.JsonObject;

import Services.AllCallsModel;
import Services.AllCallsModelImpl;
import models.ActivityModels.AllCallsView;


public class AllCallsPresenterImpl implements AllCallsPresenter
{
    private AllCallsView allCallsView ;
    private AllCallsModel  allCallsModel ;


    public AllCallsPresenterImpl(AllCallsView allCallsView)
    {
        this.allCallsModel = new AllCallsModelImpl(this);
        this.allCallsView = allCallsView ;
    }

    @Override
    public void registerCall(String Auth, JsonObject jsonObject)
    {
        allCallsModel.RegisterCall(Auth, jsonObject);
    }

    @Override
    public void setOnSuccessCallRegister ()
    {
       allCallsView.onSuccessCallRegisterDetails();
    }

    @Override
    public void setOnErrorCall(String message)
    {
      allCallsView.onErrorCall(message);
    }

    @Override
    public void setOnSuccessImageCall() {
        allCallsView.onSucessCallUploadImage();
    }

    @Override
    public void registerImage(String Auth, String imagepath) {
        allCallsModel.RegisterImage(Auth , imagepath);
    }






}
