package com.blog.blogsearch.data;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SortCondition {
    ACCURACY("accuracy"), RECENCY("recency");
    private final String value;

    public static boolean isValidSortCondition(String str) {
        for (SortCondition sortCondition : SortCondition.values()) {
            if (sortCondition.value.equals(str)) {
                return true;
            }
        }
        return false;
    }
}
