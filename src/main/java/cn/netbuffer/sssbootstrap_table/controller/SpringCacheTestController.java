package cn.netbuffer.sssbootstrap_table.controller;

import cn.netbuffer.sssbootstrap_table.model.User;
import cn.netbuffer.sssbootstrap_table.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Collection;

@Slf4j
@RestController
@RequestMapping("/cache")
public class SpringCacheTestController {

    @Resource
    private IUserService userService;
    @Resource
    private CacheManager cacheManager;

    private static final String USER_CACHE = "userCache";

    @Cacheable(value = USER_CACHE, key = "#userId")
    @RequestMapping(value = "get", method = RequestMethod.GET)
    public User getUser(Long userId) {
        log.info("get user [{}]", userId);
        return userService.getUserById(userId);
    }

    @RequestMapping(value = "/caches")
    public Collection<String> getCacheNames() {
        return cacheManager.getCacheNames();
    }

    @CacheEvict(value = {USER_CACHE}, key = "#userId")
    @RequestMapping(value = "/evict")
    public String evict(Long userId) {
        return String.format("clear [%s]:[%s]", USER_CACHE, userId);
    }
}