package com.streamershelper.streamers.dto.skills;

import lombok.Data;

import java.util.Set;

@Data
public class EndpointInfo
{

    private String methodName;
    private Set<String> urls;


}
