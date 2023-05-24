package com.streamapi_filters;

public class Human {
    private String first_name;
    private String last_name;
    private String email;
    private String image_link;
    private String ip_address;

    public Human(String first_name, String last_name, String email, String image_link, String ip_address) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.image_link = image_link;
        this.ip_address = ip_address;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImage_link() {
        return image_link;
    }

    public void setImage_link(String image_link) {
        this.image_link = image_link;
    }

    public String getIp_address() {
        return ip_address;
    }

    public void setIp_address(String ip_address) {
        this.ip_address = ip_address;
    }
}
