package com.square.apple.pdf_app.networking;

import com.square.apple.pdf_app.dataModels.GetAllPdf;
import com.square.apple.pdf_app.dataModels.GetAllResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by eapple on 29/08/2018.
 */

public interface ApiInterface {

    @FormUrlEncoded
    @POST("getPdf")
    Call<GetAllResponse> getPdf(@Field("type") String type);



}
