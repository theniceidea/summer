package org.summerframework.demo1.srv;

import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.summerframework.model.RemoteServiceSummer;
import org.summerframework.model.Summer;
import org.summerframework.model.SummerService;
import org.summerframework.restrpcmodel.CallRestrpcSummer;
import org.summerframework.restrpcmodel.CallSummerResult;

@Service
@SummerService
public class RemoteSummer {
    public void remortSummer(RemoteServiceSummer mod){
        Summer summer = mod.getSummerSum();
        CallSummerResult result = CallRestrpcSummer.sum(summer);
        if("0".equals(result.getErrCode())) {
            if(StringUtils.isEmpty(result.getResult())) { return; }

            Summer summerResult = JSON.parseObject((String) result.getResult(), summer.getClass());
            summer.setResult(summerResult.getResult());

        }else if("-1".equals(result.getErrCode())){
            throw new RuntimeException((String) result.getResult());
        }
    }
}
