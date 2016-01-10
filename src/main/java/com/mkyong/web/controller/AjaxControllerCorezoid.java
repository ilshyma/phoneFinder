package com.mkyong.web.controller;

/**
 * Created by star on 07.01.2016.
 */

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mkyong.corezoid.CreateTask;
import com.mkyong.web.jsonview.Views;
import com.mkyong.web.model.AjaxResponseBody;
import com.mkyong.web.model.SearchCriteria;
import com.mkyong.web.model.User;

import net.sf.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;


@RestController
public class AjaxControllerCorezoid {

    List<User> users;

    // @ResponseBody, not necessary, since class is annotated with @RestController
    // @RequestBody - Convert the json data into object (SearchCriteria) mapped by field name.
    // @JsonView(Views.Public.class) - Optional, limited the json data display to client.
    @JsonView(Views.Public.class)
    @RequestMapping(value = "/api/addTask2Corezoid")
    public AjaxResponseBody getSearchResultViaAjax(@RequestBody SearchCriteria addTask) throws IllegalAccessException, InstantiationException, IOException, ParseException, InterruptedException {

        CreateTask task = new CreateTask();
        task.addTask(addTask.getPhone());
        Thread.sleep(2000);
        task.findTask();
        AjaxResponseBody result = new AjaxResponseBody();

        result.setMsg("Send task to corezoid with reference " + task.getRefCorezoidTask());
        result.setResult(task.getAnswerForRequest());

        return result;

    }
}


