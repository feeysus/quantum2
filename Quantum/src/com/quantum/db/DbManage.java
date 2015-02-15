package com.quantum.db;

import java.util.ArrayList;
import java.util.List;

import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.db.sqlite.WhereBuilder;
import com.lidroid.xutils.exception.DbException;
import com.quantum.bean.Content;
import com.quantum.bean.Location;
import com.quantum.bean.Message;

import android.content.Context;
import android.util.Log;

public class DbManage {
	public static DbUtils db;

	public DbManage(Context context) {
		db = DbUtils.create(context);
		db.configAllowTransaction(true);
		db.configDebug(true);
	}

	public static void saveBean(Object obj) {
		try {
			db.save(obj);
		} catch (DbException e) {
			Log.d("db_error------------>", e.getMessage());
		}
	}

	public static void saveLocation(Location loc) {
		try {
			db.save(loc);
		} catch (DbException e) {
			Log.d("db_error------------>", e.getMessage());
		}
	}

	public static List<?> findAllByBean(Class<?> bean) {
		List<?> _list = new ArrayList();
		try {
			_list = db.findAll(Selector.from(bean));
		} catch (DbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return _list;
	}

	public static Object findFirstByBean(Class<?> bean) {
		List<?> _list = new ArrayList();
		try {
			_list = db.findAll(bean);
		} catch (DbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (_list.size() > 0)
			return _list.get(0);
		else
			return null;
	}

	public static Content findContentByType(String value) {
		Content con = null;
		try {
			con = db.findFirst(Selector.from(Content.class).where("type", "=",
					value));
		} catch (DbException e) {
			e.printStackTrace();

		}
		return con;
	}

	public static void delete(Object obj) {
		if (obj == null)
			return;
		try {
			db.delete(obj);
		} catch (DbException e) {
			e.printStackTrace();
		}
	}
}
