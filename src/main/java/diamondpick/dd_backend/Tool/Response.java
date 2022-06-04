package diamondpick.dd_backend.Tool;

import java.util.HashMap;

/**
 * 使用方式：
 * 假设要返回如下json：
 * <pre>
 *   {
 *      "code" : 0
 *      "name" : "giao",
 *      "id" : "123
 *   }
 * </pre>
 * 只需要：
 * <pre>
 *     Response r = new Response("name", "id");
 *     return r.get(0, "giao", 123);
 *     // 可以使用同一个Response对象模板返回多个不同值的json
 *     return r.get("zzy", 124);
 * </pre>
 */
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
            if(values[i] instanceof JsonArray){
                map.put(keys[i], ((JsonArray) values[i]).get());
            }else{
                map.put(keys[i], values[i]);
            }
        }
        return map;
    }
}

