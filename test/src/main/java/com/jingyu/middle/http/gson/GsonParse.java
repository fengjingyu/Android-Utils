package com.jingyu.middle.http.gson;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.jingyu.utils.function.Logger;
import com.jingyu.utils.util.UtilString;

import java.io.StringReader;
import java.lang.reflect.Type;

public class GsonParse {
    private static Gson gson = null;

    private static Gson getGson() {
        if (gson == null) {
            gson = new Gson();
        }
        return gson;
    }

    public static String toJson(Object bean) {
        return getGson().toJson(bean);
    }

    /**
     * @param bean
     * @param typeOfSrc Type typeOfSrc = new TypeToken<ArrayList<Object>>(){}.getType();
     * @return
     */
    public static String toJson(Object bean, Type typeOfSrc) {
        return getGson().toJson(bean, typeOfSrc);
    }

    public static <T> Object parseJson(String json, Class<T> objClass) {
        if (!UtilString.isBlank(json)) {
            try {
                return getGson().fromJson(json, objClass);
            } catch (JsonSyntaxException e) {
                e.printStackTrace();
                Logger.e("Parse json error: ", e);
            }
        }
        return null;
    }

    /**
     * @param json
     * @param typeOfT Type typeOfSrc = new TypeToken<ArrayList<Object>>(){}.getType();
     * @return
     */
    public static <T> Object fromJson(String json, Type typeOfT) {
        if (!UtilString.isBlank(json)) {
            try {
                return getGson().fromJson(json, typeOfT);
            } catch (JsonSyntaxException e) {
                e.printStackTrace();
                Logger.e("Parse json error: ", e);
            }
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public static <T> T fromJson(String json, Class<T> type) {
        if (json == null) {
            return null;
        }
        JsonReader reader = new JsonReader(new StringReader(json));
        reader.setLenient(true);
        return (T) getGson().fromJson(reader, TypeToken.get(type).getType());
    }
}
