package com.streamershelper.streamers.controller.pc.device;

import com.streamershelper.streamers.dto.pc.device.CpuDto;
import com.streamershelper.streamers.service.pc.device.CpuService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/api/device")
@RequiredArgsConstructor
public class DeviceController
{


    private final CpuService cpuService;
    @GetMapping("/cpu/get-all")
    public ResponseEntity<List<CpuDto>> getAllCpus() {
        List<CpuDto> cpus = cpuService.getAllCpu();
        return ResponseEntity.ok(cpus);
    }


}
