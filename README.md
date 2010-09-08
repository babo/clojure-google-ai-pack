[clojure](http://clojure.org) language pack for the latest contest hosted at [ai-content.com](http://ai-contest.com)

## License
Copyright 2010

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.

## Original code
Please note, this is a fork of Isaac Hodes original version from [github.com/ihodes/ai-contest-planet-wars-clj](http://github.com/ihodes/ai-contest-planet-wars-clj)

## How to use this bot:

All build functionality achieved through [leiningen](http://github.com/technomancy/leiningen). A copy of the current release (1.3.1) is included in the bin directory, both for Unix and Windows machines.

All you need to build the clojure bot is a simple "lein uberjar" command, the rest belongs to leiningen's magic. You don't even need to add the script to your path just call it with a relative path.
Unix:
    $ ./bin/lein uberjar
Windows:
    $ .\bin\lein.bat uberjar

The output is a single file called MyBot.jar which should run with "java -jar MyBot.jar". As clojure code starts up slower then needed you must adjust max_turn_time through the command line as a workaround. Please note, this is a benifit for this setup as max_turn_time is valid for each turn, this must be fixed by the judges somehow.
    $ java -jar tools/PlayGame.jar maps/map7.txt 3000 1000 log.txt "java -jar example_bots/RandomBot.jar" "java -jar MyBot.jar" | java -jar tools/ShowGame.jar

## Improvements

- leiningen configuration improved
- input processing speeded up
- test script added
- logging and resource inclusion added. You should add any resource files to the conf directory, that will be included in the final jar file. Logging is enabled but not used through log4j, feel free to use it during debugging but turn it off for deployment.
- to-string function fixed and improved, helps during debugging to print state

* * *

Please report any errors, bugs or improvements at [github.com:babo/clojure-google-ai-pack](http://github.com:babo/clojure-google-ai-pack])
