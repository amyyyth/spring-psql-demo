package com.dbconnect.demodb.Payloads;


public class signInResponse {
    private String accessToken;
    private Long id;
    private String username;
    private String Name;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public signInResponse(String accessToken, Long id, String username, String name) {
        this.accessToken = accessToken;
        this.id = id;
        this.username = username;
        Name = name;
    }

    public signInResponse() {
        this.accessToken = "";
    }
}
