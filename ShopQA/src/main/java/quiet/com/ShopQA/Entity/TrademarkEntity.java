package quiet.com.ShopQA.Entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import org.hibernate.annotations.Type;

@Data
@Entity
@Table(name = "trademark")
public class TrademarkEntity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "name")
	private String name;

//	@Type(type = "quiet.com.ShopQA.config.jsonb.usertype.JsonbObjType",
//			parameters = {@org.hibernate.annotations.Parameter(name = "className", value = "quiet.com.ShopQA.DTO.TranslationDto")})
//	private Object translation;
}
