package com.zw.admin.framework.api.factory;

import com.zw.admin.framework.api.service.RemoteLogService;
import com.zw.admin.framework.common.response.R;
import com.zw.admin.framework.domain.entity.SysOperLog;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 日志服务降级处理
 * 
 * @author ruoyi
 */
@Component
public class RemoteLogFallbackFactory implements FallbackFactory<RemoteLogService>
{
    private static final Logger log = LoggerFactory.getLogger(RemoteLogFallbackFactory.class);

    @Override
    public RemoteLogService create(Throwable throwable)
    {
        log.error("日志服务调用失败:{}", throwable.getMessage());
        return new RemoteLogService()
        {
            @Override
            public R<Boolean> saveLog(SysOperLog sysOperLog)
            {
                return null;
            }
        };

    }
}