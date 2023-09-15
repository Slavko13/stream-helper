package com.streamershelper.streamers.dto.skills;

import lombok.Data;

import java.util.List;

@Data
public class DemoBeanDetails
{
    private String properties;
    private Object metaData;
    private List<String> whereBeanUsed;
    private List<String> whichBeansDependOn;
    private DemoBeanMetadataDto demoBeanMetadataDto;

}
