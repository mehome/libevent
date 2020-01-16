package com.personal.oyl.event.sample;

import com.personal.oyl.event.EventMapper;
import com.personal.oyl.event.EventPublisher;
import com.personal.oyl.event.EventPusher;
import com.personal.oyl.event.jupiter.AssignmentListener;
import com.personal.oyl.event.jupiter.DefaultAssignmentListener;
import com.personal.oyl.event.jupiter.EventTransportMgr;
import com.personal.oyl.event.kafka.KafkaEventPusher;
import com.personal.oyl.event.sample.order.*;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.mapper.MapperFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author OuYang Liang
 * @since 2020-01-15
 */
@Configuration
public class AppConfiguration {

    @Bean
    public EventMapper eventMapper(SqlSessionFactory sqlSessionFactory) throws Exception {
        MapperFactoryBean<EventMapper> factory = new MapperFactoryBean<>();
        factory.setSqlSessionFactory(sqlSessionFactory);
        factory.setMapperInterface(EventMapper.class);
        return factory.getObject();
    }

    @Bean
    public EventPublisher eventPublisher(EventMapper eventMapper) {
        return new EventPublisher(eventMapper);
    }

    @Bean
    public EventPusher eventPusher() {
        return new KafkaEventPusher();
    }

    @Bean
    public EventTransportMgr eventTransportMgr(EventMapper eventMapper, EventPusher eventPusher) {
        return new EventTransportMgr(eventMapper, eventPusher);
    }

    @Bean
    public AssignmentListener assignmentListener(EventTransportMgr eventTransportMgr) {
        return new DefaultAssignmentListener(eventTransportMgr);
    }

    @Bean
    public OrderDao orderDao(SqlSessionFactory sqlSessionFactory) throws Exception {
        MapperFactoryBean<OrderDao> factory = new MapperFactoryBean<>();
        factory.setSqlSessionFactory(sqlSessionFactory);
        factory.setMapperInterface(OrderDao.class);
        return factory.getObject();
    }

    @Bean
    public UserOrderReportDao userOrderReportDao(SqlSessionFactory sqlSessionFactory) throws Exception {
        MapperFactoryBean<UserOrderReportDao> factory = new MapperFactoryBean<>();
        factory.setSqlSessionFactory(sqlSessionFactory);
        factory.setMapperInterface(UserOrderReportDao.class);
        return factory.getObject();
    }

    @Bean
    public DailyOrderReportDao dailyOrderReportDao(SqlSessionFactory sqlSessionFactory) throws Exception {
        MapperFactoryBean<DailyOrderReportDao> factory = new MapperFactoryBean<>();
        factory.setSqlSessionFactory(sqlSessionFactory);
        factory.setMapperInterface(DailyOrderReportDao.class);
        return factory.getObject();
    }

}
