package com.jinax.pm_backend.Controller;


import com.jinax.pm_backend.Component.CommonResult;
import com.jinax.pm_backend.Entity.Notify;
import com.jinax.pm_backend.Service.NotifyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags ="NotifyController")
@RestController
@RequestMapping("/notice")
public class NotifyController {
    private static final Logger LOGGER = LoggerFactory.getLogger(NotifyController.class);
    private final NotifyService notifyService;

    public NotifyController(NotifyService notifyService) {
        this.notifyService = notifyService;
    }

    @ApiOperation("获取未读通知")
    @ResponseBody
    @GetMapping("/unread")
    public CommonResult<List<Notify>> getNotifiesByUserIdAndUnread(@RequestParam int id){
        List<Notify> notifies = notifyService.getNotifiesByOwnerIdAndNotRead(id);
        return CommonResult.successResult(notifies,"获取成功");
    }
    //
    @ApiOperation("获取已读通知")
    @ResponseBody
    @GetMapping("/read")
    public CommonResult<List<Notify>> getNotifiesByUserIdAndRead(@RequestParam int id){
        List<Notify> notifies = notifyService.getNotifiesByOwnerIdAndHasRead(id);
        return CommonResult.successResult(notifies,"获取成功");
    }
    @ApiOperation("获取未读通知")
    @ResponseBody
    @GetMapping("/unreadNum")

    public CommonResult<Integer> getNotifiesNumByUserIdAndRead(@RequestParam int id){
        Integer notifiesNum = notifyService.getNumOfUnreadNotifies(id);
        return CommonResult.successResult(notifiesNum,"获取成功");
    }

    @ApiOperation("更改通知")
    @ResponseBody
    @PutMapping("")
    public CommonResult<String> getNotifiesNumByUserIdAndRead(Notify notify){
        Notify saveNotify = notifyService.updateNotify(notify);
        return CommonResult.successResult(null,"更改成功");
    }
}
