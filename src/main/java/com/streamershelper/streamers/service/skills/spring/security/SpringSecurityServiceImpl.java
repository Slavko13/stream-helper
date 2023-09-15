package com.streamershelper.streamers.service.skills.spring.security;

import com.streamershelper.streamers.dto.skills.EndpointInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.util.pattern.PathPattern;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SpringSecurityServiceImpl implements SpringSecurityService
{

    private final RequestMappingHandlerMapping requestMappingHandlerMapping;
    @Override
    public List<EndpointInfo> getAllEndpoints() {
        List<EndpointInfo> result = new ArrayList<>();

        Map<RequestMappingInfo, HandlerMethod> handlerMethods =
                requestMappingHandlerMapping.getHandlerMethods();

        for (Map.Entry<RequestMappingInfo, HandlerMethod> entry : handlerMethods.entrySet()) {
            RequestMappingInfo mapping = entry.getKey();
            HandlerMethod method = entry.getValue();

            EndpointInfo info = new EndpointInfo();
            info.setMethodName(method.getMethod().getName());
            info.setUrls(extractPatterns(mapping));

            result.add(info);
        }

        return result;
    }

    private Set<String> extractPatterns(RequestMappingInfo mapping) {
        if (mapping != null && mapping.getPathPatternsCondition() != null) {
            return mapping.getPathPatternsCondition().getPatterns().stream()
                    .map(PathPattern::getPatternString)
                    .collect(Collectors.toSet());
        }
        return Collections.emptySet();
    }

    @Override
    public Map<String, List<EndpointInfo>> getEndpointsGroupedByRole() {
        List<EndpointInfo> allEndpoints = getAllEndpoints();

        List<EndpointInfo> vipUserEndpoints = allEndpoints.stream()
                .filter(e -> e.getUrls().stream().anyMatch(url -> url.startsWith("/v1/api/device/") && url.endsWith("/update")))
                .collect(Collectors.toList());

        List<EndpointInfo> regularUserEndpoints = allEndpoints.stream()
                .filter(e -> e.getUrls().stream().anyMatch(url -> url.startsWith("/v1/api/device/")))
                .filter(e -> e.getUrls().stream().noneMatch(url -> url.endsWith("/update")))
                .collect(Collectors.toList());

        Map<String, List<EndpointInfo>> result = new HashMap<>();
        result.put("vip_user", vipUserEndpoints);
        result.put("user", regularUserEndpoints);

        return result;
    }
}
