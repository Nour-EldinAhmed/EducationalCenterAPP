package com.nour.centerapp.Uploading;

public class UploadFile {

    private String NameFile;
    private String url;

    public UploadFile(String nameFile, String url) {
        NameFile = nameFile;
        this.url = url;
    }
    public UploadFile()
    {

    }

    public String getNameFile() {
        return NameFile;
    }

    public void setNameFile(String nameFile) {
        NameFile = nameFile;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


}
