package com.apps.miaowu.bean.extend;

import com.apps.miaowu.bean.*;

import java.util.List;

public class UserExtend extends User {
    List<Animal> foundAnimals;

    List<Save> saveAnimals;

    List<User> follows;

    List<User> fans;

    public List<User> getFollows() {
        return follows;
    }

    public void setFollows(List<User> follows) {
        this.follows = follows;
    }

    public List<User> getFans() {
        return fans;
    }

    public void setFans(List<User> fans) {
        this.fans = fans;
    }

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }

    List<Article> articles;

    public List<Animal> getFoundAnimals() {
        return foundAnimals;
    }

    public void setFoundAnimals(List<Animal> foundAnimals) {
        this.foundAnimals = foundAnimals;
    }

    public List<Save> getSaveAnimals() {
        return saveAnimals;
    }

    public void setSaveAnimals(List<Save> saveAnimals) {
        this.saveAnimals = saveAnimals;
    }
}
