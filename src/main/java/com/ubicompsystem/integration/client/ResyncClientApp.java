package com.ubicompsystem.integration.client;

import com.ubicompsystem.integration.client.config.ResyncClientConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ResyncClientApp{

    @Autowired
    ResyncClient client;

    public ResyncClientApp(){
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext( ResyncClientConfig.class );
        ctx.getAutowireCapableBeanFactory().autowireBean(this);
    }

    public void resyncProperty( String[] args ){
        String propertyCodesParam = System.getProperty("PropertyCodes", "" ).trim();
        if( args != null && args.length > 0 ){
            propertyCodesParam = args[0].trim();
        }

        String channelCode = System.getProperty("Channel", "").trim();
        if( args != null && args.length > 1 ){
            channelCode = args[1].trim();
        }

        String[] propertyCodes = propertyCodesParam.split(",");
        String queueName = "push" + channelCode + "AvailabilityQueue";
        client.resyncProperty(propertyCodes, channelCode, queueName);
    }

    public static void main( String[] args ){
        ResyncClientApp app = new ResyncClientApp();
        app.resyncProperty(args);
    }
}