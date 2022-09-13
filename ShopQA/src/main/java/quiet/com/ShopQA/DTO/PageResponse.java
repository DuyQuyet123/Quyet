package quiet.com.ShopQA.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.List;


@Data
@SuperBuilder
public class PageResponse<T> implements Serializable {

    private List<T> content;

    private Integer page;

    private Integer size;

    private String sort;

    private long total;

    @JsonProperty("total_pages")
    private Integer totalPage;

    public PageResponse() {
    }

    public PageResponse(List<T> content, Integer page, Integer size, String sort, long total, Integer totalPage) {
        this.content = content;
        this.page = page;
        this.size = size;
        this.sort = sort;
        this.total = total;
        this.totalPage = totalPage;
    }
}
