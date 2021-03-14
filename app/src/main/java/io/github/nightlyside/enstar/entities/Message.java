package io.github.nightlyside.enstar.entities;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

public class Message {


    private String id;
    private String senderID;
    private String groupID;
    private Date date;
    private String content;
    private boolean isRead;

    public Message() {}

    public Message(String id, String senderID, String groupID, Date date, String content, boolean isRead) {
        this.id = id;
        this.senderID = senderID;
        this.groupID = groupID;
        this.date = date;
        this.content = content;
        this.isRead = isRead;
    }

    public String getId() {
        return id;
    }

    public String getSenderID() {
        return senderID;
    }

    public String getGroupID() {
        return groupID;
    }

    public Date getDate() {
        return date;
    }

    public String getContent() {
        return content;
    }

    public boolean hasBeenRead() {
        return isRead;
    }

    public JSONObject toJSON() {
        try {
            JSONObject res = new JSONObject();
            res.put("id", this.getId());
            res.put("sender_id", this.getSenderID());
            res.put("group_id", this.getGroupID());
            res.put("date", this.getDate().toString());
            res.put("content", this.getContent());
            res.put("is_read", this.hasBeenRead());
            return res;
        } catch (JSONException e) {
            Log.e(Message.class.getSimpleName(), e.toString());
            return null;
        }
    }
}
