package com.jinax.pm_backend.Controller;

import com.jinax.pm_backend.Component.CommonResult;
import com.jinax.pm_backend.Entity.*;
import com.jinax.pm_backend.Exception.InvalidBlockException;
import com.jinax.pm_backend.Exception.InvalidPostException;
import com.jinax.pm_backend.Exception.InvalidReplyException;
import com.jinax.pm_backend.Service.*;
import com.jinax.pm_backend.param.CreateBlockParam;
import com.jinax.pm_backend.param.CreatePostParam;
import com.jinax.pm_backend.param.CreateReplyParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Api(tags ="PostController")
@RestController
@RequestMapping("/post")
public class PostController {
    private static final Logger LOGGER = LoggerFactory.getLogger(PostController.class);
    private final PostService postService;
    private final BlockService blockService;
    private final ReplyService replyService;
    private final TagService tagService;
    private final ContentService contentService;
    private final UserService userService;
    private final NotifyService notifyService;

    public PostController(PostService postService, BlockService blockService, ReplyService replyService, TagService tagService, ContentService contentService, UserService userService, NotifyService notifyService) {
        this.postService = postService;
        this.blockService = blockService;
        this.replyService = replyService;
        this.tagService = tagService;
        this.contentService = contentService;
        this.userService = userService;
        this.notifyService = notifyService;
    }

    @ApiOperation("获取帖子信息")
    @ResponseBody
    @GetMapping("/{postId}")
    public CommonResult<Post> getPostInfos(@PathVariable("postId") Integer postId){
        LOGGER.info("getPostInfos, id is: {}",postId);
        Post postById = postService.getPostByIdNotDeleted(postId);
        if (postById==null){
            return CommonResult.failResult(null,"无该帖子");
        }
        return CommonResult.successResult(postById,"成功");
    }
    @ApiOperation("获取一定点击量的帖子")
    @ResponseBody
    @GetMapping("/view/{viewTime}")
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
    @GetMapping("/owner/{ownerId}")
    public CommonResult<List<Post>> getPostsByOwnerId(@PathVariable("ownerId") int ownerId){
        LOGGER.info("getPostsByOwnerId, ownerId is: {}",ownerId);
        List<Post> posts = postService.getPostsByOwnerId(ownerId);
        if (posts.size()<=0){
            return CommonResult.failResult(null,"该用户不存在或未发过帖子");
        }
        return CommonResult.successResult(posts,"成功");
    }

    @ApiOperation("获取指定用户回复的的帖子")
    @ResponseBody
    @GetMapping("/reply/{replierId}")
    public CommonResult<List<Post>> getPostsByReplierId(@PathVariable("replierId") int replierId){
        LOGGER.info("getPostsByReplierId, ownerId is: {}",replierId);
        List<Post> posts = postService.getPostsByReplierId(replierId);
        if (posts.size()<=0){
            return CommonResult.failResult(null,"该用户不存在或未回复过帖子");
        }
        return CommonResult.successResult(posts,"成功");
    }


    @ApiOperation("获取指定用户的的帖子")
    @ResponseBody
    @GetMapping("/answer/{postId}")
    public CommonResult<List<Block>> getAnswersByPostIdPaged(@PathVariable("postId") int postId,int page,int size){
        LOGGER.info("getAnswersByPostIdPaged, ownerId is: {}",postId);
        List<Block> blocks = blockService.getBlockByPostIdPaged(postId,page,size);
        if (blocks.size()<=0){
            return CommonResult.failResult(null,"该用户不存在或未发过帖子");
        }
        return CommonResult.successResult(blocks,"成功");
    }
//    @ApiOperation("增加新问题（发帖）")
    @ApiIgnore
    @ResponseBody
    @PostMapping("/question/add")
    @Transactional
    public CommonResult<String> createPost(CreatePostParam createPostParam){

        LOGGER.info("create post, post is: {}",createPostParam);
        try {
            User user = userService.getUserById(createPostParam.getOwner_id());
            Content content = new Content();
            content.setContent(createPostParam.getContent());
            Content saveContent = contentService.createContent(content);
            Set<Tag> tags = new HashSet<>();
            for (String tagName:createPostParam.getTagsList()){
                Tag tag = new Tag();
                tag.setName(tagName);
                tags.add(tag);
            }
            Set<Tag> saveTags = tagService.createTags(tags);
            Post post = new Post();
            post.setTitle(createPostParam.getTitle());
            post.setCity(createPostParam.getCity());
            post.setProvince(createPostParam.getProvince());
            post.setStreet(createPostParam.getStreet());
            post.setDistrict(createPostParam.getDistrict());
            post.setContent(saveContent);
            post.setTags(saveTags);
            post.setLongitude(createPostParam.getLongitude());
            post.setLatitude(createPostParam.getLatitude());
            post.setOwner(user);
            Post createdPost = postService.creatPost(post);

        }catch (InvalidPostException e){
            return CommonResult.failResult(null,"发帖失败");
        }
        return CommonResult.successResult(null,"发帖成功");
    }
    @ApiOperation("增加新回答（盖楼）")
    @ResponseBody
    @PostMapping("/question/answer/add")
    public CommonResult<String> createFloor(CreateBlockParam createBlockParam){
        LOGGER.info("create block, block is: {}",createBlockParam);
        try {
            User user = userService.getUserById(createBlockParam.getOwner_id());
            Block block = new Block();
            block.setCity(createBlockParam.getCity());
            block.setDistrict(createBlockParam.getDistrict());
            block.setProvince(createBlockParam.getProvince());
            block.setLatitude(createBlockParam.getLatitude());
            block.setLongitude(createBlockParam.getLongitude());
            block.setStreet(createBlockParam.getStreet());
            block.setPostId(createBlockParam.getPost_id());
            block.setContent(createBlockParam.getContent());
            block.setOwner(user);
            Block createdPost = blockService.createBlock(block);

            Post post = postService.getPostById(createdPost.getPostId());

            Notify notify = new Notify();
            notify.setPostId(post.getId());
            notify.setOwnerId(post.getOwner().getId());
            notify.setRespondent(user.getUsername());
            notify.setPostTitle(post.getTitle());
            notifyService.createNotify(notify);

        }catch (InvalidBlockException e){
            return CommonResult.failResult(null,"盖楼失败");
        }
        return CommonResult.successResult(null,"盖楼成功");
    }
    @ApiOperation("增加新回复")
    @ResponseBody
    @PostMapping("/question/comment/add")
    public CommonResult<String> createReply(CreateReplyParam createReplyParam){
        LOGGER.info("create reply, reply is: {}",createReplyParam);
        try {
            User user = userService.getUserById(createReplyParam.getOwner_id());
            Reply reply = new Reply();
            reply.setCity(createReplyParam.getCity());
            reply.setDistrict(createReplyParam.getDistrict());
            reply.setProvince(createReplyParam.getProvince());
            reply.setLatitude(createReplyParam.getLatitude());
            reply.setLongitude(createReplyParam.getLongitude());
            reply.setStreet(createReplyParam.getStreet());
            reply.setPostId(createReplyParam.getPost_id());
            reply.setContent(createReplyParam.getContent());
            reply.setOwner(user);
            reply.setBlockId(createReplyParam.getBlock_id());
            Reply createdReply = replyService.createReply(reply);

            Post post = postService.getPostById(createReplyParam.getPost_id());
            Block block = blockService.getBlockById(createReplyParam.getBlock_id());

            Notify notify = new Notify();
            notify.setOwnerId(block.getOwner().getId());
            notify.setPostId(createReplyParam.getPost_id());
            notify.setBlockId(block.getId());
            notify.setRespondent(user.getUsername());
            notify.setPostTitle(post.getTitle());
            notifyService.createNotify(notify);

        }catch (InvalidReplyException e){
            return CommonResult.failResult(null,"盖楼失败");
        }
        return CommonResult.successResult(null,"盖楼成功");
    }

}
