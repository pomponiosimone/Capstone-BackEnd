package pomponiosimone.Capstone_BackEnd.entities;
import jakarta.persistence.*;
import lombok.*;
import pomponiosimone.Capstone_BackEnd.enums.TipoPagamento;
import pomponiosimone.Capstone_BackEnd.enums.TipoSpedizione;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "ordini")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Ordine {

    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID id;
    @ManyToOne
    @JoinColumn(name = "id_cliente", nullable = false)
    private Cliente cliente;
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataOrdine;
    private Double totaleOrdine;
    private Double speseSpedizione;
    private String statoOrdine;
    private TipoPagamento metodoPagamento;
    private TipoSpedizione tipoSpedizione;
    private String indirizzoSpedizione;@OneToMany(mappedBy = "ordine")
    private List<Scarpa> articoli;

    @PrePersist
    protected void onCreate() {
        this.dataOrdine = new Date();
    }
//Costruttore
    public Ordine(List<Scarpa> articoli, Cliente cliente, Date dataOrdine, String indirizzoSpedizione, TipoPagamento metodoPagamento, Double speseSpedizione, String statoOrdine, TipoSpedizione tipoSpedizione, Double totaleOrdine) {
        this.articoli = articoli;
        this.cliente = cliente;
        this.dataOrdine = dataOrdine;
        this.indirizzoSpedizione = indirizzoSpedizione;
        this.metodoPagamento = metodoPagamento;
        this.speseSpedizione = speseSpedizione;
        this.statoOrdine = statoOrdine;
        this.tipoSpedizione = tipoSpedizione;
        this.totaleOrdine = totaleOrdine;
    }
}