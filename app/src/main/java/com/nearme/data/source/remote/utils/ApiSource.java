package com.nearme.data.source.remote.utils;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by xnorcode on 07/04/2018.
 */

public class ApiSource {


    /**
     * Selector for the Google Places API query
     * NEARBY: nearbysearch/
     * SEARCH: textsearch/
     */
    public static final int NEARBY = 0;
    public static final int SEARCH = 1;

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({NEARBY, SEARCH})
    public @interface SOURCE {
    }
}
