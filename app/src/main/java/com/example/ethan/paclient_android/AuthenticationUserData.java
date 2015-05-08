package com.example.ethan.paclient_android;

/**
 * Created by AJOU on 2015-04-27.
 */
public class AuthenticationUserData {

    /**
     * Created by Ethan on 04/08/2015.
     * Modified by pkb on 04/24/2015.
     */

     private  String id;
     private  String name;
     private  String pwd;


    public AuthenticationUserData(){

        id = new String();
        name = new String();
        pwd = new String();
    }

    public AuthenticationUserData(String id, String name, String pwd){

        this.id = id;
        this.name = name;
        this.pwd = pwd;
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
            if (!(object instanceof AuthenticationUserData)) {
                return false;
            }
            AuthenticationUserData other = (AuthenticationUserData) object;
            if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
                return false;
            }
            return true;
        }

        @Override
        public String toString() {
            return "AuthenticationUserData[ id=" + id + " ]";
        }
}