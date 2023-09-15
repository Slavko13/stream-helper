package com.streamershelper.streamers.service.pc.device;

import com.streamershelper.streamers.annotations.DemoBean;
import com.streamershelper.streamers.dto.pc.device.CpuDto;
import com.streamershelper.streamers.model.pc.device.Cpu;
import com.streamershelper.streamers.repository.pc.device.CpuRepository;
import com.streamershelper.streamers.service.base.BaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@DemoBean
public class CpuServiceImpl extends BaseService implements CpuService
{

    private final CpuRepository cpuRepository;

    public void saveCpu(CpuDto dto) {
        Cpu cpu = map(dto, Cpu.class);
        cpuRepository.save(cpu);
    }

    @Override
    public List<CpuDto> getAllCpu()
    {
        List<Cpu> cpus = cpuRepository.findAll();
        return mapList(cpus, CpuDto.class);
    }

    @Override
    public void saveAllCpu(final List<CpuDto> cpuDtoList)
    {
        cpuRepository.saveAll(mapList(cpuDtoList, Cpu.class));
    }
}
