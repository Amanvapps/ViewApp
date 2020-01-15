
package models.LoginReponseModel;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginResponseClass implements Serializable
{

    @SerializedName("data")
    @Expose
    private DataLoginClass data;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("timestamp")
    @Expose
    private long timestamp;
    @SerializedName("status")
    @Expose
    private long status;
    @SerializedName("isSuccess")
    @Expose
    private boolean isSuccess;
    private final static long serialVersionUID = -1913084210445921724L;

    public DataLoginClass getData() {
        return data;
    }

    public void setData(DataLoginClass data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public long getStatus() {
        return status;
    }

    public void setStatus(long status) {
        this.status = status;
    }

    public boolean isIsSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

}
