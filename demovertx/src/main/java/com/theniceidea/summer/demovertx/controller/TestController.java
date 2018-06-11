package com.theniceidea.summer.demovertx.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.theniceidea.summer.demovertx.model.TestModel;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import static com.theniceidea.summer.core.srv.Result.NewResult;

/**
 *
 */
@RestController
@RequestMapping("/test")
public class TestController {

    private ObjectMapper objectMapper = new ObjectMapper();

    @ApiOperation(value = "test")
//    @PostMapping(value = "test")
    @GetMapping(value = "test")
    public boolean orderMessage(
        @RequestBody @ApiParam()TestModel testModel
        ) throws JsonProcessingException {

//        String s = objectMapper.writeValueAsString(testModel);
//        System.out.println("=======================================");
//        System.out.println(s);
//        System.out.println("=======================================");
        testModel.setResult(NewResult(true));

        return (boolean) testModel.getResult().getValue();
    }

}
