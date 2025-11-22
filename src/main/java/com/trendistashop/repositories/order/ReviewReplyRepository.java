package com.trendistashop.repositories.order;

import com.trendistashop.entities.user.ReviewReply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 *
 * @author Locnd
 */
@Repository
public interface ReviewReplyRepository extends JpaRepository<ReviewReply, UUID> {
    List<ReviewReply> findByReviewIdAndParentReplyIsNullOrderByCreatedAtAsc(UUID reviewId);
}
