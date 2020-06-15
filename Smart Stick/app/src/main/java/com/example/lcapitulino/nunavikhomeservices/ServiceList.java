package com.example.lcapitulino.nunavikhomeservices;

import java.util.ArrayList;

class Service {
    private String name;
    private Double rate;
    private ArrayList<ServiceProvider> sProviderList;

    @Override
    public String toString() {
        return name + ", " + rate + "$ hourly rate";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public void setRate(String rate) {
        this.rate = Double.parseDouble(rate);
    }

    public String getRateString() {
        return Double.toString(rate);
    }

    public boolean isService(String name) {
        return getName().equals(name);
    }

    public ArrayList<ServiceProvider> getProviderList() {
        return sProviderList;
    }

    public ServiceProvider findServiceProvider(ServiceProvider sProvider) {
        for (ServiceProvider provider : sProviderList) {
            if (provider.isEquals(sProvider)) {
                return provider;
            }
        }
        return null;
    }

    public boolean ServiceProviderExists(ServiceProvider sProvider) {
        return findServiceProvider(sProvider) != null;
    }

    public void addServiceProvider(ServiceProvider sProvider) {
        if (ServiceProviderExists(sProvider))
            throw new IllegalArgumentException("service provider already added");
        sProviderList.add(sProvider);
    }

    public void removeServiceProvider(ServiceProvider sProvider) {
        ServiceProvider tmp = findServiceProvider(sProvider);
        if (tmp != null)
            sProviderList.remove(sProvider);
    }

    public Service (String name, Double rate) {
        this.name = name;
        this.rate = rate;
        sProviderList = new ArrayList<ServiceProvider>();
    }
}

public class ServiceList {
    private ArrayList<Service> servicesList;

    public Service find(String name) {
        for (Service serv : servicesList) {
            if (serv.isService(name))
                return serv;
        }
        return null;
    }

    public boolean exists(String name) {
        Service serv = find(name);
        if (serv != null)
            return true;
        return false;
    }

    public void add(Service serv) {
        if (exists(serv.getName()))
            throw new IllegalArgumentException("Service already exists");
        servicesList.add(serv);
    }

    public void remove(String name) {
        Service serv = find(name);
        if (serv != null)
            servicesList.remove(serv);
    }

    public void remove(Service serv) {
        if (exists(serv.getName()))
            servicesList.remove(serv);
    }

    public ArrayList<String> getLabelList() {
        ArrayList<String> sList = new ArrayList<String>();

        for (Service serv : servicesList)
            sList.add(serv.toString());

        return sList;
    }

    public Service getServiceFromLabel(String label) {
        for (Service serv : servicesList) {
            if (label.equals(serv.toString()))
                return serv;
        }
        return null;
    }

    public ServiceList() {
        servicesList = new ArrayList<Service>();
    }
}
