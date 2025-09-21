(ns clojure-java-interop.core
  (:require [clojure.java.javadoc :refer [javadoc]])
  (:import [java.nio.file  Paths]
           [java.time      LocalDate]
           [java.util      Date Map$Entry UUID]
           [java.awt.image BufferedImage]))

(comment
  (javadoc BufferedImage)
  (javadoc Paths)
  (javadoc Date)
  (javadoc LocalDate)
  (javadoc UUID))

;; https://clojure.org/reference/java_interop

;; 1. Importing Java Classes
(def d (Date.))             ; #inst "2025-09-21T17:09:39.988-00:00"
(UUID/randomUUID)           ; #uuid "3339b684-7a56-4209-8f0c-11e7c417c92e"
(def today (LocalDate/now)) ; #object[java.time.LocalDate 0xd5c1f29 "2025-09-21"]

;; 2. Calling Instance Methods
(.toString d)                              ; "Sun Sep 21 19:09:39 CEST 2025"
(.getTime d)                               ; 1758474579988
(.isAfter today (LocalDate/of 2024 12 31)) ; true

;; 3. Calling Static Methods
(comment
  (javadoc Math)
  (javadoc System))

(Math/sqrt 16)                        ; 4.0
(System/getProperty "os.name")        ; "Linux"
(System/getProperty "os.arch")        ; "amd64"
(System/getProperty "os.version")     ; "6.16.8-arch1-1"
(System/getProperty "java.io.tmpdir") ; "/tmp"

;; 4. Accessing Fields
(.length "hello") ; 5
Integer/MAX_VALUE ; 2147483647
Math/PI           ; 3.141592653589793

;; 5. Chaining Calls
(.. (LocalDate/now) ; "2025-09-26"
    (plusDays 5)
    (toString))

;; the Clojure way with threading macros
(-> (LocalDate/now) ; "2025-09-26"
    (.plusDays 5)
    (.toString))

;; 6. Arrays
(def arr (make-array String 3)) ; [nil, nil, nil]
(aset arr 0 "A") ; "A"
(aset arr 1 "B") ; "B"
(aset arr 2 "C") ; "C"
(alength arr)    ; 3
(aget arr 1)     ; "B"
arr              ; ["A", "B", "C"]

;; 7. Implementing Interfaces
(comment
  (javadoc Callable))

(def c (reify Callable
         (call [_this] "Hello from Clojure")))

(.call c) ; "Hello from Clojure"

;; Type Hints

(set! *warn-on-reflection* true)  ; true
(set! *warn-on-reflection* false)

;; Performance
(defn len1 [x]
  (.length x))

(defn len2 [^String x]
  (.length x))

(comment
  (time (reduce + (map len1 (repeat 1000000 "asdf"))))  ; 4000000
  ; (out) "Elapsed time: 5806.271653 msecs"
  (time (reduce + (map len2 (repeat 1000000 "asdf"))))) ; 4000000
  ; (out) "Elapsed time:  260.84504 msecs"

;; help with resolving methods
;; `charAt` is implemented in several Java classes
(defn foo [^String s] (.charAt s 1))

(foo "hello") ; \e

;; Java varargs

(comment
  (javadoc Paths)

  ;; variadic parameter list
  ;; get(String first, String… more)
  (Paths/get "home" ["user" "docs" "file.txt"]))   ; (err) class clojure.lang.PersistentVector cannot be cast to class

(Paths/get "home" (into-array String ["user" "docs" "file.txt"]))
; #object[sun.nio.fs.UnixPath 0x30f98524 "home/user/docs/file.txt"]

;; Inner classes
(comment
  (javadoc Map$Entry))

;; in Java: Map.Entry
Map$Entry ; java.util.Map$Entry
