package com.streamershelper.streamers.controller.pc.device;

import com.streamershelper.streamers.service.rapid.api.CpuDataFetcher;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/api/admin/device")
@RequiredArgsConstructor
public class AdminDeviceController
{

    private final CpuDataFetcher cpuDataFetcher;
    @GetMapping("/cpu/update")
    public ResponseEntity<?> getAllCpus() {
        cpuDataFetcher.fetchData();
        return ResponseEntity.ok("");
    }
}
