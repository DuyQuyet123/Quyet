package quiet.com.ShopQA.Entity.converter;


import quiet.com.ShopQA.enums.ObjType;

import javax.persistence.AttributeConverter;

public class ObjTypeConverter implements AttributeConverter<ObjType, String> {
    @Override
    public String convertToDatabaseColumn(ObjType objType) {
        return null != objType ? objType.getValue() : null;
    }

    @Override
    public ObjType convertToEntityAttribute(String s) {
        return ObjType.valueOfLabel(s);
    }
}
