package com.mkyong.corezoid;
import com.mkyong.corezoid.entity.CorezoidMessage;
import com.mkyong.corezoid.entity.RequestOperation;
import com.mkyong.corezoid.entity.RequestOperationGetInfo;
import com.mkyong.corezoid.utils.HttpManager;
import net.sf.json.JSONObject;
import org.apache.http.HttpException;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.json.simple.JSONArray;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 * Created by star on 07.01.2016.
 */
public class CreateTask {
    //Connections settings----------------------------------------
    String conv_id = "83427";
    String apiLogin = "8190";
    String apiSecret = "reVy6w0oDtSMrIwCFep0Pfv7zMhaJAGDqJ4b6Cg8Kcl583aHO8";
    //-------------------------------------------------------------

    //Params for send to corezoid
    String name;
    String tel;
    //---------------------------

    private String answerJSON;
    private String refCorezoidTask;
    private String finishStatus;

    private String answerFromCorezoid;

    public String getAnswerForRequest() {
        return answerForRequest;
    }

    private String answerForRequest;

    public String getRefCorezoidTask() {
        return refCorezoidTask;
    }
    public String getAnswerJSON() {
        return answerJSON;
    }
    public String getFinishStatus() {
        return finishStatus;
    }


    public void addTask(String PhoneNumber) throws ParseException {
        try {

            // Create task
            JSONObject data = new JSONObject()
                    .element("phone", PhoneNumber);
            String ref = "test" + System.currentTimeMillis() + new Random(System.currentTimeMillis()).nextInt(1000);
            refCorezoidTask = ref;
            RequestOperation operation = RequestOperation.create(conv_id, ref, data);
            // Add task to list
            List<RequestOperation> ops = Arrays.asList(operation);
            // Build a Message
            CorezoidMessage message = CorezoidMessage.request(apiSecret, apiLogin, ops);
            // Create HttpManager instance
            HttpManager http = new HttpManager();
            // Send all tasks to Middleware
            answerJSON = http.send(message);



            //Parsing JSON-answer from corezoid
            JSONParser parser = new JSONParser();
            try {
                JSONParser jsonParser = new JSONParser();
                org.json.simple.JSONObject jsonObject = (org.json.simple.JSONObject) jsonParser.parse(answerJSON);
                finishStatus = (String) jsonObject.get("request_proc");

                JSONArray opss = (JSONArray) jsonObject.get("ops");
                Iterator i = opss.iterator();
                while (i.hasNext()) {
                    org.json.simple.JSONObject innerObj = (org.json.simple.JSONObject) i.next();
                    refCorezoidTask = (String) innerObj.get("ref");
                }
            } catch (ParseException ex) {
                ex.printStackTrace();
            } catch (NullPointerException ex) {
                ex.printStackTrace();
            }
            //---------------------------------
        } catch (HttpException ex) {
            System.out.println(ex);
        }
        }


    public void findTask(){



        RequestOperationGetInfo operation = RequestOperationGetInfo.show(conv_id, refCorezoidTask);
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
            org.json.simple.JSONObject jsonObject = (org.json.simple.JSONObject) jsonParser.parse(answerFromCorezoid);
            String request_proc = (String) jsonObject.get("request_proc");
            System.out.println("request_proc is: " + request_proc);

            JSONArray opsAnswer = (JSONArray) jsonObject.get("ops");
            for(int i=0; i<opsAnswer.size(); i++){
                System.out.println("The " + i + " element of the array: "+opsAnswer.get(i));
            }

            Iterator i = opsAnswer.iterator();
            while (i.hasNext()) {
                org.json.simple.JSONObject innerObj = (org.json.simple.JSONObject) i.next();
                org.json.simple.JSONObject innerObjData = (org.json.simple.JSONObject) innerObj.get("data");
                answerForRequest = (String) innerObjData.get("name");
            }
        } catch (ParseException ex) {
            ex.printStackTrace();
        } catch (NullPointerException ex) {
            ex.printStackTrace();
        }

    }



}

