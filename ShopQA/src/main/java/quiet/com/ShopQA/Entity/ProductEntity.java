package quiet.com.ShopQA.Entity;


import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "product")
@Entity
@Builder
public class ProductEntity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "name")
	private String name;

	@Column(name = "product_uuid", unique = true)
	@Builder.Default
	private String productUUID = UUID.randomUUID().toString();

	@Column(name = "qr_code", length = 4000)
	private String qrCode;
	
	@Column(name = "image")
	private String image;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "price")
	private Long price;
	
	@Column(name = "quantity")
	private Long quantity;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "category_id")
	private CategoryEntity categoryEntity;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "trademark_id")
	private TrademarkEntity trademarkEntity;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "size_id")
	private SizeEntity sizeEntity;
	
	
}
