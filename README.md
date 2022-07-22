# LBC App Test
## Need
   Afficher une liste d'albums

## Libraries:
### DI:
    Hilt
### Network:
    OKhttp, Retrofit
### Images:
    Glide
### Persistance:
    Room

### UI:
    Paging library: there is a lot of results, so will encaps in Paging library

## Architecture:
MVVM
Need persistance, so the ViewModel will communicate with a Repository wich will call API or load from Room

## Notes From  Client:
Vous devez réaliser une application native Android affichant la liste des items suivant (titres
d'albums) : https://static.leboncoin.fr/img/shared/technical-test.json

Pour chaque item, nous voulons au moins voir le title et l'image. Attention, une contrainte
concernant les images vous forcera à ajouter un header avec la clé "User-Agent" pour chaque
requête visant à récupérer une image.
-> Glide addHeader ou HttpInterceptor
Les données contenues dans ce json doivent être téléchargées par l'app au Runtime, et non
mises manuellement dans les assets de l'app.
-> Retrofit
● Vous devez implémenter un système de persistance des données afin que les données
puissent être disponibles offline, même après redémarrage de l'application.
-> Room

● Votre code doit être versionné sur un dépôt Git librement consultable. Vous êtes libre de
créer plusieurs branches pour votre développement, mais nous ne relirons que la branche
configurée par défaut, veillez à ce que celle-ci soit à jour.
https://9c8cb97.online-server.cloud/gitlab/juszn/lbc
TODO: Rendre public
● Un document récapitulant les choix d'architecture, des patterns et des librairies
appliquées.


