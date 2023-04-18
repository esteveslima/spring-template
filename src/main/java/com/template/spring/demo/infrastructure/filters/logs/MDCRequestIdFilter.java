package com.template.spring.demo.infrastructure.filters.logs;

import jakarta.servlet.*;
import org.slf4j.MDC;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Random;
import java.util.UUID;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class MDCRequestIdFilter implements Filter {

    public static final String mdcRequestIdKey = "mdcRequestIdKey";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException{
        String requestId = this.generateUUID();
        MDC.put(mdcRequestIdKey, requestId);    // set the request ID in the MDC for further logging

        chain.doFilter(request, response);

        MDC.remove(mdcRequestIdKey);    // clear the MDC after the request has been processed
    }

    private String generateUUID() {
        Random random = new Random();
        long random63BitLong = random.nextLong() & 0x3FFFFFFFFFFFFFFFL;
        long variant3BitFlag = 0x8000000000000000L;
        long least64SigBits = random63BitLong | variant3BitFlag;

        final long currentTimeMillis = System.currentTimeMillis();
        final long time_low = (currentTimeMillis & 0x0000_0000_FFFF_FFFFL) << 32;
        final long time_mid = ((currentTimeMillis >> 32) & 0xFFFF) << 16;
        final long version = 1 << 12;
        final long time_hi = ((currentTimeMillis >> 48) & 0x0FFF);
        long most64SigBits =  time_low | time_mid | version | time_hi;

        UUID timeBasedUUID = new UUID(most64SigBits, least64SigBits);

        return timeBasedUUID.toString();
    }


//

//    @Override
//    public void destroy() {
//        // do nothing
//    }
//
//    @Override
//    public void init(FilterConfig config) throws ServletException {
//        // do nothing
//    }

}