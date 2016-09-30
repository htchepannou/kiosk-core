package com.tchepannou.kiosk.core.rule;

public class Validation {
    private static final Validation SUCCESS = new Validation(true, null);

    private final boolean success;
    private final String reason;

    private Validation(final boolean success, final String reason) {
        this.success = success;
        this.reason = reason;
    }

    public static Validation success(){
        return SUCCESS;
    }
    public static Validation failure(final String reason){
        return new Validation(false, reason);
    }

    public boolean isSuccess() {
        return success;
    }

    public String getReason() {
        return reason;
    }
}
