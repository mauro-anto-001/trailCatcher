package org.example.model;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity @Table(name="refresh_tokens")
public class RefreshToken {
    @Id @GeneratedValue private UUID id;

    @ManyToOne @JoinColumn(name="user_id", nullable=false)
    private User user;

    @Column(nullable=false, unique=true, length=512)
    private String token;

    @Column(nullable=false) private Instant expiresAt;
    @Column(nullable=false) private boolean revoked = false;
    private Instant createdAt = Instant.now();
    private String replacedByToken; // for rotation chaining

    // getters/setters

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Instant getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(Instant expiresAt) {
        this.expiresAt = expiresAt;
    }

    public boolean isRevoked() {
        return revoked;
    }

    public void setRevoked(boolean revoked) {
        this.revoked = revoked;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public String getReplacedByToken() {
        return replacedByToken;
    }

    public void setReplacedByToken(String replacedByToken) {
        this.replacedByToken = replacedByToken;
    }
}
