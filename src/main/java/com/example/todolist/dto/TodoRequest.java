package com.example.todolist.dto;

public class TodoRequest {
    private String context;
    private Boolean done = false;

    public TodoRequest(String context, Boolean done) {
        this.context = context;
        this.done = done;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public Boolean getDone() {
        return done;
    }

    public void setDone(Boolean done) {
        this.done = done;
    }

    public TodoRequest() {
    }
}
