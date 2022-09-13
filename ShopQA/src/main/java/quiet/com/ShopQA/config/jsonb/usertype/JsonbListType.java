package quiet.com.ShopQA.config.jsonb.usertype;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.usertype.ParameterizedType;
import org.hibernate.usertype.UserType;
import quiet.com.ShopQA.Ultils.DataUtils;

import java.io.IOException;
import java.io.Serializable;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;
import java.util.Properties;

public class JsonbListType implements UserType, ParameterizedType {

    private static final ObjectMapper MAPPER = DataUtils.objectMapper();

    private Class<?> clazz;

    @Override
    public void setParameterValues(Properties params) {
        String className = params.getProperty("className");

        try {
            clazz = Class.forName(className);
        } catch (ClassNotFoundException cnfe) {
            throw new HibernateException("className not found", cnfe);
        }
    }

    @Override
    public int[] sqlTypes() {
        return new int[]{Types.JAVA_OBJECT};
    }

    @Override
    public Class returnedClass() {
        return clazz;
    }

    @Override
    public boolean equals(Object x, Object y) throws HibernateException {
        //equality operator (==) will cause extra update during delete
        //Please see TypeHelper.findDirty()
        //return x == y;
        return x.equals(y);
    }

    @Override
    public int hashCode(Object x) throws HibernateException {
        return x.hashCode();
    }

    @Override
    public Object nullSafeGet(ResultSet rs, String[] names,
                              SharedSessionContractImplementor sharedSessionContractImplementor, Object o)
            throws HibernateException, SQLException {
        String json = rs.getString(names[0]);

        if (json == null) {
            return null;
        }

        try {
            return MAPPER.readValue(json.getBytes(StandardCharsets.UTF_8), new TypeReference<List<Object>>() {
            });
        } catch (IOException e) {
            throw new RuntimeException(
                    "Failed to convert String to " + returnedClass() + e.getMessage(), e);
        }
    }

    @Override
    public void nullSafeSet(PreparedStatement st, Object value, int index,
                            SharedSessionContractImplementor session) throws HibernateException, SQLException {
        if (value == null) {
            st.setNull(index, Types.OTHER);
        } else {
            try {
                final StringWriter writer = new StringWriter();
                MAPPER.writeValue(writer, value);
                writer.flush();
                st.setObject(index, writer.toString(), Types.OTHER);
            } catch (IOException e) {
                throw new RuntimeException(
                        "Failed to convert " + returnedClass() + " to String " + e.getMessage(),
                        e);
            }
        }
    }

    @Override
    public Object deepCopy(Object value) throws HibernateException {
        if (value != null) {
            try {
                return MAPPER.readValue(MAPPER.writeValueAsString(value), new TypeReference<List<Object>>() {
                });
            } catch (IOException e) {
                throw new HibernateException("Failed to deep copy object", e);
            }
        }
        return null;
    }

    @Override
    public boolean isMutable() {
        return true;
    }

    @Override
    public Serializable disassemble(Object value) throws HibernateException {
        try {
            return MAPPER.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            throw new HibernateException("Failed to disassemble object", e);
        }
    }

    @Override
    public Object assemble(Serializable cached,
                           Object owner) throws HibernateException {
        return deepCopy(cached);
    }

    @Override
    public Object replace(Object original, Object target,
                          Object owner) throws HibernateException {
        return deepCopy(original);
    }
}

