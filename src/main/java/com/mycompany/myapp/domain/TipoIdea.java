package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A TipoIdea.
 */
@Entity
@Table(name = "tipo_idea")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class TipoIdea implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "tipo_idea")
    private String tipoIdea;

    @JsonIgnoreProperties(value = { "tipoIdea", "reto" }, allowSetters = true)
    @OneToOne(mappedBy = "tipoIdea")
    private Idea idea;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public TipoIdea id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipoIdea() {
        return this.tipoIdea;
    }

    public TipoIdea tipoIdea(String tipoIdea) {
        this.setTipoIdea(tipoIdea);
        return this;
    }

    public void setTipoIdea(String tipoIdea) {
        this.tipoIdea = tipoIdea;
    }

    public Idea getIdea() {
        return this.idea;
    }

    public void setIdea(Idea idea) {
        if (this.idea != null) {
            this.idea.setTipoIdea(null);
        }
        if (idea != null) {
            idea.setTipoIdea(this);
        }
        this.idea = idea;
    }

    public TipoIdea idea(Idea idea) {
        this.setIdea(idea);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TipoIdea)) {
            return false;
        }
        return id != null && id.equals(((TipoIdea) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TipoIdea{" +
            "id=" + getId() +
            ", tipoIdea='" + getTipoIdea() + "'" +
            "}";
    }
}
