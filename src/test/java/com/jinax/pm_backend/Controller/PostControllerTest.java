package com.jinax.pm_backend.Controller;

import com.jinax.pm_backend.Component.CommonResult;
import com.jinax.pm_backend.Entity.Block;
import com.jinax.pm_backend.Entity.Post;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PostControllerTest {

    @Autowired
    PostController postController;

    @Test
    void getPostInfos() {
        CommonResult<Post> result = postController.getPostInfos(0);
        assert result.getMsg().equals("无该帖子");
        result = postController.getPostInfos(1);
        Post post = result.getData();
        assert post.getTitle().equals("test4");
    }


    @Test
    void getPostsByOwnerId() {
        CommonResult<List<Post>> result = postController.getPostsByOwnerId(0);
        assert result.getMsg().equals("该用户不存在或未发过帖子");
        result = postController.getPostsByOwnerId(3);
        List<Post> list = result.getData();
        assert list.size()==1;
        assert list.get(0).getTitle().equals("1");
    }

    @Test
    void getPostsByReplierId() {
        CommonResult<List<Post>> result = postController.getPostsByReplierId(0);
        assert result.getMsg().equals("该用户不存在或未回复过帖子");
        result = postController.getPostsByReplierId(6);
        List<Post> list = result.getData();
        assert list.size()==1;
        assert list.get(0).getTitle().equals("test4");
    }

    @Test
    void getAnswersByPostIdPaged() {
        CommonResult<List<Block>> result = postController.getAnswersByPostIdPaged(1,0,10);
        List<Block> list = result.getData();
        assert list.size()==10;
        assert list.get(0).getPostId()==1;
    }

//    @Test
//    void createPost() {
//
//    }
//
//    @Test
//    void createFloor() {
//    }
//
//    @Test
//    void createReply() {
//    }

    @Test
    void reportPost() {
        CommonResult<String> result = postController.reportPost(1);
        assert result.getMsg().equals("举报成功");
    }

    @Test
    void reportBlock() {
        CommonResult<String> result = postController.reportBlock(2);
        assert result.getMsg().equals("举报成功");
    }

    @Test
    void reportReply() {
        CommonResult<String> result = postController.reportReply(3);
        assert result.getMsg().equals("举报成功");
    }
}