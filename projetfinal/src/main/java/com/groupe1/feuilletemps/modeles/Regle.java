package com.groupe1.feuilletemps.modeles;

public class Regle {
    private int id_regle;
    private boolean est_respectee;
    private String message;

    public Regle() {
    }

    public Regle(int id_regle, boolean est_respectee, String message) {
        this.id_regle = id_regle;
        this.est_respectee = est_respectee;
        this.message = message;
    }

    public int getIdRegle() {
        return id_regle;
    }

    public void setIdRegle(int id_regle) {
        this.id_regle = id_regle;
    }

    public boolean estRespectee() {
        return est_respectee;
    }

    public void setRespectee(boolean est_respectee) {
        this.est_respectee = est_respectee;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Regle [est_respectee=" + est_respectee + ", id_regle=" + id_regle + ", message=" + message + "]";
    }
}