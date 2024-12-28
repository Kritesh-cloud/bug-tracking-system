package cm.ex.bug.service;

import cm.ex.bug.entity.File;
import cm.ex.bug.repository.FileRepository;
import cm.ex.bug.repository.TeamRepository;
import cm.ex.bug.response.BasicResponse;
import cm.ex.bug.service.interfaces.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
public class FileServiceImpl implements FileService {

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private TeamRepository teamRepository;

    @Override
    public File addFile(MultipartFile file) throws IOException {
        if (Objects.requireNonNull(file.getOriginalFilename()).isEmpty())
            throw new IllegalArgumentException("Input cannot be blank.");

        String imgFile = Base64.getEncoder().encodeToString(file.getBytes());
        File image = new File(file.getOriginalFilename(), imgFile, file.getContentType());
        return fileRepository.save(image);
    }

    @Override
    public File getFileById(String id) {
        Optional<File> file = fileRepository.findById(UUID.fromString(id));
        if(file.isEmpty()) throw new NoSuchElementException("File not found");
        return file.get();
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
    public BasicResponse removeFile(String fileId) {
        File removeFile = getFileById(fileId);
        fileRepository.delete(removeFile);
        return BasicResponse.builder().status(true).result(false).code(200).message("File deleted successfully").build();
    }
}
