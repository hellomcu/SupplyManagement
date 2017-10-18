package com.supply.management.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"com.supply.management"})
//@ComponentScan(basePackages = {"com.ehai.springmvc"},
//	excludeFilters={@Filter(type=FilterType.ANNOTATION, value=EnableWebMvc.class)
//	})
public class RootConfig
{

}
