/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.workflow.model.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import org.hibernate.annotations.Index;
import org.hibernate.validator.constraints.Email;

/**
 *
 * @author Lucas M Guedes
 * @since Feb 7, 2013
 * @version 0.1
 */
@Entity
@Table(name = "Usuario")
@org.hibernate.annotations.Table(
        appliesTo = "Usuario", indexes = {
    @Index(name = "idx_login", columnNames = {"login"})
})
public class Usuario implements Serializable {

    private static final long serialVersionUID = 14568789165494L;
    @Id
    @SequenceGenerator(name = "id_usr_sq", sequenceName = "sq_id_usr", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_usr_sq")
    private Long id;
    //
    @Size(min = 0, max = 50, message = "Login deve conter entre 6 e 50 caracteres")
    @Column(name = "login", columnDefinition = "varchar2(50)", length = 50, nullable = false, unique = true)
    private String login;
    //
    @Email(message = "Email inválido. Por favor entre com um valor válido.")
    @Size(min = 3, max = 50, message = "Email deve conter entre 3 e 50 caracteres")
    @Column(name = "email", columnDefinition = "varchar2(50)", length = 50)
    private String email;
    //
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "senha", columnDefinition = "varchar2(50)", length = 50)
    private String senha;
    //
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "nome", columnDefinition = "varchar2(60)", length = 60)
    private String nome;
    //
    @ManyToMany(mappedBy = "usuarios", cascade = {CascadeType.REMOVE, CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    private List<Realm> realms;

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
        if (!(object instanceof Usuario)) {
            return false;
        }
        Usuario other = (Usuario) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.workflow.entities.Usuario[ id=" + id + " ]";
    }

    public String getLogin() {
        return login;
    }

    public Usuario setLogin(String login) {
        this.login = login;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public Usuario setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getSenha() {
        return senha;
    }

    public Usuario setSenha(String senha) {
        this.senha = senha;
        return this;
    }

    public List<Realm> getRealms() {
        return realms;
    }

    public Usuario addRealm(Realm realm) {
        if (realm != null) {
            if (this.realms == null) {
                this.realms = new ArrayList<>();
            }
            if (!this.realms.contains(realm)) {
                this.realms.add(realm);
                realm.addUsuario(this);
            }
        }
        return this;

    }

    public Usuario removeRealm(Realm realm) {
        if (this.realms != null
                && this.realms != null
                && this.realms.contains(realm)) {
            this.realms.remove(realm);
            realm.removeUsuario(this);
        }
        return this;
    }

    public String getNome() {
        return nome;
    }

    public Usuario setNome(String nome) {
        this.nome = nome;
        return this;
    }
    
    
}
