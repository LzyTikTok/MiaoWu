package com.apps.miaowu.bean.extend;

import com.apps.miaowu.bean.*;

import java.util.List;

public class ArticleExtend extends ArticleWithBLOBs {
    List<Animal> animals;

    List<ArticleLabel> articleLabels;

    List<Label> labels;

    List<CommentExtend> commentExtends;

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

    public List<Animal> getAnimals() {
        return animals;
    }

    public void setAnimals(List<Animal> animals) {
        this.animals = animals;
    }

    public List<ArticleLabel> getArticleLabels() {
        return articleLabels;
    }

    public void setArticleLabels(List<ArticleLabel> articleLabels) {
        this.articleLabels = articleLabels;
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
}
