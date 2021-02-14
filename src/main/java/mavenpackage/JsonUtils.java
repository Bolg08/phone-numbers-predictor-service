package mavenpackage;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class JsonUtils {
	static Gson gson = new Gson();
	public static <K,V> Map<K,V> jsonToMap(String jsonString) throws Exception{
		Type type = new TypeToken<Map<K, V>>(){}.getType(); 
		Map<K, V> resultMap = gson.fromJson(jsonString, type);
		return resultMap;
	}
	public static <T> String toJsonString(List<T> items){
		return gson.toJson(items);
	}
}
