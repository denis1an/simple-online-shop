package ru.andreev.dracaena.entity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public abstract class AbstractEntity {

    private Long id;

    private LocalDateTime created;

    private LocalDateTime updated;

    public void toCreate(){
        setCreated(LocalDateTime.now());
        setUpdated(LocalDateTime.now());
    }

    public void toUpdate(){
        setUpdated(LocalDateTime.now());
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public LocalDateTime getUpdated() {
        return updated;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public void setUpdated(LocalDateTime updated) {
        this.updated = updated;
    }

    public void setCreated(String created){
        this.created = LocalDateTime.parse(created, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public void setUpdated(String updated){
        this.updated = LocalDateTime.parse(updated, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public String getCreatedToString(){
        return created.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public String getUpdatedString(){
        return updated.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}
