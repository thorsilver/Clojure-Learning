(ns chapter4.core
  (:gen-class))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))

(defn titleize
  [topic]
  (str topic " for the Brave and True"))

(def human-consumption [8.1 7.3 6.6 5.0])
(def critter-consumption [0.0 0.2 0.3 1.1])

(defn unify-diet-data
  [human critter]
  {:human human :critter critter})

(def sum #(reduce + %))
(def avg #(/ (sum %) (count %)))
(defn stats
  [numbers]
  (map #(% numbers) [sum count avg]))

;; Use map keyword as function to retrieve values
(def identities
  [{:alias "Batman" :real "Bruce Wayne"}
   {:alias "Spider-Man" :real "Peter Parker"}
   {:alias "Santa" :real "Your mom"}
   {:alias "Easter Bunny" :real "Your dad"}])

;; Use reduce to transform a map's values
(reduce (fn [new-map [key val]]
          (assoc new-map key (inc val)))
        {}
        {:max 30 :min 10})

;; Use reduce to filter out keys from map based on values
(reduce (fn [new-map [key val]]
          (if (> val 4)
            (assoc new-map key val) new-map))
        {}
        {:human 4.1 :critter 3.9})

(def food-journal
  [{:month 1 :day 1 :human 5.3 :critter 2.3}
   {:month 1 :day 2 :human 5.1 :critter 2.0}
   {:month 2 :day 1 :human 4.9 :critter 2.1}
   {:month 2 :day 2 :human 5.0 :critter 2.5}
   {:month 3 :day 1 :human 4.2 :critter 3.3}
   {:month 3 :day 2 :human 4.0 :critter 3.8}
   {:month 4 :day 1 :human 3.7 :critter 3.9}
   {:month 4 :day 2 :human 3.7 :critter 3.6}])

(def vampire-database
  {0 {:makes-blood-puns? false, :has-pulse? true  :name "McFishwich"}
   1 {:makes-blood-puns? false, :has-pulse? true  :name "McMackson"}
   2 {:makes-blood-puns? true,  :has-pulse? false :name "Damon Salvatore"}
   3 {:makes-blood-puns? true,  :has-pulse? true  :name "Mickey Mouse"}})

(defn vampire-related-details
  [social-security-number]
  (Thread/sleep 100)
  (get vampire-database social-security-number))

(defn vampire?
  [record]
  (and (:makes-blood-puns? record)
       (not (:has-pulse? record))
       record))

(defn identify-vampire
  [social-security-numbers]
  (first (filter vampire? (map vampire-related-details
                               social-security-numbers))))

(time (def mapped-details (map vampire-related-details (range 0 1000000))))

(concat (take 8 (repeat "na")) ["Batman!"])

(take 3 (repeatedly (fn [] (rand-int 10))))

(defn even-numbers
  ([] (even-numbers 0))
  ([n] (cons n (lazy-seq (even-numbers (+ n 2))))))

;; Use into to add elements to a map
(into {:favourite-emotion "gloomy"} [[:sunlight-reaction "Glitter!"]])

;; Use into to add elements to a vector
(into ["cherry"] '("pine" "spruce"))

;;Comparing functionality of conj and into
;; conj takes rest parameter, into takes seq-able data structure
(conj [1] [0])
(into [1] [0])
(conj [1] 0)
(conj [0] 1 2 3 4)
(conj {:time "midnight"} [:place "ye olde cemetarium"])

(defn my-conj
  [target & additions]
  (into target additions))

(my-conj [0] 1 2 3)

;; Using apply
;; Apply explodes a seq-able data structure into a rest parameter
(max 0 1 2)
(max [0 1 2])

(apply max [0 1 2])

;; Using apply to define into in terms of conj
(defn my-into
  [target additions]
  (apply conj target additions))

(my-into [0] [1 2 3])

;; Partial takes a func and arguments returns a new func
(def add10 (partial + 10))
(add10 3)
(add10 5)

(def add-missing-elements
  (partial conj ["water" "earth" "air"]))

(add-missing-elements "unobtainium" "adamantium")

(defn my-partial
  [partialed-fn & args]
  (fn [& more-args]
    (apply partialed-fn (into args more-args))))

(def add20 (my-partial + 20))
(add20 3)

;; Use partial to specialise a logging function
(defn lousy-logger
  [log-level message]
  (condp = log-level
    :warn (clojure.string/lower-case message)
    :emergency (clojure.string/upper-case message)))

(def warn (partial lousy-logger :warn))

(warn "Red light ahead")

;; A function to find all humans
(defn identify-humans-test
  [social-security-numbers]
  (filter #(not (vampire? %))
          (map vampire-related-details social-security-numbers)))

;; Using complement
(def not-vampire? (complement vampire?))
(defn identify-humans
  [social-security-numbers]
  (filter not-vampire?
          (map vampire-related-details social-security-numbers)))

;; Implementing complement + example
(defn my-complement
  [fun]
  (fn [& args]
    (not (apply fun args))))

(def my-pos? (complement neg?))

(my-pos? 1)
(my-pos? -1)
