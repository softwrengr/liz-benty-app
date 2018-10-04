package com.square.apple.pdf_app.dataModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by eapple on 01/10/2018.
 */

public class GetAllResponse {
    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("user ")
    @Expose
    private GetAllPdf getAllPdf;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public GetAllPdf getAllPdf() {
        return getAllPdf;
    }

    public void setUser(GetAllPdf getAllPdf) {
        this.getAllPdf = getAllPdf;
    }

}
