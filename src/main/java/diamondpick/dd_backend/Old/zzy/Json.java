package diamondpick.dd_backend.Old.zzy;

import java.util.HashMap;

public class Json{
    public Json(String... key){
        this.keys = key;
    }
    private String[] keys;
    public HashMap<String,Object> get(Integer code, Object... values){
        HashMap<String,Object> map = new HashMap<>();
        for(int i = 0; i < values.length; i++){
            map.put(keys[i], values[i]);
        }
        return map;
    }
}
