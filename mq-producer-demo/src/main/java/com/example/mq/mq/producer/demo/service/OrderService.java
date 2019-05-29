package com.example.mq.mq.producer.demo.service;

import com.example.mq.model.demo.entity.BrokerMessageLog;
import com.example.mq.model.demo.entity.Order;
import com.example.mq.mq.producer.demo.constant.Constants;
import com.example.mq.mq.producer.demo.mapper.BrokerMessageLogMapper;
import com.example.mq.mq.producer.demo.mapper.OrderMapper;
import com.example.mq.mq.producer.demo.producer.RabbitOrderSender;
import com.example.mq.mq.producer.demo.utils.FastJsonConvertUtil;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private BrokerMessageLogMapper brokerMessageLogMapper;

    @Autowired
    private RabbitOrderSender rabbitOrderSender;

    public void createOrder(Order order) {
        // 使用当前当做订单创建时间
        Date orderTime = new Date();
        // 插入业务数据
        orderMapper.insert(order);
        // 插入消息记录记录
        BrokerMessageLog brokerMessageLog = new BrokerMessageLog();
        // 消息唯一ID
        brokerMessageLog.setMessageId(order.getMessageId());
        // 保存消息整体转为JSON 格式存储入库
        brokerMessageLog.setMessage(FastJsonConvertUtil.convertObjectToJSON(order));
        // 设置消息状态为0 表示发送中
        brokerMessageLog.setStatus("0");
        // 设置消息未确认超时时间窗口为一分钟
        brokerMessageLog.setNextRetry(DateUtils.addMinutes(orderTime, Constants.ORDER_TIMEOUT));
        brokerMessageLog.setCreateTime(new Date());
        brokerMessageLog.setUpdateTime(new Date());
        brokerMessageLogMapper.insertSelective(brokerMessageLog);
        // 发送消息
        rabbitOrderSender.sendOrder(order);
    }
}