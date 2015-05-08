package com.example.ethan.paclient_android;

import java.io.Serializable;

/**
 * Created by Ethan on 04/08/2015.
 */
public class ServiceForPA implements Serializable{

    private Long id;
    private String name;
    private String redirectionAddress;
    private String serviceIntroduction;
    private Long ownerid;



    public ServiceForPA() {
        this.name = new String();
        this.redirectionAddress = new String();
        this.serviceIntroduction = new String();
        this.id = new Long(0);
        this.ownerid = new Long(0);
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOwnerid() {
        return ownerid;
    }

    public void setOwnerid(Long ownerid) {
        this.ownerid = ownerid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRedirectionAddress() {
        return redirectionAddress;
    }

    public void setRedirectionAddress(String redirectionAddress) {
        this.redirectionAddress = redirectionAddress;
    }

    public String getServiceIntroduction() {
        return serviceIntroduction;
    }

    public void setServiceIntroduction(String serviceIntroduction) {
        this.serviceIntroduction = serviceIntroduction;
    }

}
