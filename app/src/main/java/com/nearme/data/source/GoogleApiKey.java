package com.nearme.data.source;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

/**
 * Created by xnorcode on 16/05/2018.
 */
@Qualifier
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface GoogleApiKey {

}
