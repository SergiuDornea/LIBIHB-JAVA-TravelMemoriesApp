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
![image](https://github.com/SergiuDornea/LIBIHB-JAVA-TravelMemoriesApp/assets/88648596/e8b3d93b-8f98-4ca8-8693-2912b4f654c8)

Log in Screen: 
![image](https://github.com/SergiuDornea/LIBIHB-JAVA-TravelMemoriesApp/assets/88648596/b0cdc3ff-9877-4307-99b2-6f6966fea035)

Log in fields validation: 
- valid email required
- valid password required
![image](https://github.com/SergiuDornea/LIBIHB-JAVA-TravelMemoriesApp/assets/88648596/ce730204-65a7-453e-8b5a-e4a6a16d35ff)


Register Screen:

https://github.com/SergiuDornea/LIBIHB-JAVA-TravelMemoriesApp.git

Registration fields validation:
- name must be at least 2 characters long
- email must be valid
- phone must be valid
- password must be complex : min length of 6 characters: letters (uppercase and lowercase) and digits
![image](https://github.com/SergiuDornea/LIBIHB-JAVA-TravelMemoriesApp/assets/88648596/690628a1-197e-4d84-86a7-0c727716bb60)

 
Once the user provides valid input it will be registered using FirebaseAuth and redirected to the HomeScreen: s
- Work in progress - the home screen holds a scrollable vertical list with all the saved memories
- on the HomeScreen's bottom right corner is a floating button that lets the users add a new memory by redirecting them into the following sequence of screens:
  ![image](https://github.com/SergiuDornea/LIBIHB-JAVA-TravelMemoriesApp/assets/88648596/b725e226-b5ec-4a57-a638-06a6f580c827)
  ![image](https://github.com/SergiuDornea/LIBIHB-JAVA-TravelMemoriesApp/assets/88648596/e46c52c5-005e-4119-9466-f097af8eeddc)
  ![image](https://github.com/SergiuDornea/LIBIHB-JAVA-TravelMemoriesApp/assets/88648596/01aab20c-c3e6-4f51-b510-fd562384edf7)

PREVIEW of already completed hike memory data:

[Screen_recording_20240404_002745.webm](https://github.com/SergiuDornea/LIBIHB-JAVA-TravelMemoriesApp/assets/88648596/ee43f217-cedb-4fc1-9497-01a87f707a4a)


Memory Details Screen:
https://github.com/SergiuDornea/LIBIHB-JAVA-TravelMemoriesApp.git

Home Screen:
https://github.com/SergiuDornea/LIBIHB-JAVA-TravelMemoriesApp.git  

