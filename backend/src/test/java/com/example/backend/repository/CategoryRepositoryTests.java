package com.example.backend.repository;
import com.example.backend.repository.Category.CategoryRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Log4j2
public class CategoryRepositoryTests {

    @Autowired
    CategoryRepository categoryRepository;


//    @Test
//    public void insertCategory() {
//        Category category1 = Category.builder()
//                .categoryId(1L)
//                .categoryName("상의")
//                .build();
//        categoryRepository.save(category1);
//
//        Category category2 = Category.builder()
//                .categoryId(2L)
//                .categoryName("하의")
//                .build();
//        categoryRepository.save(category2);
//
//        Category category3 = Category.builder()
//                .categoryId(3L)
//                .categoryName("잡화")
//                .build();
//        categoryRepository.save(category3);
//
//        Category category4 = Category.builder()
//                .categoryId(4L)
//                .categoryName("신발")
//                .build();
//        categoryRepository.save(category4);
//
//    }



}
