package com.streamershelper.streamers.dto.skills;


import lombok.Data;

import java.util.List;

@Data
public class DemoBeanMetadataDto
{

    private String className;
    private List<String> interfaces;
    private List<MethodMetadataDto> methods;
    private List<String> annotations;


    @Data
    public static class MethodMetadataDto
    {
        private String methodName;
        private String returnTypeName;
        private List<Object> annotations;
    }

}
