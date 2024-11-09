package project.jobseekerplatform.Persistences;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import project.jobseekerplatform.Model.entities.Post;

import java.util.List;

@Repository
public interface PostRepo extends JpaRepository<Post, Integer> {
    List<Post> findAllByUserId(int userId);

    @Modifying
    @Transactional
    @Query(value = "UPDATE Post p SET p.content = ?2 WHERE p.id = ?1")
    void updatePostById(int id, String content);

    @Query(value = "SELECT p FROM Post p WHERE p.company.id = ?1")
    List<Post> findAllByCompanyId(int companyId, Pageable pageable);

    @Query(value = "SELECT p FROM Post p WHERE p.user.id = ?1")
    List<Post> findAllByUserId(int userId, Pageable pageable);

    long countByUserId(int userId);

    long countByCompanyId(int companyId);

    @Query(value = "select count(*) from post_react where post_id = :postId and user_id = :userId", nativeQuery = true)
    int checkLike(@Param("postId") int postId, @Param("userId") int userId);
}

