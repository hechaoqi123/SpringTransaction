package com.example.transaction.util;

import com.sun.org.apache.bcel.internal.generic.RETURN;

import java.util.UUID;

/**
 * @author hcq
 * @date 2020/4/24 23:23
 */
public class IdUtils {

    public static String get32UUID(){
        return UUID.randomUUID().toString().replace("-","");
    }
}
