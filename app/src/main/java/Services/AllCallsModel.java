package Services;

import com.google.gson.JsonObject;

public interface AllCallsModel
{
    void RegisterCall(String Auth , JsonObject jsonObject);
    void RegisterImage(String Auth  , String imagepath);
}
