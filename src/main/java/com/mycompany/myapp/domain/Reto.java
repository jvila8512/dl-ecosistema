package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Reto.
 */
@Entity
@Table(name = "reto")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Reto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "reto", nullable = false)
    private String reto;

    @NotNull
    @Column(name = "descripcion", nullable = false)
    private String descripcion;

    @OneToMany(mappedBy = "reto")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "tipoIdea", "reto" }, allowSetters = true)
    private Set<Idea> ideas = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "usurioecosistemas", "retos" }, allowSetters = true)
    private Ecosistema ecosistema;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Reto id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReto() {
        return this.reto;
    }

    public Reto reto(String reto) {
        this.setReto(reto);
        return this;
    }

    public void setReto(String reto) {
        this.reto = reto;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public Reto descripcion(String descripcion) {
        this.setDescripcion(descripcion);
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Set<Idea> getIdeas() {
        return this.ideas;
    }

    public void setIdeas(Set<Idea> ideas) {
        if (this.ideas != null) {
            this.ideas.forEach(i -> i.setReto(null));
        }
        if (ideas != null) {
            ideas.forEach(i -> i.setReto(this));
        }
        this.ideas = ideas;
    }

    public Reto ideas(Set<Idea> ideas) {
        this.setIdeas(ideas);
        return this;
    }

    public Reto addIdea(Idea idea) {
        this.ideas.add(idea);
        idea.setReto(this);
        return this;
    }

    public Reto removeIdea(Idea idea) {
        this.ideas.remove(idea);
        idea.setReto(null);
        return this;
    }

    public Ecosistema getEcosistema() {
        return this.ecosistema;
    }

    public void setEcosistema(Ecosistema ecosistema) {
        this.ecosistema = ecosistema;
    }

    public Reto ecosistema(Ecosistema ecosistema) {
        this.setEcosistema(ecosistema);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Reto)) {
            return false;
        }
        return id != null && id.equals(((Reto) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Reto{" +
            "id=" + getId() +
            ", reto='" + getReto() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            "}";
    }
}
