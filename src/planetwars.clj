(ns planetwars
  (:use [clojure.contrib.math :only (sqrt ceil)]
        [clojure.string :only (join split trim)]))

;; Fleet
(defstruct fleet
  :owner :num-ships :source-planet :destination-planet
  :total-trip-length :turns-remaining)

;; Planet
(defstruct planet
  :planet-id :x :y :owner :num-ships :growth-rate)

;; Game
(defstruct #^{:doc "This is the overall state of the game."}
  planet-wars-game :planets :fleets)

(defn add-ships
  "Returns 'planet with 'num-ships added to it."
  [planet num-ships]
  (assoc planet
    :num-ships (+ (planet :num-ships) num-ships)))

(defn remove-ships
  "Returns 'planet with 'num-ships removed from it."
  [planet num-ships]
  (assoc planet
    :num-ships (- (planet :num-ships) num-ships)))

(defn- sq [x] (* x x))

(defn distance
  "Returns the (int) Euclidean distance between 'source-planet-id and
   'dest-planet-id."
  [source-planet-id dest-planet-id]
  (let [dx (- (source-planet-id :x) (dest-planet-id :x))
        dy (- (source-planet-id :y) (dest-planet-id :y))]
    (int (ceil (sqrt (+ (sq dx)
                        (sq dy)))))))

(defn fleets
  "Returns a seq of all the fleet in 'game'"
  [game]
  (game :fleets))

(defn planets
  "Returns a seq of planets in 'game."
  [game]
  (game :planets))

(defn num-planets
  "Returns the number of planets in the game."
  [game]
  (count (planets game)))

(defn num-fleets
  "Returns the number of fleets en route."
  [game]
  (count (fleets game)))

(defn get-planet
  "Returns the planet with given id."
  [game planet-id]
  (first (filter #(= (:planet-id %) planet-id)
                 (planets game))))

(defn get-fleet ;; fleets don't have ids...
  "Returns the fleet with given id."
  [game fleet-id]
  (get (game :fleets) fleet-id))

(defn- my? [x] (== 1 (x :owner)))
(defn- neut? [x] (== 0 (x :owner)))
(defn- enemy? [x] (< 1 (x :owner)))

(defn my-planets
  "Returns a seq of all the planets owned by you."
  [game]
  (filter my? (planets game)))

(defn neutral-planets
  "Returns a seq of all the neutral planets in game."
  [game]
  (filter neut? (planets game)))

(defn enemy-planets
  "Returns a seq of all the enemy planets in the game."
  [game]
  (filter enemy? (planets game)))

(defn not-my-planets
  "Returns a seq of all the planets in game not owned by you."
  [game]
  (remove my? (planets game)))

(defn my-fleets
  "Returns a seq of all of your fleets."
  [game]
  (filter my? (fleets game)))

(defn enemy-fleets
  "Returns a seq of all of the enemy's fleets."
  [game]
  (filter enemy? (fleets game)))

(defn is-alive?
  "Returns true if player-id is alive."
  [game player-id]
  (some (partial = player-id)
        (map :owner (concat (planets game)
                            (fleets game)))))

;;;;; IO functions:
(defn to-string
  "Prints the state of the game as a string."
  [game]
  (let [pstr (for [p (planets game)]
               (join \space (list "P" (p :x) (p :y) (p :owner)
                    (p :num-ships) (p :growth-rate))))
        fstr (for [f (fleets game)]
               (join \space (list "F" (f :owner) (f :num-ships) (f :source-planet)
                    (f :destination-planet) (f :total-trip-length)
                    (f :turns-remaining))))]
        (str (join \newline (concat pstr fstr)) \newline)))

(defn issue-order
  "Issues an order, sending 'num-ships from source-planet to
   'dest-planet."
  [source-planet dest-planet num-ships]
  (prn source-planet dest-planet num-ships))

(defn finish-turn
  "Finishes turn."
  []
  (prn 'go))

; Functions for reading input state from input lines

(defn- I [x] (Integer. x))
(defn- D [x] (Double. x))

(defn- s-to-planet
      "Returns a planet struct with planet-id 'id from 's."
    [s id]
    (apply struct planet id
        (map #(apply (first %) (rest %))
             (map vector [D D I I I] s))))

(defn- s-to-fleet
  "Returns a fleet struct from 's."
  [s]
  (apply struct fleet
         (map I s)))

(defn- remove-comments [s] (when-first [x (split s #"#")] (trim x)))

(defn parse-game-state
    "Returns a new planet-wars-game struct from 's"
    [lines]
        (dissoc
            (reduce
                (fn [state line]
                    (let [[token & data] (split line #" ")]
                        (cond
                            (= "P" token)
                                (assoc state :pid (inc (:pid state)) :planets (cons (s-to-planet data (:pid state)) (:planets state)))
                            (= "F" token)
                                (assoc state :fleets (cons (s-to-fleet data) (:fleets state)))
                            :else
                                state)))
                (struct-map planet-wars-game :planets [] :fleets [] :pid 0)
                (filter seq (map remove-comments lines)))
            :pid))
