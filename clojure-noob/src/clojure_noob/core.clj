(ns clojure-noob.core
  (:gen-class))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))

(defn train
  []
  (println "Choo choo!"))
(defn error-message
  [severity]
  (str "OH GOD! DISASTER! WE'RE "
       (if (= severity :mild)
         "MILDLY INCONVENIENCED"
         "DOOOOOMED!")))
(defn too-enthusiastic
  "Return an overenthusiastic cheer"
  [name]
  (str "OMG! " name " I LOVE YOU SO MUCH!"))

(defn x-chop
  "Arity overloading demo"
  ([name chop-type]
   (str "I " chop-type " chop " name "! Take that!"))
  ([name]
   (x-chop name "karate")))

(defn favourite-things
  [name & things]
  (str "Hi, " name ", here are my favourite things: "
       (clojure.string/join ", " things)))

;; Return the first element of a collection
(defn my-first
  [[first-thing]] ; First thing is within a vector
  first-thing)

;; Rest parameters and destructuring
(defn chooser
  [[first-choice second-choice & unimportant-choices]]
  (println (str "Your first choice is: " first-choice))
  (println (str "Your second choice is: " second-choice))
  (println (str "We're ignoring the others. "
                "Here they are in case you want to cry: "
                (clojure.string/join ", " unimportant-choices))))

(defn announce-treasure-location
  [{lat :lat lng :lng}]
  (println (str "Treasure lat: " lat))
  (println (str "Treasure lng: " lng)))

(defn treasure-announce2
  [{:keys [lat lng]}]
  (println (str "Treasure lat: " lat))
  (println (str "Treasure lng: " lng)))

(defn receive-location
  [{:keys [lat lng] :as treasure-location}]
  (println (str "Treasure lat: " lat))
  (println (str "Treasure lng: " lng)))

(defn number-comment
  [x]
  (if (> x 6)
    "OMG! What a big number!"
    "Tiny, like Trump's hands"))

(defn inc-maker
  "Create a custom incrementor"
  [inc-by]
  #(+ % inc-by))

(def inc3 (inc-maker 3))

