package com.zw.admin.system.service.impl;

import com.zw.admin.framework.common.utils.IdUtils;
import com.zw.admin.framework.common.utils.verification.RandomValidateCodeUtil;
import com.zw.admin.framework.core.service.RedisTemplateUtil;
import com.zw.admin.system.service.OtherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @description:
 * @author: ZhouWei
 * @create: 2021-01
 **/
@Service
public class OtherServiceImpl implements OtherService {

    @Autowired
    private RedisTemplateUtil redisTemplateUtil;

    @Override
    public Map<String, String> getCodeChar(String type) {
        RandomValidateCodeUtil randomValidateCode = new RandomValidateCodeUtil();
        Map<String, String> randCode = randomValidateCode.getRandCode(null, Boolean.TRUE);
        String code = randCode.get("code");
        String encoded = randCode.get("encoded");
        // 保存验证码信息
        String uuid = IdUtils.simpleUUID();
        redisTemplateUtil.setCacheObject("register:" + uuid, code,2L, TimeUnit.MINUTES);
        Map<String,String> result =new HashMap<>();
        result.put("image",encoded);
        result.put("uuid",code);
        return result;
    }

}
