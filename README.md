# SmsRetriever API Demo

Gone are the days when SMS permission was required to read the OTP (one time password) from the mobile.

Android sdk comes with [SmsRetriever API] which provide two ways to read the SMS/OTP from mobile device without getting the permission from the user.

1. Automatic SMS retriver API-
- When it works:
    - Be no longer than 140 bytes
    - one time password (OTP) that you will send to your server to authenticate the user
    - Include an 11-character hash string that identifies your app [see Computing your app's hash string]
    ```Example :  23497 is your otp GTk321VTiNz```

- When it doesn't work:
    - If the message is longer than 140 bytes.
    - If the message does not contain the 11-character hash key at the end of the message
>In the above example you can put OTP anywhere and accordingly you need to update the parsing logic while extracting it from the SMS as this api will give the whole SMS.
```GTk321VTiNz``` is the hash key that you have generated from terminal and hand it over the backend people to append it with every message you will receive.

2. One tap Sms Retriever API-
- When it works:
     - The message contains a 4-10 character alphanumeric string with at least one number.
     - The message was sent by a phone number that's not in the user's contacts.
     - If you specified the sender's phone number, the message was sent by that number.
 ```Example :  23497 is your otp```

- When it doesn't work:
     - If message does not contain a 4-10 alphanumeric character string with at least one number
     - If somehow recipient has saved the sender number or it is in recipient's contacts list then also this api won't work.
     - If the mentioned number inside the code does not match with the sender's number then it won't work
> If the sender number is fixed, you can mention the number while generating the client with this api inside the code, then it will listen the OTP from that number only else it won't work.
But I would highly recommend not to put any number if you are not sure. You can just put null.
```
 client.startSmsUserConsent(null)
 It will listen the OTP from every number, if other criteria is fulfilled

 client.startSmsUserConsent("757483929")
 It will listen the otp from mentioned number only
 ```


#### Time limit for both the apis to listen to the OTP/SMS is 5 minutes only.

## When to choose which API
I have heard many developer discussions when to choose API
the answer is very simple
```
# If you have control over OTP sending capabilities (i.e. you can control the SMS format) then use Automatic SMS retriver apis, as you don't need to worry about anything else.

# If you don't have control over sending OTP's (SmS Format) then go ahead with SMS consent one/One tap sms retriver apis.
```


## Things you must do before moving to production
Remove AppSignatureHelper class from your project before going to production.
this helps you generating one time hash key which you need to append in every message If you are using Automatic SMS Retriver API

[Here you can watch the project in Action]

## Prerequisites
The SMS Retriever API is available only on Android devices with Play services version 10.2 and newer.
Honestly, I have tried with Google play services version 16.x but it wasn't working for me. Finally I have upgraded my emulator version play service to 19.x then it was working.


Reference :- https://developers.google.com/identity/sms-retriever/overview

[SmsRetriever API]: https://developers.google.com/identity/sms-retriever/overview
[see Computing your app's hash string]:https://developers.google.com/identity/sms-retriever/verify#computing_your_apps_hash_string
[Here you can watch the project in Action]:shorturl.at/beIPV

