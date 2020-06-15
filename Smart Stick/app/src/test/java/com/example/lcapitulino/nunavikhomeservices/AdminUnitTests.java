package com.example.lcapitulino.nunavikhomeservices;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class AdminUnitTests {
    private HomeService homeService = HomeService.getInstance();

    @Before
    public void setUp() {
    }

    @Test
    public void checkCreateAdmin() {
        assertEquals(homeService.adminExists(), false);
        homeService.adminCreate("1234");
        assertEquals(homeService.adminExists(), true);

        User user = homeService.findUser("admin");
        assertEquals(user.getUserName(), "admin");
        assertEquals(user.getPassword(), "1234");
        assertEquals(user.getRole(), User.Role.ADMIN);
    }

    @Test
    public void checkCreateNewServices() {
        assertEquals(homeService.serviceExists("teste"), false);
        Service serv = new Service("teste", 1.5);
        homeService.serviceAdd(serv);
        assertEquals(homeService.serviceExists("teste"), true);
        serv = homeService.serviceFind("teste");
        assertEquals(serv.getName(), "teste");
        assertEquals(serv.getRateString(), "1.5");

        assertEquals(homeService.serviceExists("teste2"), false);
        serv = new Service("teste2", 100.0);
        homeService.serviceAdd(serv);
        assertEquals(homeService.serviceExists("teste2"), true);
        serv = homeService.serviceFind("teste2");
        assertEquals(serv.getName(), "teste2");
        assertEquals(serv.getRateString(), "100.0");
    }

    @Test
    public void checkRemoveServices() {
        Service serv = new Service("teste", 1.5);
        homeService.serviceAdd(serv);
        assertEquals(homeService.serviceExists("teste"), true);
        homeService.serviceRemove("teste");
        assertEquals(homeService.serviceExists("teste"), false);
    }
}
