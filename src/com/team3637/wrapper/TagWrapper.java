package com.team3637.wrapper;

import com.team3637.model.Tag;

import java.util.List;

public class TagWrapper {

    List<Tag> tags;
    boolean[] deleted;

    public TagWrapper() {}

    public TagWrapper(List<Tag> tags, boolean[] deleted) {
        this.tags = tags;
        this.deleted = deleted;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public boolean[] getDeleted() {
        return deleted;
    }

    public void setDeleted(boolean[] deleted) {
        this.deleted = deleted;
    }
}
