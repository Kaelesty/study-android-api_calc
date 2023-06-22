package com.kaelesty.api_calc;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class MainViewModel extends AndroidViewModel {

    public static final String BASE_URL = "https://kaelesty-api-calc.loca.lt";

    private MutableLiveData<EvaluationResult> evaluationResultLD = new MutableLiveData<>();

    private CompositeDisposable subscribes = new CompositeDisposable();

    public MainViewModel(@NonNull Application application) {
        super(application);
    }

    public void evaluate(String expression) {
        expression = expression.replace("+", "p");
        Disposable disposable = evaluateRX(expression)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<EvaluationResult>() {
                    @Override
                    public void accept(EvaluationResult evaluationResult) throws Throwable {
                        evaluationResultLD.postValue(evaluationResult);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Throwable {
                        evaluationResultLD.postValue(new EvaluationResult("error", "Network Error"));
                    }
                });
    }

    private Single<EvaluationResult> evaluateRX(String expression) {
        return ApiFactory.getApiService().getEvaluationResult(expression);
    }

    public LiveData<EvaluationResult> getEvaluationResult() {
        return evaluationResultLD;
    }

}
