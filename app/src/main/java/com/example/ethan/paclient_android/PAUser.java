package com.example.ethan.paclient_android;

/**
 * Created by Ethan on 04/08/2015.
 * Modified by pkb on 04/24/2015.
 */
public class PAUser {

    private static PAUser uniqueInstance = new PAUser();

    private  String id;
    private  String name;
    private  String pwd;


    private PAUser(){

        id = new String();
        name = new String();
        pwd = new String();
    }

    public static synchronized PAUser getInstance() {
        return uniqueInstance;
    }

    public static synchronized PAUser getInstance(String id, String name, String pwd) {
        uniqueInstance.setId(id);
        uniqueInstance.setName(name);
        uniqueInstance.setPwd(pwd);

        return uniqueInstance;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) { this.pwd = pwd; }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof PAUser)) {
            return false;
        }
        PAUser other = (PAUser) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "PAUser[ id=" + id + " ]";
    }


}
