package com.partharaj.utils;

import android.os.Environment;
import android.util.Log;

import com.partharaj.BuildConfig;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {
    public static void log(Class c, String message) {
        if (BuildConfig.DEBUG) {
            Log.e(c.getClass().getName(), message);
        }
    }

    public static void log(String tag, String message) {
        if (BuildConfig.DEBUG) {
            Log.e(tag, message);
        }
    }

    public static void log(String tag, String message, Throwable tr) {
        if (BuildConfig.DEBUG) {
            Log.e(tag, message);
        }
    }

    public static void log(String message) {
        if (BuildConfig.DEBUG) {
            Log.e(BuildConfig.APPLICATION_ID, message);
        }
    }

    public static void logDump(String message) {
        logDump("", message);
    }

    public static void logDump(String tag, String message) {
        if (BuildConfig.DEBUG) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                String now = sdf.format(new Date());
                message = now + " " + tag + "\n" + message + "\n\n=====================================\n\n";

                FileOutputStream os = new FileOutputStream(new File(Environment.getExternalStorageDirectory(), "pdLog.txt"), true);
                os.write(message.getBytes(), 0, message.length());
                os.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
