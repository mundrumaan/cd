package com.harish.e_voting;

/**
 * Created by OO7 on 04/09/2015.
 */
public class party_list_model{
    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public String getcName() {
        return cName;
    }

    public void setcName(String cName) {
        this.cName = cName;
    }

    private String pid;
    private String pName;
    private String cName;

}
