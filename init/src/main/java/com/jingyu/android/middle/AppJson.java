package com.jingyu.android.middle;

import com.google.gson.Gson;
import com.jingyu.utils.function.Logger;
import com.jingyu.utils.util.UtilString;

import java.lang.reflect.Type;

public class AppJson {
    private static Gson gson = null;

    public static Gson getGson() {
        if (gson == null) {
            synchronized (AppJson.class) {
                if (gson == null) {
                    gson = new Gson();
                    //gson = new GsonBuilder().serializeNulls().create();
                }
            }
        }
        return gson;
    }

    //----------------------------------------bean2json------------------------------------------
    public static String toJson(Object bean) {
        return getGson().toJson(bean);
    }

    //----------------------------------------json2bean------------------------------------------
    public static <T> T parseJson(String json, Class<T> clazz) {
        if (!UtilString.isBlank(json)) {
            try {
                return getGson().fromJson(json, clazz);
            } catch (Exception e) {
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
    public static <T> T parseJson(String json, Type typeOfT) {
        if (!UtilString.isBlank(json)) {
            try {
                return getGson().fromJson(json, typeOfT);
            } catch (Exception e) {
                e.printStackTrace();
                Logger.e("Parse json error: ", e);
            }
        }
        return null;
    }
}
