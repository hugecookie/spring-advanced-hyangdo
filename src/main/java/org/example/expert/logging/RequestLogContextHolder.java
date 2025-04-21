package org.example.expert.logging;

public class RequestLogContextHolder {
    private static final ThreadLocal<RequestLogContext> holder = new ThreadLocal<>();

    public static void set(RequestLogContext context) {
        holder.set(context);
    }

    public static RequestLogContext get() {
        return holder.get();
    }

    public static void clear() {
        holder.remove();
    }
}
