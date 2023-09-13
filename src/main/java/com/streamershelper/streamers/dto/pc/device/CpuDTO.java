package com.streamershelper.streamers.dto.pc.device;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CpuDTO {

    @JsonProperty("CPU_Mark")
    private Integer CPUMark;

    @JsonProperty("CPU_Name")
    private String CPUName;

    @JsonProperty("CPU_Value")
    private String CPUValue;

    @JsonProperty("Category")
    private String category;

    @JsonProperty("Cores")
    private Integer cores;

    @JsonProperty("Num_Sockets")
    private Integer numSockets;

    @JsonProperty("Power_Perf")
    private String powerPerf;

    @JsonProperty("Price")
    private String price;

    @JsonProperty("Socket")
    private String socket;

    @JsonProperty("TDP")
    private String TDP;

    @JsonProperty("Thread_Mark")
    private Integer threadMark;

    @JsonProperty("Thread_Value")
    private String threadValue;

    // Геттеры, сеттеры и другие методы
}
