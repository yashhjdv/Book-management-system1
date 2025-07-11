package com.example.apicoding1.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.apicoding1.entity.books;

public interface BooksRepository extends JpaRepository<books,Integer> {

}
