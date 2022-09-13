package quiet.com.ShopQA.Entity;

import java.io.Serializable;

import javax.persistence.*;

import lombok.Data;
import quiet.com.ShopQA.Entity.converter.ProviderTypeConverter;
import quiet.com.ShopQA.enums.ProviderType;

@Entity
@Table(name = "user")
@Data
public class UserEntity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "name")
	private String name;

	@Column(name = "enabled")
	private Boolean enabled;

	@Column(name = "username")
	private String username;

	@Column(name = "password")
	private String password;

	@Column(name = "role")
	private String role;
	@Column(name = "gioitinh")
	private String gioiTinh;

	@Column(name = "email")
	private String email;

	@Column(name = "phone")
	private String phone;

	@Column(name = "address")
	private String address;

	@Convert(converter = ProviderTypeConverter.class)
	@Column(name = "provider_type")
	private ProviderType providerType;

	public UserEntity(Long id) {
		super();
		this.id = id;
	}

	public UserEntity() {
		super();
	}

}
