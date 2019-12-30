package com.yksys.isystem.zuul.locator;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.yksys.isystem.common.core.event.RemoteRefreshRouteEvent;
import com.yksys.isystem.common.core.utils.StringUtil;
import com.yksys.isystem.common.pojo.GatewayRoute;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.cloud.netflix.zuul.RoutesRefreshedEvent;
import org.springframework.cloud.netflix.zuul.filters.SimpleRouteLocator;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationListener;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;

/**
 * @program: YK-iSystem
 * @description: 动态路由加载器
 * @author: YuKai Fan
 * @create: 2019-12-27 10:23
 **/
@Slf4j
public class JdbcRouteLocator extends SimpleRouteLocator implements ApplicationListener<RemoteRefreshRouteEvent> {
    @Override
    public void doRefresh() {
        super.doRefresh();
        //发布本地刷新事件, 更新相关本地缓存, 解决动态加载完新路由映射无效的问题
        publisher.publishEvent(new RoutesRefreshedEvent(this));
    }

    //jdbc模板
    private JdbcTemplate jdbcTemplate;
    //yml中zuul配置参数
    private ZuulProperties zuulProperties;
    //事件发布, 把某个事件告诉的所有与这个事件相关的监听器
    private ApplicationEventPublisher publisher;

    public JdbcRouteLocator(String servletPath, ZuulProperties properties, JdbcTemplate jdbcTemplate, ApplicationEventPublisher publisher) {
        super(servletPath, properties);
        this.zuulProperties = properties;
        this.jdbcTemplate = jdbcTemplate;
        this.publisher = publisher;
    }

    @Override
    protected Map<String, ZuulProperties.ZuulRoute> locateRoutes() {
        Map<String, ZuulProperties.ZuulRoute> routesMap = Maps.newLinkedHashMap();
        routesMap.putAll(super.locateRoutes());
        //从数据库中加载路由信息
        routesMap.putAll(loadRoutes());
//        //优化配置
//        routesMap.forEach((key, value) -> {
//        });

        return routesMap;
    }

    private Map<String, ZuulProperties.ZuulRoute> loadRoutes() {
        Map<String, ZuulProperties.ZuulRoute> routes = Maps.newLinkedHashMap();
        List<GatewayRoute> list = Lists.newCopyOnWriteArrayList();
        try {
            list = jdbcTemplate.query(getSQL(), (RowMapper<GatewayRoute>) (resultSet, i) -> {
                GatewayRoute gatewayRoute = new GatewayRoute();
                gatewayRoute.setId(resultSet.getString("id"));
                gatewayRoute.setRouteName(resultSet.getString("route_name"));
                gatewayRoute.setPath(resultSet.getString("path"));
                gatewayRoute.setPersist(resultSet.getInt("persist"));
                gatewayRoute.setRetryable(resultSet.getInt("retryable"));
                gatewayRoute.setStripPrefix(resultSet.getInt("strip_prefix"));
                gatewayRoute.setServiceId(resultSet.getString("service_id"));
                gatewayRoute.setRouteDesc(resultSet.getString("route_desc"));
                gatewayRoute.setUrl(resultSet.getString("url"));
                gatewayRoute.setStatus(resultSet.getInt("status"));
                return gatewayRoute;
            });

            if (!CollectionUtils.isEmpty(list)) {
                list.stream().filter(result -> !StringUtil.isEmpty(result.getPath()))
                        .filter(result -> !StringUtil.isEmpty(result.getServiceId()) && ! StringUtil.isEmpty(result.getUrl()))
                        .forEach(result ->{
                            ZuulProperties.ZuulRoute zuulRoute = new ZuulProperties.ZuulRoute();
                            BeanUtils.copyProperties(result, zuulRoute);
                            zuulRoute.setId(result.getServiceId());
                            routes.put(zuulRoute.getPath(), zuulRoute);
                        });
            }

            log.info("加载动态路由:{}", list.size());
        } catch (DataAccessException e) {
            log.error("加载动态路由错误:", e);
        }

        return routes;
    }

    private String getSQL() {
        StringBuffer result = new StringBuffer("select a.id, a.route_name, a.path, a.service_id, a.url, a.strip_prefix, a.retryable, a.persist, a.status, a.route_desc");
        result.append(" from tb_gateway_route a where a.status = 1");
        return result.toString();
    }

    @Override
    public void onApplicationEvent(RemoteRefreshRouteEvent remoteRefreshRouteEvent) {

    }
}