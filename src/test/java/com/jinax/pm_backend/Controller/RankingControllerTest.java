package com.jinax.pm_backend.Controller;

import com.jinax.pm_backend.Component.CommonResult;
import com.jinax.pm_backend.Entity.Post;
import com.jinax.pm_backend.Result.GetTopTagResult;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class RankingControllerTest {
    @Autowired
    RankingController rankingController;
    @Test
    void getTopPosts() {
        CommonResult<List<Post>> result = rankingController.getTopPosts();
        List<Post> data = result.getData();
        assert data.size()==10;
        assert data.get(0).getViewTime()==25;
    }

    @Test
    void getTopPostsNearby() {
        CommonResult<List<Post>> result = rankingController.getTopPostsNearby(1.0,1.0);
        List<Post> data = result.getData();
        assert data.size()!=0;
        assert data.get(0).getViewTime()==0;
    }

}