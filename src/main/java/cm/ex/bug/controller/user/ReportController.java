package cm.ex.bug.controller.user;

import cm.ex.bug.request.CreateReportRequest;
import cm.ex.bug.response.BasicResponse;
import cm.ex.bug.response.ReportResponse;
import cm.ex.bug.response.TeamResponse;
import cm.ex.bug.service.ReportServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RequestMapping("/user/report")
@RestController
public class ReportController {

    @Autowired
    private ReportServiceImpl reportService;

    @PostMapping("/test")
    public ResponseEntity<BasicResponse> test() {
        BasicResponse response = BasicResponse.builder().status(true).result(true).code(200).message("Authentication Controller Test").build();
        return ResponseEntity.status(HttpStatusCode.valueOf(response.getCode())).body(response);
    }

    @GetMapping("/getTest")
    public ResponseEntity<BasicResponse> getTest() {
        BasicResponse response = BasicResponse.builder().status(true).result(true).code(200).message("Authentication Controller Test").build();
        return ResponseEntity.status(HttpStatusCode.valueOf(response.getCode())).body(response);
    }


    @PostMapping("/create-report")
    public ResponseEntity<BasicResponse> createReport(@RequestBody CreateReportRequest reportRequest, MultipartFile... files) throws IOException {
        BasicResponse response = reportService.createReport(reportRequest, files);
        return ResponseEntity.status(HttpStatusCode.valueOf(response.getCode())).body(response);
    }

    @GetMapping("/list-report-by-team/{teamId}")
    public ResponseEntity<List<ReportResponse>> listReportByTeam(@PathVariable String teamId) {
        return ResponseEntity.status(HttpStatusCode.valueOf(200)).body(reportService.listReportByTeam(teamId));
    }

    @GetMapping("/get-report-by-id/{reportId}")
    public ResponseEntity<ReportResponse> getReportById(@PathVariable String reportId) {
        return ResponseEntity.status(HttpStatusCode.valueOf(200)).body(reportService.getReportDetailById(reportId));
    }

    @PostMapping("/update-report")
    public ResponseEntity<BasicResponse> updateReport(@RequestBody CreateReportRequest reportRequest, MultipartFile... files) throws IOException {
        BasicResponse response = reportService.updateReport(reportRequest, files);
        return ResponseEntity.status(HttpStatusCode.valueOf(response.getCode())).body(response);
    }

    @PostMapping("/update-report-status")
    public ResponseEntity<BasicResponse> updateReportStatus(@RequestParam String reportId, @RequestParam String status) {
        BasicResponse response = reportService.updateReportStatus(reportId, status);
        return ResponseEntity.status(HttpStatusCode.valueOf(response.getCode())).body(response);
    }

    @PostMapping("/update-report-priority")
    public ResponseEntity<BasicResponse> updateReportPriority(@RequestParam String reportId, @RequestParam String priority) {
        BasicResponse response = reportService.updateReportPriority(reportId, priority);
        return ResponseEntity.status(HttpStatusCode.valueOf(response.getCode())).body(response);
    }

    @PostMapping("/update-report-severity")
    public ResponseEntity<BasicResponse> updateReportSeverity(@RequestParam String reportId, @RequestParam String severity) {
        BasicResponse response = reportService.updateReportSeverity(reportId, severity);
        return ResponseEntity.status(HttpStatusCode.valueOf(response.getCode())).body(response);
    }

    @PostMapping("/update-report-resolution")
    public ResponseEntity<BasicResponse> updateReportResolution(@RequestParam String reportId, @RequestParam String resolution) {
        BasicResponse response = reportService.updateReportResolution(reportId, resolution);
        return ResponseEntity.status(HttpStatusCode.valueOf(response.getCode())).body(response);
    }

    @PostMapping("/add-file-to-report")
    public ResponseEntity<BasicResponse> addFileToReport(@RequestPart String reportId, @RequestPart MultipartFile file) throws IOException {
        BasicResponse response = reportService.addFileToReport(reportId, file);
        return ResponseEntity.status(HttpStatusCode.valueOf(response.getCode())).body(response);
    }

    @PostMapping("/remove-file-from-report")
    public ResponseEntity<BasicResponse> removeFileFromReport(@RequestParam String reportId, @RequestParam String fileId) {
        BasicResponse response = reportService.removeFileFromReport(reportId, fileId);
        return ResponseEntity.status(HttpStatusCode.valueOf(response.getCode())).body(response);
    }

    @PostMapping("/remove-report/{reportId}")
    public ResponseEntity<BasicResponse> removeReport(String reportId) {
        BasicResponse response = reportService.removeReport(reportId);
        return ResponseEntity.status(HttpStatusCode.valueOf(response.getCode())).body(response);
    }

}

/*

create report
list report
get report
update report
update report status
update report priority
update report severty
update report resolution
add file to report
remove report
remove report


*/