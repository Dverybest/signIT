package com.dverybest.promiserynote;

/**
 * Created by BEN on 25/10/2018.
 */

public class ReceiptListModel {
    private String name;
    private String date;
    private String filePath;

    public ReceiptListModel(String name, String date, String filePath) {
        this.name = name;
        this.date = date;
        this.filePath = filePath;
    }

    public String getName() {
        return name;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}
