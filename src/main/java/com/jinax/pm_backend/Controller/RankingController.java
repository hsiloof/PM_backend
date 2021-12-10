package com.jinax.pm_backend.Controller;

import com.jinax.pm_backend.Component.CommonResult;
import com.jinax.pm_backend.Entity.Post;
import com.jinax.pm_backend.Entity.Tag;
import com.jinax.pm_backend.Result.GetTopTagResult;
import com.jinax.pm_backend.Service.PostService;
import com.jinax.pm_backend.Service.TagService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author : chara
 */
@Api(tags = "RankingController")
@RestController
@RequestMapping("/ranking")
public class RankingController {
    private PostService postService;
    private TagService tagService;

    public RankingController(PostService postService, TagService tagService) {
        this.postService = postService;
        this.tagService = tagService;
    }

    @ApiOperation("获取排名前十的帖子")
    @ResponseBody
    @GetMapping("/post")
    public CommonResult<List<Post>> getTopPosts(){
        return CommonResult.successResult(postService.getTopPosts(),"操作成功");
    }

    @ApiOperation("获取附近排名前十的帖子")
    @ResponseBody
    @GetMapping("/post/nearby")
    public CommonResult<List<Post>> getTopPostsNearby(Double longitude,Double latitude){
        return CommonResult.successResult(postService.getTopPostsNearBy(longitude,latitude),"操作成功");
    }

    @ApiOperation("获取的帖子")
    @ResponseBody
    @GetMapping("/tag")
    public CommonResult<List<GetTopTagResult>> getTopTags(){
        return CommonResult.successResult(tagService.getTopTags(),"操作成功");
    }

}
