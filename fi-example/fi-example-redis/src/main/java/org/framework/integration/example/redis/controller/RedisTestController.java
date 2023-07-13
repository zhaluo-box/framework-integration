package org.framework.integration.example.redis.controller;

import org.framework.integration.example.redis.entity.SysDataSegregationGroup;
import org.framework.integration.example.redis.service.RedisService;
import org.framework.integration.example.redis.service.RedisTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created  on 2023/7/12 15:15:59
 *
 * @author zl
 */
@RestController
@RequestMapping("redis")
public class RedisTestController {

    @Autowired
    private RedisService redisService;

    @Autowired
    private RedisTestService redisTestService;

    @GetMapping("test2")
    public void cacheP() {
        System.out.println("redisTestService.getOne() = " + redisTestService.getOne());
    }

    @GetMapping("test")
    public void test() {

        //        var longs = new ArrayList<Long>();
        //        longs.add(1L);
        //
        //        var xxx = new SysDataSegregation().setName("hhaha").setAreas(longs);
        //        var xxx2 = new SysDataSegregation().setName("hhashxxxa").setAreas(longs);
        //
        //        var sysDataSegregations = new ArrayList<SysDataSegregation>();
        //        sysDataSegregations.add(xxx2);
        //        sysDataSegregations.add(xxx2);
        //        sysDataSegregations.add(xxx2);
        //        sysDataSegregations.add(xxx2);
        //
        //        var hhaha = new SysDataSegregationGroup().setId("123").setName("0999").setSysDataSegregation(xxx);
        //        hhaha.setDataSegregationList(sysDataSegregations);
        //        redisService.setCacheObject("123", hhaha);

        SysDataSegregationGroup cacheObject = redisService.getCacheObject("123:1");
        System.out.println("cacheObject = " + cacheObject);
    }

}
