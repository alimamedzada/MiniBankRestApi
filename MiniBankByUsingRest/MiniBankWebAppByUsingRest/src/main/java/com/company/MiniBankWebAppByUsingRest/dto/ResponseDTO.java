package com.company.MiniBankWebAppByUsingRest.dto;

public class ResponseDTO {

    private int errorCode;
    private String successMessage;
    private String errorMessage;
    private Object obj;

    private ResponseDTO() {

    }

    public static ResponseDTO of(Object obj, String successMessage) {
        ResponseDTO dto = new ResponseDTO();
        dto.setObj(obj);
        dto.setSuccessMessage(successMessage);
        return dto;
    }

    public static ResponseDTO of(Object obj, int errorCode, String successMessage) {
        ResponseDTO dto = new ResponseDTO();
        dto.setObj(obj);
        dto.setSuccessMessage(successMessage);
        dto.setErrorCode(errorCode);
        return dto;
    }

    public static ResponseDTO of(Object obj) {
        ResponseDTO dto = new ResponseDTO();
        dto.setObj(obj);
        return dto;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getSuccessMessage() {
        return successMessage;
    }

    public void setSuccessMessage(String successMessage) {
        this.successMessage = successMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }

}
