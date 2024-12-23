package com.example.teachhubproject.model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Devoir {
    private String typedevoir;
    private String description;
    private float ponderation;
    private String bareme;
    private Date dateLimite;
    private String statut;

    public String getFormattedDateLimite() {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        return sdf.format(dateLimite);
    }

    // Getters et setters
    public String getTypedevoir() { return typedevoir; }
    public void setTypedevoir(String typedevoir) { this.typedevoir = typedevoir; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public float getPonderation() { return ponderation; }
    public void setPonderation(float ponderation) { this.ponderation = ponderation; }

    public String getBareme() { return bareme; }
    public void setBareme(String bareme) { this.bareme = bareme; }

    public Date getDateLimite() { return dateLimite; }
    public void setDateLimite(Date dateLimite) { this.dateLimite = dateLimite; }

    public String getStatut() { return statut; }
    public void setStatut(String statut) { this.statut = statut; }
}
