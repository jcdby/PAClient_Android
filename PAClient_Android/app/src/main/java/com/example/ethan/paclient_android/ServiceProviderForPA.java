package com.example.ethan.paclient_android;

import java.io.Serializable;

/**
 * Created by Ethan on 04/09/2015.
 */
public class ServiceProviderForPA implements Serializable{

    private Long id;
    private String spName;


    public ServiceProviderForPA() {
        this.id = new Long(0);
        this.spName = new String();
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getSpName() {
        return spName;
    }

    public void setSpName(String spName) {
        this.spName = spName;
    }
}
