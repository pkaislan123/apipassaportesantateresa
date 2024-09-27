package br.com.kapplanapi.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PaymentUpdate {

    private String action;

    @JsonProperty("api_version")
    private String apiVersion;

    private Data data;

    @JsonProperty("date_created")
    private String dateCreated;

    private long id;

    @JsonProperty("live_mode")
    private boolean liveMode;

    private String type;

    @JsonProperty("user_id")
    private String userId;

    // Getters e Setters

    public static class Data {
        private String id;

        // Getter e Setter
        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }

    // Getters e Setters
    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getApiVersion() {
        return apiVersion;
    }

    public void setApiVersion(String apiVersion) {
        this.apiVersion = apiVersion;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isLiveMode() {
        return liveMode;
    }

    public void setLiveMode(boolean liveMode) {
        this.liveMode = liveMode;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}