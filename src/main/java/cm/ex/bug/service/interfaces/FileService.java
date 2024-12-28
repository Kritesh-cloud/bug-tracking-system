package cm.ex.bug.service.interfaces;

import cm.ex.bug.entity.File;
import cm.ex.bug.response.BasicResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface FileService {

    public File addFile(MultipartFile file) throws IOException;

    public File getFileById(String id);

    public List<File> listAllFiles();

    public List<File> listAllFilesByReport(String reportId);

    public List<File> listAllFilesByTeam(String teamId);

    public BasicResponse removeFile(String fileId);
}
