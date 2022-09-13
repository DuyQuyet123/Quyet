package quiet.com.ShopQA.Entity.converter;


import quiet.com.ShopQA.enums.ProviderType;

import javax.persistence.AttributeConverter;

public class ProviderTypeConverter implements AttributeConverter<ProviderType, String> {
    @Override
    public String convertToDatabaseColumn(ProviderType providerType) {
        return null != providerType ? providerType.getValue() : null;
    }

    @Override
    public ProviderType convertToEntityAttribute(String s) {
        return ProviderType.valueOfLabel(s);
    }

}
