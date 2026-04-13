package com.kob.backend.service.impl.pk;

import com.kob.backend.consumer.WebSocketServer;
import com.kob.backend.service.pk.StartGameService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class StartGameServiceImpl implements StartGameService {
    @Override
    public String startGame(Integer aId, Integer aBotId, Integer bId, Integer bBotId) {
        log.info("开始游戏!, 玩家1: {}, 玩家2: {}", aId, bId);
        WebSocketServer.startGame(aId, aBotId, bId, bBotId);
        return "成功开始游戏";
    }
}
