package com.jinax.pm_backend.Controller;

import com.jinax.pm_backend.Component.CommonResult;
import com.jinax.pm_backend.Entity.Post;
import com.jinax.pm_backend.Entity.Tag;
import com.jinax.pm_backend.Service.PostService;
import com.jinax.pm_backend.Service.TagService;
import com.jinax.pm_backend.param.SearchAddressRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Api(tags ="SearchController")
@RestController
@RequestMapping("/search")
public class SearchController {
    private final TagService tagService;
    private final PostService postService;

    public SearchController(TagService tagService, PostService postService) {
        this.tagService = tagService;
        this.postService = postService;
    }

    @ApiOperation("根据tag模糊查询tag列表")
    @ResponseBody
    @GetMapping("/search_tag")
    public CommonResult<List<Tag>> getTagsByNameLike(String keyword){
        return CommonResult.successResult(tagService.getTagsByKeyword(keyword),"操作成功");
    }
    @ApiOperation("根据关键字返回相关帖子")
    @ResponseBody
    @GetMapping("/search_keyword")
    public CommonResult<Map<String,Object>> getPostsByTag(String keyword,Integer page,Integer size){
        return CommonResult.successResult(postService.getPostsByContent(keyword,page,size),"操作成功");
    }
    @ApiOperation("根据作者返回相关帖子")
    @ResponseBody
    @GetMapping("/search_author")
    public CommonResult<Map<String,Object>> getPostsByAuthor(String author, Integer page, Integer size){
        return CommonResult.successResult(postService.getPostsByAuthor(author, page, size),"操作成功");
    }
    @ApiOperation("根据经纬度返回相关帖子")
    @ResponseBody
    @GetMapping("/search_location")
    public CommonResult<Map<String,Object>> getPostsByNameLike(Double longitude,Double latitude,Double radius,Integer page,Integer size){
        return CommonResult.successResult(postService.getPostsByLocation(longitude,latitude,radius,page,size),"操作成功");
    }
    @ApiOperation("根据地址返回相关帖子")
    @ResponseBody
    @GetMapping("/search_address")
    public CommonResult<Map<String,Object>> getPostByAddress(SearchAddressRequest request){
        return CommonResult.successResult(postService.getPostsByAddress(request),"操作成功");
    }
    @ApiOperation("返回含有所有指定标签的帖子")
    @ResponseBody
    @GetMapping("/search_tags")
    public CommonResult<Map<String,Object>> getTagByNameLike(@RequestParam List<String> tags, Integer page, Integer size){
        return CommonResult.successResult(postService.getPostsByTags(tags,page,size),"操作成功");
    }
}
