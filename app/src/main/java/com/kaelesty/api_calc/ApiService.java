package com.kaelesty.api_calc;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    @GET("evaluate")
    Single<EvaluationResult> getEvaluationResult(@Query("expression") String expression);
}
