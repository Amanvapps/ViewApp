package Services;

import com.example.viewapp.BuildConfig;
import com.google.gson.JsonObject;

import java.io.File;

import Presenters.AllCallsPresenter;
import models.UploadImageResponse.UploadImageResopnse;
import models.UserRegisterResponse.UserRegisterModelClass;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllCallsModelImpl implements AllCallsModel
{
    private AllCallsPresenter allCallsPresenter ;

    public AllCallsModelImpl(AllCallsPresenter allCallsPresenter)
    {
        this.allCallsPresenter = allCallsPresenter ;
    }

    @Override
    public void RegisterCall(String Auth, JsonObject jsonObject)
    {
        Call<UserRegisterModelClass> call = RetrofitClient
                .getInstance()
                .getApi()
                .setUserDetails(Auth , jsonObject);



        call.enqueue(new Callback<UserRegisterModelClass>()
        {
            @Override
            public void onResponse(Call<UserRegisterModelClass> call, Response<UserRegisterModelClass> response) {
                UserRegisterModelClass userDetailsModel = response.body();

                if (response.code() == 200)
                {
                    allCallsPresenter.setOnSuccessCallRegister();
                }
                else
                {
                  allCallsPresenter.setOnErrorCall(userDetailsModel.getMessage());
                }

            }

            @Override
            public void onFailure(Call<UserRegisterModelClass> call, Throwable t) {
               allCallsPresenter.setOnErrorCall(t.getMessage());
            }
        });



    }


    @Override
    public void RegisterImage(String Auth , String imagepath)
    {
        File file = new File(imagepath);

        RequestBody requestBodyImage = RequestBody.create(MediaType.parse("image/png"), file);
        MultipartBody.Part fileData = MultipartBody.Part.createFormData("file", file.getName(), requestBodyImage);

        MultipartBody.Part documentTypeData = MultipartBody.Part.createFormData("documentType", "PROFILE_PIC");
        MultipartBody.Part idProofTypeData = MultipartBody.Part.createFormData("idProofType", "PASSPORT");

        Call<UploadImageResopnse> call = RetrofitServiceBuilder.buildClient(ApiServices.class, BuildConfig.BASE_URL).uploadImage(Auth
                , documentTypeData
                , idProofTypeData
                , fileData);


        call.enqueue(new Callback<UploadImageResopnse>() {
            @Override
            public void onResponse(Call<UploadImageResopnse> call, Response<UploadImageResopnse> response) {

                UploadImageResopnse uploadImageResopnse = response.body();

                if (response.code() == 200) {
                    allCallsPresenter.setOnSuccessImageCall();
                } else {
                    allCallsPresenter.setOnErrorCall(uploadImageResopnse.getMessage());
                }
            }

            @Override
            public void onFailure(Call<UploadImageResopnse> call, Throwable t) {
                allCallsPresenter.setOnErrorCall(t.getMessage());
            }
        });
    }
}
