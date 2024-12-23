package cm.ex.bug.service.interfaces;

import cm.ex.bug.entity.Report;
import cm.ex.bug.entity.Team;
import cm.ex.bug.request.CreateReportRequest;
import cm.ex.bug.response.BasicResponse;
import cm.ex.bug.response.ReportResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ReportService {

    public BasicResponse createReport(CreateReportRequest reportData, MultipartFile... files);

    public List<ReportResponse> listReportByUser();

    public List<ReportResponse> listReportByTeam(String teamId);

    public BasicResponse updateReportStatus(String status);

    public BasicResponse updateReportResolution(String resolution);

    public BasicResponse updateReportPriority(String priority);

    public BasicResponse updateReportSeverity(String severity);

    public BasicResponse addFileToReport(String reportId, MultipartFile file);

    public BasicResponse removeFileFromReport(String reportId, String fileId);

    public BasicResponse removeReport(String reportId);

}
