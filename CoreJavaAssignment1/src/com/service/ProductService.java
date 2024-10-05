package com.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.bean.Product;

public class ProductService {

	private List<Product> listOfProducts;
	public ProductService() {
		try {
			File file = new File("products.ser");
			if(file.exists())
			{
				FileInputStream fis = new FileInputStream("products.ser");
				ObjectInputStream ois = new ObjectInputStream(fis);
				listOfProducts = (List<Product>)ois.readObject();
			}
			else
			{
				file.createNewFile();
				listOfProducts = new ArrayList<Product>();
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			System.out.println("products.ser Not found");
		}
	}
	
	
	
	public String addProduct(Product product) {
		int flag = 0;
		if(listOfProducts.size()==0) {
		listOfProducts.add(product);
		writeData();		
		return "Product added successfully";
		}else {
				Iterator<Product> li = listOfProducts.iterator();
				while(li.hasNext()) {
					Product p  = li.next();
					if(p.getPid()==product.getPid()) {
						flag++;
						break;
					}
				}
		}
		if(flag==0) {
			listOfProducts.add(product);
			writeData();
			return "Product added successfully";
		}else {
			flag =0;
			return "Product is must be unique";
		}
	}

	public List<Product> findAllProducts(){
		return listOfProducts;
	}
	
	public String deleteProduct(int pid) {
		int flag = 0;
				
		Iterator<Product>	li = listOfProducts.iterator();
		while(li.hasNext()) {
		Product p  = li.next();
		if(p.getPid()==pid) {
			li.remove();
				flag++;
				break;
				}
		}
		
		if(flag==0) {
			return "Product not present";
		}else {
			writeData();
			flag =0;
			return "Product deleted successfully";
		}
	}
	public String updatetProduct(Product product) {
		int flag = 0;		
		Iterator<Product>	li = listOfProducts.iterator();
		while(li.hasNext()) {
		Product p  = li.next();
		if(p.getPid()==product.getPid()) {
			p.setPrice(product.getPrice());
				flag++;
				break;
				}
		}
		if(flag==0) {
			return "Product not present";
		}else {
			writeData();
			flag =0;
			return "Product price updated successfully";
		}
	}
	
	private void writeData() {
		try {
			File file = new File("products.ser");
			file.delete();
			file.createNewFile();
			FileOutputStream fos = new FileOutputStream("products.ser");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(listOfProducts);
			oos.close();
			fos.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
