(ns my_bot
    (:gen-class :main true :prefix "-")
    (:use planetwars))

;; Helpers for your bot
(defn my-strongest-planet [pw]
    ((first (sort-by #(> (:num-ships %1) (:num-ships %2))
                   (my-planets pw))) :planet-id))

(defn weakest-enemy-planet [pw]
    ((first (sort-by #(< (:num-ships %1) (:num-ships %2))
                   (enemy-planets pw))) :planet-id))

(defn ihalf [x] (int (/ x 2)))

;; Your Robot
(defn do-turn [pw]
    (cond
    ;; Handle empty input
        (nil? pw) nil
    ;; Do nothing if a fleet is in flight
        (pos? (count (my-fleets pw))) nil
    ;; Else send half your ships from your strongest planets
    ;; to your enemy's weakest planet
        :else
            (let [  source (my-strongest-planet pw) 
                    dest (weakest-enemy-planet pw)]
                (when (and (pos? source) (pos? dest))
                    (issue-order source dest
                        (ihalf ((get-planet pw source) :num-ships)))))))

;; utility functions
(defn- go? [s] (= (apply str (take 2 s)) "go"))

(defn- take-turn
    [f pw]
    (f (parse-game-state pw)) ;; run your bot (f) on parsed pw
    (finish-turn)) ;; say go

;; Main IO loop
(defn -main [& args]
    (try
        (loop [line (read-line) pw ""]
            (cond
                (go? line)
                    (if-not (empty? pw)
                        (do
                            (take-turn do-turn pw)
                            (recur (read-line) ""))
                        (do
                            (finish-turn)
                            (recur (read-line) "")))
                :else
                    (recur (read-line)
                           (apply str (concat pw line "\n")))))
        (catch Exception e
            (java.lang.System/exit 1)))
    (java.lang.System/exit 0))
