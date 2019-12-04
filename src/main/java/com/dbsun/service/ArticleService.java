package com.dbsun.service;

import com.dbsun.entity.system.Page;
import com.dbsun.entity.system.PageData;
import com.dbsun.mapper.ArticleMapper;
import com.dbsun.mapper.ProCusContractMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 */
@Service
public class ArticleService extends BaseService {

    @Autowired
    private ArticleMapper articleMapper;

    public List<PageData> getArticleList(Page page) {
        return articleMapper.getArticleList(page);
    }

    public List<PageData> getList(Page page) {
        return articleMapper.getList(page);
    }

    public List<PageData> getTitleList(Page page) {
        return articleMapper.getTitleList(page);
    }

    public List<PageData> findText(Page page) {
        return articleMapper.findText(page);
    }

    public List<PageData> findTt(Page page) {
        return articleMapper.findTt(page);
    }

    public int updateById(PageData pd) {

        return articleMapper.updateById(pd);
    }

    public List<PageData> findPd() {
        return articleMapper.findPd();
    }

    public int getOrder() {
        return articleMapper.getOrder();
    }

    public int deleteText(PageData pd) {
        return articleMapper.deleteText(pd);
    }

    public int insertForeach(List<PageData> list) {
        return articleMapper.insertForeach(list);
    }

    public int addArticle(PageData pd) {
        return articleMapper.addArticle(pd);
    }

}
