package io.github.nightlyside.enstar.entities;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

public class Group {

    private String id;
    private String name;
    private boolean isPM;
    private Date creationDate;
    private String members;

    public Group() {}

    public Group(String id, String name, boolean isPM, Date creationDate, String members) {
        this.id = id;
        this.name = name;
        this.isPM = isPM;
        this.creationDate = creationDate;
        this.members = members;
    }

    public String getImage() { return "https://www.arkhane-asylum.fr/wp-content/uploads/2016/11/adventure-time.png"; }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean isThisAPM() {
        return isPM;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public String getMembers() {
        return members;
    }
    public void setMembers(String value) {
        this.members = value;
    }

    public JSONObject toJSON() {
        try {
            JSONObject res = new JSONObject();
            res.put("id", this.getId());
            res.put("name", this.getName());
            res.put("is_pm", this.isThisAPM());
            res.put("creation_date", this.getCreationDate().toString());
            res.put("members", this.getMembers());
            return res;
        } catch (JSONException e) {
            Log.e(Group.class.getSimpleName(), e.toString());
            return null;
        }
    }
}
