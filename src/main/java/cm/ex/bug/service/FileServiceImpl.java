package cm.ex.bug.service;

import cm.ex.bug.entity.File;
import cm.ex.bug.response.BasicResponse;
import cm.ex.bug.service.interfaces.FileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class FileServiceImpl implements FileService {
    @Override
    public File addFile(MultipartFile file) throws IOException {
        return null;
    }

    @Override
    public Optional<File> getFileById(String id) {
        return Optional.empty();
    }

    @Override
    public List<File> listAllFiles() {
        return List.of();
    }

    @Override
    public List<File> listAllFilesByReport(String reportId) {
        return List.of();
    }

    @Override
    public List<File> listAllFilesByTeam(String teamId) {
        return List.of();
    }

    @Override
    public BasicResponse removeFile(String imageId) {
        return null;
    }
}
