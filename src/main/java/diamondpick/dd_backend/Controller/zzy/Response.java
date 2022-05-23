package diamondpick.dd_backend.Controller.zzy;

import java.util.HashMap;

public class Response{
    public Response(String... key){
        this.keys = key;
    }
    private String[] keys;
    private HashMap<String,Object> map;
    public void generateKey(String... keys){
        this.keys = keys;
    }
    public HashMap<String,Object> set(Integer code, Object... values){
        HashMap<String,Object> map = new HashMap<>();
        if(code != null){
            map.put("code", code);
        }
        for(int i = 0; i < keys.length; i++){
            map.put(keys[i], values[i]);
        }
        return map;
    }
    public HashMap<String,Object> get(){
        return map;
    }
}
