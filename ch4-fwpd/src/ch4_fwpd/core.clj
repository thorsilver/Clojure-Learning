(ns ch4-fwpd.core
  (:gen-class))
(def filename "suspects.csv")

(def vamp-keys [:name :glitter-index])

(defn str->int
  [str]
  (Integer. str))

(def conversions {:name identity :glitter-index str->int})

(defn convert
  [vamp-key value]
  ((get conversions vamp-key) value))

(defn parse
  "Convert a CSV into rows of columns"
  [string]
  (map #(clojure.string/split % #",")
       (clojure.string/split string #"\n")))

(defn mapify
  "Return a seq of maps"
  [rows]
  (map (fn [unmapped-row]
         (reduce (fn [row-map [vamp-key value]]
                   (assoc row-map vamp-key (convert vamp-key value)))
                 {}
                 (map vector vamp-keys unmapped-row)))
       rows))

(defn glitter-filter
  [minimum-glitter records]
  (filter #(>= (:glitter-index %) minimum-glitter) records))

(glitter-filter 3 (mapify (parse (slurp filename))))

;; Exercise 1: Get list of names from filter
(defn name-extract
  [records]
  (map :name records))

(def initial-suspects (mapify (parse (slurp filename))))

(glitter-filter 3 initial-suspects)
(name-extract (glitter-filter 3 initial-suspects))

;; Exercise 3: validate potential new entry
(defn validate
  "Check for :name and :glitter-index"
  [entry]
  (and (contains? entry :name) (contains? entry :glitter-index)))

(validate {:name "Nosferatu", :glitter-index 7})

;; Exercise 2: append new entry
(defn add-suspect
  [current-suspects new-suspect]
  (if (validate new-suspect)
    (conj current-suspects new-suspect)
    current-suspects))

(add-suspect initial-suspects {:name "Nosferatu", :glitter-index 7})

;; Exercise 4: convert list of maps to csv
(def select-values (comp vals select-keys))

(defn suspects->csv
  "Convert list of suspects to a CSV"
  [suspects]
  (def suspect-values (map #(select-values % [:name, :glitter-index])
                           suspects))
  (reduce (fn [input-str values]
            (str input-str (clojure.string/join "," values) "\n"))
          ""
          suspect-values))
  
 (println (suspects->csv (add-suspect initial-suspects {:name "Nosferatu", :glitter-index 7})))
