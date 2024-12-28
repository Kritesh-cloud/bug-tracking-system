package cm.ex.bug.configuration.data;

import cm.ex.bug.entity.Authority;
import cm.ex.bug.entity.DataHolder;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class DataHolderData {

    public List<DataHolder> dataHolderStatus = new ArrayList<>();
    public List<DataHolder> dataHolderResolution = new ArrayList<>();
    public List<DataHolder> dataHolderPriority = new ArrayList<>();
    public List<DataHolder> dataHolderSeverity = new ArrayList<>();
    public List<DataHolder> dataHolderRole = new ArrayList<>();
    public List<Authority> authorityList = new ArrayList<>();

    public DataHolderData(){

        authorityList.add(new Authority("admin",1,"admin"));
        authorityList.add(new Authority("moderator",1,"admin"));
        authorityList.add(new Authority("user",1,"moderator"));

        dataHolderStatus.add(new DataHolder("","test",true));

        dataHolderStatus.add(new DataHolder("new","status",true));
        dataHolderStatus.add(new DataHolder("assigned","status",true));
        dataHolderStatus.add(new DataHolder("in progress","status",true));
        dataHolderStatus.add(new DataHolder("reopened","status",true));
        dataHolderStatus.add(new DataHolder("verified","status",true));
        dataHolderStatus.add(new DataHolder("closed","status",true));
        dataHolderStatus.add(new DataHolder("reopened","status",true));
        dataHolderStatus.add(new DataHolder("deferred","status",true));
        dataHolderStatus.add(new DataHolder("rejected","status",true));

        dataHolderPriority.add(new DataHolder("critical","priority",true));
        dataHolderPriority.add(new DataHolder("high","priority",true));
        dataHolderPriority.add(new DataHolder("medium","priority",true));
        dataHolderPriority.add(new DataHolder("low","priority",true));

        dataHolderSeverity.add(new DataHolder("blocker","severity",true));
        dataHolderSeverity.add(new DataHolder("critical","severity",true));
        dataHolderSeverity.add(new DataHolder("major","severity",true));
        dataHolderSeverity.add(new DataHolder("minor","severity",true));
        dataHolderSeverity.add(new DataHolder("trivial","severity",true));

        dataHolderRole.add(new DataHolder("lead tester","role",true));
        dataHolderRole.add(new DataHolder("manual tester","role",true));
        dataHolderRole.add(new DataHolder("automation tester","role",true));
        dataHolderRole.add(new DataHolder("end user tester","role",true));

    }


}
