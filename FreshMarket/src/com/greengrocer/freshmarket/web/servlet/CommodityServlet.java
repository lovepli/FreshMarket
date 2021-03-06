package com.greengrocer.freshmarket.web.servlet;


import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.greengrocer.freshmarket.domain.Commodity;
import com.greengrocer.freshmarket.domain.PageBean;
import com.greengrocer.freshmarket.service.CommodityService;

import com.greengrocer.freshmarket.utils.WebUtils;
import com.greengrocer.freshmarket.web.formbean.CommodityForm;

public class CommodityServlet extends BaseServlet {

	private CommodityService service = new CommodityService();
	

	
	/**
	 * 查询所有商品信息实体（分页查询）
	 * @param request
	 * @param response
	 * @return 
	 * @throws ServletException
	 * @throws IOException
	 */
	public String findAllCommodity(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//获得url
		String url = getUrl(request);
		//获取页面传递过来的当前页码数
		int pageCode = getPageCode(request);
		//给pageSize,每页的记录数赋值
		int pageSize = 10;
		//得到商品信息实体集合
		PageBean<Commodity> pb = service.findAllCommodity(pageCode,pageSize);
		//设置url
		pb.setUrl(url);
		//存入request域中
		request.setAttribute("pb", pb);
		//跳转到显示商品信息页面
		return "/list.jsp";
		
	}
	
	/**
	 * 获取url信息
	 * @param request
	 * @return
	 */
	private String getUrl(HttpServletRequest request) {
		String url = request.getQueryString();
		 //url中有可能存在pageCode，需要把它截取掉
		int index = url.lastIndexOf("&pageCode=");
		if(index == -1) {
			return url;
		}
		return url.substring(0, index);
	}

	/**
	 * 获取当前页码数
	 * @param request
	 * @return
	 */
	private int getPageCode(HttpServletRequest request) {
		/*
		 * 1. 得到PageCode
		 *   如果PageCode参数不存在，说明PageCode=1
		 *   如果PageCode参数存在，需要转换成int类型即可
		 */
		String value = request.getParameter("pageCode");
		if(value == null || value.trim().isEmpty()) {
			return 1;
		}
		return Integer.parseInt(value);
	}


	
	
	

	/**
	 * 多条件查询商品信息
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String queryCommodity(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//获得url
		String url = getUrl(request);
		//获取页面传递过来的当前页码数
		int pageCode = getPageCode(request);
		//给pageSize,每页的记录数赋值
		int pageSize = 10;
		//把提交的表单封装成一个bean对象
		CommodityForm commodityForm = WebUtils.request2Bean(request, CommodityForm.class);
		//得到商品信息实体集合
		PageBean<Commodity> pb = service.queryCommodity(pageCode,pageSize,commodityForm);
		//设置url
		pb.setUrl(url);
		//存入request域中
		request.setAttribute("pb", pb);
		//转发到修改的列表
		return "/list.jsp";
	}
	
	

}
