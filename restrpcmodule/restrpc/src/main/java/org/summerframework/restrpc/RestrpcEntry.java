package org.summerframework.restrpc;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.summerframework.restrpcmodel.ReceiveRestrpcSummer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("summerentry")
public class RestrpcEntry {

    @PostMapping
    public void summerentry(
        HttpServletRequest request,
        HttpServletResponse response
    ){
        ReceiveRestrpcSummer.sum(request, response);
    }
}
