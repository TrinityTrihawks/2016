
[![Build Status](https://travis-ci.org/TrinityTrihawks/2016.svg?branch=master)](https://travis-ci.org/TrinityTrihawks/2016)

﻿Uses ToastAPI

install with [Hotplate](https://github.com/Open-RIO/HotPlate)

﻿Some hint for those who are suffering having millions of errors about bad imports:

1. Install ruby somehow.
    Windows: google ruby, download and install. Then go to command line and type (not including the '>'):
        > gem install hotplate        
    Linux: type in terminal, not including the '$', password req.d:
        $ sudo apt-get install ruby
      (password and some lines)
        $ sudo gem install hotplate
      (possibly password, and some lines)
2. In the command line / terminal, cd to the 2016 folder, and (no $):
        $ toast init
      The name of the module asked: 2016 (I guess, but please ask someone else to make sure)
      What kind of Toast Module - java
      Team number you know what that is
3. Deploying Toast - not needed
4. Development Environment
    Eclipse: type in:
        gradlew eclipse (for Linux use "./gradlew eclipse")
    IntelliJ: type in:
        gradlew idea
5. Turn off&on your IDE. 
6. We are good to code now.

Referenced from:
https://github.com/Open-RIO/ToastAPI/wiki/Creating-A-Toast-Module----Start-to-Finish
