package org.seckill.service.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExcution;
import org.seckill.entity.Seckill;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SeckillCloseException;
import org.seckill.service.SeckillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({
        "classpath:spring/spring-dao.xml",
        "classpath:spring/spring-service.xml"})
public class SeckillServiceImplTest {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private SeckillService seckillService;
    @Test
    public void getSeckillList() throws Exception {

        List<Seckill> list = seckillService.getSeckillList();
        logger.info("list={}",list);
    }

    @Test
    public void getById() throws Exception {
        long seckillId = 1000;
        Seckill seckill = seckillService.getById(seckillId);
        logger.info("seckill={}",seckill);
    }

    @Test
    public void exportSeckillUrl() {
        long seckillId = 1000;
        Exposer exposer = seckillService.exportSeckillUrl(seckillId);
        logger.info("exposer={}",exposer);
    }

    @Test
    public void executeSeckill() {
        long seckillId = 1000;
        long userPhone = 15735659122L;
        Exposer exposer = seckillService.exportSeckillUrl(seckillId);
        if(exposer.isExposed()) {
            logger.info("Exposer={}",exposer);
            String md5 = exposer.getMd5();
            try {
                SeckillExcution seckillExcution = seckillService.executeSeckill(seckillId,userPhone,md5);
                logger.info("seckillExcution={}",seckillExcution);
            } catch (RepeatKillException e){
                System.out.println("RepeatKillException");
                logger.error(e.getMessage());
            } catch (SeckillCloseException e) {
                System.out.println("SeckillCloseException");
                logger.error(e.getMessage());
            } catch (Exception e) {
                logger.error(e.getMessage());
            }

        } else {
            //秒杀未开启
            logger.warn("Exposer={}",exposer);
        }
    }
    @Test
    public void executeSeckillProcedureTest() {
        Long seckillId = 1001L;
        Long phone = 12312345678L;
        Exposer exposer = seckillService.exportSeckillUrl(seckillId);
        if(exposer.isExposed()) {
            String md5 = exposer.getMd5();
            SeckillExcution seckillExcution = seckillService.executeSeckillProcedure(seckillId,phone,md5);
            logger.info(seckillExcution.getStateInfo());
        }
    }
}