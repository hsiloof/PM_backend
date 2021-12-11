package com.jinax.pm_backend.Controller;

import com.jinax.pm_backend.Component.CommonResult;
import com.jinax.pm_backend.Entity.Block;
import com.jinax.pm_backend.Entity.Post;
import com.jinax.pm_backend.Entity.Reply;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class AdminControllerTest {

    @Autowired
    AdminController adminController;



    @Test
    void getReportedPosts() {
        CommonResult<Map<String,Object>> result = adminController.getReportedPosts(7,0,10);
        Map<String,Object> data = result.getData();
        List<Post> list = (List<Post>) data.get("questions");
        assert list.size()==1;
        assert list.get(0).getIsDeleted()==1;
    }

    @Test
    void getReportedBlocks() {
        CommonResult<Map<String,Object>> result = adminController.getReportedBlocks(7,0,10);
        Map<String,Object> data = result.getData();
        List<Block> list = (List<Block>) data.get("answers");
        assert list.size()==6;
        assert list.get(0).getIsDeleted()==1;
    }

    @Test
    void getReportedComments() {
        CommonResult<Map<String,Object>> result = adminController.getReportedComments(7,0,10);
        Map<String,Object> data = result.getData();
        List<Reply> list = (List<Reply>) data.get("comments");
        assert list.size()==1;
        assert list.get(0).getIsDeleted()==1;
    }

    @Test
    void updateReportedPost() {
        adminController.updateReportedPost(7,7,(short) 0);
        CommonResult<Map<String,Object>> result = adminController.getReportedPosts(7,0,10);
        Map<String,Object> data = result.getData();
        List<Post> list = (List<Post>) data.get("questions");
        assert list.size()==0;
        adminController.updateReportedPost(7,7,(short) 1);
    }

    @Test
    void updateBlock() {
        adminController.updateBlock(7,2,(short) 0);
        CommonResult<Map<String,Object>> result = adminController.getReportedBlocks(7,0,10);
        Map<String,Object> data = result.getData();
        List<Block> list = (List<Block>) data.get("answers");
        assert list.size()==5;
        adminController.updateBlock(7,2,(short) 1);
    }

    @Test
    void updateReply() {
        adminController.updateReply(7,4,(short) 0);
        CommonResult<Map<String,Object>> result = adminController.getReportedComments(7,0,10);
        Map<String,Object> data = result.getData();
        List<Reply> list = (List<Reply>) data.get("comments");
        assert list.size()==0;
        adminController.updateReply(7,4,(short) 1);
    }
}