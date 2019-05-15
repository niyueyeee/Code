package cn.itcast.sms;


import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 发短信
 *
 */
@Component
public class CommonRpc {

    @Autowired
    private Environment env;

    //发短信
    public void sendSms(Map<String,String> map)  {
        DefaultProfile profile = DefaultProfile.getProfile("default",
                env.getProperty("accessKeyId"),
                env.getProperty("accessSecret"));
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        //request.setProtocol(ProtocolType.HTTPS);
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");
        request.putQueryParameter("PhoneNumbers", map.get("PhoneNumbers"));//"17862655057"
        request.putQueryParameter("SignName", map.get("SignName"));//"品优购商城"
        request.putQueryParameter("TemplateCode", map.get("TemplateCode"));//"SMS_126462276"
        request.putQueryParameter("TemplateParam",map.get("TemplateParam"));//"{number:123456}"
        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }
}
