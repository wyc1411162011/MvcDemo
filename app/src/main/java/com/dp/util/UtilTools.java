package com.dp.util;

import android.content.Intent;

/**
 * Created by ufsoft on2020-04-15
 * describle:
 */
public class UtilTools {
    public static int parseInt(String str){
        int number = 0;
        try {
            number = Integer.parseInt(str);
        }catch (Exception e){
            e.printStackTrace();
            return  number;
        }
        return  number;
    }
}
