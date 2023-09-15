package com.streamershelper.streamers.service.skills.spring.security;

import com.streamershelper.streamers.dto.skills.EndpointInfo;

import java.util.List;
import java.util.Map;

public interface SpringSecurityService
{

    List<EndpointInfo> getAllEndpoints();

    Map<String, List<EndpointInfo>> getEndpointsGroupedByRole();


}
