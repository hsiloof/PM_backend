package com.jinax.pm_backend.Controller;

import com.jinax.pm_backend.Component.CommonResult;
import com.jinax.pm_backend.Entity.Post;
import com.jinax.pm_backend.Service.BlockService;
import com.jinax.pm_backend.Service.PostService;
import com.jinax.pm_backend.Service.TagService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags ="PostController")
@RestController
@RequestMapping("/post")
public class PostController {
    private static final Logger LOGGER = LoggerFactory.getLogger(PostController.class);
    private final PostService postService;
    private final BlockService blockService;
    private final TagService tagService;

    public PostController(PostService postService, BlockService blockService, TagService tagService) {
        this.postService = postService;
        this.blockService = blockService;
        this.tagService = tagService;
    }

    @ApiOperation("获取帖子信息")
    @ResponseBody
    @GetMapping("/post/{postId}")
    public CommonResult<Post> getPostInfos(@PathVariable("postId") Integer postId){
        LOGGER.info("getPostInfos, id is: {}",postId);
        Post postById = postService.getPostById(postId);
        if (postById==null){
            return CommonResult.failResult(null,"无该帖子");
        }
        return CommonResult.successResult(postById,"成功");
    }
    @ApiOperation("获取一定点击量的帖子")
    @ResponseBody
    @GetMapping("/post/{viewTime}")
    public CommonResult<List<Post>> getPostsByViewTime(@PathVariable("viewTime") int viewTime){
        LOGGER.info("getPostsByViewTime, viewTime is: {}",viewTime);
        List<Post> posts = postService.getPostsByViewTimeGreaterThanEqual(viewTime);
        if (posts.size()<=0){
            return CommonResult.failResult(null,"无该帖子");
        }
        return CommonResult.successResult(posts,"成功");
    }
    @ApiOperation("获取指定用户的的帖子")
    @ResponseBody
    @GetMapping("/post/{ownerId}")
    public CommonResult<List<Post>> getPostsByOwnerId(@PathVariable("ownerId") int ownerId){
        LOGGER.info("getPostsByOwnerId, ownerId is: {}",ownerId);
        List<Post> posts = postService.getPostsByOwnerId(ownerId);
        if (posts.size()<=0){
            return CommonResult.failResult(null,"该用户不存在或未发过帖子");
        }
        return CommonResult.successResult(posts,"成功");
    }
}
