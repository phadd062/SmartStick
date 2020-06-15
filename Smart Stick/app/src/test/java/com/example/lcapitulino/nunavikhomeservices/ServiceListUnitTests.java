package com.example.lcapitulino.nunavikhomeservices;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Simple unit-tests for the ServiceList class
 */
public class ServiceListUnitTests {
    private ServiceList mServiceList = null;

    @Before
    public void setUp() {
        mServiceList = new ServiceList();
    }

    @Test
    public void checkNewService() {
        Service serv = new Service("teste", 10.5);
        assertEquals(serv.getName(), "teste");
        assertEquals((Object) serv.getRate(), 10.5);
        assertEquals(serv.getRateString(), "10.5");
    }

    @Test
    public void checkAddNewService() {
        Service serv = new Service("teste", 1.6);
        mServiceList.add(serv);
        serv = mServiceList.find("teste");
        assertNotNull(serv);
        assertEquals(serv.getName(), "teste");
        assertEquals(serv.getRateString(), "1.6");
    }

    @Test
    public void checkRemoveService() {
        Service serv = new Service("teste", 1.6);
        mServiceList.add(serv);
        mServiceList.remove("teste");
        serv = mServiceList.find("teste");
        assertNull(serv);
    }

    @Test
    public void checkExistsService() {
        assertEquals(mServiceList.exists("teste"), false);
        Service serv = new Service("teste", 1.6);
        mServiceList.add(serv);
        assertEquals(mServiceList.exists("teste"), true);

    }
}