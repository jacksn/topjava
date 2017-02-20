package ru.javawebinar.topjava.util.exception;

/**
 * User: gkislin
 * Date: 19.08.2014
 */
public class ErrorInfo {
    public final String url;
    public final String detail;

    public ErrorInfo(CharSequence url, Throwable ex) {
        this.url = url.toString();
        this.detail = ex.getLocalizedMessage();
    }

    public ErrorInfo(CharSequence url, String detail) {
        this.url = url.toString();
        this.detail = detail;
    }
}
