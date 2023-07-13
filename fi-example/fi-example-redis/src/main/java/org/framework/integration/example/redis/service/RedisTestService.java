package org.framework.integration.example.redis.service;

import org.framework.integration.example.redis.entity.SysDataSegregation;
import org.framework.integration.example.redis.entity.SysDataSegregationGroup;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * Created  on 2023/7/12 16:16:30
 *
 * @author zl
 */
@Service
public class RedisTestService {

    @CachePut(value = "123", key = "1")
    public SysDataSegregationGroup getOne() {

        var longs = new ArrayList<Long>();
        longs.add(1L);

        var xxx = new SysDataSegregation().setName("hhaha").setAreas(longs);
        var xxx2 = new SysDataSegregation().setName("hhashxxxa").setAreas(longs);

        var sysDataSegregations = new ArrayList<SysDataSegregation>();
        sysDataSegregations.add(xxx2);
        sysDataSegregations.add(xxx2);
        sysDataSegregations.add(xxx2);
        sysDataSegregations.add(xxx2);

        var hhaha = new SysDataSegregationGroup().setId("123").setName("0999").setSysDataSegregation(xxx);
        hhaha.setDataSegregationList(sysDataSegregations);

        return hhaha;
    }
}
