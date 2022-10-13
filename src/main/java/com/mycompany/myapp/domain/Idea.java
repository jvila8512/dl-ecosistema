package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Idea.
 */
@Entity
@Table(name = "idea")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Idea implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "numero_registro", nullable = false)
    private Integer numeroRegistro;

    @NotNull
    @Column(name = "entidad", nullable = false)
    private String entidad;

    @NotNull
    @Column(name = "titulo", nullable = false)
    private String titulo;

    @NotNull
    @Column(name = "descripcion", nullable = false)
    private String descripcion;

    @NotNull
    @Column(name = "autor", nullable = false)
    private String autor;

    @Column(name = "fecha_incripcion")
    private LocalDate fechaIncripcion;

    @JsonIgnoreProperties(value = { "idea" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private TipoIdea tipoIdea;

    @ManyToOne
    @JsonIgnoreProperties(value = { "ideas", "ecosistema" }, allowSetters = true)
    private Reto reto;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Idea id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumeroRegistro() {
        return this.numeroRegistro;
    }

    public Idea numeroRegistro(Integer numeroRegistro) {
        this.setNumeroRegistro(numeroRegistro);
        return this;
    }

    public void setNumeroRegistro(Integer numeroRegistro) {
        this.numeroRegistro = numeroRegistro;
    }

    public String getEntidad() {
        return this.entidad;
    }

    public Idea entidad(String entidad) {
        this.setEntidad(entidad);
        return this;
    }

    public void setEntidad(String entidad) {
        this.entidad = entidad;
    }

    public String getTitulo() {
        return this.titulo;
    }

    public Idea titulo(String titulo) {
        this.setTitulo(titulo);
        return this;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public Idea descripcion(String descripcion) {
        this.setDescripcion(descripcion);
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getAutor() {
        return this.autor;
    }

    public Idea autor(String autor) {
        this.setAutor(autor);
        return this;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public LocalDate getFechaIncripcion() {
        return this.fechaIncripcion;
    }

    public Idea fechaIncripcion(LocalDate fechaIncripcion) {
        this.setFechaIncripcion(fechaIncripcion);
        return this;
    }

    public void setFechaIncripcion(LocalDate fechaIncripcion) {
        this.fechaIncripcion = fechaIncripcion;
    }

    public TipoIdea getTipoIdea() {
        return this.tipoIdea;
    }

    public void setTipoIdea(TipoIdea tipoIdea) {
        this.tipoIdea = tipoIdea;
    }

    public Idea tipoIdea(TipoIdea tipoIdea) {
        this.setTipoIdea(tipoIdea);
        return this;
    }

    public Reto getReto() {
        return this.reto;
    }

    public void setReto(Reto reto) {
        this.reto = reto;
    }

    public Idea reto(Reto reto) {
        this.setReto(reto);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Idea)) {
            return false;
        }
        return id != null && id.equals(((Idea) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Idea{" +
            "id=" + getId() +
            ", numeroRegistro=" + getNumeroRegistro() +
            ", entidad='" + getEntidad() + "'" +
            ", titulo='" + getTitulo() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            ", autor='" + getAutor() + "'" +
            ", fechaIncripcion='" + getFechaIncripcion() + "'" +
            "}";
    }
}
