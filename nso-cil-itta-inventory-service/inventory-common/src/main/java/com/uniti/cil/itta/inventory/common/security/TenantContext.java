package com.uniti.cil.itta.inventory.common.security;

/** Thread-bound tenant / actor holder populated by {@link TenantFilter}. */
public final class TenantContext {

    private static final ThreadLocal<String> TENANT = new ThreadLocal<>();
    private static final ThreadLocal<String> ACTOR = new ThreadLocal<>();

    private TenantContext() {
    }

    public static void set(String tenant, String actor) {
        TENANT.set(tenant);
        ACTOR.set(actor);
    }

    public static String requireTenant() {
        String tenant = TENANT.get();
        if (tenant == null || tenant.isBlank()) {
            throw new IllegalStateException("No tenant bound to the current request");
        }
        return tenant;
    }

    public static String currentActor() {
        String actor = ACTOR.get();
        return actor == null ? "system" : actor;
    }

    public static void clear() {
        TENANT.remove();
        ACTOR.remove();
    }
}
