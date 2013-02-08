/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.workflow.model.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author Lucas M Guedes
 * @since Feb 7, 2013
 * @version 0.1
 */
@Entity
@Table(name = "Realm")
public class Realm implements Serializable {

    private static final long serialVersionUID = 1456987456L;
    @Id
    @SequenceGenerator(name = "id_realm_sq", sequenceName = "sq_realm_usr", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_realm_sq")
    private Long id;
    @Column(name = "nome", columnDefinition = "varchar2(50)", length = 50)
    private String nome;
    //
    @ManyToMany(cascade = {CascadeType.REMOVE, CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinTable(name = "C_USR_REALM", uniqueConstraints =
    @UniqueConstraint(columnNames = {"ID_REALM", "ID_USUARIO"}),
    joinColumns = {
        @JoinColumn(name = "ID_REALM", referencedColumnName = "ID")},
    inverseJoinColumns = {
        @JoinColumn(name = "ID_USUARIO")})
    private List<Usuario> usuarios;

    public Long getId() {
        return id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Realm)) {
            return false;
        }
        Realm other = (Realm) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.workflow.entities.Realm[ id=" + id + " ]";
    }

    public String getNome() {
        return nome;
    }

    public Realm setNome(String nome) {
        this.nome = nome;
        return this;
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public Realm addUsuario(Usuario usuario) {
        if (usuario != null) {
            if (this.usuarios == null) {
                this.usuarios = new ArrayList<>();
            }
            if (!this.usuarios.contains(usuario)) {
                this.usuarios.add(usuario);
                usuario.addRealm(this);
            }
        }
        return this;
    }

    public Realm removeUsuario(Usuario usuario) {
        if (usuario != null && this.usuarios != null && this.usuarios.contains(usuario)) {
            this.usuarios.remove(usuario);
            usuario.removeRealm(this);
        }
        return this;
    }
}
