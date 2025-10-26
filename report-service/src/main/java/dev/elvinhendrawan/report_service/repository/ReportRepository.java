package dev.elvinhendrawan.report_service.repository;

import dev.elvinhendrawan.report_service.model.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {

    @Query(
            value = """
                SELECT u.email, COUNT(r.id) AS total_reports,
                       MAX(r.status) AS last_status
                FROM reports r
                JOIN users u ON r.user_id = u.id
                GROUP BY u.email
                HAVING COUNT(r.id) > :minCount
                ORDER BY total_reports DESC
                """,
            nativeQuery = true
    )
    List<Object[]> findUserReportStats(@Param("minCount") int minCount);

    @Query(value = """
        SELECT r.user_email AS email, COUNT(r.id) AS reportCount
        FROM reports r
        GROUP BY r.user_email
        ORDER BY reportCount DESC
        """, nativeQuery = true)
    List<Map<String, Object>> countReportsByEmail();
}