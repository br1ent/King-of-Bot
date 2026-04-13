package com.kob.matchingsystem.service.impl.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

@Component
public class MatchingPool extends Thread{
    private static final Logger log = LoggerFactory.getLogger(MatchingPool.class);
    private static List<Player> players = new ArrayList<>();
    private ReentrantLock lock = new ReentrantLock();

    private static RestTemplate restTemplate;

    private static final String startGameUrl = "http://127.0.0.1:3000/pk/start/game";

    @Autowired
    public void setRestTemplate(RestTemplate restTemplate) {
        MatchingPool.restTemplate = restTemplate;
    }

    public void addPlayer(Integer userId, Integer rating, Integer botId) {
        lock.lock();
        try {
            players.add(new Player(userId, rating, botId, 0));
        } finally {
            lock.unlock();
        }
    }

    // 把不用删的玩家加到一个新的列表里面，然后重新赋值给players
    public void removePlayer(Integer userId) {
        lock.lock();
        try {
            List<Player> newPlayer = new ArrayList<>();
            for (Player player :players) {
                if (!player.getUserId().equals(userId)) {
                    newPlayer.add(player);
                }
            }
            players = newPlayer;
        } finally {
            lock.unlock();
        }
    }

    // 每名玩家的等待时间加1s
    private void increase_waitingTime() {
        for (Player player: players) {
            player.setWaitingTime(player.getWaitingTime() + 1);
        }
    }

    // 判断两名玩家是否匹配
    private boolean checkMatched(Player a, Player b) {
        int ratingDelta = Math.abs(a.getRating() - b.getRating());
        int waitingTime = Math.min(a.getWaitingTime(), b.getWaitingTime());

        return ratingDelta <= waitingTime * 10;
    }

    // 返回匹配结果
    private void sendResult(Player a, Player b) {
        log.info("发送信息:{}, {}", a, b);
        MultiValueMap<String, String> data = new LinkedMultiValueMap<>();
        data.add("a_id", a.getUserId().toString());
        data.add("a_bot_id", a.getBotId().toString());
        data.add("b_id", b.getUserId().toString());
        data.add("b_bot_id", b.getBotId().toString());
        restTemplate.postForObject(startGameUrl, data, String.class);
    }

    // 尝试匹配玩家
    private void matchPlayers() {
        log.info("匹配的玩家有: {}", players.toString());
        boolean[] matched = new boolean[players.size()];
        for (int i = 0; i < players.size(); i ++ ) {
            if (matched[i]) continue;
            for (int j = i + 1; j < players.size(); j ++ ) {
                if (matched[j]) continue;
                Player a = players.get(i), b = players.get(j);
                if (checkMatched(a, b)) {
                    matched[i] = matched[j] = true;
                    sendResult(a, b);
                    break;
                }
            }
        }
        List<Player> newPlayers = new ArrayList<>();
        for (int i = 0; i < players.size(); i ++ ) {
            if (!matched[i]) {
                newPlayers.add(players.get(i));
            }
        }
        players = newPlayers;
    }
    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(1000);
                lock.lock();
                try {
                    increase_waitingTime();
                    matchPlayers();
                } finally {
                    lock.unlock();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
            }
        }
    }
}
