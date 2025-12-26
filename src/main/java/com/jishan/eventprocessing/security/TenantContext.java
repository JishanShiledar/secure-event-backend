package com.jishan.eventprocessing.security;

public class TenantContext {

    private static final ThreadLocal<String> CURRENT_COMPANY = new ThreadLocal<>();

    public static void setCompanyId(String companyId) {
        CURRENT_COMPANY.set(companyId);
    }

    public static String getCompanyId() {
        return CURRENT_COMPANY.get();
    }

    public static void clear() {
        CURRENT_COMPANY.remove();
    }
}

