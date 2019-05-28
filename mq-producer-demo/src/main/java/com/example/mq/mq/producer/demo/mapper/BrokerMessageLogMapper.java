package com.example.mq.mq.producer.demo.mapper;

import com.example.mq.model.demo.entity.BrokerMessageLog;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface BrokerMessageLogMapper {

    /**
     * 查询消息状态为0(发送中)且已经超时的消息集合
     * @return
     */
    List<BrokerMessageLogMapper> query4StatusAndTimeoutMessage();

    /**
     * 重新发送统计count发送次数+1
     * @param messageId
     * @param updateTime
     */
    void update4ReSend(@Param("messageId") String messageId, @Param("updateTime")Date updateTime);

    /**
     * 更新最终消息发送结果成功or失败
     * @param messageId
     * @param status
     * @param updateTime
     */
    void changeBrokerMessageLogStatus(@Param("messageId") String messageId, @Param("status") String status, @Param("updateTime") Date updateTime);

    int insertSelective(BrokerMessageLog record);
}