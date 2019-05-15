package cn.itcast.sms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 接收消息
 * 监听器
 */
@Component
public class ReceiverMessage {

    @Autowired
    private CommonRpc commonRpc;
    //监听器
    @JmsListener(destination = "sms")
    public void receiverMessage(Map<String,String> map){

        //发短信
        commonRpc.sendSms(map);

    }
}
