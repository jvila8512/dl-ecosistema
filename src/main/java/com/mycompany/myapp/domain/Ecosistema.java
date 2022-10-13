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
 * A Ecosistema.
 */
@Entity
@Table(name = "ecosistema")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Ecosistema implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 100)
    @Column(name = "nombre", length = 100, nullable = false)
    private String nombre;

    @OneToMany(mappedBy = "ecosistema")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "user", "ecosistema" }, allowSetters = true)
    private Set<UsuarioEcosistema> usurioecosistemas = new HashSet<>();

    @OneToMany(mappedBy = "ecosistema")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "ideas", "ecosistema" }, allowSetters = true)
    private Set<Reto> retos = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Ecosistema id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return this.nombre;
    }

    public Ecosistema nombre(String nombre) {
        this.setNombre(nombre);
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Set<UsuarioEcosistema> getUsurioecosistemas() {
        return this.usurioecosistemas;
    }

    public void setUsurioecosistemas(Set<UsuarioEcosistema> usuarioEcosistemas) {
        if (this.usurioecosistemas != null) {
            this.usurioecosistemas.forEach(i -> i.setEcosistema(null));
        }
        if (usuarioEcosistemas != null) {
            usuarioEcosistemas.forEach(i -> i.setEcosistema(this));
        }
        this.usurioecosistemas = usuarioEcosistemas;
    }

    public Ecosistema usurioecosistemas(Set<UsuarioEcosistema> usuarioEcosistemas) {
        this.setUsurioecosistemas(usuarioEcosistemas);
        return this;
    }

    public Ecosistema addUsurioecosistema(UsuarioEcosistema usuarioEcosistema) {
        this.usurioecosistemas.add(usuarioEcosistema);
        usuarioEcosistema.setEcosistema(this);
        return this;
    }

    public Ecosistema removeUsurioecosistema(UsuarioEcosistema usuarioEcosistema) {
        this.usurioecosistemas.remove(usuarioEcosistema);
        usuarioEcosistema.setEcosistema(null);
        return this;
    }

    public Set<Reto> getRetos() {
        return this.retos;
    }

    public void setRetos(Set<Reto> retos) {
        if (this.retos != null) {
            this.retos.forEach(i -> i.setEcosistema(null));
        }
        if (retos != null) {
            retos.forEach(i -> i.setEcosistema(this));
        }
        this.retos = retos;
    }

    public Ecosistema retos(Set<Reto> retos) {
        this.setRetos(retos);
        return this;
    }

    public Ecosistema addReto(Reto reto) {
        this.retos.add(reto);
        reto.setEcosistema(this);
        return this;
    }

    public Ecosistema removeReto(Reto reto) {
        this.retos.remove(reto);
        reto.setEcosistema(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Ecosistema)) {
            return false;
        }
        return id != null && id.equals(((Ecosistema) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Ecosistema{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            "}";
    }
}
