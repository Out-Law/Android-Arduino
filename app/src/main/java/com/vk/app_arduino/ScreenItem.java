package com.vk.app_arduino;

public class ScreenItem {

    String Title, Description;
    int ScreenImg;


    public void setTitle(String title) {
        this.Title = title;
    }

    public String getTitle() {
        return Title;
    }

    public void setDescription(String description) {
        this.Description = description;
    }

    public String getDescription() {
        return Description;
    }

    public void setScreenImg(int screenImg) {
        this.ScreenImg = screenImg;
    }

    public int getScreenImg() {
        return ScreenImg;
    }
}
