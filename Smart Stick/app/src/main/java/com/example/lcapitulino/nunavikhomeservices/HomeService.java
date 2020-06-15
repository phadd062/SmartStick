package com.example.lcapitulino.nunavikhomeservices;

import java.util.ArrayList;

/* Singleton Facade class for the entire system */
public class HomeService {
    private ServiceList serviceList;
    private Admin admin = null;
    private ArrayList<HomeOwner> homeOwnerList;
    private ArrayList<ServiceProvider> sproviderList;
    private static HomeService thisInstance;

    /* ServiceList methods */
    public Service serviceFind(String name) {
        return serviceList.find(name);
    }

    public boolean serviceExists(String name) {
        return serviceList.exists(name);
    }

    public void serviceAdd(Service serv) {
        serviceList.add(serv);
    }

    public void serviceRemove(String name) {
        serviceList.remove(name);
    }

    public ArrayList<String> getLabelList() {
        return serviceList.getLabelList();
    }

    public Service getServiceFromLabel(String label) {
        return serviceList.getServiceFromLabel(label);
    }

    /* Provider methods */
    public ServiceProvider findProvider(String username) {
        for (ServiceProvider sProvider : sproviderList) {
            if (sProvider.getUserName().equals(username))
                return sProvider;
        }

        return null;
    }

    public void addServiceProvider(ServiceProvider sProvider) {
        if (userExists(sProvider))
            throw new IllegalArgumentException("username already exists");
        sproviderList.add(sProvider);
    }

    public Service searchServiceByName(String name) {
        return serviceList.find(name);
    }

    public void addHomeOwner(HomeOwner homeOwner) {
        if (userExists(homeOwner))
            throw new IllegalArgumentException("username already exists");
        homeOwnerList.add(homeOwner);
    }

    public HomeOwner searchHomeOwner(String username) {
        for (HomeOwner homeOwner : homeOwnerList) {
            if (homeOwner.getUserName().contentEquals(username))
                return homeOwner;
        }

        return null;
    }

    /* User management methods */
    public boolean userExists(User user) {
        return findUser(user.getUserName()) != null;
    }

    public User findUser(String username) {
        if (username.equals("admin"))
            return (User) admin;

        HomeOwner homeOwner = searchHomeOwner(username);
        if (homeOwner != null)
            return (User) homeOwner;

        ServiceProvider provider = findProvider(username);
        if (provider != null)
            return (User) provider;

        return null;
    }

    public boolean adminExists() {
        return admin != null;
    }

    public void adminCreate(String password) {
        if (adminExists())
            throw new IllegalArgumentException("admin user already exits");
        admin = new Admin(password);
    }

    private HomeService() {
        serviceList = new ServiceList();
        sproviderList = new ArrayList<ServiceProvider>();
        homeOwnerList = new ArrayList<HomeOwner>();
    }

    public static HomeService getInstance() {
        if (thisInstance == null)
            thisInstance = new HomeService();
        return thisInstance;
    }
}