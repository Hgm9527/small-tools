package com.zhengqing.demo.config;

import com.zhengqing.common.redis.constant.RedisConstant;
import com.zhengqing.common.redis.listener.RedisTestListener;
import com.zhengqing.demo.constant.DemoConstant;
import com.zhengqing.demo.listener.DemoRedisTestListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

/**
 * <p>
 * redis监听事件配置
 * </p>
 *
 * @author zhengqingya
 * @description {@link com.zhengqing.common.redis.config.RedisListenerConfig}
 * 这里加入发布订阅配置
 * @date 2020/11/13 13:56
 */
@Configuration
public class DemoRedisListenerConfig {

    @Bean
    RedisMessageListenerContainer redisContainer(RedisConnectionFactory factory) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(factory);
        // 添加消息监听者，可以是多个
        container.addMessageListener(
                new MessageListenerAdapter(new DemoRedisTestListener()),
                new ChannelTopic(DemoConstant.REDIS_CHANNEL_DEMO)
        );
        container.addMessageListener(
                new MessageListenerAdapter(new RedisTestListener()),
                new ChannelTopic(RedisConstant.REDIS_CHANNEL_TEST)
        );
        return container;
    }
    
}