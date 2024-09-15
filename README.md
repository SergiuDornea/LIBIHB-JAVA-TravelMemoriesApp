I hope you believe that life is better in hiking boots 🥾 :)) so this is a native android 📱 travel application 🗺 built with JAVA lets you record all the mountain peaks that you have conquered 🗻

‼‼WORK IN PROGRESS - the list below may suffer changes 😁

"Life is better in hiking boots" is an Android application that allows users to create, view, and edit their travel memories. The app will use a SQLite database to store the user's travel memories. The main features of the application are as follows: Splash Screen:

The application should have a splash screen that displays the app icon and the name of the app.
Main Screen: The main screen should display a list of travel memories. Each item in the list should display the following fields:
• Location image 
• Place name 
• Place location 
• Date of travel

Navigation Drawer: The main screen should also have a navigation drawer with the following options: 
• Home (item) 
• Explore (item) 
• Favorites (item) 
• SOS (item) 
• About Us (item) 
• Settings (item) 
• Logout (item) 

Add/Edit Travel Memory:
The application should allow users to add and edit travel memories. When the user adds a new travel memory, they should be able to enter the following information: 
• Place name (input) 
• Memory description (input) 
• Place location (use Google Map with Google Places API to get readable location) 
• Date of travel (date picker) 
• Gallery Photos (select photos from the gallery)

Details Screen:
The application should allow users to view the details of a travel memory. The details screen should display all of the fields mentioned in the "Add/Edit Travel Memory" section above. 
Additionally, the details screen should display the following information: • Weather at the location of the travel memory • Location of the travel memory on Google Maps (using Google Maps API)

Authentication with Firebase 🔥 and data persistence with ROOM.

Use Case diagram: 

![usecasev4](https://github.com/user-attachments/assets/faa1aff9-31c2-44da-848e-d2d9b7e0abda)

Deployment diagram:

![deploymentv2](https://github.com/user-attachments/assets/227ea81a-c8d4-492a-8c19-f258d6a21421)

Splash Screen:

![image](https://github.com/SergiuDornea/LIBIHB-JAVA-TravelMemoriesApp/assets/88648596/eecb94ea-4d4b-4e61-8585-98752770f937)

Log in Screen: 
Log in fields validation: (first for big phone screens, second for small phone screens)
- valid email required
- valid password required

![image](https://github.com/SergiuDornea/LIBIHB-JAVA-TravelMemoriesApp/assets/88648596/aa61076d-4e37-4cc8-98ef-a67d4a807169) ![image](https://github.com/SergiuDornea/LIBIHB-JAVA-TravelMemoriesApp/assets/88648596/3c900da1-5a75-40ff-a335-83e6aea0c351) ![image](https://github.com/SergiuDornea/LIBIHB-JAVA-TravelMemoriesApp/assets/88648596/fa1eec16-43ab-4f4e-92a6-f05ae74bae37)

Register Screen:
Registration fields validation: (first for big phone screens, second for small phone screens)
- name must be at least 2 characters long
- email must be valid
- phone must be valid
- password must be complex : min length of 6 characters: letters (uppercase and lowercase) and digits
  
![image](https://github.com/SergiuDornea/LIBIHB-JAVA-TravelMemoriesApp/assets/88648596/a3f13f8f-6663-49f1-84b7-468c75fa9f87) ![image](https://github.com/SergiuDornea/LIBIHB-JAVA-TravelMemoriesApp/assets/88648596/4ffb11b5-7562-4420-a7d9-39ad71460f77)  ![image](https://github.com/SergiuDornea/LIBIHB-JAVA-TravelMemoriesApp/assets/88648596/57f0a222-eb25-4319-88b0-040c5ec35ea1)

 
Once the user provides valid input it will be registered using FirebaseAuth and redirected to the HomeScreen: 
(Initial home screen at first launch/ while user has no memories saved)

![image](https://github.com/SergiuDornea/LIBIHB-JAVA-TravelMemoriesApp/assets/88648596/195e3c22-d46f-4d0b-b291-e20c3dbf2ca1)

From top to bottom: 
- Top toolbar containing the screen name and a hamburger button - at on click opens the navigation drawer

![image](https://github.com/SergiuDornea/LIBIHB-JAVA-TravelMemoriesApp/assets/88648596/43b339c9-1f52-493a-b2e7-f1cb6e9f28dd)


- "Discover new places" section (details bellow) - a scrollable horizontal list

[discover_new_places_slide.webm](https://github.com/SergiuDornea/LIBIHB-JAVA-TravelMemoriesApp/assets/88648596/2250c614-eb8f-49d0-a117-9f23e2b11108)   ![image](https://github.com/SergiuDornea/LIBIHB-JAVA-TravelMemoriesApp/assets/88648596/cbbe54ff-c55e-46b5-8ce1-cfdffe5bb535)


When an item is clicked the user is redirected to DiscoverScreen:

![image](https://github.com/SergiuDornea/LIBIHB-JAVA-TravelMemoriesApp/assets/88648596/b3165fd4-1734-4d30-9ba0-e20e92ef6578)

-"LIBIHB SOS system" button - redirects user the to SosScreen (details bellow)
-"Click here to explore new places" button - redirects the user to ExploreScreen (details bellow)
-"Don't have a memory yet? Start here" button - redirect the user to adding a new memory screens:

![image](https://github.com/SergiuDornea/LIBIHB-JAVA-TravelMemoriesApp/assets/88648596/12e25cfe-c0d8-4339-9d50-d17a5599f0bb)     ![image](https://github.com/SergiuDornea/LIBIHB-JAVA-TravelMemoriesApp/assets/88648596/9c0de282-72c5-4b61-ac52-03d01a8ecddb)     ![image](https://github.com/SergiuDornea/LIBIHB-JAVA-TravelMemoriesApp/assets/88648596/1b52b8ba-e9e2-49a0-8eb2-a942a3fbf23f)

If the user inserted all the necessary details pressing the "Save memory" button will redirect the user to the HomeScreen,
instead of the 3 butoons and the backpack artwork it is now displayed a scrollable vertical list containing all the users memories:

![image](https://github.com/SergiuDornea/LIBIHB-JAVA-TravelMemoriesApp/assets/88648596/d252d519-570a-4c08-9117-2a5b58e23961)

On memory item click the user is redirected to the DetailsScreen, containig details about the memory,
from top to bottom : 
- toolbar with "back arrow", "add to favorites", "dropdown menu" buttons
- scrollable horizontal memory image list
- memory title
- memory location details and map
- current weather at memory location
- memory description

![image](https://github.com/SergiuDornea/LIBIHB-JAVA-TravelMemoriesApp/assets/88648596/26e3f7d7-c370-42ba-bbdc-191647a0b1a8)

  The dropdown menu enables the user to:
  -edit the memory
  -delete the memory
  
  ![image](https://github.com/SergiuDornea/LIBIHB-JAVA-TravelMemoriesApp/assets/88648596/b4bd268b-a800-4af6-a602-f30e78eb8391)

Edit screen and delete prompt:

![image](https://github.com/SergiuDornea/LIBIHB-JAVA-TravelMemoriesApp/assets/88648596/875f8645-80b9-4f06-bbac-9d1ef5d1a879)      ![image](https://github.com/SergiuDornea/LIBIHB-JAVA-TravelMemoriesApp/assets/88648596/3415cd7e-d8ac-4516-9fa1-e16f214e6c27)

ExploreScreen: - explore new places 

[explore_screen.webm](https://github.com/SergiuDornea/LIBIHB-JAVA-TravelMemoriesApp/assets/88648596/2b9db9a1-2608-4684-85b5-437a6dbe76d2)

Search feature for ExploreScreen: 

 [k2_rec.webm](https://github.com/SergiuDornea/LIBIHB-JAVA-TravelMemoriesApp/assets/88648596/d6039690-46f3-433a-ba20-f17999fe5b71)

Favorites screen:

![image](https://github.com/SergiuDornea/LIBIHB-JAVA-TravelMemoriesApp/assets/88648596/87ba98b0-635a-4858-b38d-6c34474e6c6b)

[favorite_screen.webm](https://github.com/SergiuDornea/LIBIHB-JAVA-TravelMemoriesApp/assets/88648596/612fe800-ba10-4899-b31f-9cf9a5d9df30)

SOS Screen: - the user needs to set an emergency contact so that the SOS button becomes active. Once the SOS button is active, if pressed it sends an emergency
message to the emergency contact - containig the phones current coordinates.

![image](https://github.com/SergiuDornea/LIBIHB-JAVA-TravelMemoriesApp/assets/88648596/252ceb05-342c-4b8f-847d-feaa2e041d4a)                 ![image](https://github.com/SergiuDornea/LIBIHB-JAVA-TravelMemoriesApp/assets/88648596/31ddc2ae-2edb-423b-804d-5b163b48caeb)   ![WhatsApp Image 2024-05-03 at 00 15 31_cd7564b9](https://github.com/SergiuDornea/LIBIHB-JAVA-TravelMemoriesApp/assets/88648596/84e5aed9-f1e9-4db2-b516-ed5e1066da10)


Aboutus Screen:

![image](https://github.com/SergiuDornea/LIBIHB-JAVA-TravelMemoriesApp/assets/88648596/09a9036f-d74f-498f-a689-4419bba13a8b)

Settings screen: 
-units of measurement for weather in DetailsScreen
-number of recomended places to discover is a slider that lets the user to decide how many recomandations to see on the home screen 

![image](https://github.com/SergiuDornea/LIBIHB-JAVA-TravelMemoriesApp/assets/88648596/0461a35c-24f7-4eac-a95d-539e2e95da7a)

