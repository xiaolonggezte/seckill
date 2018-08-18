package org.seckill.service.impl;

import com.sun.net.httpserver.Authenticator;
import org.seckill.dao.SecKillDao;
import org.seckill.dao.SuccessKilledDao;
import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExcution;
import org.seckill.entity.Seckill;
import org.seckill.entity.SuccessKilled;
import org.seckill.enums.SeckillStateEnum;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SeckillCloseException;
import org.seckill.exception.SeckillException;
import org.seckill.service.SeckillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.List;

@Service
public class SeckillServiceImpl implements SeckillService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    //md5盐值字符串，用来混淆MD5
    private final String slat = "ashfkasfje21efaj239^&&(*&*%^$";
    @Autowired
    private SecKillDao secKillDao;

    @Autowired
    private SuccessKilledDao successKilledDao;

    @Override
    public List<Seckill> getSeckillList() {
        return secKillDao.queryAll(0,4);
    }

    @Override
    public Seckill getById(long seckillId) {
        return secKillDao.queryById(seckillId);
    }
    @Override
    public Exposer exportSeckillUrl(long seckillId) {
        Seckill seckill = secKillDao.queryById(seckillId);
        if(seckill == null) {
            return new Exposer(false,seckillId);
        }
        Date startTime = seckill.getStartTime();
        Date endTime = seckill.getEndTime();
        //当前的系统时间
        Date nowTime = new Date();
        if(nowTime.getTime() < startTime.getTime()
                ||nowTime.getTime() > endTime.getTime()) {
            return new Exposer(false,seckillId,nowTime.getTime(),
                    startTime.getTime(),endTime.getTime());
        }
        //转化特定字符的过程，加密
        String md5 = getMD5(seckillId);//TODO
        return new Exposer(true,md5,seckillId,nowTime.getTime(),startTime.getTime(),endTime.getTime());

    }

    private String getMD5(long seckillId) {
        String base = seckillId + "/"+slat;
        String md5 = DigestUtils.md5DigestAsHex(base.getBytes());
        return md5;
    }

    @Override
    @Transactional
    /**
     * 使用注解控制事务的优点：
     * 1.开发团队达成一致约定，明确标注事务方法风格
     * 2.保证事务的运行时间尽可能短，不要穿插其他网络操作，RPC/HTTP剥离事务之外
     * 3.不是所有操作需要事务，只有一条修改操作，只读操作不需要事务
     *
     */
    public SeckillExcution executeSeckill(long SeckillId, long userPhone, String md5) throws SeckillException,
            RepeatKillException, SeckillCloseException {

        if(md5 == null || !md5.equals(getMD5(SeckillId))) {
            throw new SeckillException("Seckill data rewrite");
        }
        //执行秒杀逻辑：减库存+ 记录购买记录
        Date nowTime = new Date();
        try {
            //减库存
            int updateCount = secKillDao.reduceNumber(SeckillId,nowTime);
            if(updateCount <= 0) {
                //没有更新记录，秒杀失败
                throw new SeckillCloseException("seckill is closed");
            } else {
                //记录购买逻辑
                int insertCount = successKilledDao.insertSuccessKilled(SeckillId,userPhone);
                if(insertCount <= 0) {
                    throw new RepeatKillException("seckill repeated");
                } else {
                    //秒杀成功
                    SuccessKilled successKilled = successKilledDao.queryByIdWithSeckill(SeckillId,userPhone);
                    return new SeckillExcution(SeckillId,SeckillStateEnum.SUCCESS,successKilled);
                }


            }

        } catch (SeckillCloseException e1) {
            throw e1;
        } catch (RepeatKillException e2) {
            throw e2;
        } catch (Exception e) {
            //所有编译器异常，转化成运行期异常
            logger.error(e.getMessage(),e);
            throw new SeckillException("inner error:" + e.getMessage());
        }

    }
}
