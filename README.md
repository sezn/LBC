# LBC App Test
## Need
- Display an album list
- Put User-Agent in headers: OkHttpInterceptor or Glide.addHeader
- Implement a Storage method: Room
- Not in compose :(

## Libraries:
### DI:
    Hilt
### Network:
    OKhttp, Retrofit
### Images:
    Glide
### Persistance:
    Room, DataStore

### UI:
    Paging library: there is a lot of results, so will encaps in Paging library
    Navigation Component

### Tests:
    JUnit, Espresso

## Architecture:
Implement a MVVM architecture, we need persistance, so the ViewModel will communicate with a Repository wich will call API and or load Datas from Room. 
The API & Room implementation will be injected by Hilt, we provide implementations in modules.

The APIService and Room implementation share the same Album Object.
At the beginning, the Repository call API if need, will load from Db if Datas is here and Fresh. 
With the RemoteMediator implementation of PagingSource, this is this RemoteMediator who is responsible to Refresh or Not Datas when Override InitializeAction.

The update will be when data has more than an hour, the last update TimeStamp is stock in DataStoreManager wich is a wrapper for DataStore\<Preferences\> . 
If there is no network, it will take from Database if Populated, the connection is check via an extension fun of NetworkCapabilities.

# Notes Dev: 
Because there is a lot of items, I tested with simple RecyclerView.Adapter at beginning. 
But that was laggy, so I decided to test the PagingLibary, and it works fun, even we havent an API who handle Paging.

This was not asked to have a detail view, but I did it for show how we could do. And so, I changed to NavHostFragment for show we can hold navigation with a NavGraph.



## Notes From  Client:
Vous devez réaliser une application native Android affichant la liste des items suivant (titres
d'albums) : https://static.leboncoin.fr/img/shared/technical-test.json

Pour chaque item, nous voulons au moins voir le title et l'image. Attention, une contrainte
concernant les images vous forcera à ajouter un header avec la clé "User-Agent" pour chaque
requête visant à récupérer une image.

Les données contenues dans ce json doivent être téléchargées par l'app au Runtime, et non
mises manuellement dans les assets de l'app.

● Vous devez implémenter un système de persistance des données afin que les données
puissent être disponibles offline, même après redémarrage de l'application.

● Votre code doit être versionné sur un dépôt Git librement consultable. Vous êtes libre de
créer plusieurs branches pour votre développement, mais nous ne relirons que la branche
configurée par défaut, veillez à ce que celle-ci soit à jour.

https://9c8cb97.online-server.cloud/gitlab/juszn/lbc
TODO: Rendre public

● Un document récapitulant les choix d'architecture, des patterns et des librairies
appliquées.


