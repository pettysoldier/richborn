package com.yami.shop.security.admin.adapter;

import com.yami.shop.security.common.adapter.DefaultAuthConfigAdapter;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * @author 菠萝凤梨
 * @date 2022/3/28 14:57
 */
@Component
public class ResourceServerAdapter extends DefaultAuthConfigAdapter {
    public static final List<String> EXCLUDE_PATH = Arrays.asList(
            "/richborn/admin/webjars/**",
            "/richborn/admin/swagger/**",
            "/richborn/admin/v2/api-docs",
            "/richborn/admin/doc.html",
            "/richborn/admin/swagger-ui.html",
            "/richborn/admin/swagger-resources/**",
            "/richborn/admin/captcha/**",
            "/richborn/admin/adminLogin",
            "/richborn/admin/admin/file/upload/element");

    @Override
    public List<String> excludePathPatterns() {
        return EXCLUDE_PATH;
    }
}
