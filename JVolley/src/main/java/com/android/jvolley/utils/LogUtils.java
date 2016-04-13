package com.android.jvolley.utils;

/**
 * @author shihuajian
 * @since 16-4-12 下午3:22
 * @depict 日志工具类
 */
public class LogUtils {

    public static boolean mIsDebug = true;

    /**
     * 设置是否打开log日志开关
     *
     * @param mIsDebug
     */
    public static void setJVolleyDebug(boolean mIsDebug) {
        LogUtils.mIsDebug = mIsDebug;
    }


    /**
     * 根据tag打印相关v信息
     *
     * @param tag
     * @param msg
     */
    public static void v(String tag, String msg) {
        if (mIsDebug) {
            StackTraceElement ste = new Throwable().getStackTrace()[1];
            String traceInfo = ste.getClassName() + "::";
            traceInfo += ste.getMethodName();
            traceInfo += "@" + ste.getLineNumber() + ">>>";
            android.util.Log.v(tag, traceInfo + msg);
        }
    }

    /**
     * 根据tag打印v信息,包括Throwable的信息
     * * @param tag
     *
     * @param msg
     * @param tr
     */
    public static void v(String tag, String msg, Throwable tr) {
        if (mIsDebug) {
            android.util.Log.v(tag, msg, tr);
        }
    }


    /**
     * 根据tag打印输出debug信息
     *
     * @param tag
     * @param msg
     */
    public static void d(String tag, String msg) {
        if (mIsDebug) {
            StackTraceElement ste = new Throwable().getStackTrace()[1];
            String traceInfo = ste.getClassName() + "::";
            traceInfo += ste.getMethodName();
            traceInfo += "@" + ste.getLineNumber() + ">>>";
            android.util.Log.d(tag, traceInfo + msg);
        }
    }

    /**
     * 根据tag打印输出debug信息 包括Throwable的信息
     * * @param tag
     *
     * @param msg
     * @param tr
     */
    public static void d(String tag, String msg, Throwable tr) {
        if (mIsDebug) {
            android.util.Log.d(tag, msg, tr);
        }
    }

    /**
     * 根据tag打印输出info的信息
     * * @param tag
     *
     * @param msg
     */
    public static void i(String tag, String msg) {
        if (mIsDebug) {
            StackTraceElement ste = new Throwable().getStackTrace()[1];
            String traceInfo = ste.getClassName() + "::";
            traceInfo += ste.getMethodName();
            traceInfo += "@" + ste.getLineNumber() + ">>>";
            android.util.Log.i(tag, traceInfo + msg);
        }
    }

    /**
     * 根据tag打印输出info信息 包括Throwable的信息
     *
     * @param tag
     * @param msg
     * @param tr
     */
    public static void i(String tag, String msg, Throwable tr) {
        if (mIsDebug) {
            android.util.Log.i(tag, msg, tr);
        }
    }

    /**
     * 根据tag打印输出error信息
     *
     * @param tag
     * @param msg
     */
    public static void e(String tag, String msg) {
        if (mIsDebug) {
            StackTraceElement ste = new Throwable().getStackTrace()[1];
            String traceInfo = ste.getClassName() + "::";
            traceInfo += ste.getMethodName();
            traceInfo += "@" + ste.getLineNumber() + ">>>";
            android.util.Log.e(tag, traceInfo + msg);
        }
    }

    /**
     * 根据tag打印输出的error信息 包括Throwable的信息
     *
     * @param tag
     * @param msg
     * @param tr
     */
    public static void e(String tag, String msg, Throwable tr) {
        if (mIsDebug) {
            android.util.Log.e(tag, msg, tr);
        }
    }
}
