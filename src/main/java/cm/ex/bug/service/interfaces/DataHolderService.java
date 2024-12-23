package cm.ex.bug.service.interfaces;

import cm.ex.bug.entity.DataHolder;
import cm.ex.bug.entity.Team;
import cm.ex.bug.response.BasicResponse;

import java.util.List;

public interface DataHolderService {

    public BasicResponse createDataHolder(DataHolder dataHolder);

    public DataHolder getDataHolderById(String dataHolderId);

    public List<DataHolder> listAllDataHolder();

    public List<DataHolder> listAllDataHolderByType();

    public BasicResponse removeDataHolder(String dataHolderId);
}
