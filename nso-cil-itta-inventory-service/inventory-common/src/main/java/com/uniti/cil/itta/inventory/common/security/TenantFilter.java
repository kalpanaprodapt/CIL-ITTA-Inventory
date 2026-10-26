package com.uniti.cil.itta.inventory.common.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE + 10)
public class TenantFilter extends OncePerRequestFilter {

    public static final String TENANT_HEADER = "X-Tenant-Id";
    public static final String ACTOR_HEADER = "X-Actor";

    private final String defaultTenant;

    public TenantFilter(@Value("${inventory.tenancy.default-tenant:uniti}") String defaultTenant) {
        this.defaultTenant = defaultTenant;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return request.getRequestURI().startsWith("/actuator");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        String tenant = request.getHeader(TENANT_HEADER);
        if (tenant == null || tenant.isBlank()) {
            tenant = defaultTenant;
        }
        String actor = request.getHeader(ACTOR_HEADER);
        if (actor == null && request.getUserPrincipal() != null) {
            actor = request.getUserPrincipal().getName();
        }
        TenantContext.set(tenant, actor);
        MDC.put("tenant", tenant);
        try {
            chain.doFilter(request, response);
        } finally {
            MDC.remove("tenant");
            TenantContext.clear();
        }
    }
}
