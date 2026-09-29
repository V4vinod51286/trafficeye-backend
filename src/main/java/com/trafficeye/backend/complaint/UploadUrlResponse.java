package com.trafficeye.backend.complaint;

public class UploadUrlResponse {

    private String uploadUrl;
    private String objectKey;

    public UploadUrlResponse(String uploadUrl, String objectKey) {
        this.uploadUrl = uploadUrl;
        this.objectKey = objectKey;
    }

    public String getUploadUrl() {
        return uploadUrl;
    }

    public String getObjectKey() {
        return objectKey;
    }
}