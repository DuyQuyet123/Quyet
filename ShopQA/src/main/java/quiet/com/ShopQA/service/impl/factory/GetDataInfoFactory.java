package quiet.com.ShopQA.service.impl.factory;

import org.springframework.stereotype.Component;
import quiet.com.ShopQA.DTO.DataGetInfo;

@Component
public class GetDataInfoFactory {
    public <T> DataGetInfo<T> mapDataResponse(T data) {
        DataGetInfo dataGetInfo = new DataGetInfo<>();
        dataGetInfo.setData(data);
        return dataGetInfo;
    }
}
