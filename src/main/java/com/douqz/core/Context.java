package com.douqz.core;

import jakarta.annotation.Nullable;
import org.apache.catalina.Container;

import java.util.Collection;

/**
 * @author yui
 */
public interface Context {
    void addChild(Wrapper child);

    Collection<Wrapper> findChildren();

    @Nullable
    Wrapper findMatchChild(String uri);
}
