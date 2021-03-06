package diamondpick.dd_backend.Tool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 此类用来产生Json数组，使用方法
 */
public class JsonArray {
    public JsonArray(String... key){
        this.keys = key;
    }
    private List<Map<String, Object>> arr  = new ArrayList<>();
    private String[] keys;
    public void add(Map<String, Object> json){
        arr.add(json);
    }
    public void add(Object... values){
        HashMap<String,Object> map = new HashMap<>();
        for(int i = 0; i < values.length; i++){
            map.put(keys[i], values[i]);
        }
        arr.add(map);
    }

    public List<Map<String, Object>> get(){
        return arr;
    }
}
