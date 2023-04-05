package quiet.com.ShopQA.service.impl.factory;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import quiet.com.ShopQA.DTO.PageResponse;

@Component
public class ResponseFactory {
    public <T> PageResponse<T> mapPageResponse(Page<T> page) {
        PageResponse<T> responseObject = new PageResponse<>();
        responseObject.setContent(page.getContent());
        responseObject.setPage(page.getNumber() + 1);
        responseObject.setSize(page.getSize());
        responseObject.setSort(String.valueOf(page.getSort()));
        responseObject.setTotal(page.getTotalElements());
        responseObject.setTotalPage(page.getTotalPages());
        return responseObject;
    }
}
