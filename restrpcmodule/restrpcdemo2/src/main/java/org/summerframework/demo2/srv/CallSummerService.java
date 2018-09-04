package org.summerframework.demo2.srv;

import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;
import org.summerframework.model.Summer;
import org.summerframework.model.SummerService;
import org.summerframework.restrpcmodel.CallSummer;
import org.summerframework.restrpcmodel.CallSummerResult;

import java.io.PrintWriter;
import java.io.StringWriter;

@Service
@SummerService
public class CallSummerService {

    @SuppressWarnings("all")
    public void callSummer(CallSummer mod){
        CallSummerResult csr = new CallSummerResult();
        mod.setSummerResult(csr);

        csr.setErrCode("0");
        csr.setErrMsg("");
        try {
            Summer summer = mod.getSummer();
            Object o = summer.sum();
            csr.setResult(o);
        }catch (Exception e){
            csr.setErrCode("-1");
            csr.setErrMsg(e.getMessage());
            csr.setResult(getTrace(e));
        }
    }
    @SuppressWarnings("all")
    private String getTrace(Exception e){
        StringWriter sw = null;
        PrintWriter pw = null;
        try{
            sw = new StringWriter();
            pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            return sw.toString();
        }catch (Exception e2){
            IOUtils.closeQuietly(sw);
            IOUtils.closeQuietly(pw);
        }
        return "";
    }
}
