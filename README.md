# TP2 – Tests Unitaires en Java avec Mockito

## 🎯 Objectifs

Ce TP vise à :
- Comprendre l’importance des tests unitaires dans le cycle de développement.
- Apprendre à utiliser les **mocks** pour isoler les composants.
- Maîtriser **JUnit 5** et **Mockito** pour écrire des tests d’état et d’interaction.
- Distinguer les tests d’état et les tests d’interaction.
- Vérifier le comportement d’une classe testée via ses dépendances.

---

## ✅ Exercice 1 – Initiation avec Calculatrice

- **Classe testée** : `Calculatrice.additionner(a, b)`
- **Type de test** : Test d’état (vérification du résultat retourné)
- **Vérifications faites** :
  - Résultat retourné correct.
  - Appel de la méthode avec les bons arguments via `verify`.
  - Aucune autre interaction avec le mock via `verifyNoMoreInteractions`.

---

## ✅ Exercice 2 – Mock d’un service externe (UtilisateurApi)

- **Classe testée** : `UserService.creerUtilisateur()`
- **Classe mockée** : `UtilisateurApi`
- **Type de test** : Test d’interaction.
- **Vérification** :
  - Le mock `utilisateurApi` est bien appelé avec l’objet `Utilisateur`.

---

## ✅ Exercice 3 – Scénarios variés avec mocks

### 1. **Exception lors de la création** :
- Simulation d’un `ServiceException` avec `doThrow(...)`.
- Vérification que l’exception est bien levée et gérée.

### 2. **Erreur de validation** :
- On vérifie que si un utilisateur est invalide, l’API **n’est jamais appelée** avec `verify(..., never())`.

### 3. **Réponse avec identifiant (retour de l’API)** :
- Vérification que l’identifiant est correctement attribué à l’utilisateur après retour de l’API.

### 4. **Capture d’arguments** :
- Utilisation de `ArgumentCaptor<Utilisateur>` pour vérifier le contenu exact envoyé à l’API.

---

## ✅ Exercice 4 – Jeu de dés

### Objectif :
Tester en isolation la méthode `jouer()` de la classe `Jeu`, en simulant ses dépendances (`Banque`, `Joueur`, `De`).

---

### Question 1 : Objets à mocker

- Les objets `Joueur`, `Banque`, `De` sont **mockés**.
- Pourquoi ? Car on teste `Jeu`, donc on isole ses dépendances.

---

### Question 2 : Scénarios testés (classes d'équivalence)

1. Jeu fermé → lancer de dé interdit → lève exception.
2. Joueur insolvable → aucun appel aux dés.
3. Gain (somme des dés = 7) → crédit du joueur + débit de la banque.
4. Banque devient insolvable après un gain → le jeu se ferme.
5. Banque concrète intégrée → test d’intégration.

---

### Question 3 : Type de test pour chaque cas

- **Jeu fermé** : Test d’état (jeu doit être fermé).
- **Joueur insolvable** : Test d’interaction (dés non appelés).
- **Gagnant avec fermeture** : Mixte (résultat + interactions).
- **Banque concrète** : Test d’intégration avec une vraie classe métier.

---

## ✅ Conclusion

Les tests unitaires avec mocks permettent :
- d’isoler les comportements internes,
- de simuler des cas complexes (erreurs, retours spécifiques),
- et de vérifier finement les interactions entre modules.

Les scénarios ont été validés à travers des tests précis, documentés, et automatisés avec **JUnit 5 + Mockito**.

---

## 🛠️ Technologies utilisées

- Java 13
- JUnit 5
- Mockito 5.10.0
- IntelliJ IDEA
