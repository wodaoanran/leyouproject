package com.leyou.goodsweb.service.impl;

import com.leyou.goodsweb.service.GoodsHtmlService;
import com.leyou.goodsweb.service.GoodsService;
import com.leyou.goodsweb.utils.ThreadUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.File;
import java.io.PrintWriter;
import java.util.Map;

@Service
public class GoodsHtmlServiceImpl implements GoodsHtmlService {

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private TemplateEngine templateEngine;

    private static  final Logger logger=LoggerFactory.getLogger(GoodsHtmlService.class);



    @Override
    public void createHtml(Long spuId) {

        //查询后台数据
        Map<String, Object> modelMap = goodsService.lodelModel(spuId);

        Context context=new Context();
        //吧数据存到上下文里面
        context.setVariables(modelMap);

        File file=new File("G:\\leyoulx\\nginx-1.16.0\\html\\item\\"+spuId+".html");

        //创建输出流
        PrintWriter out=null;
        try {
            out = new PrintWriter(file);

            //执行页面静态化
            templateEngine.process("item", context, out);
        }catch(Exception e){
            logger.error("页面静态化出错了: "+spuId);
        }finally {
            if(out!=null){
                out.close();
            }
        }

    }

    public void asyncExcute(Long spuId){
        ThreadUtils.execute(()->createHtml(spuId));
    }

    @Override
    public void deleteHtml(Long spuId) {
        File f=new File("G:\\leyoulx\\nginx-1.16.0\\html\\item\\"+spuId+".html");
        f.delete();
    }
}
