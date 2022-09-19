package com.groupe1.feuilletemps.modeles;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Regle {
    private int id_regle;
    private boolean est_respectee;
    private String message;
}