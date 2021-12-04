package com.jinax.pm_backend.Repository;

import com.jinax.pm_backend.Entity.Notify;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotifyRepository extends JpaRepository<Notify,Integer> {
    Notify getNotifyById(int id);
    List<Notify> getNotifiesByOwnerId(int ownerId);
    List<Notify> getNotifiesByOwnerIdAndIsRead(int ownerId, short isRead);
    Integer countByOwnerIdAndIsRead(int ownerId,short isRead);
}
