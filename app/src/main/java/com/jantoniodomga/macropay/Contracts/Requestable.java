package com.jantoniodomga.macropay.Contracts;

import com.jantoniodomga.macropay.Entitys.LoginResponse;
import com.jantoniodomga.macropay.Entitys.Session;

import retrofit2.Call;
import retrofit2.Response;

public interface Requestable<T> {
    void onTimeOut(Call<?> call, Throwable t);

    void onFailure(Call<?> call, Throwable t);

    void onResponseError(Call<?> call, Response<?> response);

    void onResponseFail(Call<?> call, T response);

    void onSuccess(Session logged);
}
