package Services;

import com.google.gson.JsonObject;

import java.io.File;

import models.LoginReponseModel.LoginResponseClass;
import models.UploadImageResponse.UploadImageResopnse;
import models.UserRegisterResponse.UserRegisterModelClass;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface ApiServices
{


    @POST("user/login")
    Call<LoginResponseClass> userLogin(@Body JsonObject jsonObject);

    @POST("user/user_profile")
    Call<UserRegisterModelClass> setUserDetails(@Header("Authorization") String authToken ,@Body JsonObject jsonObject);


    @Multipart
    @POST("user/documents")
    Call<UploadImageResopnse> uploadImage(@Header("Authorization") String authToken,
                                          @Part MultipartBody.Part documentType ,
                                          @Part  MultipartBody.Part idProofTYpe ,
                                          @Part MultipartBody.Part file);

}