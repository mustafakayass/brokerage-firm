package com.kayas.brokerageFirm.dto.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class BaseResponse<T> {
    private String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;
    private String status;

    public static <T> BaseResponse<T> success(String message) {
        BaseResponse<T> response = new BaseResponse<>();
        response.setStatus("success");
        response.setData(null);
        response.setMessage(message);
        return response;
    }

    public static <T> BaseResponse<T> failure(String message) {
        BaseResponse<T> response = new BaseResponse<>();
        response.setStatus("failure");
        response.setMessage(message);
        response.setData(null);
        return response;
    }

}


