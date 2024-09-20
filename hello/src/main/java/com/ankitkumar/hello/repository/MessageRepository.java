package com.ankitkumar.hello.repository;

import com.ankitkumar.hello.model.MessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository< MessageEntity,Long> {
}
