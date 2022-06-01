package diamondpick.dd_backend.Tool;

import java.util.HashMap;


public class Response{
    public Response(String... key){
        this.keys = key;
    }
    private String[] keys;
    public HashMap<String,Object> get(Integer code, Object... values){
        HashMap<String,Object> map = new HashMap<>();
        if(code != null){
            map.put("code", code);
        }
        for(int i = 0; i < values.length; i++){
            map.put(keys[i], values[i]);
        }
        return map;
    }
}

