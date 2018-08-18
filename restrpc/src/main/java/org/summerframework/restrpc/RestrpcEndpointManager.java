package org.summerframework.restrpc;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.summerframework.model.Summer;
import org.summerframework.model.SummerService;
import org.summerframework.restrpcmodel.GetRestrpcEndpoint;
import org.summerframework.restrpcmodel.GetRestrpcEndpoints;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Service
@SummerService
public class RestrpcEndpointManager {
    private static ConcurrentHashMap<String, Set<String>> ENDPOINTS = new ConcurrentHashMap<>();

    public void getRestrpcEndpoint(GetRestrpcEndpoint model){
        Summer<?> summer = model.getSummer();
        String className = summer.getClass().getName();

        Set<String> rpcEndPoints = ENDPOINTS.get(className);
        if(CollectionUtils.isEmpty(rpcEndPoints)){
            Set<String> pointList = GetRestrpcEndpoints.sum(className);


        }



    }
}
