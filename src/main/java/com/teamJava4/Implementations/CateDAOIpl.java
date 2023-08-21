package com.teamJava4.Implementations;

import java.util.List;

import com.teamJava4.Entity.Categories;
import com.teamJava4.User.DAO.CategoryDAO;
import com.teamJava4.User.DAO.SystemDAO;

public class CateDAOIpl extends SystemDAO<Categories> implements CategoryDAO<Categories, Long>{

	@Override
	public List<Categories> findAllCategories() {
		return super.findAll(Categories.class);
	}
	
	
	public static void main(String[] args) {
		List<Categories> list = new CateDAOIpl().findAllCategories();
		list.forEach(item ->{
			System.out.println(item.toString());
		}
			);
		
	}
	
	
}
