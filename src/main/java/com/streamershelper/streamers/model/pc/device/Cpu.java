package com.streamershelper.streamers.model.pc.device;

import com.streamershelper.streamers.model.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "cpu")
@Getter
@Setter
@NoArgsConstructor
public class Cpu extends BaseEntity
{

    @Column(name = "CPU_mark")
    private Integer CPUMark;
    @Column(name = "CPU_name")
    private String CPUName;
    @Column(name = "CPU_value")
    private String CPUValue;
    @Column(name = "category")
    private String category;
    @Column(name = "cores")
    private Integer cores;
    @Column(name = "num_sockets")
    private Integer numSockets;
    @Column(name = "power_perf")
    private String powerPerf;
    @Column(name = "price")
    private String price;
    @Column(name = "socket")
    private String socket;
    @Column(name = "TDP")
    private String TDP;
    @Column(name = "thread_mark")
    private Integer ThreadMark;
    @Column(name = "thread_value")
    private String ThreadValue;


}
