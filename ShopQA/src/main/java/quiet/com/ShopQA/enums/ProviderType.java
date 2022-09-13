package quiet.com.ShopQA.enums;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Objects;

public enum ProviderType {
    LOCAL("local"), FACEBOOK("facebook"), GOOGLE("google");

    private final String value;

    ProviderType(String value) {
        this.value = value;
    }

    public static ProviderType valueOfLabel(String status) {
        if (Objects.nonNull(status)) {
            for (ProviderType e : values()) {
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