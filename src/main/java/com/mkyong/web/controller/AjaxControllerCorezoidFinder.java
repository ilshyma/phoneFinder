package com.mkyong.web.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.mkyong.corezoid.CreateTask;
import com.mkyong.corezoid.SearchTask;
import com.mkyong.web.jsonview.Views;
import com.mkyong.web.model.AjaxResponseBody;
import com.mkyong.web.model.SearchCriteria;
import org.json.simple.parser.ParseException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * Created by star on 09.01.2016.
 */
@RestController
public class AjaxControllerCorezoidFinder {

    @JsonView(Views.Public.class)
    @RequestMapping(value = "/api/findTaskInCorezoid")
    public AjaxResponseBody getSearchResultViaAjax(@RequestBody SearchCriteria searchTaskByRef) throws IllegalAccessException, InstantiationException, IOException, ParseException {

        CreateTask task = new CreateTask();
        task.findTask();
        AjaxResponseBody result = new AjaxResponseBody();

        //result.setCode("200");
        result.setMsg("");
        result.setResult(task.getAnswerForRequest());

        return result;

    }
}
