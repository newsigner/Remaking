package com.example.myapplication;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface BoxService {
    @GET("/kobisopenapi/webservice/rest/boxoffice/searchDailyBoxOfficeList.json")
    Call<Map<String, Object>> getBoxOffice(@Query("key") String key,
                                           @Query("targetDt") String tagetDt);

    @GET("/youtube/v3/search")
    Call<Map<String, Object>> searchVideo(@Query("key") String key,
                             @Query("part") String part,
                             @Query("q") String title,
                             @Query("maxResults") int maxResults,
                             @Query("type") String type);
}
