package com.blogger.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.blogger.model.Blog;
import com.blogger.model.User;
import com.blogger.service.BlogService;
import com.blogger.service.MyUtility;
import com.blogger.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
public class AccountController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private MyUtility myUtility;
	
	@Autowired
	private BlogService blogService;
	
	@PostMapping("/registerUser")
	public ModelAndView register(User user,@RequestParam("file-upload")MultipartFile file, ModelAndView modelAndView) throws IOException {
		if(!userService.checkEmail(user.getEmail())) {
			user.setImageData(file.getBytes());
			user.setPassword(myUtility.generateHash(user.getPassword()));
			userService.saveUser(user);
			modelAndView.setViewName("redirect:/signin");		
		}
		else {
			modelAndView.setViewName("signup");					
			modelAndView.addObject("message", "The Email has already been taken");
		}
		
		
		return modelAndView;
	}
	
	
	@PostMapping("/authenticate")
	public ModelAndView authenticateUser(@RequestParam("email")String email,@RequestParam("password")String password,HttpSession session, ModelAndView modelAndView ) {
		Integer userId = userService.authenticateUser(email, password);
		if(userId!=null) {
			modelAndView.setViewName("redirect:/myblogs");
			modelAndView.addObject("userId",userId);
			session.setAttribute("userId",userId);
		}
		else {
			modelAndView.setViewName("signin");
			modelAndView.addObject("message", "Something went wrong!");
		}
		
		return modelAndView;
	}
	
	@GetMapping("/myblogs")
	public String getMyblogsPage(HttpSession session,Model model) {
		User user = userService.getUserById((Integer)session.getAttribute("userId"));
		model.addAttribute("blogs", user.getBlogs());
		return "myblogs.html";
	}
	@GetMapping("/createblog")
	public String getcreateblogPage() {
		return "createblog.html";
	}
	
	@PostMapping("/saveBlog")
	public String saveBlog(Blog blog,@RequestParam("coverImage")MultipartFile file,HttpSession session) throws IllegalStateException, IOException {
		
		User user = userService.getUserById((Integer)session.getAttribute("userId"));
		if(file!=null) {
			blog.setCoverImageName(myUtility.saveImage(file));
		}
		  
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss a");
        String formattedDateTime = currentDateTime.format(formatter);
        blog.setDateTime(LocalDateTime.parse(formattedDateTime, formatter));
       List<Blog> blogs= user.getBlogs();
       blogs.add(blog);
       user.setBlogs(blogs);
       blog.setUser(user);
       userService.saveUser(user);
       return "redirect:/myblogs";
	}
	
	@GetMapping("/deleteBlog/{blogId}")
	public String deleteBlog(@PathVariable Integer blogId,HttpSession session) {
		User user = userService.getUserById((Integer)session.getAttribute("userId"));
		Blog blog = blogService.findByBlogId(blogId);
		myUtility.deleteImage(blog.getCoverImageName());
		List<Blog> blogs= user.getBlogs();
		Blog oldBlog= blogs.stream()
			.filter(b->b.getBlogId()== blog.getBlogId())
			.findFirst()
			.get();
		blogs.remove(oldBlog);
		blogService.deleteBlog(blog);
		user.setBlogs(blogs);
		userService.saveUser(user);		
		return "redirect:/myblogs";
	}
	
	@GetMapping("/modifyPost/{blogId}")
	public String getModifyBlogPage(@PathVariable("blogId") Integer blogId,Model model) {
		Blog blog = blogService.findByBlogId(blogId);
		model.addAttribute("blog", blog);
		
		return "modifyPost.html";
	}
	
	@PostMapping("/modifyBlogI")
	public String modifyblogwithImage(Blog blog,MultipartFile file,HttpSession session) throws IOException {
		User user = userService.getUserById((Integer)session.getAttribute("userId"));
		Blog oldblog = blogService.findByBlogId(blog.getBlogId());
		oldblog.setTitle(blog.getTitle());
		oldblog.setSubTitle(blog.getSubTitle());
		if(file!=null) {
			myUtility.deleteImage(oldblog.getCoverImageName());
			oldblog.setCoverImageName(myUtility.saveImage(file));
		}
		LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss a");
        String formattedDateTime = currentDateTime.format(formatter);
        oldblog.setDateTime(LocalDateTime.parse(formattedDateTime, formatter));
		oldblog.setContent(blog.getContent());
		List<Blog> blogs= user.getBlogs();
		Blog oldBlog2= blogs.stream()
			.filter(b->b.getBlogId()== blog.getBlogId())
			.findFirst()
			.get();
		oldBlog2=oldblog;
		user.setBlogs(blogs);
		userService.saveUser(user);	
		return "redirect:/myblogs";
	}
	
	@PostMapping("/modifyBlog")
	public String modifyblogwithoutImage(Blog blog,HttpSession session){
		User user = userService.getUserById((Integer)session.getAttribute("userId"));
		Blog oldblog = blogService.findByBlogId(blog.getBlogId());
		oldblog.setTitle(blog.getTitle());
		oldblog.setSubTitle(blog.getSubTitle());		
		LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss a");
        String formattedDateTime = currentDateTime.format(formatter);
        oldblog.setDateTime(LocalDateTime.parse(formattedDateTime, formatter));
		oldblog.setContent(blog.getContent());
		List<Blog> blogs= user.getBlogs();
		Blog oldBlog2= blogs.stream()
			.filter(b->b.getBlogId()== blog.getBlogId())
			.findFirst()
			.get();
		oldBlog2=oldblog;
		user.setBlogs(blogs);
		userService.saveUser(user);	
		return "redirect:/myblogs";
	}
	
	
}
