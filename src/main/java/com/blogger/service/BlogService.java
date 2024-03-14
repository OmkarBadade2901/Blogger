package com.blogger.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blogger.model.Blog;
import com.blogger.repository.BlogRepository;

@Service
public class BlogService {

	@Autowired
	private BlogRepository blogRepository;
	
	public Blog findByBlogId(Integer id) {
		return blogRepository.findById(id).get();
	}
	
	public void deleteBlog(Blog blog) {
		blogRepository.delete(blog);
	}
	
	public List<Blog> getAllBlog(){
		List<Blog> blogs=null;
		blogs = blogRepository.findAll();
		return blogs;
	}
}
