package com.springboot.CurriculumManagement.Payloads;

public class ApiResponse {
    private String status;
    private boolean success;
    public ApiResponse(){

    }
    public ApiResponse(String status, boolean success) {
        this.status = status;
        this.success = success;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
