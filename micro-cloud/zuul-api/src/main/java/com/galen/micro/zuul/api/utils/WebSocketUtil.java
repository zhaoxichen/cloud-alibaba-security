package com.galen.micro.zuul.api.utils;

import com.galen.model.AplResponse;
import com.galen.micro.zuul.api.component.PgsWebSocket;

import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: Galen
 * @Description: webSocket工具类
 * @Date: 2019/6/26-15:31
 * @Param:
 * @return:
 **/
public class WebSocketUtil {

    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static int onlineCount = 0;

    /**
     * 以用户的姓名为key，WebSocket为对象保存起来
     */
    private static Map<String, PgsWebSocket> webSocketClients = new ConcurrentHashMap<String, PgsWebSocket>();

    //获取所有WebSocket对象
    public static Map<String, PgsWebSocket> getWebSocketClients() {
        return webSocketClients;
    }

    //添加一个WebSocket对象
    public static void addSocket(String username, PgsWebSocket pgsWebSocket) {
        webSocketClients.put(username, pgsWebSocket);
        addOnlineCount();//在线数加1
    }

    //移除一个WebSocket对象
    public static void removeSocket(String username) {
        webSocketClients.remove(username);
        subOnlineCount();//在线数减1
    }

    //群发消息
    public static void sendMessageAll(AplResponse aplResponse) {
        //value
        for (PgsWebSocket item : webSocketClients.values()) {
            try {
                item.sendMessage(aplResponse);
            } catch (IOException e) {
                continue;
            }
        }
    }

    public static Set<String> getClientKeys() {
        return webSocketClients.keySet();
    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        WebSocketUtil.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        WebSocketUtil.onlineCount--;
    }
}
