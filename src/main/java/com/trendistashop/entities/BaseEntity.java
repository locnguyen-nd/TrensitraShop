package com.trendistashop.entities;

import jakarta.annotation.PreDestroy;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
/**
 * This class provides the first creation and last update dates for extended entities\
 * contains fields createAt and updateAt formatted according to local date
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public class BaseEntity {
    @Column(nullable = false, updatable = false, name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @Column(nullable = true, name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;
    @Column(nullable = true, name = "deleted_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date deletedAt;
    @PrePersist
    public void prePersist() {
        TimeZone vietnamTimeZone = TimeZone.getTimeZone("Asia/Ho_Chi_Minh");
        Calendar calendar = Calendar.getInstance(vietnamTimeZone);
        this.createdAt = calendar.getTime();
        this.updatedAt = null;
        this.deletedAt = null;
    }

    @PreUpdate
    public void preUpdate() {
        TimeZone vietnamTimeZone = TimeZone.getTimeZone("Asia/Ho_Chi_Minh");
        Calendar calendar = Calendar.getInstance(vietnamTimeZone);
        this.updatedAt = calendar.getTime();
    }
    @PreDestroy
    public void preDestroy() {
        TimeZone vietnamTimeZone = TimeZone.getTimeZone("Asia/Ho_Chi_Minh");
        Calendar calendar = Calendar.getInstance(vietnamTimeZone);
        this.deletedAt = calendar.getTime();
    }
}
