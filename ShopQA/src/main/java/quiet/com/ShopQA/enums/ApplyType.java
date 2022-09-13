package quiet.com.ShopQA.enums;

import lombok.Getter;

import java.util.Objects;

@Getter
public enum ApplyType {

    ALL("all"), DEPARTMENT("department"), POSITION("position");

    private final String value;

    ApplyType(String value) {
        this.value = value;
    }

    public static ApplyType valueOfLabel(String status) {
        if (Objects.nonNull(status)) {
            for (ApplyType e : values()) {
                if (e.value.equals(status)) {
                    return e;
                }
            }
        }
        return null;
    }

}
