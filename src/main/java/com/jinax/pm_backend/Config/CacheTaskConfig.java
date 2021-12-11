package com.jinax.pm_backend.Config;

import com.jinax.pm_backend.Service.PostService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration      //1.主要用于标记配置类，兼备Component的效果。
@EnableScheduling   // 2.开启定时任务
public class CacheTaskConfig {
    private static final Logger LOGGER = LoggerFactory.getLogger(CacheTaskConfig.class);
    private PostService postService;
    @Autowired
    public CacheTaskConfig(PostService postService) {
        this.postService = postService;
    }

    //3.添加定时任务
    @Scheduled(cron = "* * 2 * * ?")
    private void updateCache() {
        postService.updateCacheTopPosts();
        LOGGER.info("定时刷新缓存");
    }
}
