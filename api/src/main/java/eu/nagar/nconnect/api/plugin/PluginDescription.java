/*
 * Copyright (c) 2016 - 2019  Nagar Group
 */

package eu.nagar.nconnect.api.plugin;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface PluginDescription {
    String name();
    String version();
    String[] authors();
}
