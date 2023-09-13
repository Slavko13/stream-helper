package com.streamershelper.streamers.service.pc.device;

import com.streamershelper.streamers.dto.pc.device.CpuDTO;

import java.util.List;

public interface CpuService
{
    void saveCpu(CpuDTO cpuDTO);
    void saveAllCpu(List<CpuDTO> cpuDTOList);
    List<CpuDTO> getAllCpu();
}
