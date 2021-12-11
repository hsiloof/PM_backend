package com.jinax.pm_backend.Service;


import com.jinax.pm_backend.Entity.Notify;
import com.jinax.pm_backend.Repository.NotifyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

@Service
public class NotifyService {
    private static final Logger LOGGER = LoggerFactory.getLogger(NotifyService.class);
    private final NotifyRepository notifyRepository;

    public NotifyService(NotifyRepository notifyRepository) {
        this.notifyRepository = notifyRepository;
    }
    public Notify getNotifyById(int id){
        Notify byId = notifyRepository.getNotifyById(id);
        return byId;
    }


    public List<Notify> getNotifiesByOwnerIdAndNotRead(int id){
        List<Notify> byOwnerId = notifyRepository.getNotifiesByOwnerIdAndIsRead(id,(short) 0);
        LOGGER.info("getNotifiesByOwnerIdAndNotRead, owner_id : {}, notifies: {}",id,byOwnerId.toString());
        return byOwnerId;
    }
    public Notify createNotify(Notify notify){
        notify.setIsRead((short)0);
        Notify saveNotify = notifyRepository.save(notify);
        LOGGER.info("createNotify, notify before create : {}, notify after create : {}",notify,saveNotify);
        return saveNotify;
    }
    public List<Notify> getNotifiesByOwnerIdAndHasRead(int id){
        List<Notify> byOwnerId = notifyRepository.getNotifiesByOwnerIdAndIsRead(id,(short) 1);
        LOGGER.info("getNotifiesByOwnerIdAndHasRead, owner_id : {}, notifies: {}",id,byOwnerId.toString());
        return byOwnerId;
    }

    @Async
    public Future<Integer> getNumOfUnreadNotifies(int id) throws InterruptedException {

        while (true){
            synchronized (this){
                int count = notifyRepository.countByOwnerIdAndIsRead(id,(short) 0);
                if (count!=0){
                    return new AsyncResult<>(count);
                }
            }
            Thread.sleep(4000);
        }

    }

    public Notify updateNotify(Notify notify){
        Notify byId = getNotifyById(notify.getId());
        if (byId==null||byId.getIsRead()==(short)1){
            return byId;
        }
        byId.setIsRead((short)1);
        Notify saveNotify=notifyRepository.save(byId);
        LOGGER.info("updateNotify, notify after update : {}",saveNotify);
        return saveNotify;
    }

    private void change(List<Notify> list){
        ArrayList<Notify> list1 = new ArrayList<>(list);

    }
}
