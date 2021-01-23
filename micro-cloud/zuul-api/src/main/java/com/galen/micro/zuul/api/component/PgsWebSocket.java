package com.galen.micro.zuul.api.component;

import com.alibaba.fastjson.JSONObject;
import com.galen.model.AplResponse;
import com.galen.model.ResponseUtils;
import com.galen.micro.zuul.api.utils.WebSocketUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

@ServerEndpoint(value = "/websocket")
@Component
public class PgsWebSocket {

    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    /**
     * @Author: Galen
     * @Description: 连接建立成功调用的方法
     * @Date: 2019/6/26-14:48
     * @Param: [session]
     * @return: void
     **/
    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        WebSocketUtil.addSocket(session.getId(), this);  //加入到set中
        log.info("有新连接{}加入！当前{}人在线", session.getId(), WebSocketUtil.getOnlineCount());
        WebSocketUtil.sendMessageAll(ResponseUtils.build(201, "欢迎" + session.getId() + "登陆了！当前在线人数为" + WebSocketUtil.getOnlineCount(), WebSocketUtil.getClientKeys()));
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        WebSocketUtil.removeSocket(session.getId());  //从set中删除
        log.info("有一连接关闭！当前还有{}人在线", WebSocketUtil.getOnlineCount());
        WebSocketUtil.sendMessageAll(ResponseUtils.build(202, "欢迎加入！当前在线人数为" + WebSocketUtil.getOnlineCount(), WebSocketUtil.getClientKeys()));
    }

    /**
     * @Author: Galen
     * @Description: 收到客户端消息后调用的方法
     * @Date: 2019/6/26-14:35
     * @Param: [message, sessionCurrent]
     * @return: void
     **/
    @OnMessage
    public void onMessage(String message, Session sessionCurrent) {
        log.info("来自客户端{}的消息:{}", sessionCurrent.getId(), message);
    }

    /**
     * @Author: Galen
     * @Description: 发生错误时调用
     * @Date: 2019/6/26-14:35
     * @Param: [session, error]
     * @return: void
     **/
    @OnError
    public void onError(Session session, Throwable error) {
        log.info("关闭{}发生错误", session.getId());
        error.printStackTrace();
    }


    public void sendMessage(AplResponse aplResponse) throws IOException {
        this.session.getBasicRemote().sendText(JSONObject.toJSONString(aplResponse));
        //this.session.getAsyncRemote().sendText(message);
    }

}
