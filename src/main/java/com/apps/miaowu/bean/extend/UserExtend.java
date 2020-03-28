package com.apps.miaowu.bean.extend;

import com.apps.miaowu.bean.Found;
import com.apps.miaowu.bean.Save;
import com.apps.miaowu.bean.User;

import java.util.List;

public class UserExtend extends User {
    List<Found> found;
    List<Save> save;

    public List<Found> getFound() {
        return found;
    }

    public void setFound(List<Found> found) {
        this.found = found;
    }

    public List<Save> getSave() {
        return save;
    }

    public void setSave(List<Save> save) {
        this.save = save;
    }
}
