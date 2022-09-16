package com.realfinance.sofa.flow.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * 重写了toString
 */
public class UserIdList extends ArrayList<String> {

    public UserIdList(int initialCapacity) {
        super(initialCapacity);
    }

    public UserIdList() {
    }

    public UserIdList(Collection<? extends String> c) {
        super(c);
    }

    @Override
    public String toString() {
        Iterator<String> it = iterator();
        if (! it.hasNext())
            return "";

        StringBuilder sb = new StringBuilder();
        for (;;) {
            String e = it.next();
            sb.append(e);
            if (! it.hasNext())
                return sb.toString();
            sb.append(',').append(' ');
        }
    }
}
