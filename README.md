# MVI + Clean Architecture + Corourtines
Sample app to demostrate using Coroutine along with MVI following Clean architecture guidelines. 

# Libraries used
- Jetpack Compose
- Corourtines
- Retrofit + Okhttp
- Hilt
- Room
- MockK
- Junit


# App Summary

App just has a single screen which fetches Domains via the https://gist.githubusercontent.com/ API. 
As per clean architecture the app is divided into three layers Data, Domain or Presentation.
Data is fetched with network first-cache fallback stratergy, which means data is first fetched from network and if any error is encountered then we return data from cache db instead.
Then we ping each of the domains 5 times via coroutines and calculate the average latency

