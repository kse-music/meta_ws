package com.hiekn.meta.util;

import java.lang.reflect.Type;
import java.util.Date;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.hiekn.meta.exception.JsonException;

public final class JsonUtils {

	private static ThreadLocal<Gson> local = new ThreadLocal<Gson>();

	private static Gson getGson() {
		if (local.get() == null) {
			GsonBuilder gsonBuilder = new GsonBuilder();
			gsonBuilder.registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
				public Date deserialize(JsonElement p1, Type p2,JsonDeserializationContext p3) {
					return new Date(p1.getAsLong());
				}
			}).registerTypeAdapter(Date.class, new JsonSerializer<Date>(){
				public JsonElement serialize(Date arg0, Type arg1,JsonSerializationContext arg2) {
					return new JsonPrimitive(arg0.getTime());
				}
			});
			Gson gson = gsonBuilder.create();
			local.set(gson);
			return gson;
		} else {
			return local.get();
		}
	}

	public static <T> T fromJson(String json, Class<T> cls) {
		try {
			return getGson().fromJson(json, cls);
		} catch (Exception e) {
			throw JsonException.newInstance(); 
		}
	}

	public static <T> T fromJson(String json,  Type typeOfT) {
		try {
			return getGson().fromJson(json, typeOfT);
		} catch (Exception e) {
			throw JsonException.newInstance(); 
		}
	}

	public static String toJson(Object obj) {
		try {
			return getGson().toJson(obj);
		} catch (Exception e) {
			throw JsonException.newInstance(); 
		}
	}

}