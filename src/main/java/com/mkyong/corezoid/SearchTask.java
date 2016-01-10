package com.mkyong.corezoid;

import com.mkyong.corezoid.entity.CorezoidMessage;
import com.mkyong.corezoid.entity.RequestOperationGetInfo;
import com.mkyong.corezoid.utils.HttpManager;
import org.apache.http.HttpException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * Created by star on 09.01.2016.
 */
public class SearchTask {

    private String answerFromCorezoid;

    public String getAnswerForRequest() {
        return answerForRequest;
    }

    private String answerForRequest;

    //Connections settings----------------------------------------
    // ID conveyor
    String conv_id = "83427";
    // API user settings
    String apiLogin = "8190";
    String apiSecret = "reVy6w0oDtSMrIwCFep0Pfv7zMhaJAGDqJ4b6Cg8Kcl583aHO8";
    //-------------------------------------------------------------


    public void findTask( String refOrId){



        RequestOperationGetInfo operation = RequestOperationGetInfo.show(conv_id, refOrId);
        // Add task to list
        List<RequestOperationGetInfo> ops = Arrays.asList(operation);
        // Build a Message
        CorezoidMessage message = CorezoidMessage.requestGet(apiSecret, apiLogin, ops);
        // Create HttpManager instance
        HttpManager http = new HttpManager();
        // Send all tasks to Middleware
        try {
            answerFromCorezoid = http.send(message);
        } catch (HttpException e) {
            e.printStackTrace();
        }


        //Parsing answer from corezoid
        try {
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(answerFromCorezoid);
            String request_proc = (String) jsonObject.get("request_proc");
            System.out.println("request_proc is: " + request_proc);

            JSONArray opsAnswer = (JSONArray) jsonObject.get("ops");
            for(int i=0; i<opsAnswer.size(); i++){
                System.out.println("The " + i + " element of the array: "+opsAnswer.get(i));
            }

            Iterator i = opsAnswer.iterator();
            while (i.hasNext()) {
                JSONObject innerObj = (JSONObject) i.next();
                JSONObject innerObjData = (JSONObject) innerObj.get("data");
                answerForRequest = (String) innerObjData.get("name");
            }
        } catch (ParseException ex) {
            ex.printStackTrace();
        } catch (NullPointerException ex) {
            ex.printStackTrace();
        }

    }


}
