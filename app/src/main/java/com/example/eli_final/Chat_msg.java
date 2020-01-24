package com.example.eli_final;

import androidx.annotation.NonNull;

public class Chat_msg {
    private String user;
    private String content;
    private int img;

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }



    public Chat_msg(String user, String content, int img) {
        this.user = user;
        this.content = content;
        this.img=img;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @NonNull
    @Override
    public String toString() {
        return getUser() + getContent();
    }
}
