package pl.kargolek.cryptopriceservice.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity(name = "Price")
@Table(name = "price", uniqueConstraints = @UniqueConstraint(
        name = "UniqueCryptocurrency",
        columnNames = "cryptocurrency_id")
)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Price {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToOne
    @JoinColumn(name = "cryptocurrency_id", nullable = false)
    @JsonBackReference
    private Cryptocurrency cryptocurrency;

    @Column(name = "price_current", precision = 24, scale = 12)
    private BigDecimal priceCurrent;

    @Column(name = "percent_change_1h", precision = 24, scale = 12)
    private BigDecimal percentChange1h;

    @Column(name = "percent_change_24h", precision = 24, scale = 12)
    private BigDecimal percentChange24h;

    @Column(name = "percent_change_7d", precision = 24, scale = 12)
    private BigDecimal percentChange7d;

    @Column(name = "percent_change_30d", precision = 24, scale = 12)
    private BigDecimal percentChange30d;

    @Column(name = "percent_change_60d", precision = 24, scale = 12)
    private BigDecimal percentChange60d;

    @Column(name = "percent_change_90d", precision = 24, scale = 12)
    private BigDecimal percentChange90d;

    @Column(name = "last_update", nullable = false)
    private LocalDateTime lastUpdate;

}
