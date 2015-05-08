package com.example.ethan.paclient_android;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ethan on 04/13/2015.
 */
public class SPCatalogWrap implements Serializable{
    private List<ServiceProviderForPA> sps;
    private List<ServiceForPA> services;

    public SPCatalogWrap() {

        services = new ArrayList<ServiceForPA>();
        sps = new ArrayList<ServiceProviderForPA>();

    }

    public List<ServiceForPA> getServices() {
        return services;
    }

    public void setServices(ArrayList<ServiceForPA> services) {
        this.services = services;
    }

    public List<ServiceProviderForPA> getSps() {
        return sps;
    }

    public void setSps(ArrayList<ServiceProviderForPA> sps) {
        this.sps = sps;
    }


}
