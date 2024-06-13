package webframework.finalproject.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import webframework.finalproject.Model.Title;

import java.util.List;

@Repository
public interface TitleRepository extends JpaRepository<Title, Integer> {
    Title findByTitle(String title);
}
