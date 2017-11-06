package com.hiekn.meta.util;

import java.util.Date;
import java.util.UUID;

public final class CommonUtils {
	
	public static String getRandomUUID(){
		return UUID.randomUUID().toString().replace("-", "");
	}
	
	public static Date getTime(){
		return new Date();
	}
}
