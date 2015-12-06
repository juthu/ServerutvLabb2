package jayray.net.common.viewModel;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Created by luben on 2015-12-06.
 */
@XmlRootElement
public class ProfileList {
    private List<profile> list;

    public List<profile> getList() {
        return list;
    }

    public void setList(List<profile> list) {
        this.list = list;
    }

    public ProfileList() {

    }
}
