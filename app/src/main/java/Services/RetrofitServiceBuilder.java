package Services;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitServiceBuilder {

    public static <object> object buildClient(Class<object> serviceClass, String baseUrl) {
        OkHttpClient.Builder okHttpBuilder=new OkHttpClient.Builder();
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            okHttpBuilder.addInterceptor(interceptor);
        OkHttpClient httpClient = okHttpBuilder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                Request requestWithUserAgent = request.newBuilder()
                        .header("User-Agent", "Android")
                        .build();
                okhttp3.Response response = chain.proceed(requestWithUserAgent);
                if (response.code() >= 500) {
                    JSONObject jsonObject=new JSONObject();
                    try {
                        jsonObject.put("message","There is an issue with your request.Please try again.");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    MediaType contentType = response.body().contentType();
                    ResponseBody body = ResponseBody.create(contentType, jsonObject.toString());
                    return response.newBuilder().body(body).build();
                }
                return response;
            }
        }).build();
        Retrofit.Builder builder =
                new Retrofit.Builder()
                        .baseUrl(baseUrl)
                        .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.client(httpClient.newBuilder().connectTimeout(85, TimeUnit.SECONDS)
                .readTimeout(85, TimeUnit.SECONDS).writeTimeout(85, TimeUnit.SECONDS).build()).build();
        return retrofit.create(serviceClass);
    }
}
