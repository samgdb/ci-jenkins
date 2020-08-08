package com.lti.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.lti.dao.util.ConnManager;
import com.lti.entity.Product;

//classes which contain DB code are commonly
//referred to as DAO (Data Access Objects)
public class ProductDao {
	
	public Product select(int id) {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conn = ConnManager.connect();			
			
			String sql = "SELECT * FROM tb1_product WHERE id = ?";
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, id);  //1 means first ?
			
			rs = stmt.executeQuery();   //result set is like cursor
			if(rs.next()) {
				Product product = new Product();
				product.setId(rs.getInt("id"));
				product.setName(rs.getString("name"));
				product.setPrice(rs.getDouble("price"));
				return product;
			}
			return null; //something wrong person not authenticated
		}
		catch (SQLException e) {
			e.printStackTrace(); //
			return null;
		}
		finally {
			try { conn.close(); }  catch(Exception e) {}
		}
	}
	
	public List<Product> selectAll() {
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conn = ConnManager.connect();		
			String sql = "SELECT * FROM tb1_product";
			stmt = conn.prepareStatement(sql);			
			rs = stmt.executeQuery();   //result set is like cursor
			
			List<Product> list = new ArrayList<>();
			while(rs.next()) {
				Product product = new Product();
				product.setId(rs.getInt("id"));
				product.setName(rs.getString("name"));
				product.setPrice(rs.getDouble("price"));
				list.add(product);
			}
			return list;
		}
		catch (SQLException e) {
			e.printStackTrace(); //
			return null;
		}
		finally {
			try { conn.close(); }  catch(Exception e) {}
		}
	}
	
	public void insert(Product product) {
		
		Connection conn = null;
		PreparedStatement stmt = null;
	
		try {
			conn = ConnManager.connect();
			String sql = "INSERT INTO tb1_product VALUES(?, ?, ?)";
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, product.getId());  //1 means first ?
			stmt.setString(2, product.getName());
			stmt.setDouble(3, product.getPrice());
			int count = stmt.executeUpdate();
			//checking count important in case of update/delete statements
			//for ex: if(count == 0) indicates no rows got updated/deleted
		}
		catch (SQLException e) {
			e.printStackTrace(); //we shud rather throw an user defined exception
		}
		finally {
			try { conn.close(); }  catch(Exception e) {}
		}
	}

}