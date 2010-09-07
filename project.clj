(defproject MyBot "1.0"
  :description "Clojure language pack"
  :license {:name "Apache License, Version 2.0"
            :url "http://www.apache.org/licenses/LICENSE-2.0.html"
            :distribution :repo}
  :dependencies [[org.clojure/clojure "1.2.0"]
                 [org.clojure/clojure-contrib "1.2.0"]]
  :uberjar-name "MyBot.jar"
  :aot [bot planetwars]
  :main bot
  :disable-implicit-clean true
)
