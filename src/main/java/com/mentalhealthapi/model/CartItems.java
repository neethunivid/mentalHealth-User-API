package com.mentalhealthapi.model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="cart_items")
public class CartItems {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(columnDefinition="VARCHAR(255)")
	private String title;
	@Column(columnDefinition="VARCHAR(255)")
	private String author;
	@Column(columnDefinition = "TEXT")
	private String casting;
	@Column(columnDefinition="VARCHAR(255)")
	private String press;
	private int price;
	@Column(columnDefinition = "tinyint")
	private byte type;
	@Column(columnDefinition="VARCHAR(255)")
	private String url;
	@Column(columnDefinition = "tinyint")
	private byte memberType;
	private int bookType;
	private int refprice;
	private int shipping;
	private int inventory;
	@Column(columnDefinition = "tinyint")
	private byte status;
	private Date date;
	public CartItems(String title, String author, String casting, String press, int price, byte type, String url,
			byte memberType, int bookType, int refprice, int shipping, int inventory, byte status, Date date) {
		super();
		this.title = title;
		this.author = author;
		this.casting = casting;
		this.press = press;
		this.price = price;
		this.type = type;
		this.url = url;
		this.memberType = memberType;
		this.bookType = bookType;
		this.refprice = refprice;
		this.shipping = shipping;
		this.inventory = inventory;
		this.status = status;
		this.date = date;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getCasting() {
		return casting;
	}
	public void setCasting(String casting) {
		this.casting = casting;
	}
	public String getPress() {
		return press;
	}
	public void setPress(String press) {
		this.press = press;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public byte getType() {
		return type;
	}
	public void setType(byte type) {
		this.type = type;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public byte getMemberType() {
		return memberType;
	}
	public void setMemberType(byte memberType) {
		this.memberType = memberType;
	}
	public int getBookType() {
		return bookType;
	}
	public void setBookType(int bookType) {
		this.bookType = bookType;
	}
	public int getRefprice() {
		return refprice;
	}
	public void setRefprice(int refprice) {
		this.refprice = refprice;
	}
	public int getShipping() {
		return shipping;
	}
	public void setShipping(int shipping) {
		this.shipping = shipping;
	}
	public int getInventory() {
		return inventory;
	}
	public void setInventory(int inventory) {
		this.inventory = inventory;
	}
	public byte getStatus() {
		return status;
	}
	public void setStatus(byte status) {
		this.status = status;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
	

}
