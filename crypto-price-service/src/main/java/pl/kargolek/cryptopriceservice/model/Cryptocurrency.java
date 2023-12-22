package pl.kargolek.cryptopriceservice.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name = "Cryptocurrency")
@Table(name = "cryptocurrency", uniqueConstraints = {
        @UniqueConstraint(name = "UniqueCoinMarketCapId", columnNames = "coin_market_id"),
        @UniqueConstraint(name = "UniqueName", columnNames = "name"),
        @UniqueConstraint(name = "UniqueSymbol", columnNames = "symbol"),
        @UniqueConstraint(name = "UniqueSmartContractAddress", columnNames = "token_address")
})
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Cryptocurrency {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "symbol", nullable = false, length = 20)
    private String symbol;

    @Column(name = "coin_market_id", nullable = false)
    private Long coinMarketId;

    @Column(name = "platform", length = 100)
    private String platform;

    @Column(name = "token_address")
    private String tokenAddress;

    @Column(name = "last_update", nullable = false)
    private LocalDateTime lastUpdate;

    @OneToOne(mappedBy = "cryptocurrency", cascade = CascadeType.ALL)
    @JsonManagedReference
    private Price price;

}