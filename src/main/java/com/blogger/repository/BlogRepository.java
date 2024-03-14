package com.blogger.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blogger.model.Blog;

public interface BlogRepository extends JpaRepository<Blog, Integer> {

}
