package com.apps.miaowu.bean.extend;

import com.apps.miaowu.bean.*;

import java.util.List;

public class ArticleExtend extends ArticleWithBLOBs {
    Animal animal;

    List<Label> labels;

    List<CommentExtend> commentExtends;

    User user;

    String authorName;

    Clip clip;


    public Clip getClip() {
        return clip;
    }

    public void setClip(Clip clip) {
        this.clip = clip;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public List<Label> getLabels() {
        return labels;
    }

    public void setLabels(List<Label> labels) {
        this.labels = labels;
    }

    public List<CommentExtend> getCommentExtends() {
        return commentExtends;
    }

    public void setCommentExtends(List<CommentExtend> commentExtends) {
        this.commentExtends = commentExtends;
    }

    public Animal getAnimal() {
        return animal;
    }

    public void setAnimal(Animal animal) {
        this.animal = animal;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    
}
