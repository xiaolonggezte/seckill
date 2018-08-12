package org.seckill.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.entity.Seckill;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * 配置spring和junit整合，Junit启东市加载springIOC
 * spring-test,junit
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SecKillDaoTest {

//    注入Dao实现依赖
    @Resource
    private SecKillDao secKillDao;

    @Test
    public void queryById() {
        long id = 1000;
        Seckill seckill = secKillDao.queryById(id);
        System.out.println(seckill.getName());
        System.out.println(seckill);
    }


    @Test
    public void reduceNumber() {
        Date killTime = new Date();
        int updateCount = secKillDao.reduceNumber(1000L,killTime);
        System.out.println(updateCount);
    }



    @Test
    public void queryAll() {
        List<Seckill> seckills = secKillDao.queryAll(0,100);
        for (Seckill seckill:seckills) {
            System.out.println(seckill);
        }
    }
}