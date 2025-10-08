package com.practice.ui.config;

import org.aeonbits.owner.Config;

@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({"classpath:config/browser.properties", "classpath:config/tests.properties"})
public interface BrowserConfig extends Config {
    
    @Key("browser.type")
    @DefaultValue("chrome")
    String browserType();
    
    @Key("browser.headless")
    @DefaultValue("false")
    boolean isHeadless();
    
    @Key("browser.width")
    @DefaultValue("1920")
    int browserWidth();
    
    @Key("browser.height")
    @DefaultValue("1080")
    int browserHeight();
    
    @Key("browser.timeout")
    @DefaultValue("60")
    int timeout();
    
    @Key("browser.remote")
    @DefaultValue("false")
    boolean isRemote();

    @Key("browser.remote.url")
    String remoteUrl();
    
    @Key("form.page.url")
    String formPageUrl();
}