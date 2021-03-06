package com.wyj.search.service.impl;

import java.util.List;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wyj.common.pojo.SearchItem;
import com.wyj.common.pojo.TaotaoResult;
import com.wyj.search.mapper.ItemMapper;
import com.wyj.search.service.SearchItemService;

@Service
public class SearchItemServiceImpl implements SearchItemService {

		@Autowired
	    private SolrServer solrServer;

	    @Autowired
	    private ItemMapper itemMapper;

	    @Override
	    public TaotaoResult importAllItemToIndex() throws Exception {
	        // 1、查询所有商品数据。
	        List<SearchItem> itemList = itemMapper.getItemList();
	        // 2、创建一个SolrServer对象。
	        // 3、为每个商品创建一个SolrInputDocument对象。
	        //SolrInputDocument document = new SolrInputDocument();
	        for (SearchItem searchItem : itemList) {
	        	SolrInputDocument document = new SolrInputDocument();
	            // 4、为文档添加域
	            document.addField("id", searchItem.getId());
	            document.addField("item_title", searchItem.getTitle());
	            document.addField("item_sell_point", searchItem.getSell_point());
	            document.addField("item_price", searchItem.getPrice());
	            document.addField("item_image", searchItem.getImage());
	            document.addField("item_category_name", searchItem.getCaterory_name());
	            document.addField("item_desc", searchItem.getItem_desc());
	            // 5、向索引库中添加文档。
	            solrServer.add(document);
	        }
	        // 提交
	        solrServer.commit();
	        return TaotaoResult.ok();
	    }

}
