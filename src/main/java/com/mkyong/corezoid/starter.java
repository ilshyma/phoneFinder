package com.mkyong.corezoid;

import com.mkyong.corezoid.entity.CorezoidMessage;
import com.mkyong.corezoid.entity.RequestOperation;
import com.mkyong.corezoid.entity.RequestOperationGetInfo;
import com.mkyong.corezoid.utils.HttpManager;

import org.apache.http.HttpException;


import org.json.simple.JSONAware;
import org.apache.http.HttpException;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;


import java.util.Arrays;
import java.util.Iterator;
import java.util.List;


/**
 * Created by star on 07.01.2016.
 */
public class starter {
    public static void main(String[] args) {


        //Connections settings----------------------------------------
        // ID conveyor
        String conv_id = "83427";
        // API user settings
        String apiLogin = "8190";
        String apiSecret = "reVy6w0oDtSMrIwCFep0Pfv7zMhaJAGDqJ4b6Cg8Kcl583aHO8";
        //-------------------------------------------------------------

        String answer = null;

        try {

            RequestOperationGetInfo operation = RequestOperationGetInfo.show(conv_id, "568fe08182ba9668af3990ed");
            // Add task to list
            List<RequestOperationGetInfo> ops = Arrays.asList(operation);
            // Build a Message
            CorezoidMessage message = CorezoidMessage.requestGet(apiSecret, apiLogin, ops);
            // Create HttpManager instance
            HttpManager http = new HttpManager();
            // Send all tasks to Middleware
           answer =  http.send(message);
        } catch (HttpException ex) {
            System.out.println(ex);
        }


        System.out.println(answer);
      /*
        String test = "{
                        "request_proc":"ok",
                        "ops":[
                                {
                                "id":"",
                                "proc":"ok",
                                "obj":"task",
                                "ref":"test1452269708415981",
                                "obj_id":"568fe08182ba9668af3990ed",
                                "task_id":"568fe08182ba9668af3990ed",
                                "reference":"test1452269708415981",
                                "status":4,"user_id":8139,
                                "create_time":1452269697,
                                "change_time":1452269697,
                                "node_id":"5676d99f60e32731f23fac7b",
                                "node_prev_id":"5676d99f60e32731f23fac7a",
                                "data":
                                    {
                                        "name":"vlad",
                                        "phone":"1231231231"
                                    }
                                    }
                               ]
                            }";
*/

        try {
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(answer);
            String request_proc = (String) jsonObject.get("request_proc");
            System.out.println("request_proc is: " + request_proc);

            JSONArray ops = (JSONArray) jsonObject.get("ops");
            for(int i=0; i<ops.size(); i++){
                System.out.println("The " + i + " element of the array: "+ops.get(i));
            }

            Iterator i = ops.iterator();
            while (i.hasNext()) {
                JSONObject innerObj = (JSONObject) i.next();
                JSONObject innerObjData = (JSONObject) innerObj.get("data");
                System.out.println("innerObjData: " + innerObj.get("data"));
                System.out.println("name = " +innerObjData.get("name"));
            }
        } catch (ParseException ex) {
            ex.printStackTrace();
        } catch (NullPointerException ex) {
            ex.printStackTrace();
        }

    }
}


