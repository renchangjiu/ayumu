package com.douqz.core;

import jakarta.annotation.Nullable;
import org.apache.catalina.Container;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author yui
 */
public class DefaultContext implements Context {
    protected final HashMap<String, Wrapper> children = new HashMap<>();

    @Override
    public void addChild(Wrapper child) {
        synchronized (children) {
            this.children.put(child.getName(), child);
        }
    }

    @Override
    public Collection<Wrapper> findChildren() {
        synchronized (children) {
            return this.children.values();
        }
    }

    @Override
    @Nullable
    public Wrapper findMatchChild(String uri) {
        for (Map.Entry<String, Wrapper> ent : children.entrySet()) {
            Wrapper val = ent.getValue();
            if (val.uriMatch(uri)) {
                return val;
            }
        }
        return null;
    }
}
