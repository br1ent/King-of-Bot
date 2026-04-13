package com.kob.matchingsystem.service.impl;

import com.kob.matchingsystem.service.MatchingService;
import com.kob.matchingsystem.service.impl.utils.MatchingPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class MatchingServiceImpl implements MatchingService {
    private static final Logger log = LoggerFactory.getLogger(MatchingServiceImpl.class);
    public final static MatchingPool matchingPool = new MatchingPool();

    @Override
    public String addPlayer(Integer userId, Integer rating, Integer botId) {
        log.info("玩家开始匹配,id: {}, 分值: {}, bot_id: {}", userId, rating, botId);
        matchingPool.addPlayer(userId, rating, botId);
        return "add player success!";
    }

    @Override
    public String removePlayer(Integer userId) {
        log.info("玩家取消匹配, id: {}", userId);
        matchingPool.removePlayer(userId);
        return "remove player success!";
    }
}
