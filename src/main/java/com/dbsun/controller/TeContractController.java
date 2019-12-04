package com.dbsun.controller;

import com.dbsun.base.BaseController;
import com.dbsun.entity.system.Page;
import com.dbsun.entity.system.PageData;
import com.dbsun.service.ArticleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

//import com.dbsun.core.TokenLimit;

/**
 * 测试
 */
@Api(value = "pro_cus_contract", description = "测试", tags = "测试")
@Controller
@RequestMapping("/pro_cus_contract")
public class TeContractController extends BaseController {

    @Autowired
    private ArticleService articleService;

    /*@RequestMapping(value = "/goArticlePage", method = RequestMethod.GET)
    @ApiOperation(value = "跳转到测试页面")
    public String goProCusContractPage() {
        return "";
    }

    *//**
     * 获取测试列表数据
     *
     * @return
     *//*
    @RequestMapping(value = "/getPageArticleList", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "获取测试列表数据", notes = "分页")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "showCount", value = "每页记录数", required = false, dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "currentPage", value = "当前页", required = true, dataType = "String"),
    })
    public JSONObject getArticleList() {
        PageData pd = this.getPageData();
        Page page = this.getPage();
        page.setPd(pd);
        List<PageData> proCusContractList = null;
        try {
            proCusContractList = articleService.getArticleList(page);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return viewReturnPageData(page, proCusContractList);
    }
*/

    /**
     * 查询表数据根据title
     *
     * @return
     */
    @RequestMapping(value = "/titleList", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "根据title获取测试列表数据", notes = "分页")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "title", value = "每页记录数", required = false, dataType = "String"),
    })
    public JSONObject gettitleList() {
        PageData pd = this.getPageData();
        Page page = this.getPage();
        page.setPd(pd);
        List<PageData> pl = null;

        try {
            pl = articleService.getTitleList(page);
            int i = 0;
            List<PageData> content = articleService.findText(page);
            //写一个for循环 如果a.id=c.f_id  就把数据封装成list放在a表数据后面
            for (PageData pageData : pl) {
                if (pl.get(i).get("id").equals(content.get(i).get("f_id"))) {
                    pageData.put(pl.get(i).get("title"), content);
                    i++;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return viewReturnPageData(page, pl);
    }

    /**
     * 根据条件获取测试列表数据
     *
     * @return
     */
    @RequestMapping(value = "/List", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "根据条件获取测试列表数据", notes = "分页")
    public JSONObject getList() {
        PageData pd = this.getPageData();
        Page page = this.getPage();
        page.setPd(pd);
        List<PageData> List = null;
        try {
            List = articleService.getList(page);

            int i = 0;
            List<PageData> content = articleService.findTt(page);
            //写一个for循环 如果a.id=c.f_id  就把数据封装成list放在a表数据后面
            for (PageData pageData : List) {
                if (List.get(i).get("id").equals(content.get(i).get("f_id"))) {
                    pageData.put(List.get(i).get("title"), content);
                    i++;
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return viewReturnPageData(page, List);
    }

    /**
     * 修改表数据
     *
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    @ResponseBody
    @ApiOperation(value = "获取测试列表数据", notes = "分页")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "id", value = "id", required = false, dataType = "int"),
            @ApiImplicitParam(paramType = "query", name = "f_id", value = "f_id", required = false, dataType = "int"),
            @ApiImplicitParam(paramType = "query", name = "title", value = "标题", required = false, dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "author", value = "作者", required = false, dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "tag", value = "标签", required = false, dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "list", value = "内容", required = false, dataType = "String"),
    })
    public void updateData() {
        PageData pd = this.getPageData();
        Page page = this.getPage();
        page.setPd(pd);

        List<PageData> one = null;
        int i = -1;
        try {
            //点开这条数据 得到相应的数据
            one = articleService.findPd();

            if (!one.isEmpty()) {
                //修改a表
                articleService.updateById(pd);

                //修改b表 修改text 先根据f_id删除数据 再加content数据
                articleService.deleteText(pd);
                pd.get("list");
                // 添加
                JSONArray jsonArray = JSONArray.fromObject(pd.getString("list"));
                Iterator iterator = jsonArray.iterator();
                //得到order条数
                int order = articleService.getOrder();
                ArrayList a = new ArrayList();
                List<PageData> list2 = null;
                while (iterator.hasNext()) {
                    String or = +order + "";
                    JSONObject jsonObject = JSONObject.fromObject(iterator.next());
                    //设置order值
                    jsonObject.put("a_order", or);
                    //把数据都添加到a list集合里面
                    a.add(jsonObject);
                    order++;
                    i++;
                }
                //把list<a> 转成 JSONArray
                JSONArray jsonArr = JSONArray.fromObject(a);
                //转成list
                list2 = JSONArray.toList(jsonArr, a.get(i), new JsonConfig());

                articleService.insertForeach(list2);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 新增表数据
     *
     * @return
     */
    @RequestMapping(value = "/addDatePage", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "获取测试列表数据", notes = "分页")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "title", value = "标题", required = false, dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "author", value = "作者", required = false, dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "tag", value = "标签", required = false, dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "contentList", value = "内容", required = false, dataType = "String")
    })
    public void addData() {
        PageData pd = this.getPageData();
        Page page = this.getPage();
        page.setPd(pd);
        List<PageData> one = null;
        int i = -1;
        try {
            one = articleService.findPd();
            //添加article表
            articleService.addArticle(pd);

            // 添加content
            JSONArray jsonArray = JSONArray.fromObject(pd.getString("contentList"));
            Iterator iterator = jsonArray.iterator();
            int order = articleService.getOrder();

            ArrayList a = new ArrayList();
            List<PageData> list2 = null;
            while (iterator.hasNext()) {
                String or = +order + "";
                //得到order条数
                JSONObject jsonObject = JSONObject.fromObject(iterator.next());
                jsonObject.put("a_order", or);
                a.add(jsonObject);
                order++;
                i++;
            }
            //把list<a> 转成 JSONArray
            JSONArray jsonArr = JSONArray.fromObject(a);

            list2 = JSONArray.toList(jsonArr, a.get(i), new JsonConfig());

            articleService.insertForeach(list2);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 添加图片
     */
    @RequestMapping(value = "/addImg", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject addImg(@RequestParam("file") MultipartFile file) {
        PageData pd = this.getPageData();
        JSONObject json = this.uploadPhoto(file);
        //文件不能为空
        if (json.get("msg").equals(500)) return this.getFalJson();

        return this.getSucJson(json);
    }


    public static void main(String[] args) {
        List<PageData> list = new ArrayList();
        PageData pageData = new PageData();
        pageData.put("text", "df打发");
        pageData.put("f_id", "6");
        pageData.put("type_ti", "1");
        PageData pageData2 = new PageData();
        pageData2.put("text", "是打发");
        pageData2.put("f_id", "6");
        pageData2.put("type_ti", "1");
        list.add(pageData);
        list.add(pageData2);

        System.out.println(list.toString());
    }


}
