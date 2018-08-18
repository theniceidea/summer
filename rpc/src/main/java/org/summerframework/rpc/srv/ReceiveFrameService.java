package org.summerframework.rpc.srv;

import org.springframework.stereotype.Service;
import org.summerframework.model.SummerService;
import org.summerframework.rpcmodel.ReceiveFrame;

import java.util.logging.Logger;

@Service
@SummerService
public class ReceiveFrameService {
    private static Logger logger = Logger.getLogger(ReceiveFrameService.class.getName());

    public void receiveFrame(ReceiveFrame receiveFrame){
    }
}
