package org.seckill.dto;

import org.seckill.entity.SuccessKilled;
import org.seckill.enums.SeckillStateEnum;

public class SeckillExcution {

    private long SeckillId;


    //执行结果
    private int state;

    //执行结果表示
    private String stateInfo;

    //秒杀成功对象
    private SuccessKilled successKilled;

    public SeckillExcution(long seckillId, SeckillStateEnum seckillStateEnum, SuccessKilled successKilled) {
        SeckillId = seckillId;
        this.state = seckillStateEnum.getState();
        this.stateInfo = seckillStateEnum.getStateInfo();
        this.successKilled = successKilled;
    }

    public SeckillExcution(long seckillId, SeckillStateEnum seckillStateEnum) {
        SeckillId = seckillId;
        this.state = seckillStateEnum.getState();
        this.stateInfo = seckillStateEnum.getStateInfo();
    }

    @Override
    public String toString() {
        return "SeckillExcution{" +
                "SeckillId=" + SeckillId +
                ", state=" + state +
                ", stateInfo='" + stateInfo + '\'' +
                ", successKilled=" + successKilled +
                '}';
    }

    public void setSeckillId(long seckillId) {
        SeckillId = seckillId;
    }

    public void setState(int state) {
        this.state = state;
    }

    public void setStateInfo(String stateInfo) {
        this.stateInfo = stateInfo;
    }

    public void setSuccessKilled(SuccessKilled successKilled) {
        this.successKilled = successKilled;
    }

    public long getSeckillId() {
        return SeckillId;
    }

    public int getState() {
        return state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public SuccessKilled getSuccessKilled() {
        return successKilled;
    }
}
