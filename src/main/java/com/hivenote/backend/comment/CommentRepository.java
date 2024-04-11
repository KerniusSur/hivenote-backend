package com.hivenote.backend.comment;

import com.hivenote.backend.comment.entity.CommentEntity;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<CommentEntity, UUID> {}
