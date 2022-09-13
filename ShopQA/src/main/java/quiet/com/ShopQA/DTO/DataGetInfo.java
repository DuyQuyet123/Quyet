package quiet.com.ShopQA.DTO;

import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@Data
@SuperBuilder
public class DataGetInfo<T> implements Serializable {

    private T data;

    public DataGetInfo() {
    }

    public DataGetInfo(T data) {
        this.data = data;
    }
}
