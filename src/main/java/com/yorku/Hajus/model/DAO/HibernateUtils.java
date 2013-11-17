package com.yorku.Hajus.model.DAO;

import java.util.List;

import org.hibernate.Query;

final class HibernateUtils {

	public static <T> List<T> listAndCast(Query q) {
	    @SuppressWarnings("unchecked")
	    List<T> list = q.list();
	    return list;
	}
}