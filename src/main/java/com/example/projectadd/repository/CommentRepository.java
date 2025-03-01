package com.example.projectadd.repository;

import com.example.projectadd.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
    List<Comment> getCommentsByAds_Id(int id);

    void deleteAllByAds_Id(int id);

    Optional<Comment> findById(int integer);
}


