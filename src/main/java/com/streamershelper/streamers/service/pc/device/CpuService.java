package com.streamershelper.streamers.service.pc.device;

import com.streamershelper.streamers.dto.pc.device.CpuDto;

import java.util.List;

public interface CpuService
{
    void saveCpu(CpuDto cpuDTO);
    void saveAllCpu(List<CpuDto> cpuDtoList);
    List<CpuDto> getAllCpu();
}
