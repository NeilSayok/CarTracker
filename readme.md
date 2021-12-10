# Vehicle Tracker 

An android project to track the current location of a vehicle.

The Project is divided into 3 Parts.

- [Car Tracker App](https://github.com/NeilSayok/CarTracker/tree/CarApp)
- [User App](https://github.com/NeilSayok/CarTracker/tree/UserApp)
- [WebAPI](https://github.com/NeilSayok/CarTracker/tree/WebAPI)

## Car Tracker App:

This app tracks the current position of the device and send the location data to be stored in the Database.

Using this app user needs to Signup first if they donot have an account. Users are verified by using a OTP over mail. Once a user is verified, the user can press a button "Start Tracking" which will start a service to send data to the Server.

|Technologies Used|
| ----------- |
|Kotlin|
|Fragments|
|Navigation Architecture|
|RecyclerView|
|ConstraintLayout|
|Retrofit|
|Sevices|
|Notification|
|Coroutines|
||



### **ScreenShots:**


## Car Locator App:

An app where a user can see all their vehicles in a list. They will be able to see which car is currently online/sending data to the server and on taping an item it will open in Maps view where they will be able to see the current location on google maps.

|Technologies Used|
| ----------- |
|Kotlin|
|Fragments|
|Navigation Architecture|
|RecyclerView|
|ConstraintLayout & MotionLayout|
|Retrofit|
|Coroutines|
|Google Maps|
|Room DB|
|ViewModels|
||

### **ScreenShots:**
|||
| ----------- | ----------- |
|<img style="width:50%;height:auto;" src="https://i.imgur.com/HKc6abh.gif" /><br><p>Splash Screen</p>|<img style="width:50%;height:auto;" src="https://i.imgur.com/vh6ctsN.jpeg" /><br><p>Add User Screen</p>|
|<img style="width:50%;height:auto;" src="https://i.imgur.com/iQdhNkc.jpeg" /><br><p>Car List Screen</p>|<img style="width:50%;height:auto;" src="https://i.imgur.com/JqktKdg.jpeg" /><br><p>View Car on Map Screen</p>|

## WebAPI

The web API is hosted on heroku written in PHP. It handles the REST API requests along with mailing users OTP via SendInBlue SMTP.

|Technologies Used|
| ----------- |
|Heroku|
|PHP 7|
|SendInBlue SMTP(For Sending Mails)|
|REST API|
||





