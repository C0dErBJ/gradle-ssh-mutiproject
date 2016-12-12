package com.ZP.task;

import com.ZP.service.impl.KDSerivceImpl;
import com.ZP.service.impl.OrderServiceImpl;
import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * ProjectName: QJZ
 * PackageName: com.ZP.task
 * User: C0dEr
 * Date: 2016-11-21
 * Time: 16:46
 * Description:
 */
public class DbSync {

    Logger logger = Logger.getLogger(this.getClass());
    @Resource
    KDSerivceImpl kdSerivce;

    @Resource
    OrderServiceImpl orderService;

    @Scheduled(cron = "*/5 * * * * ?")//5秒运行一次
    public void excute() {
        logger.info("------------------------开始同步---------------------------");

        kdSerivce.syncUser();

        kdSerivce.syncProduct();

        kdSerivce.syncChannel();

        kdSerivce.syncExpress();

        kdSerivce.updateExpress();

        logger.info("------------------------同步结束---------------------------");
    }

}
