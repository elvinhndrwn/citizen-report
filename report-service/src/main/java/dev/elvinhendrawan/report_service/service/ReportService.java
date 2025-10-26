package dev.elvinhendrawan.report_service.service;

import dev.elvinhendrawan.report_service.dto.ReportCountDTO;
import dev.elvinhendrawan.report_service.model.Report;
import dev.elvinhendrawan.report_service.repository.ReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final ReportRepository reportRepository;
    private final RestClient restClient;

    @Value("${user.service.url}")
    private String userServiceUrl;

    public Report createReport(Report report) {
        boolean userExists = Boolean.TRUE.equals(restClient.get()
                .uri(userServiceUrl + "/users/exists?email=" + report.getUserEmail())
                .retrieve()
                .body(Boolean.class));

        if (!userExists) {
            throw new IllegalArgumentException("User dengan email " + report.getUserEmail() + " tidak ditemukan");
        }

        report.setCreatedAt(LocalDateTime.now());
        report.setStatus("PENDING");
        return reportRepository.save(report);
    }

    public List<Report> getAllReports() {
        return reportRepository.findAll();
    }

    public Report getReportById(Long id) {
        return reportRepository.findById(id).orElse(null);
    }

    public ResponseEntity<?> getStats(int minCount) {
        List<Object[]> rows = reportRepository.findUserReportStats(minCount);

        List<Map<String, Object>> result = rows.stream()
                .map(r -> Map.of(
                        "email", r[0],
                        "totalReports", r[1],
                        "lastStatus", r[2]
                ))
                .toList();

        return ResponseEntity.ok(result);
    }

    public List<ReportCountDTO> getReportCountByEmail() {
        List<Map<String, Object>> rawResults = reportRepository.countReportsByEmail();

        return rawResults.stream()
                .map(row -> new ReportCountDTO(
                        (String) row.get("email"),
                        ((Number) row.get("reportCount")).longValue()
                ))
                .toList();
    }

    public ResponseEntity<?> updateStatus(Long id, String newStatus) {
        Optional<Report> optionalReport = reportRepository.findById(id);
        if (optionalReport.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "Report not found"));
        }

        Report report = optionalReport.get();
        report.setStatus(newStatus);
        reportRepository.save(report);

        return ResponseEntity.ok(Map.of(
                "message", "Status updated successfully"
        ));
    }
}