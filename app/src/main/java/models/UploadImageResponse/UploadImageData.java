
package models.UploadImageResponse;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UploadImageData implements Serializable
{

    @SerializedName("id")
    @Expose
    private long id;
    @SerializedName("createdOn")
    @Expose
    private long createdOn;
    @SerializedName("originalFileName")
    @Expose
    private String originalFileName;
    @SerializedName("savedFileName")
    @Expose
    private String savedFileName;
    @SerializedName("documentUrl")
    @Expose
    private String documentUrl;
    @SerializedName("idProofType")
    @Expose
    private String idProofType;
    @SerializedName("documentType")
    @Expose
    private String documentType;
    private final static long serialVersionUID = -3646876030607461630L;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(long createdOn) {
        this.createdOn = createdOn;
    }

    public String getOriginalFileName() {
        return originalFileName;
    }

    public void setOriginalFileName(String originalFileName) {
        this.originalFileName = originalFileName;
    }

    public String getSavedFileName() {
        return savedFileName;
    }

    public void setSavedFileName(String savedFileName) {
        this.savedFileName = savedFileName;
    }

    public String getDocumentUrl() {
        return documentUrl;
    }

    public void setDocumentUrl(String documentUrl) {
        this.documentUrl = documentUrl;
    }

    public String getIdProofType() {
        return idProofType;
    }

    public void setIdProofType(String idProofType) {
        this.idProofType = idProofType;
    }

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

}
