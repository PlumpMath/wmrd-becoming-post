(ns user
  (:require (wmrd-becoming-post [core :as c])
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

(defn biggest-shot [best best-shot runs]
  (when-let [[{:keys [run shots]} & rest] (seq runs)]
    ;; iterate over shots!
    (let [{:keys [shot iters]} shots]
      (cond (> best 30)
            best-shot
            (> (count iters) best)
            (biggest-shot (count iters) shot rest)
            :else
            (biggest-shot best best-shot rest)))
    best-shot))

;;(biggest-shot 0 nil runs)
(:shots (first runs))




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
