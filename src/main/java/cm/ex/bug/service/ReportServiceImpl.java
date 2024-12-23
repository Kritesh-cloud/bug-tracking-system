package cm.ex.bug.service;

import cm.ex.bug.request.CreateReportRequest;
import cm.ex.bug.response.BasicResponse;
import cm.ex.bug.response.ReportResponse;
import cm.ex.bug.service.interfaces.ReportService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class ReportServiceImpl implements ReportService {
    @Override
    public BasicResponse createReport(CreateReportRequest reportData, MultipartFile... files) {
        return null;
    }

    @Override
    public List<ReportResponse> listReportByUser() {
        return List.of();
    }

    @Override
    public List<ReportResponse> listReportByTeam(String teamId) {
        return List.of();
    }

    @Override
    public BasicResponse updateReportStatus(String status) {
        return null;
    }

    @Override
    public BasicResponse updateReportResolution(String resolution) {
        return null;
    }

    @Override
    public BasicResponse updateReportPriority(String priority) {
        return null;
    }

    @Override
    public BasicResponse updateReportSeverity(String severity) {
        return null;
    }

    @Override
    public BasicResponse addFileToReport(String reportId, MultipartFile file) {
        return null;
    }

    @Override
    public BasicResponse removeFileFromReport(String reportId, String fileId) {
        return null;
    }

    @Override
    public BasicResponse removeReport(String reportId) {
        return null;
    }
}
