(defproject MyBot "1.0"
  :description "Clojure language pack"
  :license {:name "Apache License, Version 2.0"
            :url "http://www.apache.org/licenses/LICENSE-2.0.html"
            :distribution :repo}
  :dependencies [[org.clojure/clojure "1.2.0"]
                 [org.clojure/clojure-contrib "1.2.0"]
                 [log4j "1.2.15" :exclusions  [ javax.mail/mail
                              javax.jms/jms
                              com.sun.jdmk/jmxtools
                              com.sun.jmx/jmxri]]
                 [org.slf4j/slf4j-api "1.6.1"]
                 [org.slf4j/slf4j-log4j12 "1.6.1"]
  ]
  :resources-path "conf"
  :uberjar-name "MyBot.jar"
  :aot [my_bot planetwars]
  :main my_bot
  :disable-implicit-clean true
)
