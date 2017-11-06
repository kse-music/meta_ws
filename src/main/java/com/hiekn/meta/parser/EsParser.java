package com.hiekn.meta.parser;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.apache.commons.lang3.time.DateUtils;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;

import com.google.common.collect.Lists;
import com.hiekn.meta.bean.search.QueryCondition;
import com.hiekn.meta.bean.search.QueryOrder;

public class EsParser {

	private static final String PATTERN = "yyyy-MM-dd HH:mm:ss";
	
	private static final List<String> TIMEFIELDS = Lists.newArrayList("addTime","lastModifyTime","startTime","endTime","publishTime","foundedTime","enterCloseTime");

	public static boolean checkIs4ES(List<QueryCondition> qcList){
		if(Objects.isNull(qcList)|| qcList.isEmpty()){
			return false;
		}
		for(QueryCondition q : qcList){
			String condition = q.getCondition();
			if(QueryCondition.CONDITION.PREFIX.equals(condition) || QueryCondition.CONDITION.QUERYSTRING.equals(condition)){
				return true;
			}
		}
		return true;
	}

	public static void queryCondition2ES(List<QueryCondition> qcList,BoolQueryBuilder query){
		if(Objects.isNull(query)|| Objects.isNull(qcList) || qcList.isEmpty()){
			return;
		}
		for(QueryCondition qc : qcList){
			parseCondition(query,qc);
		}
		query.minimumShouldMatch(1);
	}

	public static void queryOrder2ES(List<QueryOrder> qoList, List<QueryCondition> qcList,SearchRequestBuilder searchRequestBuilder){
		if(Objects.isNull(qoList)|| qoList.isEmpty()){
			return;
		}
		//如果有搜索条件 排序无效
		if(Objects.nonNull(qcList)){
			for(QueryCondition qc : qcList){
				if(QueryCondition.CONDITION.PREFIX.equals(qc.getCondition()) || QueryCondition.CONDITION.QUERYSTRING.equals(qc.getCondition())){
					return;
				}
			}
		}
		FieldSortBuilder fsb = null;
		for(QueryOrder queryOrder : qoList){
			if(null!=queryOrder.getOrderField()&& null!=queryOrder.getOrderType()){
				fsb = SortBuilders.fieldSort(queryOrder.getOrderField()).order(queryOrder.getOrderType().toLowerCase().equals("asc")?SortOrder.ASC:SortOrder.DESC);
				searchRequestBuilder.addSort(fsb);
			}

		}
	}

	private static void parseCondition(BoolQueryBuilder bool,QueryCondition qc){
		String relation = qc.getRelation();
		String condition = qc.getCondition();
		boolean isFilter= qc.getIsFilter();
		String and = QueryCondition.RELATION.AND.toString();
		String or = QueryCondition.RELATION.OR.toString();
		for (QueryCondition.CONDITION con : QueryCondition.CONDITION.values()) {
			if (Objects.equals(condition,con.toString())) {
				QueryBuilder qb = null;
				if(QueryCondition.CONDITION.QUERYSTRING.equals(condition)){
					qb = QueryBuilders.queryStringQuery(qc.getvList().get(0)).field(qc.getField());
				}else if(QueryCondition.CONDITION.IN.equals(condition)){
					qb = QueryBuilders.termsQuery(qc.getField(), qc.getvList());
				}else if(QueryCondition.CONDITION.RANGE.equals(condition)){
					String field = qc.getField();
					if(TIMEFIELDS.contains(field)){
						try {
							Date from = DateUtils.parseDate(qc.getvList().get(0), PATTERN);
							Date to = DateUtils.parseDate(qc.getvList().get(1), PATTERN);
							qb = QueryBuilders.rangeQuery(field).from(from).to(to);
						} catch (ParseException e) {
						}
					}else{
						qb = QueryBuilders.rangeQuery(field).from(qc.getvList().get(0)).to(qc.getvList().get(1));
					}
				}else{
					qb = QueryBuilders.termQuery(qc.getField(), qc.getvList().get(0));
				}
				if(and.equals(relation)){
					if(isFilter){
						bool.filter(qb);
					}else{
						bool.must(qb);
					}
				}else if(or.equals(relation)){
					if(isFilter){
						bool.filter(qb);
					}else{
						bool.should(qb);
					}
				}
			}
		}
	}
}