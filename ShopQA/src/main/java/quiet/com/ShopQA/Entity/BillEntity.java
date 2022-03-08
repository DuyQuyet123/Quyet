package quiet.com.ShopQA.Entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name = "bill")
public class BillEntity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "buy_date")
	private Date buyDate;

	@Column(name = "price_total")
	private Long priceTotal;

	@Column(name = "discount_percent")
	private Integer discountPercent;
	
	@Column(name = "status")
	private String status;
	
	@Column(name = "pay")
	private String pay;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "buyer_id")
	private UserEntity buyer;
	
	@OneToMany(mappedBy = "billEntity",cascade = CascadeType.ALL)
	@Column(name = "bill_Products")
	private List<BillProductEntity> billProducts;
	
	@ManyToOne
	@JoinColumn(name = "discount_id")
	private DiscountEntity discountEntity;
}
