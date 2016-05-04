package com.set.leo.climax.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;

/**
 * Author    LeoCheung
 * Email     leocheung4ever@gmail.com
 * Description  以防特殊情况工具类 主要是关闭一些易造成内存泄露的对象 比如游标 读取写入流 etc.
 * Date          Author          Version          Description
 * ------------------------------------------------------------------
 * 2016/4/23     LeoCheung       1.0              1.0
 * Why & What is modified:
 */
public class InCaseUtils {

    public static void close(BufferedInputStream inputStream) {
        try {
            inputStream.close();
            inputStream = null;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void close(BufferedOutputStream outputStream) {
        try {
            outputStream.close();
            outputStream = null;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
