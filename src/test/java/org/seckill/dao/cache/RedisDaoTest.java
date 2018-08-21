package org.seckill.dao.cache;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.dao.SecKillDao;
import org.seckill.entity.Seckill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

/**
 * @author: xiaolong
 * @Date: 下午10:38 2018/8/21
 * @Description:
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class RedisDaoTest {

    private long id = 1001;
    @Autowired
    private RedisDao redisDao;
    @Autowired
    private SecKillDao secKillDao;

    @Test
    public void TestSeckill() {
        //get and put
        Seckill seckill = redisDao.getSeckill(id);
        if (null == seckill) {
            seckill = secKillDao.queryById(id);
            if(seckill != null) {
                String result = redisDao.putSeckill(seckill);
                System.out.println(result);
                seckill = redisDao.getSeckill(id);
                System.out.println(seckill);
            }
        }
    }
    @Test
    public void getSeckill() {
    }

    @Test
    public void putSeckill() {
    }
}