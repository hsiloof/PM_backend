package com.jinax.pm_backend.Controller;

import com.jinax.pm_backend.Component.CommonResult;
import com.jinax.pm_backend.Entity.Block;
import com.jinax.pm_backend.Entity.Post;
import com.jinax.pm_backend.Entity.Reply;
import com.jinax.pm_backend.Service.BlockService;
import com.jinax.pm_backend.Service.PostService;
import com.jinax.pm_backend.Service.ReplyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Api(tags = "AdminController")
@RestController
@RequestMapping("/admin")
public class AdminController {
    private PostService postService;
    private BlockService blockService;
    private ReplyService replyService;

    public AdminController(PostService postService, BlockService blockService, ReplyService replyService) {
        this.postService = postService;
        this.blockService = blockService;
        this.replyService = replyService;
    }
    @ApiOperation("获取被举报的问题")
    @ResponseBody
    @GetMapping("/report/questions")
    public CommonResult<Map<String,Object>> getReportedPosts(@RequestParam int userId,@RequestParam int page, @RequestParam int size) {
        Page<Post> pageList = postService.getReportedPost(page, size);
        Map<String,Object> map=new HashMap<>();
        List<Post> data = pageList.get().collect(Collectors.toList());
        map.put("questions",data);
        map.put("totalPage", pageList.getTotalPages());
        map.put("curPage", pageList.getNumber());
        return CommonResult.successResult(map,"成功获取被举报的问题");
    }
    @ApiOperation("获取被举报的回答")
    @ResponseBody
    @GetMapping("/report/answers")
    public CommonResult<Map<String,Object>> getReportedBlocks(@RequestParam int userId,@RequestParam int page, @RequestParam int size) {
        Page<Block> pageList = blockService.getReportedBlock(page, size);
        Map<String,Object> map=new HashMap<>();
        List<Block> data = pageList.get().collect(Collectors.toList());
        map.put("answers",data);
        map.put("totalPage", pageList.getTotalPages());
        map.put("curPage", pageList.getNumber());
        return CommonResult.successResult(map,"成功获取被举报的回答");
    }
    @ApiOperation("获取被举报的评论")
    @ResponseBody
    @GetMapping("/report/comments")
    public CommonResult<Map<String,Object>> getReportedComments(@RequestParam int userId,@RequestParam int page, @RequestParam int size) {
        Page<Reply> pageList = replyService.getReportedReplyList(page, size);
        Map<String,Object> map=new HashMap<>();
        List<Reply> data = pageList.get().collect(Collectors.toList());
        map.put("comments",data);
        map.put("totalPage", pageList.getTotalPages());
        map.put("curPage", pageList.getNumber());
        return CommonResult.successResult(map,"成功获取被举报的评论");
    }

    @ApiOperation("更新被举报的问题")
    @ResponseBody
    @PutMapping("/reverseQuestion/{id}")
    public CommonResult<String> updateReportedPost(@RequestParam int userId,@PathVariable int id,@RequestParam short operation) {
        postService.updateReportedPost(id,operation);
        return CommonResult.successResult(null,"更新成功");
    }

    @ApiOperation("更新被举报的回答")
    @ResponseBody
    @PutMapping("/reverseAnswer/{id}")
    public CommonResult<String> updateBlock(@RequestParam int userId,@PathVariable int id,@RequestParam short operation) {
        blockService.updateBlock(id,operation);
        return CommonResult.successResult(null,"更新成功");
    }

    @ApiOperation("更新被举报的评论")
    @ResponseBody
    @PutMapping("/reverseComment/{id}")
    public CommonResult<String> updateReply(@RequestParam int userId,@PathVariable int id,@RequestParam short operation) {
        replyService.updateReply(operation,id);
        return CommonResult.successResult(null,"更新成功");
    }
}
