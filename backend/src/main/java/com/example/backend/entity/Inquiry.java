package com.example.backend.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class Inquiry extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long inquiryId;

    @Column(nullable = false, length = 150)
    private String inquiryTitle;

    @Column(nullable = false, length = 300)
    private String inquiryContent;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private Users user;

    public void change(String inquiryTitle, String inquiryContent) {
        this.inquiryContent = inquiryContent;
        this.inquiryTitle = inquiryTitle;
    }
}