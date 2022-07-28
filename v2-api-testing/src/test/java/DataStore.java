import java.util.LinkedHashMap;

/*
this approach is to implement
passing parameters from one test to another
*/

public class DataStore {
    private DataStore() {};

    private static LinkedHashMap<String, Object> dataMap = new LinkedHashMap<String,Object>();

    public static void setValue(String key, Object value){
        dataMap.put(key,value);
    }

    public static Object getValue(String key){
        return dataMap.get(key);
    }

}
