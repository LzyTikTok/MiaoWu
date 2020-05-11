package com.apps.miaowu.bean.extend;

import com.apps.miaowu.bean.*;

import java.util.List;

public class UserExtend extends User {
    //todo 救助的动物
    //todo 发现的动物
    List<Animal> foundAnimals;
    List<Save> saveAnimals;

    //todo token

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
