package quiet.com.ShopQA.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
//import org.codehaus.jackson.annotate.JsonValue;

import java.util.Objects;

@Getter
public enum ObjType {
    SYSTEM("system"), CUSTOM("custom");

    private final String value;

    ObjType(String value) {
        this.value = value;
    }

    public static ObjType valueOfLabel(String status) {
        if (Objects.nonNull(status)) {
            for (ObjType e : values()) {
                if (e.value.equals(status)) {
                    return e;
                }
            }
        }
        return null;
    }

    @JsonValue
    public String getValue() {
        return this.value;
    }
}
