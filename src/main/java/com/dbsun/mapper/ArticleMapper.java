package com.dbsun.mapper;

import com.dbsun.entity.system.Page;
import com.dbsun.entity.system.PageData;

import java.util.List;

public interface ArticleMapper {

    List<PageData> getArticleList(Page page);

    List<PageData> getList(Page page);

    List<PageData> getTitleList(Page page);

    int updateById(PageData pd);

    List<PageData> findPd();

    int deleteText(PageData pd);

    int insertForeach(List<PageData> list);

    int addArticle(PageData od);

    int getOrder();

    List<PageData> findText(Page page);

    List<PageData> findTt(Page page);

}
