package org.karic.smartrefreshlayout;

import java.util.Collection;

final class Utils {
    static boolean isEmpty(Collection<?> c) {
        return c == null || c.size() < 1;
    }
}
