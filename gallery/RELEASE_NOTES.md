# Dark Studio Pro v1.0.0

Galerie intelligente en mode sombre, **un seul fichier HTML**, 100 % hors ligne (aucun serveur, aucune dépendance).

## Installation
Télécharger `dark-studio-pro.html` ci-dessous et l'ouvrir dans Chrome, Firefox, Edge ou Safari.

## Fonctionnalités
- Galerie groupée par date, grille 3 colonnes (2 sur mobile), rail de filtres TOUT / DOC / IMG / PHOTO / TEXT / DESSIN avec compteurs.
- Import multiple (bouton ou glisser-déposer) avec classement automatique :
  - DOC et TEXT par extension / type MIME ;
  - SVG → DESSIN ;
  - images matricielles : analyse de pixels sur canvas 128 px (fond clair, traits, niveaux de gris, richesse de palette, aplats/dégradés/contours) → DESSIN, PHOTO ou IMG, avec explication affichée.
- Dossiers personnalisés (nom, couleur, description) : ajout, retrait, modification, suppression.
- Corbeille : restauration, suppression définitive, « Vider la corbeille » avec confirmation, annulation rapide.
- Aperçu agrandi avec correction manuelle de catégorie, gestion des dossiers et navigation clavier.
- Persistance dans le localStorage du navigateur, données de démonstration au premier lancement.

## Limites connues
- Stockage limité à ~5 Mo par le navigateur : les images sont réduites (1024 px) ; le fichier original n'est pas conservé.
- PDF / Office affichés en fiche descriptive (pas de rendu du contenu hors ligne).
- La détection dessin/photo est heuristique : corrigeable manuellement dans l'aperçu.
