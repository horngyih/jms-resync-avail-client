package com.ubicompsystem.integration.client;

import static org.junit.Assert.*;

import com.ubicompsystem.integration.client.config.ResyncClientConfig;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
    "classpath*:spring/applicationContext-resync-client.xml"
})
public class ResyncClientTest{
    @Autowired
    ResyncClient client;

    @Before
    public void buildMeUp(){

    }

    @After
    public void letMeDown(){

    }

    @Test
    public void defaultTest(){
        assertNotNull("Should autowire a non-null ResyncClient", client );
        client.resyncProperty(new String[]{"BHS","BHCM"}, "RATETIGER", "TESTQUEUE");
    }

}