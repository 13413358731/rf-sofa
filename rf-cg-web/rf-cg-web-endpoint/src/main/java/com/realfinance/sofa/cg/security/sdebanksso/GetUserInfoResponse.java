package com.realfinance.sofa.cg.security.sdebanksso;

import java.util.List;

public class GetUserInfoResponse {
    private String loginName;
    private String displayName;
    private List<String> spRoleList;


    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public List<String> getSpRoleList() {
        return spRoleList;
    }

    public void setSpRoleList(List<String> spRoleList) {
        this.spRoleList = spRoleList;
    }
}
