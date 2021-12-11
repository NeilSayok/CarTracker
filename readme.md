<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">

# Vehicle Tracker 

An android project to track the current location of a vehicle.

The Project is divided into 3 Parts.

- [Car Tracker App](https://github.com/NeilSayok/CarTracker/tree/CarApp)    <a href= "https://github.com/NeilSayok/CarTracker/releases/download/v0.0.1/Car.Tracker.apk"><button class="btn"><i class="fa fa-android"></i> Download Car Tracker App</button></a>
- [User App](https://github.com/NeilSayok/CarTracker/tree/UserApp) <a href="https://github.com/NeilSayok/CarTracker/releases/download/v0.0.1/User.App.apk"><button class="btn"><i class="fa fa-android"></i> Download User App</button></a>
- [WebAPI](https://github.com/NeilSayok/CarTracker/tree/WebAPI)






## Car Tracker App:  <a href= "https://github.com/NeilSayok/CarTracker/releases/download/v0.0.1/Car.Tracker.apk"><button class="btn"><i class="fa fa-android"></i> Download Car Tracker App</button></a>

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
|Lottie|
||



|**ScreenShots:**||
| ----------- | ----------- |
|<img style="width:50%;height:auto;" src="https://i.imgur.com/OtIDvef.gif" /><br><p>Splash Screen</p>|<img style="width:50%;height:auto;" src="https://i.imgur.com/gu9J0SC.gif" /><br><p>Permission Request Dialog</p>|
|<img style="width:50%;height:auto;" src="https://i.imgur.com/npqK4LJ.jpeg" /><br><p>Login Screen</p>|<img style="width:50%;height:auto;" src="https://i.imgur.com/prYQXTe.jpeg" /><br><p>Signup Screen</p>|
|<img style="width:50%;height:auto;" src="https://i.imgur.com/cpQPlLE.jpeg" /><br><p>OTP Not Filled Screen</p>|<img style="width:50%;height:auto;" src="https://i.imgur.com/gzC2PoH.jpeg" /><br><p>OTP Filled Screen</p>|
|<img style="width:50%;height:auto;" src="https://i.imgur.com/vEJTx2b.jpeg" /><br><p>Tracking Not Active Screen</p>|<img style="width:50%;height:auto;" src="https://i.imgur.com/b5uZM8H.jpeg" /><br><p>Car Tracking Active Screen</p>|
|<img style="width:50%;height:auto;" src="https://i.imgur.com/2dCmMU0.gif" /><br><p>Loading Screen Screen</p>|<img style="width:100%;height:auto;" src="https://i.imgur.com/IktzeDm.jpeg" /><br><p>Sticky Notification for Service</p>|


## Car Locator App: ![Custom badge](https://img.shields.io/endpoint?color=blue&label=Download%20Car%20Locator%20APK&logo=android&url=https%3A%2F%2Fgithub.com%2FNeilSayok%2FCarTracker%2Freleases%2Fdownload%2Fv0.0.1%2FCar.Tracker.apk)

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
|Lottie|
||


|**ScreenShots:**||
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


|Heroku Addons|
| ----------- |
|Clear DB|






