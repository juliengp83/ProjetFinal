# Résumé des tests

## Règle 1
## Stipule que les employés de l'administration doivent travailler au moins 36 heures au bureau par semaine.
- 1 employé ADMIN, horaire de 6 jours, projet < 900
-- J2 et J3 zéro heures
-- J4 à J7 9h/J Total = 36h
-- Résultat attendu true;

## Règle 1 fail
- 1 employé ADMIN, horaire de 4 jours, projet < 900
-- J4 à J7 5h/J Total = 20h
-- Résultat attendu False;

## Règle 2
## Stipule que les employés réguliers doivent travailler au moins 38 heures au bureau par semaine.
- 1 employé RÉGULIER, horaire de 4 jours, projet < 900
-- J4 à J7 44h
-- Résultat attendu true;

## Règle 2 Fail
- 1 employé RÉGULIER, horaire de 4 jours, projet < 900
-- J4 à J7 5h/J Total = 20h
-- Résultat attendu False;

## Règle 3
## Stipule qu'aucun employé n'a le droit de passer plus de 43 heures au bureau par semaine.
- 1 employé, horaire de 4 jours, projet < 900
-- J4 à J7 9h/J Total = 36h
-- Résultat attendu true;

## Règle 3 fail
- 1 employé, horaire de 4 jours, 
-- J4 à J7 5h/J Total = 66h
-- Résultat attendu False;

## Règle 4
## Stipule que les employés de l'administration ne peuvent faire plus que 10 heures de télétravail par semaine.
- 1 employé ADMIN, horaire de 4 jours, projet > 900 9h, reste < 900
-- J4 à J7 9h/J Total = 36h
-- Résultat attendu true;

## Règle 4 fail
- 1 employé ADMIN, horaire de 4 jours, projet > 900 20h
-- J4 à J7 5h/J 
-- Résultat attendu False;

## Règle 5
## Stipule que les employés réguliers peuvent faire autant de télétravail qu'ils le souhaitent
- 1 employé RÉGULIER, horaire de 4 jours, projet < 900 36h
-- J4 à J7 9h/J Total = 36h
-- Résultat attendu true;

## Règle 5 fail
-- impossible;

## Règle 6
## Stipule que les employés réguliers doivent faire un minimum de 6 heures au bureau tout les jours ouvrables.
- 1 employé RÉGULIER, horaire de 4 jours, projet > 900 9h, reste < 900
-- J4 à J7 9h/J Total = 36h
-- Résultat attendu true;

## Règle 6 fail
- 1 employé RÉGULIER, horaire de 4 jours, projet > 900 36h
-- J4 à J7 5h/J 
-- Résultat attendu False;

# Règle 7
## Stipule que les employés de l'administration doivent faire un minimum de 4 heures au bureau pour tout les jours ouvrables.
- 1 employé ADMIN, horaire de 4 jours, projet > 900 5h, reste < 900
-- J4 à J7 9h/J Total = 36h
-- Résultat attendu true;

## Règle 7 fail
- 1 employé ADMIN, horaire de 4 jours, projet > 900 36h
-- J4 à J7 5h/J 
-- Résultat attendu False;