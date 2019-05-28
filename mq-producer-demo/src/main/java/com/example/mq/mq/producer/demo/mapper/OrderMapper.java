package com.example.mq.mq.producer.demo.mapper;

import com.example.mq.model.demo.entity.Order;

public interface OrderMapper {

    int insert(Order record);

    int deleteByPrimaryKey(Integer id);

    int insertSelective(Order record);

    Order selectBYPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Order record);

    int updateByPrimaryKey(Order record);


}
