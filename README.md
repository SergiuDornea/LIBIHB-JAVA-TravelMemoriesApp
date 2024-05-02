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
• About Us (item) 
• Contact Us (item) 
• Share (item) 
• Settings (item) 
• Logout (item) 

Add/Edit Travel Memory:
The application should allow users to add and edit travel memories. When the user adds a new travel memory, they should be able to enter the following information: 
• Place name (input) 
• Place location (use Google Map with Google Places API to get readable location) 
• Date of travel (date picker) • Type of travel (dropdown picker) 
• Mood (slider) • Notes (input) 
•Gallery Photos (select photos from the gallery) [optional] 
• Take Photos (take photos from the camera) [optional] When the user edits a travel memory, they should be able to edit all of the above fields.

Details Screen:
The application should allow users to view the details of a travel memory. The details screen should display all of the fields mentioned in the "Add/Edit Travel Memory" section above. 
Additionally, the details screen should display the following information: • Weather at the location of the travel memory • Location of the travel memory on Google Maps (using Google Maps API)

Authentication with Firebase 🔥 and data persistence with ROOM.

Splash Screen:

![image](https://github.com/SergiuDornea/LIBIHB-JAVA-TravelMemoriesApp/assets/88648596/eecb94ea-4d4b-4e61-8585-98752770f937)


Log in Screen: 

![image](https://github.com/SergiuDornea/LIBIHB-JAVA-TravelMemoriesApp/assets/88648596/aa61076d-4e37-4cc8-98ef-a67d4a807169)


Log in fields validation: 
- valid email required
- valid password required

![image](https://github.com/SergiuDornea/LIBIHB-JAVA-TravelMemoriesApp/assets/88648596/3c900da1-5a75-40ff-a335-83e6aea0c351)

Register Screen:

![image](https://github.com/SergiuDornea/LIBIHB-JAVA-TravelMemoriesApp/assets/88648596/a3f13f8f-6663-49f1-84b7-468c75fa9f87)

Registration fields validation:
- name must be at least 2 characters long
- email must be valid
- phone must be valid
- password must be complex : min length of 6 characters: letters (uppercase and lowercase) and digits
  
![image](https://github.com/SergiuDornea/LIBIHB-JAVA-TravelMemoriesApp/assets/88648596/4ffb11b5-7562-4420-a7d9-39ad71460f77)
 
Once the user provides valid input it will be registered using FirebaseAuth and redirected to the HomeScreen: 
(Initial home screen at first launch/ while user has no memories saved)

![image](https://github.com/SergiuDornea/LIBIHB-JAVA-TravelMemoriesApp/assets/88648596/195e3c22-d46f-4d0b-b291-e20c3dbf2ca1)

From top to bottom: 
- Top toolbar containing the screen name and a hamburger button - at on click opens the navigation drawer

![image](https://github.com/SergiuDornea/LIBIHB-JAVA-TravelMemoriesApp/assets/88648596/eedc74e6-bb64-4602-a6d8-3669c9d6152b)

- "Discover new places" section (details bellow) - a scrollable horizontal list

[discover_new_places_slide.webm](https://github.com/SergiuDornea/LIBIHB-JAVA-TravelMemoriesApp/assets/88648596/2250c614-eb8f-49d0-a117-9f23e2b11108)

When an item is clicked the user is redirected to DiscoverScreen:

![image](https://github.com/SergiuDornea/LIBIHB-JAVA-TravelMemoriesApp/assets/88648596/b3165fd4-1734-4d30-9ba0-e20e92ef6578)

-"LIBIHB SOS system" button - redirects user the to SosScreen (details bellow)
-"Click here to explore new places" button - redirects the user to ExploreScreen (details bellow)
-"Don't have a memory yet? Start here" button - redirect the user to adding a new memory screens:

![image](https://github.com/SergiuDornea/LIBIHB-JAVA-TravelMemoriesApp/assets/88648596/12e25cfe-c0d8-4339-9d50-d17a5599f0bb)

![image](https://github.com/SergiuDornea/LIBIHB-JAVA-TravelMemoriesApp/assets/88648596/9c0de282-72c5-4b61-ac52-03d01a8ecddb)

![image](https://github.com/SergiuDornea/LIBIHB-JAVA-TravelMemoriesApp/assets/88648596/1b52b8ba-e9e2-49a0-8eb2-a942a3fbf23f)

If the user inserted all the necessary details pressing the "Save memory" button will redirect the user to the HomeScreen:

![image](https://github.com/SergiuDornea/LIBIHB-JAVA-TravelMemoriesApp/assets/88648596/d252d519-570a-4c08-9117-2a5b58e23961)


 
