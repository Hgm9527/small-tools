package com.zhengqing.tool.crawler.api;

import com.zhengqing.common.core.api.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 小工具 - 爬虫 - 测试 接口
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2020/10/31 20:06
 */
@Slf4j
@RestController
@RequestMapping("/web/api/crawler/test")
@Api(tags = {"小工具 - 爬虫 - 测试 接口"})
public class StCrawlerTestController extends BaseController {

    @GetMapping("crawl")
    @ApiOperation("爬虫")
    public void crawl(String url) {

    }

}
