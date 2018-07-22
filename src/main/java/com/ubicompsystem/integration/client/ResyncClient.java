package com.ubicompsystem.integration.client;

import java.util.Arrays;

import com.ubicompsystem.data.changelog.AvailabilityDateChangeLogData;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;

public class ResyncClient{

    @Autowired
    JmsTemplate jmsTemplate;

    public void resyncProperty( String[] propertyCodes, String channelCode, String queueName ){
        if( propertyCodes != null ){
            for( String propertyCode : propertyCodes ){
                AvailabilityDateChangeLogData resyncMessage = new AvailabilityDateChangeLogData();
                resyncMessage.setPropertyCode(propertyCode.trim());
                resyncMessage.setTargetChannel(channelCode);
                resyncMessage.setAuthenticated("RESYNC");

                jmsTemplate.convertAndSend(queueName, Arrays.asList(new AvailabilityDateChangeLogData[]{resyncMessage}));
            }
        }
    }
}