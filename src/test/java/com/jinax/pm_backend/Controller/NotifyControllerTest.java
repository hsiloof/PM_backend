package com.jinax.pm_backend.Controller;

import com.jinax.pm_backend.Component.CommonResult;
import com.jinax.pm_backend.Entity.Notify;
import com.jinax.pm_backend.Repository.NotifyRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class NotifyControllerTest {
    ArrayList<Notify> list = new ArrayList<>();
    @Autowired
    NotifyController notifyController;
    @Autowired
    NotifyRepository notifyRepository;

    @BeforeEach
    private void addNotify(){
        for (int i = 0;i<2;i++){
            Notify notify = new Notify();
            notify.setOwnerId(2);
            notify.setPostId(1);
            notify.setIsRead((short)i);
            notify.setPostTitle("123");
            notify.setRespondent("123");
            Notify savedNotify = notifyRepository.save(notify);
            list.add(savedNotify);
        }
    }

    @Test
    void getNotifiesByUserIdAndUnread() {
        CommonResult<List<Notify>> result = notifyController.getNotifiesByUserIdAndUnread(2);
        List<Notify> data = result.getData();
        assert data.size()==1;
        assert data.get(0).getOwnerId()==2;
    }

    @Test
    void getNotifiesByUserIdAndRead() {
        CommonResult<List<Notify>> result = notifyController.getNotifiesByUserIdAndRead(2);
        List<Notify> data = result.getData();
        assert data.size()==1;
        assert data.get(0).getOwnerId()==2;
    }

    @Test
    void getNotifiesNumByUserIdAndUnRead() {
        CommonResult<Integer> result = notifyController.getNotifiesNumByUserIdAndUnRead(2);
        Integer data = result.getData();
        assert data==1;
    }

    @Test
    void updateNotify() {
        CommonResult<String> result = notifyController.updateNotify(list.get(0));
        assert result.getMsg().equals("更改成功");
        CommonResult<List<Notify>> result1 = notifyController.getNotifiesByUserIdAndRead(2);
        List<Notify> data = result1.getData();
        assert data.size()==2;
    }

    @AfterEach
    private void deleteNotify(){
        for (Notify notify:list){
            notifyRepository.deleteById(notify.getId());
        }
    }
}