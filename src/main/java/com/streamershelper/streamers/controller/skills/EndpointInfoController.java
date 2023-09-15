package com.streamershelper.streamers.controller.skills;

import com.streamershelper.streamers.dto.skills.EndpointInfo;
import com.streamershelper.streamers.service.skills.spring.security.SpringSecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/v1/api/endpoints")
@RequiredArgsConstructor
public class EndpointInfoController {

    private final SpringSecurityService springSecurityService;

    @GetMapping("/by-role")
    public ResponseEntity<Map<String, List<EndpointInfo>>> getEndpointsByRole() {
        Map<String, List<EndpointInfo>> result = springSecurityService.getEndpointsGroupedByRole();
        return ResponseEntity.ok(result);
    }
}
