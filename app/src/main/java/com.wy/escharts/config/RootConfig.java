package com.wy.escharts.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * Created by wangyang on 2016/12/23.
 */
@Configuration
@ComponentScan(basePackages = {"com.wy.escharts"},
                excludeFilters = {
                    @Filter(type = FilterType.ANNOTATION, value = EnableWebMvc.class)
                })
public class RootConfig {
}
