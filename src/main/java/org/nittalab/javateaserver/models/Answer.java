package org.nittalab.javateaserver.models;

import java.util.HashMap;

public class Answer {
    private String aid;
    private String body;
    private String uid;

    //コンストラクタ
    public Answer(String aid, String body, String uid) {
        this.aid = aid;
        this.body = new String();
        this.uid = new String();
    }

    public String getAid() {return aid;}; //aidを返す(念のため)

    public String getBody() {return body;};

    public void setBody(String body) {
        this.body = body;
    }

    public String getUid() {return uid;};

    public void setUid(String uid) {
        this.uid = uid;
    }
}
