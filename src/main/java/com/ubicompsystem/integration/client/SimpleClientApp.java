package com.ubicompsystem.integration.client;

import java.util.Arrays;

import com.ubicompsystem.data.changelog.AvailabilityDateChangeLogData;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.jms.core.JmsTemplate;

public class SimpleClientApp{
    JmsTemplate jmsTemplate;
    ActiveMQConnectionFactory connectionFactory;

    public SimpleClientApp(String queueURL){
        connectionFactory = new ActiveMQConnectionFactory(queueURL);
        System.out.println( "Connecting to " + queueURL);
        jmsTemplate = new JmsTemplate(connectionFactory);
    }

    public void resyncProperties( String[] propertyCodes, String channelCode, String queueName ){
        if( propertyCodes != null && !"".equals(channelCode.trim()) && !"".equals(queueName.trim())){
            for( String propertyCode : propertyCodes ){
                if( !"".equals(propertyCode.trim())){
                    resyncPropertyAvailability(propertyCode, channelCode, queueName);
                }
            }
        }
    }

    public void resyncPropertyAvailability( String propertyCode, String channelCode, String queueName ){
        AvailabilityDateChangeLogData resyncMessage = new AvailabilityDateChangeLogData();
        resyncMessage.setPropertyCode(propertyCode);
        resyncMessage.setTargetChannel("PROP");
        resyncMessage.setAuthenticated("RESYNC");

        System.out.println( "Sending resyncing message for " + propertyCode + " to " + channelCode + " via " + queueName );
        jmsTemplate.convertAndSend( queueName, Arrays.asList(new AvailabilityDateChangeLogData[]{resyncMessage}));
    }

    public static void main(String[] args){
        String queueURL = System.getProperty("url", "tcp://activemq:61616");
        if( args.length > 2 ){
            queueURL = args[2].trim();
        }

        String propertyCodesParam = System.getProperty("PropertyCodes", "RATETIGER");
        if( args.length > 0 ){
            propertyCodesParam = args[0].trim();
        }

        String channelParam = System.getProperty("Channel", "");
        if( args.length > 1 ){
            channelParam = args[1].trim();
        }

        String queueName = "push"+channelParam+"AvailabilityQueue";

        SimpleClientApp app = new SimpleClientApp(queueURL);
        app.resyncProperties(propertyCodesParam.split(","), channelParam, queueName);
    }
}