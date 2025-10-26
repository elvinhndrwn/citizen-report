package dev.elvinhendrawan.report_service.controller;

import dev.elvinhendrawan.report_service.dto.ReportCountDTO;
import dev.elvinhendrawan.report_service.dto.ReportUpdateDTO;
import dev.elvinhendrawan.report_service.model.Report;
import dev.elvinhendrawan.report_service.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reports")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @PostMapping
    public ResponseEntity<Report> createReport(@RequestBody Report report) {
        return ResponseEntity.ok(reportService.createReport(report));
    }

    @GetMapping
    public ResponseEntity<List<Report>> getAllReports() {
        return ResponseEntity.ok(reportService.getAllReports());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Report> getReportById(@PathVariable Long id) {
        return ResponseEntity.ok(reportService.getReportById(id));
    }

    @GetMapping("/stats")
    public ResponseEntity<?> getStats(@RequestParam(defaultValue = "0") int minCount) {
        return reportService.getStats(minCount);
    }

    @GetMapping("/count-by-email")
    public ResponseEntity<List<ReportCountDTO>> countReportsByEmail() {
        return ResponseEntity.ok(reportService.getReportCountByEmail());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateStatus(
            @PathVariable Long id,
            @RequestBody ReportUpdateDTO requestBody) {
        return reportService.updateStatus(id, requestBody.getStatus());
    }
}
