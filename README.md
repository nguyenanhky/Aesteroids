# Asteroid Radar app

## GIF
<img src="https://user-images.githubusercontent.com/43718257/121768273-147bb680-cb7b-11eb-8098-2746d5743069.gif" width=240 height=460>

## About

This app fetches live asteroid data from the internet using the NASA NeoWs API, along with the Picture of The Day using another NASA's API. It displays a list of all the asteroids  (marked hazardous or otherwise) approaching the earth in the next 7 days from the user's current date. On clicking on an asteroid item, the user is taken to the detail screen where s(he) can see the details about that asteroid.

* Main screen
  - This screen reuses views using RecyclerView to display the list of the next 7 days' live asteroid data.
  - It also displays NASA's Image of The Day at the top of the screen.

* Detail screen
  - This screen displays useful asteroid data related to the asteroid item the user clicks. The data is displayed for the labels:
    1. Code name
    2. Close approcah date
    3. Estimated maximum diameter (km)
    4. Absolute magnitude (au)
    5. Relative velocity (km/s)
    6. Distance from Earth

* Offline
  - The app implements offline caching to allow users to interact with online content offline.
  - A Room database is created to store and access user data over time.
  - The app downloads the next 7 days asteroids and saves them in the database once a day using workManager with requirements of internet connection and device plugged in. The app can display saved asteroids from the database even if internet connection is not available.
  - The app can save the downloaded asteroids in the database and then display them also from the database. The app filters asteroids from the past.

* The app works in offline mode too, as it fetches the data once every day. The app uses networking and image best practices to fetch data and images.

* The app works correctly in talk back mode, it provides descriptions for all the texts and images: Asteroid images in details screen and image of the day. It also provides description for the details screen help button
 
