package diamondpick.dd_backend.Tool;

import java.util.HashMap;

/**
 * 使用方式：
 * 假设要返回如下json：
 * <pre>
 *   {
 *      "name" : "giao",
 *      "id" : "123
 *   }
 * </pre>
 * 只需要：
 * <pre>
 *     Json json = new Json("name", "id");
 *     return json.get("giao", 123);
 *     // 可以使用同一个Json对象模板返回多个不同值的json
 *     return json.get("zzy", 124);
 * </pre>
 */
public class Json{
    public Json(String... key){
        this.keys = key;
    }
    private String[] keys;
    public HashMap<String,Object> get(Integer code, Object... values){
        HashMap<String,Object> map = new HashMap<>();
        for(int i = 0; i < values.length; i++){
            if(values[i] instanceof JsonArray){
                map.put(keys[i], ((JsonArray) values[i]).get());
            }else{
                map.put(keys[i], values[i]);
            }
        }
        return map;
    }
}
