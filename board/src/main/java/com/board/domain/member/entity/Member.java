package com.board.domain.member.entity;

import com.board.domain.member.entity.enumPackage.Role;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "members")
public class Member {

    // 회원 PK
    @Id @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    private UUID memberUUID;

    // 회원 아이디
    @Column(nullable = false, unique = true, length = 100)
    private String email;

    // 회원 패스워드
    @Column(nullable = false, length = 100)
    private String password;

    @Column(nullable = false, length = 60)
    private String nickname;

    @CreationTimestamp
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    @Column(nullable = false, updatable = false)
    private LocalDate enrollDate;

    @UpdateTimestamp
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate updateDate;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private List<Role> roles = new ArrayList<>();

    private String refreshToken;

    private Boolean emailAuth;

    @Builder
    public Member(String email, String password, String nickname, List<Role> roles, Boolean emailAuth, String provider) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.roles = Collections.singletonList(Role.ROLE_MEMBER);
        this.emailAuth = emailAuth;
    }

    public void addRole(Role role) {
        this.roles.add(role);
    }

    public void updateRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public void emailVerifiedCheck() {
        this.emailAuth = true;
    }

}
