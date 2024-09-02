package com.hr.springboot.examples.demo.customannotation.context;

public class TenantContext {
    private static final ThreadLocal<String> CURRENT_DATASOURCE = new ThreadLocal<>();

    public static void setCurrentDataSource(String dataSource) {
        CURRENT_DATASOURCE.set(dataSource);
    }

    public static String getCurrentDataSource() {
        return CURRENT_DATASOURCE.get();
    }

    public static void clear() {
        CURRENT_DATASOURCE.remove();
    }
}
