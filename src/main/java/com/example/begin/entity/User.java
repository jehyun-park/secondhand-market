package com.example.begin.entity;

import com.example.begin.entity.listener.DateListener;
import com.example.begin.entity.listener.LibraryEntityListener;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data

@EntityListeners(value = {LibraryEntityListener.class})

public class User implements DateListener {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    private String userId;
    private String userPw;
    private String nick;
    private String email;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}

