package cm.ex.bug.service;

import cm.ex.bug.entity.*;
import cm.ex.bug.repository.*;
import cm.ex.bug.request.CreateReportRequest;
import cm.ex.bug.response.BasicResponse;
import cm.ex.bug.response.ReportResponse;
import cm.ex.bug.security.authentication.UserAuth;
import cm.ex.bug.service.interfaces.ReportService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.NotActiveException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    private ReportRepository reportRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private DataHolderRepository dataHolderRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public BasicResponse createReport(CreateReportRequest reportData, MultipartFile... files) throws IOException {
        //transferring all string data from dto to report
        validateReportData(reportData, true);
        Report report = modelMapper.map(reportData, Report.class);
        report.setId(null);

        //reporter and team for report
        report.setReporter(getLoggedInUser());
        report.setTeam(getTeamById(reportData.getTeamId()));

        //report status and all data holders
        report.setStatus(getStatusByName("new"));
        report.setSeverity(getSeverityByName(reportData.getSeverity()));
        report.setPriority(getPriorityByName(reportData.getPriority()));
        report.setDueDate(reportData.getDueDate() != null ? dateTimeConverter(reportData.getDueDate()) : null);

        Set<File> fileSet = new HashSet<>();
        for (MultipartFile file : files) {
            String reportFile = Base64.getEncoder().encodeToString(file.getBytes());
            File savedFile = new File(file.getOriginalFilename(), reportFile, file.getContentType());
            //saving file in repository and saving that file in has set at same time
            fileSet.add(fileRepository.save(savedFile));
        }

        report.setFileSet(fileSet);
        reportRepository.save(report);
        return BasicResponse.builder().status(true).result(true).code(200).message("Report created successfully").build();
    }

    @Override
    public ReportResponse getReportDetailById(String reportId) {
        return reportToResponse(getReportById(reportId));
    }


    @Override
    public List<ReportResponse> listReportByUser() {
        User user = getLoggedInUser();
        List<Report> reportList = reportRepository.findByReporter(user);
        return reportList.isEmpty() ? List.of() : reportListToResponse(reportList);
    }

    @Override
    public List<ReportResponse> listReportByTeam(String teamId) {
        Team team = getTeamById(teamId);
        List<Report> reportList = reportRepository.findByTeam(team);
        System.out.println("reportListToResponse(reportList)");
        for (ReportResponse rr : reportListToResponse(reportList)) {
            System.out.println("rr: " + rr.toString());
        }
        return reportList.isEmpty() ? List.of() : reportListToResponse(reportList);
    }

    //TODO work on progress
    @Override
    public BasicResponse updateReport(CreateReportRequest reportData, MultipartFile... files) throws IOException {
        validateReportData(reportData, false);

        //transferring all string data from dto to report
        Report report = modelMapper.map(reportData, Report.class);

        //reporter and team for report
        report.setReporter(getLoggedInUser());
        report.setTeam(getTeamById(reportData.getTeamId()));

        //report status and all data holders
        report.setStatus(getStatusByName("new"));
        report.setSeverity(getSeverityByName(reportData.getSeverity()));
        report.setPriority(getPriorityByName(reportData.getPriority()));
        report.setDueDate(dateTimeConverter(reportData.getDueDate()));

        for (String removeFile : reportData.getRemoveFileIds()) {
            Optional<File> removeThisFile = fileRepository.findById(UUID.fromString(removeFile));
            if (removeThisFile.isEmpty()) throw new NotActiveException("File not found");
        }

        Set<File> fileSet = new HashSet<>(report.getFileSet());
        for (MultipartFile file : files) {
            String reportFile = Base64.getEncoder().encodeToString(file.getBytes());
            File savedFile = new File(file.getOriginalFilename(), reportFile, file.getContentType());
            //saving file in repository and saving that file in has set at same time
            fileSet.add(fileRepository.save(savedFile));
        }
        // using previously saved has set for saving file save in report
        report.setFileSet(fileSet);
        reportRepository.save(report);
        return BasicResponse.builder().status(true).result(true).code(200).message("Report updated successfully").build();
    }

    @Override
    public BasicResponse updateReportStatus(String reportId, String status) {
        Report report = getReportById(reportId);
        DataHolder dataStatus = getStatusByName(status);
        report.setStatus(dataStatus);
        reportRepository.save(report);
        return BasicResponse.builder().status(true).result(true).code(200).message("Report status updated successfully").build();
    }

    @Override
    public BasicResponse updateReportResolution(String reportId, String resolution) {
        Report report = getReportById(reportId);
        DataHolder dataResolution = getStatusByName(resolution);
        report.setResolution(dataResolution);
        reportRepository.save(report);
        return BasicResponse.builder().status(true).result(true).code(200).message("Report resolution updated successfully").build();
    }

    @Override
    public BasicResponse updateReportPriority(String reportId, String priority) {
        Report report = getReportById(reportId);
        DataHolder dataPriority = getPriorityByName(priority);
        report.setPriority(dataPriority);
        reportRepository.save(report);
        return BasicResponse.builder().status(true).result(true).code(200).message("Report priority updated successfully").build();
    }

    @Override
    public BasicResponse updateReportSeverity(String reportId, String severity) {
        Report report = getReportById(reportId);
        DataHolder dataSeverity = getSeverityByName(severity);
        report.setSeverity(dataSeverity);
        reportRepository.save(report);
        return BasicResponse.builder().status(true).result(true).code(200).message("Report severity updated successfully").build();
    }

    @Override
    public BasicResponse addFileToReport(String reportId, MultipartFile file) throws IOException {
        Report report = getReportById(reportId);
        Set<File> fileSet = report.getFileSet();
        String reportFile = Base64.getEncoder().encodeToString(file.getBytes());
        File savedFile = new File(file.getOriginalFilename(), reportFile, file.getContentType());
        fileSet.add(fileRepository.save(savedFile));
        reportRepository.save(report);
        return BasicResponse.builder().status(true).result(true).code(200).message("New file add to report successfully").build();
    }

    @Override
    public BasicResponse removeFileFromReport(String reportId, String fileId) {
        Report report = getReportById(reportId);
        //get file by id
        Optional<File> file = fileRepository.findById(UUID.fromString(fileId));
        if (file.isEmpty()) throw new NoSuchElementException("File not found");
        //get set of file from report
        Set<File> fileSet = report.getFileSet();
        //and remove file from set files
        fileSet.remove(file.get());
        //now save that file set in report again and save
        report.setFileSet(fileSet);
        reportRepository.save(report);
        return BasicResponse.builder().status(true).result(false).code(200).message("File removed from report successfully").build();
    }

    @Override
    public BasicResponse removeReport(String reportId) {
        Report report = getReportById(reportId);
        reportRepository.delete(report);
        return BasicResponse.builder().status(true).result(false).code(200).message("Report removed successfully").build();
    }

    private void validateReportData(CreateReportRequest reportData, boolean create) throws IOException {
        if (!create && reportData.getId().toString().isBlank()) throw new IOException("Please input report id");
        if (reportData.getTitle().isBlank()) throw new IOException("Please input report title");
        if (reportData.getDescription().isBlank()) throw new IOException("Please input report description");
        if (reportData.getTeamId().isBlank()) throw new IOException("Please input report team id");
        if (reportData.getPriority().isBlank()) throw new IOException("Please input priority id");
        if (reportData.getSeverity().isBlank()) throw new IOException("Please input severity id");
//        if(reportData.getStepsToReproduce().isBlank()) throw new IOException("Please input report id");
//        if(reportData.getVersion().isBlank()) throw new IOException("Please input report id");
//        if(reportData.getEnvironment().isBlank()) throw new IOException("Please input report id");
//        if(reportData.getDueDate().isBlank()) throw new IOException("Please input report id");
    }

    private User userRemovePassword(User user) {
        return User.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .profileUrl(user.getProfileUrl())
                .authoritySet(user.getAuthoritySet())
                .build();
    }

    private Set<User> userListRemovePassword(Set<User> userSet) {
        return userSet.stream().map(this::userRemovePassword).collect(Collectors.toSet());
    }

    private User getLoggedInUser() {
        UserAuth userAuth = (UserAuth) SecurityContextHolder.getContext().getAuthentication();
        return userAuth.getUser();
    }

    public User getUserById(String userId) {
        Optional<User> user = userRepository.findById(UUID.fromString(userId));
        if (user.isEmpty()) throw new NoSuchElementException("User not found");
        return user.get();
    }

    public Team getTeamById(String teamId) {
        Optional<Team> team = teamRepository.findById(UUID.fromString(teamId));
        if (team.isEmpty()) throw new NoSuchElementException("Team not found");
        return team.get();
    }

    private String extractFileId(String fileUrl) {
        String uuidRegex = "[a-fA-F0-9]{8}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{12}";
        Pattern pattern = Pattern.compile(uuidRegex);
        Matcher matcher = pattern.matcher(fileUrl);
        if (matcher.find()) {
            return matcher.group();
        }
        return null;
    }

    private DataHolder getStatusByName(String name) {
        return getDataHolderByName(name, "status");
    }

    private DataHolder getResolutionByName(String name) {
        return getDataHolderByName(name, "resolution");
    }

    private DataHolder getSeverityByName(String name) {
        return getDataHolderByName(name, "severity");
    }

    private DataHolder getPriorityByName(String name) {
        return getDataHolderByName(name, "priority");
    }

    private DataHolder getDataHolderByName(String name, String type) {
        Optional<DataHolder> dataHolder = dataHolderRepository.findByNameAndType(name, type);
        if (dataHolder.isEmpty())
            throw new NoSuchElementException("Data holder: " + type + "-" + name + " of not found");
        return dataHolder.get();
    }

    private Report getReportById(String reportId) {
        Optional<Report> report = reportRepository.findById(UUID.fromString(reportId));
        if (report.isEmpty()) throw new NoSuchElementException("Report not found");
        return report.get();
    }

    private LocalDateTime dateTimeConverter(String strDateTime) {
        try {

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate localDate = LocalDate.parse(strDateTime, formatter);
            LocalDateTime localDateTime = localDate.atStartOfDay();
//            System.out.println("LocalDateTime: " + localDateTime);
            return localDateTime;
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Date time exception " + e);
        }
    }

    private ReportResponse reportToResponse(Report report) {
        ReportResponse response = modelMapper.map(report, ReportResponse.class);

        response.setReporterId(String.valueOf(report.getReporter().getId()));
        response.setTeamId(String.valueOf(report.getTeam().getId()));
        response.setStatus(report.getStatus().getName());
        response.setResolution(report.getResolution() != null ? report.getResolution().getName() : null);
        response.setPriority(report.getPriority().getName());
        response.setSeverity(report.getSeverity().getName());

        // Simplified mapping for assignees and file IDs
        response.setAssigneesIds(report.getAssignees().stream()
                .map(user -> String.valueOf(user.getId()))
                .toList());

        response.setFileIds(report.getFileSet().stream()
                .map(file -> String.valueOf(file.getId()))
                .toList());

        return response;
    }

    private List<ReportResponse> reportListToResponse(List<Report> reportList) {
        return reportList.stream()
                .map(report -> {
//                    System.out.println("report: "+report.toString());
                    return reportToResponse(report);
                })
                .toList();
    }
}
