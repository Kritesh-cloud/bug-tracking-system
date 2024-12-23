package cm.ex.bug.service;

import cm.ex.bug.entity.DataHolder;
import cm.ex.bug.response.BasicResponse;
import cm.ex.bug.service.interfaces.DataHolderService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DataHolderServiceImpl implements DataHolderService {
    @Override
    public BasicResponse createDataHolder(DataHolder dataHolder) {
        return null;
    }

    @Override
    public DataHolder getDataHolderById(String dataHolderId) {
        return null;
    }

    @Override
    public List<DataHolder> listAllDataHolder() {
        return List.of();
    }

    @Override
    public List<DataHolder> listAllDataHolderByType() {
        return List.of();
    }

    @Override
    public BasicResponse removeDataHolder(String dataHolderId) {
        return null;
    }
}
