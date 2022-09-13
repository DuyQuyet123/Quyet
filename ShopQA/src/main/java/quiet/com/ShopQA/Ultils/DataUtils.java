package quiet.com.ShopQA.Ultils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import com.google.common.base.CaseFormat;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import quiet.com.ShopQA.DTO.Page;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class DataUtils {

    public static ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(MapperFeature.DEFAULT_VIEW_INCLUSION, true);
        mapper.registerModule(new ParameterNamesModule())
                .registerModule(new Jdk8Module())
                .registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return mapper;
    }

    public static String objToJson(Object obj) {
        try {
            return objectMapper().writeValueAsString(obj);
        } catch (IOException e) {
            return "";
        }
    }

    public static <T> T stringToObj(String input, Class<T> clazz) {
        try {
            return objectMapper().readValue(input, clazz);
        } catch (IOException e) {
            return null;
        }
    }

    public static <T> List<T> stringToLstObj(String input) {
        try {
            return objectMapper().readValue(input, new TypeReference<List<T>>() {
            });
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    public static String convertToCamelCase(String sort) {
        if (sort.equals("updated_at")) {
            sort = "updatedAt";
        }
        if (sort.equals("created_at")) {
            sort = "createdAt";
        }
        return sort;
    }

    public static Sort getSort(String sort) {
        Sort sort1;
        if (sort == null) {
            sort1 = Sort.by("updatedAt").descending();
        } else {
            String[] _sort = sort.split("\\s");
            if (_sort[1].equals("asc")) {
                sort1 = Sort.by(convertToCamelCase(_sort[0])).ascending();
            } else {
                sort1 = Sort.by(convertToCamelCase(_sort[0])).descending();
            }
        }
        return sort1;
    }

    public static Pageable customPageable(Integer page, Integer size, String sort) {
        if (page == null) {
            page = 1;
        }
        if (size == null) {
            size = 10;
        }
        Sort sort1;
        if (sort == null) {// đọc bên truyền vào không có truyền mặc định
            sort1 = Sort.by(Sort.Direction.DESC, "updated_at");
        } else {// đọc bên truyền vào, cắt ra qua khoảng trống
            String[] words = sort.split("\\s");
            if (words[1].equals("desc")) {
                sort1 = Sort.by(Sort.Direction.DESC, words[0]);
            } else {
                sort1 = Sort.by(Sort.Direction.ASC, words[0]);
            }
        }
        Pageable pageable = PageRequest.of(page - 1, size, sort1);
        return pageable;
    }

    public static String buildSearch(String source) {
        if (source == null) {
            source = "";
            return "%" + source + "%";
        } else {
            while (source.contains("  ")) {
                source = source.replaceAll(" {2}", " ");
            }

            return "%" + source.trim().replaceAll(" ", "%") + "%";
        }
    }
    public static Pageable getPageFromUrl(Map<String, String> paramUrl) {

        Page.page = Utils.parseIntegerWithDefault(paramUrl.get("page"), EnumUtils.DEFAULT_PAGE);
        Page.size = Utils.parseIntegerWithDefault(paramUrl.get("size"), EnumUtils.PAGE_SIZE);

        Page.sort = paramUrl.get("sort");

        if (Page.sort == null || Page.sort.length() <= 0) {
            Page.sort = EnumUtils.DEFAULT_PAGE_SORT;

        }
        String[] arrSort = Page.sort.split("\\s");

        Sort.Direction sortDirection = null;
        try {
            sortDirection = Sort.Direction.fromString(arrSort[1]);
        } catch (Exception e) {
            sortDirection = Sort.DEFAULT_DIRECTION;
        }

        Sort sort = Sort.by(sortDirection,
                CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, arrSort[0]));

        return PageRequest.of(Page.page - 1, Page.size, sort);
    }
}
