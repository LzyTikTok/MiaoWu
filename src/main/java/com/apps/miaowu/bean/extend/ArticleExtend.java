package com.apps.miaowu.bean.extend;

import com.apps.miaowu.bean.*;

import java.util.List;

public class ArticleExtend extends ArticleWithBLOBs {
    List<Animal> animals;

    List<ArticleLabel> articleLabels;

    List<Label> labels;

    List<Comment> comments;

    String authorName;

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public List<Animal> getAnimals() {
        return animals;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
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


}
