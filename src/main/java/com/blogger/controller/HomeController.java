package com.blogger.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.blogger.model.Blog;
import com.blogger.service.BlogService;

import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {

	@Autowired
	private BlogService blogService;
	
	
	@GetMapping("/index")
	public String getHomePage() {
		return "index.html";
	}
	
	@GetMapping("/signup")
	public String getSignupPage() {
		return "signup.html";
	}
	
	@GetMapping("/signin")
	public String getSigninPage(HttpSession httpSession) {

		if(httpSession!=null && httpSession.getAttribute("userId")!=null)
			return "redirect:/myblogs";
		return "signin.html";
	}
	
	@GetMapping("/logout")
	public String getLogoutPage(HttpSession httpSession) {
		if(httpSession!=null && httpSession.getAttribute("userId")!=null) {
			httpSession.invalidate();
			}
		return "redirect:/index";
	}
	
	@GetMapping("/blogs")
	public String getBlogsPage(Model model) {
		model.addAttribute("blogs", blogService.getAllBlog());
		return "blogs.html";
	}
	
	@GetMapping("/viewBlog/{blogId}")
	public String getViewBlogPage(@PathVariable("blogId") Integer blogId,Model model) {
		Blog blog = blogService.findByBlogId(blogId);
		model.addAttribute("blog", blog);
		
		return "viewBlog.html";
	}
	
}
