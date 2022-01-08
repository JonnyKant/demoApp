package com.modeling.demo.service;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.support.RequestDataValueProcessor;

/**
 * A <code>RequestDataValueProcessor</code> that pushes a hidden field with a CSRF token into forms.
 * This process implements the {@link #getExtraHiddenFields(HttpServletRequest)} method to push the
 * CSRF token obtained from {@link CSRFTokenManager}. To register this processor to automatically process all
 * Spring based forms register it as a Spring bean named 'requestDataValueProcessor' as shown below:
 * <pre>
 *  &lt;bean name="requestDataValueProcessor" class="com.eyallupu.blog.springmvc.controller.csrf.CSRFRequestDataValueProcessor"/&gt;
 * </pre>
 * @author Eyal Lupu
 *
 */
@Component
public class CSRFRequestDataValueProcessor implements RequestDataValueProcessor {


    @Override
    public String processAction(HttpServletRequest httpServletRequest, String s, String s1) {
        return s;
    }

    @Override
    public String processFormFieldValue(HttpServletRequest request, String name, String value, String type) {
        return value;
    }

    @Override
    public Map<String, String> getExtraHiddenFields(HttpServletRequest request) {
        Map<String, String> hiddenFields = new HashMap<String, String>();
        hiddenFields.put(CSRFTokenManager.CSRF_PARAM_NAME, CSRFTokenManager.getTokenForSession(request.getSession()));
        return hiddenFields;
    }

    @Override
    public String processUrl(HttpServletRequest request, String url) {
        return url;
    }

}