package com.realfinance.sofa.cg.sup.model;

import java.io.Serializable;

public class registrationInformation implements Serializable {


    private String contactName;
    private String mobile;
    private String email;






    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
