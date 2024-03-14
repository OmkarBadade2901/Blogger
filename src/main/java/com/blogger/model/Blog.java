package com.blogger.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;

@Entity
public class Blog implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int blogId;
	
	private String title;
	private String subTitle;
	@Lob
	@Column(columnDefinition = "BLOB")
	private String content;
	private String coverImageName;
	@Column(columnDefinition = "DATETIME")
	private LocalDateTime dateTime;
	
	@ManyToOne
	@JoinColumn
	private User user;

	public Blog() {
		super();
	}

	public Blog(int blogId, String title, String subTitle, String content, String coverImageName,
			LocalDateTime dateTime, User user) {
		super();
		this.blogId = blogId;
		this.title = title;
		this.subTitle = subTitle;
		this.content = content;
		this.coverImageName = coverImageName;
		this.dateTime = dateTime;
		this.user = user;
	}

	public int getBlogId() {
		return blogId;
	}

	public void setBlogId(int blogId) {
		this.blogId = blogId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSubTitle() {
		return subTitle;
	}

	public void setSubTitle(String subTitle) {
		this.subTitle = subTitle;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getCoverImageName() {
		return coverImageName;
	}

	public void setCoverImageName(String coverImageName) {
		this.coverImageName = coverImageName;
	}

	public LocalDateTime getDateTime() {
		return dateTime;
	}

	public void setDateTime(LocalDateTime dateTime) {
		this.dateTime = dateTime;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	
}
