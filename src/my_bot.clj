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

;; Main IO loop
(defn -main [& args]
    (try
        (loop [message '()]
            (let [line (read-line)]
                (cond
                    (nil? line) nil
                    (= line "go")
                        (do
                            (do-turn (parse-game-state message))
                            (finish-turn)
                            (recur '()))
                    :else (recur (conj message line)))))
        (catch Exception e
            (java.lang.System/exit 1)))
    (java.lang.System/exit 0))
