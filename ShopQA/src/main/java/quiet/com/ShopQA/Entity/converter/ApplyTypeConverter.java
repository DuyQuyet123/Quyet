package quiet.com.ShopQA.Entity.converter;


import quiet.com.ShopQA.enums.ApplyType;

import javax.persistence.AttributeConverter;

public class ApplyTypeConverter implements AttributeConverter<ApplyType, String> {
    @Override
    public String convertToDatabaseColumn(ApplyType applyType) {
        return null != applyType ? applyType.getValue() : null;
    }

    @Override
    public ApplyType convertToEntityAttribute(String s) {
        return ApplyType.valueOfLabel(s);
    }

}
