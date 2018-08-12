package org.seckill.entity;

import java.util.Date;

public class SuccessKilled {
    private long seckillId;

    private long userPhone;

    private short status;

    private Date createTime;

    private Seckill seckill;

    public void setSeckill(Seckill seckill) {
        this.seckill = seckill;
    }

    public Seckill getSeckill() {

        return seckill;
    }

    public void setSeckillId(long seckillId) {
        this.seckillId = seckillId;
    }

    public void setUserPhone(long userPhone) {
        this.userPhone = userPhone;
    }

    public void setStatus(short status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public long getSeckillId() {
        return seckillId;
    }

    public long getUserPhone() {
        return userPhone;
    }

    public short getStatus() {
        return status;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "SuccessKilled{" +
                "seckillId=" + seckillId +
                ", userPhone=" + userPhone +
                ", status=" + status +
                ", createTime=" + createTime +
                ", secKill=" + seckill +
                '}';
    }
}
