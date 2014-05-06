(ns user
  (:require #_ (wmrd-becoming-post [core :as c])
            (me.raynes [fs :as fs]
                       [conch :as sh])))

;; --- Testing.

(.getFile (clojure.java.io/resource "genavi.sh"))

(sh/programs bash pwd ls)

(pwd)

(ls "-l" {:seq true})

;; source = _self.screenshotRoot
(def SCREENSHOT-ROOT "/media/marc/proximity/bl/screenshots/")

;; sources = glob(source+"/run_*")
;; sources.sort()
(def sources
  (sort
   (map #(.toString %)
        (fs/glob (str SCREENSHOT-ROOT "run_*")))))

;; biggest = 0
;; biggestAt = ""
;; for n in range(len(sources)-1, len(sources)-10, -1):
;; 	shots = glob(source+"/run_%08i/shot_*" % n)
;; 	for q in shots:
;; 		iters = glob(q+"/iter*")
;; 		if (len(iters)>biggest):
;; 			biggest = len(iters)
;; 			biggestAt = q
;; 			if (biggest>30):
;; 				break




;; Entire run/shot/iteration tree:
(def runs
  (for [n (range (dec (count sources))
                 (- (count sources) 10)
                 -1)]
    (let [shots (fs/glob (str SCREENSHOT-ROOT (format "run_%08d/shot_*" n)))]
      {:run n
       :shots (for [q shots]
                {:shot q
                 :iters
                 (fs/glob (str q "/iter*"))})})))

;; Find combo with most iterations, but be satisfied with 30:

(defn find-best-shot [shots
                      {:keys [best-shot most-iters]
                       :as result}]

  (if-let [[{:keys [shot iters]} & rest] (seq shots)]
    (let [c (count iters)]
      (if (> c most-iters)
        (if (> c 30)
          {:most-iters c
           :best-shot shot}
          (find-best-shot rest {:most-iters c
                                :best-shot shot}))
        (find-best-shot rest result)))
    result))

(defn find-best-shot-from-runs [runs
                                {:keys [best-shot most-iters]
                                 :as result}]
  (if-let [[{:keys [run shots]} & rest] (seq runs)]
    (recur rest
           (find-best-shot shots result))
    result))

(def b
  (find-best-shot-from-runs runs {:best-shot nil :most-iters -1}))

(:best-shot b)

(.mkdirs
 (java.io.File. (:best-shot b) "bundle"))

(str (:best-shot b) "/iteration_*/full_a.jpg")

;; Glob busted!
(fs/glob (str (:best-shot b) "/iteration_*/full_a.jpg"))






;;---

(sh/with-programs [ls]
  (ls {:seq true :verbose true}))

(sh/let-programs
 [produce-seq (.getFile (clojure.java.io/resource "produceSeq.sh"))
  genavi (.getFile (clojure.java.io/resource "genavi.sh"))]


 )

(fs/expand-home "~/Desktop")

(fs/glob "README*")

(binding [fs/*cwd* (fs/expand-home "~/Desktop")]
  (fs/glob "*.mov"))

;; --- Testing findrun

(let [screenshot-root (fs/expand-home "~/Desktop/screenshotRoot")]
  )
