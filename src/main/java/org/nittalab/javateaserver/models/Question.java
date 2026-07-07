package org.nittalab.javateaserver.models;

import java.util.List;

public class Question {
    private String title;
    private String body;
    private String uid;
    private List<String> tags;
    private String viewPermission;
    private String resPermission;

    public Question(String tilte, String body, String uid, List<String> tags, String viewPermission, String resPermission) {
        this.title = tilte;
        this.body = body;
        this.uid = uid;
        this.tags = tags;
        this.viewPermission = viewPermission;
        this.resPermission = resPermission;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getUid() {
        return uid;
    }
    public void setUid(String uid) {
        this.uid = uid;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public String getViewPermission() {
        return viewPermission;
    }

    public void setViewPermission(String viewPermission) {
        this.viewPermission = viewPermission;
    }

    public String getResPermission() {
        return resPermission;
    }

    public void setResPermission(String resPermission) {
        this.resPermission = resPermission;
    }
}