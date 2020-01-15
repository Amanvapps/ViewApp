
package models.LoginReponseModel;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DataLoginClass implements Serializable
{

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("phoneNumber")
    @Expose
    private String phoneNumber;
    @SerializedName("phoneCode")
    @Expose
    private String phoneCode;
    @SerializedName("kycApproved")
    @Expose
    private boolean kycApproved;
    @SerializedName("active")
    @Expose
    private boolean active;
    @SerializedName("passportUploadSelfie")
    @Expose
    private boolean passportUploadSelfie;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("countryId")
    @Expose
    private long countryId;
    @SerializedName("roleName")
    @Expose
    private String roleName;
    @SerializedName("preferredLanguage")
    @Expose
    private String preferredLanguage;
    @SerializedName("authorization")
    @Expose
    private String authorization;
    @SerializedName("validSubscription")
    @Expose
    private boolean validSubscription;
    @SerializedName("profilePicUrl")
    @Expose
    private String profilePicUrl;
    private final static long serialVersionUID = 9006370639471186748L;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneCode() {
        return phoneCode;
    }

    public void setPhoneCode(String phoneCode) {
        this.phoneCode = phoneCode;
    }

    public boolean isKycApproved() {
        return kycApproved;
    }

    public void setKycApproved(boolean kycApproved) {
        this.kycApproved = kycApproved;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isPassportUploadSelfie() {
        return passportUploadSelfie;
    }

    public void setPassportUploadSelfie(boolean passportUploadSelfie) {
        this.passportUploadSelfie = passportUploadSelfie;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getCountryId() {
        return countryId;
    }

    public void setCountryId(long countryId) {
        this.countryId = countryId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getPreferredLanguage() {
        return preferredLanguage;
    }

    public void setPreferredLanguage(String preferredLanguage) {
        this.preferredLanguage = preferredLanguage;
    }

    public String getAuthorization() {
        return authorization;
    }

    public void setAuthorization(String authorization) {
        this.authorization = authorization;
    }

    public boolean isValidSubscription() {
        return validSubscription;
    }

    public void setValidSubscription(boolean validSubscription) {
        this.validSubscription = validSubscription;
    }

    public String getProfilePicUrl() {
        return profilePicUrl;
    }

    public void setProfilePicUrl(String profilePicUrl) {
        this.profilePicUrl = profilePicUrl;
    }

}
