package com.mkyong.corezoid.entity;

import net.sf.json.JSONObject;

/**
 * Created by star on 09.01.2016.
 */
public class RequestOperationGetInfo {
    //----------------------------------------------------------------------------------------------------------------------

    /**
     * Builder for 'Show task' operation
     *
     * @param convId - conveyor id
     * @param ref_or_obj_id - task reference
     * @return
     */
    public static RequestOperationGetInfo show(String convId, String ref_or_obj_id) {

        return new RequestOperationGetInfo(Query.show, convId, ref_or_obj_id, null, null);
    }

//----------------------------------------------------------------------------------------------------------------------

    /**
     * @param type - request type
     * @param convId - conveyor id
     * @param ref_or_obj_id - task reference
     * @param data - task data
     */
    private RequestOperationGetInfo(Query type, String convId, String ref_or_obj_id, String objId,
                             JSONObject data) {
        if(convId == null || convId.equals("") ){
            throw new IllegalArgumentException("convId is null or empty");
        }
        if(objId == null && ref_or_obj_id == null ){
            throw new IllegalArgumentException("ref and taskId is null");
        }
        if(ref_or_obj_id == null && objId.equals("") ){
            throw new IllegalArgumentException("taskId is empty");
        }
        if(objId == null && ref_or_obj_id.equals("") ){
            throw new IllegalArgumentException("ref is empty");
        }

        this.ref_or_obj_id = ref_or_obj_id;
        this.objId = objId;
        this.type = type;
        this.obj = "task";
        this.convId = convId;
    }

    //----------------------------------------------------------------------------------------------------------------------
    public JSONObject toJSONObject() {
        JSONObject res = new JSONObject();
        if(ref_or_obj_id!= null){
            res.element("ref_or_obj_id", ref_or_obj_id);
        }
        if(objId!= null){
            res.element("obj_id", objId);
        }
        res.element("conv_id", convId);
        res.element("type", type);
        res.element("obj", obj);
        return res;
    }
    //----------------------------------------------------------------------------------------------------------------------
    private final String convId;
    private final String objId;
    private final String ref_or_obj_id;
    private final Query type;
    private final String obj;


    private enum Query {

        show
    }
}

