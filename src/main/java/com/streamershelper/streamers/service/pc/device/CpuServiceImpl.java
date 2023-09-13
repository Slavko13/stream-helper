package com.streamershelper.streamers.service.pc.device;

import com.streamershelper.streamers.dto.pc.device.CpuDTO;
import com.streamershelper.streamers.model.pc.device.Cpu;
import com.streamershelper.streamers.repository.pc.device.CpuRepository;
import com.streamershelper.streamers.service.base.BaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CpuServiceImpl extends BaseService implements CpuService
{

    private final CpuRepository cpuRepository;

    public void saveCpu(CpuDTO dto) {
        Cpu cpu = map(dto, Cpu.class);
        cpuRepository.save(cpu);
    }

    @Override
    public List<CpuDTO> getAllCpu()
    {
        List<Cpu> cpus = cpuRepository.findAll();
        return mapList(cpus, CpuDTO.class);
    }

    @Override
    public void saveAllCpu(final List<CpuDTO> cpuDTOList)
    {
        cpuRepository.saveAll(mapList(cpuDTOList, Cpu.class));
    }
}
