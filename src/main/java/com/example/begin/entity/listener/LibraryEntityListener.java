package com.example.begin.entity.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;

public class LibraryEntityListener {
    private static final Logger log = LoggerFactory.getLogger(LibraryEntityListener.class);
    public LibraryEntityListener() {
    }
    @PrePersist
    public void prePersist(Object o){
        log.info("prePersist ------------------------------- ");
        if(o instanceof DateListener){
            ((DateListener)o).setCreatedAt(LocalDateTime.now());
            ((DateListener)o).setUpdatedAt(LocalDateTime.now());
         }
    }

    @PreUpdate
    public void preUpdate(Object o){
        log.info("prePersist ------------------------------- ");
        if(o instanceof DateListener){
            ((DateListener)o).setUpdatedAt(LocalDateTime.now());

        }
    }
}
